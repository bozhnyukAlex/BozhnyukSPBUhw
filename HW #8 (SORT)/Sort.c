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
#define SIZE_COUNT 9
#define SORTS_COUNT 3


void swap(int *a, int *b);
void countSort(int *arr, size_t size);
void insertionSort(int *arr, size_t size);
void quickSortEX(int *arr, size_t left, size_t right);
void quickSort(int *arr, size_t size);
void fill(int *arr, size_t size);
void test(int *arr, size_t size, void (*f)(int *a, size_t s));

int main() {
    srand(5);
    int **arrs;
    size_t i, j;
    int sizes[] = {FIVE_SIZE, TEN_SIZE, HUNDRED_SIZE, THOUSAND_SIZE, T_THOUSAND_SIZE, H_THOUSAND_SIZE, MILLION_SIZE, T_MILLION_SIZE, H_MILLION_SIZE};
    arrs = (int**) malloc(SIZE_COUNT * sizeof(int*));
    if (arrs == NULL) {
        printf("ERROR\n");
        return 0;
    }
    for (i = 0; i < SIZE_COUNT; i++) {
        arrs[i] = (int*) malloc(sizes[i] * sizeof(int));
        if (arrs[i] == NULL) {
            printf("ERROR");
            return 0;
        }
        fill(arrs[i], sizes[i]);
    }

    void (*sortMethods[SORTS_COUNT]) (int *ar, size_t size) = {countSort, quickSort, insertionSort};

    for (i = 0; i < SORTS_COUNT; i++) {
        switch (i) {
            case 0: {
                printf("Count Sort: \n");
                break;
            }
            case 1: {
                printf("Quick Sort: \n");
                break;
            }
            case 2: {
                printf("Insertion Sort: \n");
                break;
            }
        }
        for (j = 0; j < SIZE_COUNT; j++) {
            test(arrs[j], sizes[j], sortMethods[i]);
        }
        printf("\n");
    }

    for (i = 0; i < SIZE_COUNT; i++) {
        free(arrs[i]);
    }
    free(arrs);
    return 0;
}



void swap(int *a, int *b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}
void countSort(int *arr, size_t size) {
    int *cnt;
    size_t i, j;
    cnt = (int*) malloc(MILLION_SIZE * sizeof(int));
    if (cnt == NULL) {
        printf("ERROR");
        return;
    }
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
    free(cnt);
}

void insertionSort(int *arr, size_t size) {
    size_t i, j;
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

void quickSortEX(int *arr, size_t left, size_t right) {
    if (left < right) {
        int pivot = arr[left + (rand() % (right - left + 1))];
        size_t i = left, j = right;
        while (i <= j) {
            while (arr[i] < pivot) {
                i++;
            }
            while (arr[j] > pivot) {
                j--;
            }
            if (i <= j) {
                swap(&arr[i], &arr[j]);
                i++;
                j--;
            }
        }
        quickSortEX(arr, left, j);
        quickSortEX(arr, i , right);
    }
}

void quickSort(int *arr, size_t size) {
    quickSortEX(arr, 0, size - 1);
}

void fill(int *arr, size_t size) {

    size_t i;
    for (i = 0; i < size; i++) {
        arr[i] = rand() % MILLION_MOD;
    }
}

void test(int *arr, size_t size, void (*sort)(int *a, size_t s)) {
    clock_t begin, end;
    double timeSpend;
    begin = clock();
    sort(arr, size);
    end = clock();
    timeSpend = (double) (end - begin) / CLOCKS_PER_SEC;
    printf("Size %d - %.10lf\n", size, timeSpend);
    fill(arr, size);
}

