#include <atomic>
#include <cassert>
#include "jni.h"

static_assert(sizeof(jlong) == sizeof(intptr_t));

static_assert(sizeof(int32_t) == sizeof(jint));
static_assert(sizeof(std::atomic<int32_t>) == sizeof(int32_t));
static_assert(std::atomic<int32_t>::is_always_lock_free);

static_assert(sizeof(int64_t) == sizeof(jlong));
static_assert(sizeof(std::atomic<int64_t>) == sizeof(int64_t));
static_assert(std::atomic<int64_t>::is_always_lock_free);

extern "C" {

JNIEXPORT jint JNICALL Atomic32_load(JNIEnv *, jlong addr);
JNIEXPORT void JNICALL Atomic32_store(JNIEnv *, jlong addr, jint val);
JNIEXPORT jint JNICALL Atomic32_exchange(JNIEnv *, jlong addr, jint val);
JNIEXPORT jint JNICALL Atomic32_fetchAdd(JNIEnv *, jlong addr, jint val);
JNIEXPORT jint JNICALL Atomic32_fetchAnd(JNIEnv *, jlong addr, jint val);
JNIEXPORT jint JNICALL Atomic32_fetchOr(JNIEnv *, jlong addr, jint val);
JNIEXPORT jint JNICALL Atomic32_fetchXor(JNIEnv *, jlong addr, jint val);
JNIEXPORT jboolean JNICALL Atomic32_compareAndSet(JNIEnv *, jlong addr, jint expected, jint val);

JNIEXPORT jlong JNICALL Atomic64_load(JNIEnv *, jlong addr);
JNIEXPORT void JNICALL Atomic64_store(JNIEnv *, jlong addr, jlong val);
JNIEXPORT jlong JNICALL Atomic64_exchange(JNIEnv *, jlong addr, jlong val);
JNIEXPORT jlong JNICALL Atomic64_fetchAdd(JNIEnv *, jlong addr, jlong val);
JNIEXPORT jlong JNICALL Atomic64_fetchAnd(JNIEnv *, jlong addr, jlong val);
JNIEXPORT jlong JNICALL Atomic64_fetchOr(JNIEnv *, jlong addr, jlong val);
JNIEXPORT jlong JNICALL Atomic64_fetchXor(JNIEnv *, jlong addr, jlong val);
JNIEXPORT jboolean JNICALL Atomic64_compareAndSet(JNIEnv *, jlong addr, jlong expected_, jlong val);

jint Atomic32_load(JNIEnv *, jlong addr) {
    auto* a = reinterpret_cast<std::atomic<int32_t>*>(addr);
    return a->load(std::memory_order_acquire);
}

void Atomic32_store(JNIEnv *, jlong addr, jint val) {
    auto* a = reinterpret_cast<std::atomic<int32_t>*>(addr);
    a->store(val, std::memory_order_release);
}

jint Atomic32_exchange(JNIEnv *, jlong addr, jint val) {
    auto* a = reinterpret_cast<std::atomic<int32_t>*>(addr);
    return a->exchange(val, std::memory_order_acq_rel);
}

jint Atomic32_fetchAdd(JNIEnv *, jlong addr, jint val) {
    auto* a = reinterpret_cast<std::atomic<int32_t>*>(addr);
    return a->fetch_add(val, std::memory_order_acq_rel);
}

jint Atomic32_fetchAnd(JNIEnv *, jlong addr, jint val) {
    auto* a = reinterpret_cast<std::atomic<int32_t>*>(addr);
    return a->fetch_and(val, std::memory_order_acq_rel);
}

jint Atomic32_fetchOr(JNIEnv *, jlong addr, jint val) {
    auto* a = reinterpret_cast<std::atomic<int32_t>*>(addr);
    return a->fetch_or(val, std::memory_order_acq_rel);
}

jint Atomic32_fetchXor(JNIEnv *, jlong addr, jint val) {
    auto* a = reinterpret_cast<std::atomic<int32_t>*>(addr);
    return a->fetch_xor(val, std::memory_order_acq_rel);
}

jboolean Atomic32_compareAndSet(JNIEnv *, jlong addr, jint expected, jint val) {
    auto* a = reinterpret_cast<std::atomic<int32_t>*>(addr);
    return a->compare_exchange_strong(expected, val, std::memory_order_acq_rel);
}

jlong Atomic64_load(JNIEnv *, jlong addr) {
    auto* a = reinterpret_cast<std::atomic<int64_t>*>(addr);
    return a->load(std::memory_order_acquire);
}

void Atomic64_store(JNIEnv *, jlong addr, jlong val) {
    auto* a = reinterpret_cast<std::atomic<int64_t>*>(addr);
    a->store(val, std::memory_order_release);
}

jlong Atomic64_exchange(JNIEnv *, jlong addr, jlong val) {
    auto* a = reinterpret_cast<std::atomic<int64_t>*>(addr);
    return a->exchange(val, std::memory_order_acq_rel);
}

jlong Atomic64_fetchAdd(JNIEnv *, jlong addr, jlong val) {
    auto* a = reinterpret_cast<std::atomic<int64_t>*>(addr);
    return a->fetch_add(val, std::memory_order_acq_rel);
}

jlong Atomic64_fetchAnd(JNIEnv *, jlong addr, jlong val) {
    auto* a = reinterpret_cast<std::atomic<int64_t>*>(addr);
    return a->fetch_and(val, std::memory_order_acq_rel);
}

jlong Atomic64_fetchOr(JNIEnv *, jlong addr, jlong val) {
    auto* a = reinterpret_cast<std::atomic<int64_t>*>(addr);
    return a->fetch_or(val, std::memory_order_acq_rel);
}

jlong Atomic64_fetchXor(JNIEnv *, jlong addr, jlong val) {
    auto* a = reinterpret_cast<std::atomic<int64_t>*>(addr);
    return a->fetch_xor(val, std::memory_order_acq_rel);
}

jboolean Atomic64_compareAndSet(JNIEnv *, jlong addr, jlong expected_, jlong val) {
    auto* a = reinterpret_cast<std::atomic<int64_t>*>(addr);
    int64_t expected = (int64_t) expected_;
    return a->compare_exchange_strong(expected, val, std::memory_order_acq_rel);
}

}
