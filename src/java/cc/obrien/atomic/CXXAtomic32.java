package cc.obrien.atomic;

public final class CXXAtomic32 extends CXXAtomicAPI {

    final long addr;

    public CXXAtomic32(long addr) {
        if ((addr & 0x03) != 0) {
            throw new IllegalArgumentException(
                String.format(
                    "invalid address %d (0x%016x); must be 32-bit aligned (i.e. divisible by 4)",
                    addr, addr));
        }
        this.addr = addr;
    }

    /**
     * Load this atomic's {@literal int} value.  Uses {@literal release} memory ordering.
     *
     */
    public int load() { return load32(addr); }
    public void store(int val) { store32(addr, val); }
    public int exchange(int val) { return exchange32(addr, val); }
    public int fetchAdd(int val) { return fetchAdd32(addr, val); }
    public int fetchAnd(int val) { return fetchAnd32(addr, val); }
    public int fetchOr(int val) { return fetchOr32(addr, val); }
    public int fetchXor(int val) { return fetchXor32(addr, val); }
    public boolean compareAndSet(int expected, int val) { return compareAndSet32(addr, expected, val); }

}
