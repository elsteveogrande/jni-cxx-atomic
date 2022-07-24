package cc.obrien.atomic;

class CXXAtomic32 extends CXXAtomicAPI {

    final long addr;
    public CXXAtomic32(long addr) {
        assert ((addr & ~0x03) == 0);
        this.addr = addr;
    }

    public final int load() { return load32(addr); }
    public final void store(int val) { store32(addr, val); }

}
