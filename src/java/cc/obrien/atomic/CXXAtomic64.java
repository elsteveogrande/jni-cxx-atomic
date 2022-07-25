package cc.obrien.atomic;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

public final class CXXAtomic64 extends CXXAtomicAPI {

    final long addr;

    private CXXAtomic64(long addr) {
        if ((addr & 0x07) != 0) {
            throw new IllegalArgumentException(
                String.format(
                    "invalid address %d (0x%016x); must be 64-bit aligned (i.e. divisible by 8)",
                    addr, addr));
        }
        this.addr = addr;
    }

    public CXXAtomic64(ByteBuffer bb, long offset) { this(getBufferAddress(bb) + offset); }
    public CXXAtomic64(ByteBuffer bb) { this(bb, 0); }

    public CXXAtomic64(LongBuffer lb, long longIndex) { this(getBufferAddress(lb) + longIndex * 8); }
    public CXXAtomic64(LongBuffer lb) { this(lb, 0); }

    /** Load this atomic's value.  Uses {@literal acquire} memory ordering. */
    public long load() { return load64(addr); }

    /** Store a new value into the atomic.  Uses {@literal release} memory ordering. */
    public void store(int val) { store64(addr, val); }

    /** Exchange; store a new value and retrieve the old value.  Uses {@literal acq_rel} memory ordering. */
    public long exchange(int val) { return exchange64(addr, val); }

    /** Add a value to the atomic; return the old value.  Uses {@literal acq_rel} memory ordering. */
    public long fetchAdd(int val) { return fetchAdd64(addr, val); }

    /** AND a value to the atomic; return the old value.  Uses {@literal acq_rel} memory ordering. */
    public long fetchAnd(int val) { return fetchAnd64(addr, val); }

    /** OR a value to the atomic; return the old value.  Uses {@literal acq_rel} memory ordering. */
    public long fetchOr(int val) { return fetchOr64(addr, val); }

    /** XOR a value to the atomic; return the old value.  Uses {@literal acq_rel} memory ordering. */
    public long fetchXor(int val) { return fetchXor64(addr, val); }

    /**
     * Compare-and-set: if atomic value is {@literal expected}, then set the new value to {@literal val}
     * and return {@literal true}; otherwise, take no action and return {@literal false}.
     *
     * This operation uses {@literal compare_exchange_strong} with {@literal acq_rel} memory ordering.
     */
    public boolean compareAndSet(long expected, long val) { return compareAndSet64(addr, expected, val); }

}
