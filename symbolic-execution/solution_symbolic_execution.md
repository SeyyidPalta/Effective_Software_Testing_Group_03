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
my_pow(0, 0) -> return 0 -> 0 mod 2 == 0 -> return 0
```
b = 0 AND e = 0
e_temp = 0 AND r >= 0

## b)
new sub-condition: ```if (r > 0) {```

## c)
b=1, e=0 with these new values the changed sub-condition can be fulfilled and the assertion is violated:
```
my_pow(1, 0) -> return 1 -> 0 mod 2 -> 1 > 0 -> assert(false)
```

## d)
When 'my_pow' is treated as a library function, the concolic execution cannot symbolically execute its body. So for the input `b = 0, e = 0`, the library call 
```
my_pow(0, 0) = 0
```
is evaluated only concretely and returns '0'.

So in 'pow_client', 'r' is not treated as a symbolic expression but just the value 0. However, the executor can still collect constraints from 
```
e mod 2 == 0
```
The inner condition r < 0 becomes 0 < 0, which does not make sense.

In conclusion, the only meaningful symbolic constraint is that `e` is even.

## e)
No.
In the white-box scenario, the executor knows that for `e = 0`, `my_pow(b, e)` returns `b`. Therefore it can turn the condition `r < 0` into `b < 0` and generate an input such as `b = -1, e = 0`.
But in the black-box scenario, this relationship is lost. The executor only observed that the previous concrete call returned `r = 0`. If we negate the inner branch condition, we get: 

'''
r < 0
'''
but for the observed execution, it means: 
'''
0 < 0
'''
which is unsatisfiable.

## f)
We tried multiple ways to execute the KLEE program (within the browser and with docker) but we were not able to get a meaningful output. In the Web IDE no output was generated, also not for the examples, we think because it is not supported anymore. With the Docker image we got these errors during compilation: clang -I ../../include -emit-llvm -c -g -O0 -Xclang -disable-O0-optnone pow_client.c it throws no error but it also not generated the assert.err file. But the .bc file was generated.
