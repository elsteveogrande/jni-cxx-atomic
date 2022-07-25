package cc.obrien.atomic;

public final class CXXAtomic64 extends CXXAtomicAPI {

    final long addr;

    public CXXAtomic64(long addr) {
        assert ((addr & 0x07) == 0);
        this.addr = addr;
    }

    public long load() { return load64(addr); }
    public void store(long val) { store64(addr, val); }
    public long fetchAdd(long val) { return fetchAdd64(addr, val); }

}
