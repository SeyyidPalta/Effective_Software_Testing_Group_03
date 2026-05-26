# Task 1

## a) 
```
int bounded_gcd(int a, int b) {
    if (b != 0) {
        int tmp = b;
        b = a mod b;
        a = tmp;

        if (b != 0) {
            int tmp2 = b;
            b = a mod b;
            a = tmp2;
        }
    }
    return a;
}
```

## b)
```
1. Path: b==0 -> return a
2. Path: b!=0 -> a mod b -> b==0 -> return a
3. Path: b!=0 -> a mod b -> b!=0 -> a mod b -> return a
```

## c)
The values a=5 and b=3 differs in the results of gcd and bounded_gcd.
gcd: will return 1 because it will go through the following steps in the loop:
1. 5 mod 3 -> 2
2. 3 mod 2 -> 1
3. 2 mod 1 -> 0
bounded_gcd: will return 2 because it will take go through the following steps:
1. 5 mod 3 -> 2
2. 3 mod 2 -> 1

No it is not an under-approximation of the result computed by gcd, just an under-approximation of the execution space of the bounded program.
Thus is because in some cases the result of bounded_gcd can differ from the result of gcd, as shown in the example above, 
when the loop was cut off after two iterations.

## d)
To compute an under-approximation of gcd, we have to modify the bounded_gcd function, so the loop is not cut off after two iterations, 
but instead it is cut off after n iterations, where n is a parameter of the function.
```
int under_approx_gcd(int a, int b, int n) {
    for (int i = 0; i < n; i++) {
        if (b == 0) return a;
        int tmp = b;
        b = a mod b;
        a = tmp;
    }
    if (b != 0) {
        throw new IllegalStateException("result not equal to gcd after n iterations");
    }
}
```

# Task 2
```
int pow_client(int b, int e) {
    int r = my_pow(b, e);
    if (e mod 2 == 0) {
        if (r < 0) { // (*)
            assert(false); // should not happen
        }
    }
    return r;
}

int my_pow(int b, int e) {
    int r = b;
    for (int i = 1; i < e; i++) {
        r = r * b;
    }
    return r;
}
// incorrect implementation
```


## a) b = e = 0
```
my_pow(0, 0) -> loop is skipped -> r = 0 -> 0 mod 2 == 0 -> r < 0 is false -> return 0
```
The collected path constraint is:
```
e >= 0
AND
!(1 < e)
AND
e mod 2 == 0
AND
!(b < 0)
```

Equivalently:
```
e >= 0
AND
e <= 1
AND
e mod 2 == 0
AND
b >= 0
```

## b)
The previous run did not enter the assertion branch because `r < 0` was false.
Since `r = b` on this path, we negate the last sub-constraint:
```
e >= 0
AND
e <= 1
AND
e mod 2 == 0
AND
b < 0
```

A satisfying assignment is:
```
b = -1
e = 0
```

## c)
Using `b = -1, e = 0`, the second run reaches the assertion:
```
my_pow(-1, 0) -> loop is skipped -> r = -1 -> 0 mod 2 == 0 -> r < 0 -> assert(false)
```

The collected path constraint is:
```
e >= 0
AND
e <= 1
AND
e mod 2 == 0
AND
b < 0
```

## d)
If `my_pow` is a black-box library function, concolic execution cannot build a symbolic expression for its result.
For the concrete input `b = 0, e = 0`, the library call is only executed concretely:
```
my_pow(0, 0) = 0
```

So after the call, `r` is treated as the concrete value `0`, not as a symbolic expression depending on `b` and `e`.
The visible branches in `pow_client` give the following path constraint:
```
e mod 2 == 0
AND
!(r < 0)
```

Since `r` is concrete in this run, this is:
```
e mod 2 == 0
AND
0 >= 0
```

Thus the only meaningful symbolic constraint learned from this execution is that `e` is even.

## e)
No, we cannot proceed analogously to subtasks b)-c).
In the white-box setting, concolic execution can express `r` symbolically in terms of `b` and `e`, so negating the branch condition `r < 0` can lead to a useful new input.

In the black-box setting, the execution only knows the concrete result `r = 0` for the first run.
Negating the second branch condition would require:
```
r < 0
```
but with the concrete value from the run this becomes:
```
0 < 0
```
which is unsatisfiable.

Therefore, the concolic engine cannot derive the input `b < 0, e = 0`, for example `b = -1, e = 0`, from the collected path constraints.
Pure concolic execution will not systematically reach the assertion in this example when `my_pow` is treated as a black box.
It could only hit the bug by chance, or with some additional input generation strategy that tries unconstrained values for `b`.

## f)
A KLEE harness for the program can look like this:
```
#include <assert.h>
#include <stdbool.h>
#include <klee/klee.h>

int my_pow(int b, int e) {
    int r = b;
    for (int i = 1; i < e; i++) {
        r = r * b;
    }
    return r;
}

int pow_client(int b, int e) {
    int r = my_pow(b, e);
    if (e % 2 == 0) {
        if (r < 0) {
            klee_assert(false);
        }
    }
    return r;
}

int main(void) {
    int b;
    int e;
    klee_make_symbolic(&b, sizeof(b), "b");
    klee_make_symbolic(&e, sizeof(e), "e");
    klee_assume(e >= 0);

    pow_client(b, e);
    return 0;
}
```

Compile and run it with Z3 and stop at the first assertion failure:
```
clang -I /path/to/klee/include -emit-llvm -c -g -O0 -Xclang -disable-O0-optnone pow_client.c
klee -solver-backend=z3 -exit-on-error pow_client.bc
```

KLEE finds the bug on the path where the loop in `my_pow` is skipped, `e` is even, and `r < 0`.
The corresponding path condition is:
```
e >= 0
AND
!(1 < e)
AND
e mod 2 == 0
AND
b < 0
```

One satisfying test case is:
```
b = -1
e = 0
```

For this input:
```
my_pow(-1, 0) = -1
e mod 2 == 0
r < 0
```
so `klee_assert(false)` is reached.

With `-exit-on-error`, KLEE stops as soon as this failing path is found.
In the run where this path is selected first, KLEE has `0` completed non-error paths before the failure and produces `1` test case, the assertion-failing one.
It is usually stored as `klee-last/test000001.ktest` or another `test<x>.ktest` depending on the exploration order.
