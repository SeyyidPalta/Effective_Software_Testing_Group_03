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