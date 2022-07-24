all: cxx-atomic.so

cxx-atomic.so: cxx-atomic.o
	cc \
		-shared \
		-fPIC \
		-L $(JAVA_HOME)/lib \
		-o cxx-atomic.so \
		cxx-atomic.o

cxx-atomic.o: cxx-atomic.S
	cc \
		-g1 -gz -gsplit-dwarf \
		-c \
		-fPIC \
		-o cxx-atomic.o \
		cxx-atomic.S

cxx-atomic.S: cxx-atomic.cc
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
		-o cxx-atomic.S \
		cxx-atomic.cc

clean:
	rm -f cxx-atomic.S cxx-atomic.o cxx-atomic.so
