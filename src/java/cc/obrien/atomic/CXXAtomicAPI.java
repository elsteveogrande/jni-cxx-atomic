package cc.obrien.atomic;

abstract class CXXAtomicAPI {

    static { System.loadLibrary("CXXAtomicAPI"); }

    static native int load32(long addr);
    static native void store32(long addr, int val);
    static native int exchange32(long addr, int val);
    static native int fetchAdd32(long addr, int val);
    static native int fetchAnd32(long addr, int val);
    static native int fetchOr32(long addr, int val);
    static native int fetchXor32(long addr, int val);
    static native boolean compareAndSet32(long addr, int expected, int val);

    static native long load64(long addr);
    static native void store64(long addr, long val);
    static native long exchange64(long addr, long val);
    static native long fetchAdd64(long addr, long val);
    static native long fetchAnd64(long addr, long val);
    static native long fetchOr64(long addr, long val);
    static native long fetchXor64(long addr, long val);
    static native boolean compareAndSet64(long addr, long expected, long val);

}
