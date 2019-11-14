#include <stdio.h>
#include <time.h>
#include <malloc.h>
#include <stdlib.h>

#define FIVE_SIZE 5
#define TEN_SIZE 10
#define HUNDRED_SIZE 100
#define THOUSAND_SIZE 1000
#define T_THOUSAND_SIZE 10000
#define H_THOUSAND_SIZE 100000
#define MILLION_SIZE 1000000
#define T_MILLION_SIZE 10000000
#define H_MILLION_SIZE 100000000
#define MILLION_MOD 1000000


void swap(int *a, int *b);
void countSort(int *arr, int size);
void insertionSort(int *arr, int size);
void quickSort(int *arr, int left, int right);
void sort(int *arr, int size, void (*f)(int *a, int s));
void fill(int *arr, int size);
void test(int *arr, int size, void (*f)(int *a, int s));
void qtest(int *arr, int left, int right);


int main() {
    srand(5);
    int *arrF,
        *arrTN,
        *arrH,
        *arrT,
        *arrTT,
        *arrHT,
        *arrM,
        *arrTM,
        *arrHM;
    int i;
    arrF = malloc(FIVE_SIZE * sizeof(int));
    arrTN = malloc(TEN_SIZE * sizeof(int));
    arrH = malloc(HUNDRED_SIZE * sizeof(int));
    arrT = malloc(THOUSAND_SIZE * sizeof(int));
    arrTT = malloc(T_THOUSAND_SIZE * sizeof(int));
    arrHT = malloc(H_THOUSAND_SIZE * sizeof(int));
    arrM = malloc(MILLION_SIZE * sizeof(int));
    arrTM = malloc(T_MILLION_SIZE * sizeof(int));
    arrHM = malloc(H_MILLION_SIZE * sizeof(int));

    if (arrF == NULL || arrTN == NULL || arrH == NULL || arrT == NULL || arrTT == NULL || arrHT == NULL || arrM == NULL || arrHM == NULL || arrTM == NULL) {
        printf("ERROR");
        return 0;
    }

    fill(arrF, FIVE_SIZE);
    fill(arrTN, TEN_SIZE);
    fill(arrH, HUNDRED_SIZE);
    fill(arrT, THOUSAND_SIZE);
    fill(arrHT, H_THOUSAND_SIZE);
    fill(arrTT, T_THOUSAND_SIZE);
    fill(arrM, MILLION_SIZE);
    fill(arrHM, H_MILLION_SIZE);
    fill(arrTM, T_MILLION_SIZE);


    int option = 0;
    while (1) {
        printf("\nChoose your option: \n 1 - Check countSort \n 2 - Check insertionSort \n 3 - Check quickSort \n 4 - Exit program \n");
        scanf("%d", &option);
        if (option < 1 || option > 4) {
            printf("Wrong option!\n");
            continue;
        }
        int (*sortType) (int *a, int s);
        switch (option) {
            case 1: {
                sortType = countSort;
                break;
            }
            case 2: {
                sortType = insertionSort;
                break;
            }
            case 3: {
                qtest(arrF, 0, FIVE_SIZE - 1);
                qtest(arrTN, 0, TEN_SIZE - 1);
                qtest(arrH, 0, HUNDRED_SIZE - 1);
                qtest(arrT, 0, THOUSAND_SIZE - 1);
                qtest(arrTT, 0, T_THOUSAND_SIZE - 1);
                qtest(arrHT, 0, H_THOUSAND_SIZE - 1);
                qtest(arrM, 0, MILLION_SIZE - 1);
                qtest(arrTM, 0, T_MILLION_SIZE - 1);
                qtest(arrHM, 0, H_MILLION_SIZE - 1);
                break;
            }
            case 4: {
                return 0;
            }
        }
        if (option == 3) {
            continue;
        }
        printf("\nTest Results: \n");
        test(arrF, FIVE_SIZE, sortType);
        test(arrTN, TEN_SIZE, sortType);
        test(arrH, HUNDRED_SIZE, sortType);
        test(arrT, THOUSAND_SIZE, sortType);
        test(arrTT, T_THOUSAND_SIZE, sortType);
        test(arrHT, H_THOUSAND_SIZE, sortType);
        test(arrM, MILLION_SIZE, sortType);
        test(arrTM, T_MILLION_SIZE, sortType);
        test(arrHM, H_MILLION_SIZE, sortType);
    }


    free(arrF);
    free(arrH);
    free(arrHM);
    free(arrHT);
    free(arrM);
    free(arrT);
    free(arrTM);
    free(arrTN);
    free(arrTT);
    return 0;
}



void swap(int *a, int *b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}
void countSort(int *arr, int size) {
    int *cnt;
    int i, j;
    cnt = malloc(MILLION_SIZE * sizeof(int));
    for (i = 0; i < MILLION_SIZE; i++) {
        cnt[i] = 0;
    }
    for (i = 0; i < size; i++) {
        cnt[arr[i]]++;
    }
    for (i = 0; i < MILLION_SIZE; i++) {
        if (cnt[i] != 0) {
            for (j = 0; j < cnt[i]; j++) {
                //printf("%d ", i);
                printf("");
            }
        }
    }
}

void insertionSort(int *arr, int size) {
    int i, j;
    int mn, mni;
    for (i = 0; i < size - 1; i++) {
        mn = MILLION_MOD;
        mni = i;
        for (j = i; j < size; j++) {
            if (mn > arr[j]) {
                mn = arr[j];
                mni = j;
            }
        }
        swap(&arr[i], &arr[mni]);
    }
}

void quickSort(int *arr, int left, int right) {
    srand(time(0));
    if (left < right) {
        int pivot = arr[left + (rand() % (right - left + 1))];
        int i = left, j = right;
        while (i <= j) {
            while (arr[i] < pivot) {
                i++;
            }
            while (arr[j] > pivot) {
                j--;
            }
            if (i <= j) {
                swap(&arr[i++], &arr[j--]);
            }
        }
        quickSort(arr, left, j);
        quickSort(arr, i , right);
    }
}

void sort(int *arr, int size, void (*f)(int *a, int s)) {
    f(arr, size);
}
void fill(int *arr, int size) {
    srand(5);
    int i;
    for (i = 0; i < size; i++) {
        arr[i] = rand() % MILLION_MOD;
    }
}

void test(int *arr, int size, void (*f)(int *a, int s)) {
    clock_t begin, end;
    double timeSpend;
    begin = clock();
    sort(arr, size, f);
    end = clock();
    timeSpend = (double) (end - begin) / CLOCKS_PER_SEC;
    printf("Size %d - %.10lf\n", size, timeSpend);
    fill(arr, size);
}
void qtest(int *arr, int left, int right) {
    clock_t begin, end;
    int size = right + 1;
    double timeSpend;
    begin = clock();
    quickSort(arr, left, right);
    end = clock();
    timeSpend = (double) (end - begin) / CLOCKS_PER_SEC;
    printf("Size %d - %.10lf\n", size, timeSpend);
    fill(arr, size);
}
