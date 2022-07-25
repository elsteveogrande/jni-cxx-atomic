package cc.obrien.atomic;

import java.nio.Buffer;

public final class BufferAtomic32 extends BufferAtomic {

    public BufferAtomic32(Buffer buffer, long byteOffset) { super(buffer, byteOffset, 4); }
    public BufferAtomic32(Buffer buffer) { this(buffer, 0); }

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
     * Compare-and-set: if current value is {@literal expected}, then set the new value to {@literal val}
     * and return {@literal true}; otherwise, take no action and return {@literal false}.
     *
     * This operation uses {@literal compare_exchange_strong} with {@literal acq_rel} memory ordering.
     */
    public boolean compareAndSet(int expected, int val) { return compareAndSet32(addr, expected, val); }

}
