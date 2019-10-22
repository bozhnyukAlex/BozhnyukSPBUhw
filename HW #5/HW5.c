#include <stdio.h>
#include <malloc.h>
int isPrime(int x){
    if(x == 1) return 0;
    int d;
    for(d = 2; d*d <= x; d++){
        if(x % d == 0) return 0;
    }
    return 1;
}

int gcd(int a, int b){
    if(b == 0) return a;
    return gcd(b, a % b);
}

void prHello(){
    printf("Hello user!");
}

int glA = 15123, glB, glC = -1000000000;
char glD = 'M', glE, glF = 'V';
double glG = 1.31341, glH, glI = 4.122112;

int main(){
    int lcA = 1213, lcB = 10000, lcC = -120000;
    char lcD = 'a', lcE = 'L', lcF = '9';
    double lcG = 3.1415, lcH = 2.71124, lcI = 320.15;

    int *pGlA = &glA,
        *pGlB = &glB,
        *pGlC = &glC,
        *pGlD = &glD,
        *pGlE = &glE,
        *pGlF = &glF,
        *pGlG = &glG,
        *pGlH = &glH,
        *pGlI = &glI;

    int *pLcA = &lcA,
        *pLcB = &lcB,
        *pLcC = &lcC,
        *pLcD = &lcD,
        *pLcE = &lcE,
        *pLcF = &lcF,
        *pLcG = &lcG,
        *pLcH = &lcH,
        *pLcI = &lcI;

    int *pUFunA = &isPrime,
        *pUFunB = &gcd,
        *pUFunC = &prHello;

    int *pSFunA = &main,
        *pSFunB = &printf,
        *pSFunC = &scanf;

    int arrSize = 5;
    int *arr, i;

    int *dinA = (int*)malloc(sizeof(int)),
        *dinB = (int*)malloc(sizeof(int)),
        *dinC = (int*)malloc(sizeof(int)),
        *dinD = (int*)malloc(sizeof(char)),
        *dinE = (int*)malloc(sizeof(char)),
        *dinF = (int*)malloc(sizeof(char));
    *dinA = 1000;
    *dinC = 10001;
    *dinD = '0'; //значение меняется!
    *dinE = 'Q';
    *dinF = 'R';

    arr = (int*)malloc(arrSize * sizeof(int));
    if (arr == NULL || dinA == NULL || dinB == NULL || dinC == NULL || dinD == NULL || dinE == NULL || dinF == NULL) {
        printf("ERROR!");
        return 0;
    }
    for(i = 0; i < arrSize; i++){
        arr[i] = i;
    }

    printf("\t\t------Local variable addresses------\n");
    printf("lcA: %p\nlcB: %p\nlcC: %p\nlcD: %p\nlcE: %p\nlcF: %p\nlcG: %p\nlcH: %p\nlcI: %p\n", pLcA, pLcB, pLcC, pLcD, pLcE, pLcF, pLcG, pLcH, pLcI);

    printf("\t\t------Global variable addresses------\n");
    printf("glA: %p\nglB: %p\nglC: %p\nglD: %p\nglE: %p\nglF: %p\nglG: %p\nglH: %p\nglI: %p\n", pGlA, pGlB, pGlC, pGlD, pGlE, pGlF, pGlG, pGlH, pGlI);

    printf("\t\t------User function addresses------\n");
    printf("UFuncA (isPrime): %p\nUFuncB (gcd): %p\nUFuncC (prHello): %p\n", pUFunA, pUFunB, pUFunC);

    printf("\t\t------System function addresses------\n");
    printf("SFuncA (main): %p\nSFuncB (printf): %p\nSFuncC (scanf): %p\n", pSFunA, pSFunB, pSFunC);

    printf("\t\t------Dynamic variable and array addresses------\n");
    printf("DinA: %p\nDinB: %p\nDinC: %p\nDinD: %p\nDinE: %p\nDinF: %p\nArr: %p\n", dinA, dinB, dinC, dinD, dinE, dinF, arr);
    free(arr);
    free(dinA);
    free(dinB);
    free(dinC);
    free(dinD);
    free(dinE);
    free(dinF);
    return 0;
}
