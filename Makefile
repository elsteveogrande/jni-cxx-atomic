all: build/CXXAtomicAPI.jar

build/CXXAtomicAPI.jar: build/libCXXAtomicAPI.jnilib build/cc/obrien/atomic/CXXAtomicAPI.class build/cc/obrien/atomic/CXXAtomic32.class build/cc/obrien/atomic/CXXAtomic64.class
	rm -f build/CXXAtomicAPI.jar
	jar \
		-cf build/CXXAtomicAPI.jar \
		-C build libCXXAtomicAPI.jnilib \
		-C build cc/obrien/atomic/CXXAtomic32.class \
		-C build cc/obrien/atomic/CXXAtomic64.class \
		-C build cc/obrien/atomic/CXXAtomicAPI.class

build/cc/obrien/atomic/CXXAtomicAPI.class: src/java/cc/obrien/atomic/CXXAtomicAPI.java
	javac --source-path src/java --source 17 --target 17 -d build/ $<

build/cc/obrien/atomic/CXXAtomic32.class: src/java/cc/obrien/atomic/CXXAtomic32.java
	javac --source-path src/java --source 17 --target 17 -d build/ $<

build/cc/obrien/atomic/CXXAtomic64.class: src/java/cc/obrien/atomic/CXXAtomic64.java
	javac --source-path src/java --source 17 --target 17 -d build/ $<

build/libCXXAtomicAPI.jnilib: build/CXXAtomicAPI.o
	cc \
		-shared \
		-fPIC \
		-L $(JAVA_HOME)/lib \
		-o build/libCXXAtomicAPI.jnilib \
		$<

build/CXXAtomicAPI.o: build/CXXAtomicAPI.S
	cc \
		-g1 -gz -gsplit-dwarf \
		-c \
		-fPIC \
		-o build/CXXAtomicAPI.o \
		$<

build/CXXAtomicAPI.S: src/cxx/CXXAtomicAPI.cc
	cc \
		-x c++ \
		-std=c++17 \
		-Wall -Wextra -Werror \
		-g1 -gz -gsplit-dwarf \
		-fPIC \
		-Os \
		-I $(JAVA_HOME)/include \
		-I $(JAVA_HOME)/include/linux \
		-I $(JAVA_HOME)/include/darwin \
		-S \
		-c \
		-o build/CXXAtomicAPI.S \
		$<

clean:
	rm -rf build/*
