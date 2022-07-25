package cc.obrien.atomic;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public final class CXXAtomic32 extends CXXAtomicAPI {

    final long addr;

    private CXXAtomic32(long addr) {
        if ((addr & 0x03) != 0) {
            throw new IllegalArgumentException(
                String.format(
                    "invalid address %d (0x%016x); must be 32-bit aligned (i.e. divisible by 4)",
                    addr, addr));
        }
        this.addr = addr;
    }

    public CXXAtomic32(ByteBuffer bb, long offset) { this(getBufferAddress(bb) + offset); }
    public CXXAtomic32(ByteBuffer bb) { this(bb, 0); }

    public CXXAtomic32(IntBuffer ib, long intIndex) { this(getBufferAddress(ib) + intIndex * 4); }
    public CXXAtomic32(IntBuffer ib) { this(ib, 0); }

    /** Load this atomic's value.  Uses {@literal acquire} memory ordering. */
    public int load() { return load32(addr); }

    /** Store a new value into the atomic.  Uses {@literal release} memory ordering. */
    public void store(int val) { store32(addr, val); }

    /** Exchange; store a new value and retrieve the old value.  Uses {@literal acq_rel} memory ordering. */
    public int exchange(int val) { return exchange32(addr, val); }

    /** Add a value to the atomic; return the old value.  Uses {@literal acq_rel} memory ordering. */
    public int fetchAdd(int val) { return fetchAdd32(addr, val); }

    /** AND a value to the atomic; return the old value.  Uses {@literal acq_rel} memory ordering. */
    public int fetchAnd(int val) { return fetchAnd32(addr, val); }

    /** OR a value to the atomic; return the old value.  Uses {@literal acq_rel} memory ordering. */
    public int fetchOr(int val) { return fetchOr32(addr, val); }

    /** XOR a value to the atomic; return the old value.  Uses {@literal acq_rel} memory ordering. */
    public int fetchXor(int val) { return fetchXor32(addr, val); }

    /**
     * Compare-and-set: if atomic value is {@literal expected}, then set the new value to {@literal val}
     * and return {@literal true}; otherwise, take no action and return {@literal false}.
     *
     * This operation uses {@literal compare_exchange_strong} with {@literal acq_rel} memory ordering.
     */
    public boolean compareAndSet(int expected, int val) { return compareAndSet32(addr, expected, val); }

}
