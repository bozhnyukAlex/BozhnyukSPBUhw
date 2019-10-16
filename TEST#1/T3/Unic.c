#include <stdio.h>
#include <malloc.h>
#include <string.h>
#include <stdlib.h>
int main(){
    size_t inN, index, radix = 10;
    const int cntSize = 256;
    const size_t mxSize = 1000000;
    size_t cnt[cntSize];
    for (index = 0; index < cntSize; index++) {
        cnt[index] = 0;
    }
    printf("Input numbers count: ");
    scanf("%d", &inN);
    int *arr;
    char result[mxSize];
    arr = malloc(inN * sizeof(int));
    if (arr == NULL) {
        printf("ERROR");
        return 0;
    }
    printf("Input numbers: ");
    for (index = 0; index < inN; index++) {
        scanf("%d", &arr[index]);
        cnt[arr[index]]++;
        if (index == 0) {
            sprintf(result, "%s", "");
        }
        if (cnt[arr[index]] == 1) {
            sprintf(result, "%s %d", result, arr[index]);
        }
    }
    puts(result);
    free(arr);
    return 0;
}
