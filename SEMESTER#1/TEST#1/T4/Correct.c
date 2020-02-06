#include <stdio.h>
#include <assert.h>
#include <malloc.h>
#include <string.h>
#define SWAP(x,y) x ^= y ^= x ^= y;

int main(){
    char s[] = {'u','v','w','x','y','z', '\0'};
    char *t;
    unsigned long a, b, c;
    int i;
    printf("String: %s (%d entries).\n" "Change character to '1'? ", s,strlen(s));
    scanf("%lu", &a);

    assert((0 <= a) && (a < strlen(s)));
    s[a] = '1';
    printf("Original changed to %s\n", s);
    printf("Reverse which range of characters (from-to)? ");

    scanf("%lu%lu", &b, &c);
    assert(b <= c);

    t = malloc(strlen(s)+1);

    strcpy(t, s);
    int dist = ((c - b + 1) % 2 == 1)? (c - b) / 2 : (c - b) / 2 + 1;
    for (i = 0; i < dist; i++) {
        SWAP(t[b + i], t[c - i]);
    }
    printf("Result of reverse is %s -> %s\n", s, t);
    return 0;
}
