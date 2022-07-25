package cc.obrien.atomic;

import java.nio.Buffer;

public final class BufferAtomic64 extends BufferAtomic {

    public BufferAtomic64(Buffer buffer, long byteOffset) { super(buffer, byteOffset, 8); }
    public BufferAtomic64(Buffer buffer) { this(buffer, 0); }

    /** Load this atomic's value.  Uses {@literal acquire} memory ordering. */
    public long load() { return load64(addr); }

    /** Store a new value into the atomic.  Uses {@literal release} memory ordering. */
    public void store(long val) { store64(addr, val); }

    /** Exchange; store a new value and retrieve the old value.  Uses {@literal acq_rel} memory ordering. */
    public long exchange(long val) { return exchange64(addr, val); }

    /** Add a value to the atomic; return the old value.  Uses {@literal acq_rel} memory ordering. */
    public long fetchAdd(long val) { return fetchAdd64(addr, val); }

    /** AND a value to the atomic; return the old value.  Uses {@literal acq_rel} memory ordering. */
    public long fetchAnd(long val) { return fetchAnd64(addr, val); }

    /** OR a value to the atomic; return the old value.  Uses {@literal acq_rel} memory ordering. */
    public long fetchOr(long val) { return fetchOr64(addr, val); }

    /** XOR a value to the atomic; return the old value.  Uses {@literal acq_rel} memory ordering. */
    public long fetchXor(long val) { return fetchXor64(addr, val); }

    /**
     * Compare-and-set: if current value is {@literal expected}, then set the new value to {@literal val}
     * and return {@literal true}; otherwise, take no action and return {@literal false}.
     *
     * This operation uses {@literal compare_exchange_strong} with {@literal acq_rel} memory ordering.
     */
    public boolean compareAndSet(long expected, long val) { return compareAndSet64(addr, expected, val); }

}
