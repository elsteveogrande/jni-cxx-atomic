package cc.obrien.atomic;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.util.Objects;

sealed abstract class CXXAtomicAPI permits CXXAtomic32, CXXAtomic64 {

    static {
        /*
         * Here we try to load our native library, packaged inside this jar.
         * (Alternatively we might not be running using the packaged jar, but
         * instead in the IDE which is not running this coda via the jar;
         * in that case it'll find the "libCXXAtomicAPI.jnilib" file in this
         * project's "resources" dir, actually a symlink to the built jar).
         */
        try {
            final var cl = CXXAtomicAPI.class.getClassLoader();
            // Once per JVM / classloader's static initialization of this class,
            // we'll quickly extract the library into a temp directory
            // (which will be deleted at JVM exit).  Should be low-overhead...
            final var tmpDir = Files.createTempDirectory("cxxatomicapi");
            final var jnilib = tmpDir.resolve("libCXXAtomicAPI.jnilib");
            Files.copy(Objects.requireNonNull(cl.getResourceAsStream("libCXXAtomicAPI.jnilib")), jnilib);
            System.load(jnilib.toString());
        } catch (IOException e) {
            // If anything went wrong above, co-op Java's usual error
            // it throws when it fails to load native libraries.
            throw new UnsatisfiedLinkError(e.getMessage());
        }
    }

    /**
     * Given a {@link Buffer}, get the base address of it.
     * The only way to do it reliably / portably / properly is to do it via
     * functions provided by JNI.
     */
    protected static native long getBufferAddress(Buffer buf);

    // All the other, atomic-related, JNI-exported functions in CXXAtomicAPI.cc.

    protected static native int load32(long addr);
    protected static native void store32(long addr, int val);
    protected static native int exchange32(long addr, int val);
    protected static native int fetchAdd32(long addr, int val);
    protected static native int fetchAnd32(long addr, int val);
    protected static native int fetchOr32(long addr, int val);
    protected static native int fetchXor32(long addr, int val);
    protected static native boolean compareAndSet32(long addr, int expected, int val);

    protected static native long load64(long addr);
    protected static native void store64(long addr, long val);
    protected static native long exchange64(long addr, long val);
    protected static native long fetchAdd64(long addr, long val);
    protected static native long fetchAnd64(long addr, long val);
    protected static native long fetchOr64(long addr, long val);
    protected static native long fetchXor64(long addr, long val);
    protected static native boolean compareAndSet64(long addr, long expected, long val);

}
