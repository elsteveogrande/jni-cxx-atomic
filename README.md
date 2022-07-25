# C++ Atomics for Java Buffers

## Overview

This is a JNI library for accessing C++-style
`std::atomic<int32_t>` and `std::atomic<int64_t>`
operations for Java `int`s and `long`s respectively.

## Why not simply `AtomicInteger` and `AtomicLong`?

In very particular, niche situations we might find
ourselves needing to access data outside the usual
JVM memory, such as a file-backed mmap buffer or
any other external / "off-heap" memory, for which
Java's atomics don't provide access.

While concepts like `DirectMappedBuffer` and others
provide off-heap memory access, they don't provide
for atomic loads, stores, adjustments,
compare-and-swap operations, and so on.

## Usage

```java
// create a small empty file
final var path = Path.of("/tmp/somefile");
Files.write(path, new byte[4]);

// mmap that file
final var channel = (FileChannel) Files.newByteChannel(path, WRITE, READ);
final var mmap = channel.map(READ_WRITE, 0, 4);

// get an atomic integer "inside" that file-backed mmap buffer
final var atomic = new CXXAtomic32(mmap);
```

One can run the above code, plus the following in one process:
```java
atomic.store(42);
```

and on another process on that same machine:
```java
assert (atomic.load() == 42);
```

This gives a new way for processes to "communicate".
While `mmap` is of course nothing new, we do now have
ways to atomically update shared memory in a lock-free,
concurrency-friendly way between threads and processes.

This all leverages C++'s atomics as described above,
as well as some OS (POSIX) concepts like file-backed
memory regions, allowing Java code to perform new
tricks.

## Supported types

* `CXXAtomic32` supported by `std::atomic<int32_t>`
* `CXXAtomic64` supported by `std::atomic<int64_t>`

And that's it; more `std::atomic<...>`'s could be
supported if there are valuable use cases for such things.

## Further reading

I searched quite a bit over the years but haven't
found a library similar to this, and also was never
quite sure if such a thing could work.

C++'s atomic memory model and POSIX systems have
provided these basic tools for ages, however, and
while the specs don't make it 100% clear that this
is _guaranteed_ to work, it _appears_ to work out
alright.

I'll direct you straight to some of the resources I've
stumbled upon while developing this.

### "Is C++11 atomic<T> usable with mmap?"
https://stackoverflow.com/questions/18321244/is-c11-atomict-usable-with-mmap

The answerer has done quite a bit of digging in to the matter
and upon their parsing of the relevant technical notes
(which I tend to agree with, though I'm not a C++ lawyer)
it seems that this ought to work.

In practice -- and by that I mean my informal testing on my
Intel, OSX laptop -- it indeed works as I would expect.
This may be due to the standards and specs outright trying
to deliver functionality, or might just be the underlying
implementation -- a Unix-like OS running on a modern CPU
with a proper memory-management (paging) unit -- just happens
to support what we want here.

### cppreference.com's docs on `atomic`

https://en.cppreference.com/w/cpp/atomic/atomic

The exact behavior, including according to different C++
language levels, are described here, so I'll drop that
link and direct the reader to those resources.

This project targets `-std=c++17` for C++, and Java 17.

## Future work

First and foremost: if there are any issues with this library,
including correctness, compatibility, lack of features you'd
like to see, or any other glaring problems (e.g. if you can
direct me to a spec that indicates this should not work),
do let me know or file an issue!

But anyway, future plans:

* support for more basic types.  A single-byte object such as a `bool` might be useful.  Doubtful that `char` or `short` would be.
* `float` and `double` atomics might be useful
* collections which are backed by a file and use concurrency-friendly primitives could be very useful.
  (This was my primary motivation by the way.)  A lock-free and persistable
  data collection could be built upon this.
  If I ever manage to make such a thing I'll let you know 😉
