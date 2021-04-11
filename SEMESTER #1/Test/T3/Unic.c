#include <stdio.h>
#include <malloc.h>
#include <string.h>
#include <stdlib.h>
int main() {
    size_t inN, index;
    const int cntSize = 256;
    const size_t mxSize = 1000000;
    size_t cnt[cntSize];
    for (index = 0; index < cntSize; index++) {
        cnt[index] = 0;
    }
    printf("Input numbers count: ");
    scanf("%d", &inN);
    int num;
    char result[mxSize];
    printf("Input numbers: ");
    for (index = 0; index < inN; index++) {
        scanf("%d", &num);
        cnt[num]++;
        if (index == 0 && cnt[num] == 1) {
            sprintf(result, "%d", num);
        }
        if (index != 0 && cnt[num] == 1) {
            sprintf(result, "%s %d", result, num);
        }
    }
    puts(result);
    return 0;
}
