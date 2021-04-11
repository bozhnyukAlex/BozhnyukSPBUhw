#include <stdio.h>

int main() {
    union {
        int a;
        char c[sizeof(int)];
    } un;
    un.a = 1;
    if (un.c[0] == 1) {
        printf("Little Endian");
    }
    else {
        printf("Big Endian");
    }
    return 0;
}
