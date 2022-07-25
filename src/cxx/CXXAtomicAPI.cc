#include <atomic>
#include <cassert>
#include "jni.h"

#include "cc_obrien_atomic_CXXAtomicAPI.h"

static_assert(sizeof(jlong) == sizeof(intptr_t));

static_assert(sizeof(int32_t) == sizeof(jint));
static_assert(sizeof(std::atomic<int32_t>) == sizeof(int32_t));
static_assert(std::atomic<int32_t>::is_always_lock_free);

static_assert(sizeof(int64_t) == sizeof(jlong));
static_assert(sizeof(std::atomic<int64_t>) == sizeof(int64_t));
static_assert(std::atomic<int64_t>::is_always_lock_free);

extern "C" {

jlong Java_cc_obrien_atomic_CXXAtomicAPI_getBufferAddress(JNIEnv *jenv, jclass, jobject buffer) {
    auto* ptr = jenv->GetDirectBufferAddress(buffer);
    return (jlong) (intptr_t) ptr;
}

jint Java_cc_obrien_atomic_CXXAtomicAPI_load32(JNIEnv *, jclass, jlong addr) {
    auto* a = reinterpret_cast<std::atomic<int32_t>*>(addr);
    return a->load(std::memory_order_acquire);
}

void Java_cc_obrien_atomic_CXXAtomicAPI_store32(JNIEnv *, jclass, jlong addr, jint val) {
    auto* a = reinterpret_cast<std::atomic<int32_t>*>(addr);
    a->store(val, std::memory_order_release);
}

jint Java_cc_obrien_atomic_CXXAtomicAPI_exchange32(JNIEnv *, jclass, jlong addr, jint val) {
    auto* a = reinterpret_cast<std::atomic<int32_t>*>(addr);
    return a->exchange(val, std::memory_order_acq_rel);
}

jint Java_cc_obrien_atomic_CXXAtomicAPI_fetchAdd32(JNIEnv *, jclass, jlong addr, jint val) {
    auto* a = reinterpret_cast<std::atomic<int32_t>*>(addr);
    return a->fetch_add(val, std::memory_order_acq_rel);
}

jint Java_cc_obrien_atomic_CXXAtomicAPI_fetchAnd32(JNIEnv *, jclass, jlong addr, jint val) {
    auto* a = reinterpret_cast<std::atomic<int32_t>*>(addr);
    return a->fetch_and(val, std::memory_order_acq_rel);
}

jint Java_cc_obrien_atomic_CXXAtomicAPI_fetchOr32(JNIEnv *, jclass, jlong addr, jint val) {
    auto* a = reinterpret_cast<std::atomic<int32_t>*>(addr);
    return a->fetch_or(val, std::memory_order_acq_rel);
}

jint Java_cc_obrien_atomic_CXXAtomicAPI_fetchXor32(JNIEnv *, jclass, jlong addr, jint val) {
    auto* a = reinterpret_cast<std::atomic<int32_t>*>(addr);
    return a->fetch_xor(val, std::memory_order_acq_rel);
}

jboolean Java_cc_obrien_atomic_CXXAtomicAPI_compareAndSet32(JNIEnv *, jclass, jlong addr, jint expected, jint val) {
    auto* a = reinterpret_cast<std::atomic<int32_t>*>(addr);
    return a->compare_exchange_strong(expected, val, std::memory_order_acq_rel);
}

jlong Java_cc_obrien_atomic_CXXAtomicAPI_load64(JNIEnv *, jclass, jlong addr) {
    auto* a = reinterpret_cast<std::atomic<int64_t>*>(addr);
    return a->load(std::memory_order_acquire);
}

void Java_cc_obrien_atomic_CXXAtomicAPI_store64(JNIEnv *, jclass, jlong addr, jlong val) {
    auto* a = reinterpret_cast<std::atomic<int64_t>*>(addr);
    a->store(val, std::memory_order_release);
}

jlong Java_cc_obrien_atomic_CXXAtomicAPI_exchange64(JNIEnv *, jclass, jlong addr, jlong val) {
    auto* a = reinterpret_cast<std::atomic<int64_t>*>(addr);
    return a->exchange(val, std::memory_order_acq_rel);
}

jlong Java_cc_obrien_atomic_CXXAtomicAPI_fetchAdd64(JNIEnv *, jclass, jlong addr, jlong val) {
    auto* a = reinterpret_cast<std::atomic<int64_t>*>(addr);
    return a->fetch_add(val, std::memory_order_acq_rel);
}

jlong Java_cc_obrien_atomic_CXXAtomicAPI_fetchAnd64(JNIEnv *, jclass, jlong addr, jlong val) {
    auto* a = reinterpret_cast<std::atomic<int64_t>*>(addr);
    return a->fetch_and(val, std::memory_order_acq_rel);
}

jlong Java_cc_obrien_atomic_CXXAtomicAPI_fetchOr64(JNIEnv *, jclass, jlong addr, jlong val) {
    auto* a = reinterpret_cast<std::atomic<int64_t>*>(addr);
    return a->fetch_or(val, std::memory_order_acq_rel);
}

jlong Java_cc_obrien_atomic_CXXAtomicAPI_fetchXor64(JNIEnv *, jclass, jlong addr, jlong val) {
    auto* a = reinterpret_cast<std::atomic<int64_t>*>(addr);
    return a->fetch_xor(val, std::memory_order_acq_rel);
}

jboolean Java_cc_obrien_atomic_CXXAtomicAPI_compareAndSet64(JNIEnv *, jclass, jlong addr, jlong expected_, jlong val) {
    auto expected = (int64_t) expected_;
    auto* a = reinterpret_cast<std::atomic<int64_t>*>(addr);
    return a->compare_exchange_strong(expected, val, std::memory_order_acq_rel);
}

}
