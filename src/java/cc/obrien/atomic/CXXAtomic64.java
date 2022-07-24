package cc.obrien.atomic;

class CXXAtomic64 extends CXXAtomicAPI {

    final long addr;
    public CXXAtomic64(long addr) {
        assert ((addr & ~0x07) == 0);
        this.addr = addr;
    }

    public final long load() { return load64(addr); }
    public final void store(long val) { store64(addr, val); }

}
