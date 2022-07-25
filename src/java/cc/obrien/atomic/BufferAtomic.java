package cc.obrien.atomic;

import java.nio.Buffer;

public sealed abstract class BufferAtomic extends CXXAtomicAPI permits BufferAtomic32, BufferAtomic64 {
    /**
     * The {@link Buffer} with which this was created.
     * This buffer is not actually used at any point after construction;
     * this is merely to hold a strong reference to it to avoid its getting
     * garbage-collected while this atomic is possibly still in use.
     */
    protected final Buffer buffer;

    /** The actual location in memory of the {@link #buffer}. */
    protected final long addr;

    protected BufferAtomic(Buffer buffer, long byteOffset, long requiredAlignment) {
        this.buffer = buffer;
        this.addr = getBufferAddress(buffer) + byteOffset;
        if (buffer.isReadOnly()) {
            throw new IllegalArgumentException("buffer is read-only");
        }
        if ((addr % requiredAlignment) != 0) {
            throw new IllegalArgumentException(
                String.format(
                    "invalid address %d (0x%016x); required alignment is %d",
                    addr, addr, requiredAlignment));
        }
    }
}
