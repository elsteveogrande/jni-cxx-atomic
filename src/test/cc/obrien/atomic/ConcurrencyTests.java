package cc.obrien.atomic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.nio.channels.FileChannel.MapMode.READ_WRITE;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;

public final class ConcurrencyTests {
    @Test public void lotsOfConcurrentIncrements() throws Exception {
        final var threadCount = 100; //Integer.parseInt(args[0]);
        final var threadIters = 100000; //Integer.parseInt(args[1]);

        // Initialize a small empty file, which we'll mmap as a read-write buffer.
        // (File is deleted when this JVM exits.)
        final var path = Files.createTempFile("CXXAtomicTests", "foo").toAbsolutePath();
        path.toFile().deleteOnExit();
        Files.write(path, new byte[8]);

        // mmap that file
        final var channel = (FileChannel) Files.newByteChannel(path, WRITE, READ);
        final var mmap = channel.map(READ_WRITE, 0, 8);
        final long addr = CXXAtomicAPI.getBufferAddress(mmap);

        // the start signal for the threads we're about to create
        final var start = new AtomicBoolean(false);

        // create a bunch of threads and get them ready
        final var threads = new ArrayList<Thread>(threadCount);
        for (int i = 0; i < threadCount; i++) {
            threads.add(new Thread(() ->
                ConcurrencyTests.threadFunc(addr, start, threadIters)));
        }

        // Start all the threads, give the OS enough time to get them all started,
        // and finally pull the trigger to get them all started roughly at once.
        threads.forEach(Thread::start);
        Thread.sleep(1500);
        start.set(true);

        // All threads are now started and should be running.
        // Wait for each thread sequentially.
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                // Set this thread's interrupt flag again; keep on trying to join.
                Thread.currentThread().interrupt();
            }
        });

        // All threads are done.  Check that the counter is consistent:
        Assertions.assertEquals(
            threadCount * threadIters,
            new CXXAtomic64(addr).load());
    }

    private static void threadFunc(long addr, AtomicBoolean start, int iters) {
        final var a = new CXXAtomic64(addr);

        //noinspection StatementWithEmptyBody
        while (!start.get()) {
            // Busy-wait until the main thread says go
        }

        for (int i = 0; i < iters; i++) { a.fetchAdd(1L); }
    }
}
