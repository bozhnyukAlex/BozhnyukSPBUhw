#include <stdio.h>
#include <string.h>
#include <malloc.h>
#include <limits.h>
#include "mylist.h"
#include "hashTable.h"
#define TABLE_SIZE 256
#define INFINITY 100000000
size_t hashSum(char* key) {
	size_t index = 0;
	size_t hashIndex = 0;
	while (key[index]) {
		hashIndex += key[index++];
	}
	return hashIndex;
}

long long binpow(int a, int n) {
	if (n == 0){
		return 1;
	}
	if (n % 2 == 1){
		return binpow(a, n - 1) * a;
	}
	else {
		long long b = binpow(a, n / 2);
		return b * b;
	}
}

size_t hashConst(char* key) {
    return 42;
}

size_t hashPolynom(char* key) {
    const int constA = 2;
    size_t hashIndex = 0;
    int power = 1;
    size_t index = 0,
           klen = strlen(key);
    for (index = 0; index < klen; index++) {
        if (key[index] >= 'a' && key[index] <= 'z') {
            hashIndex += (key[index] - 'a' + 1) * power;
        }
        else if (key[index] >= 'A' && key[index] <= 'Z') {
            hashIndex += (key[index] - 'A' + 1) * power;
        }
        else if (key[index] >= '0' && key[index] <= '9') {
            hashIndex += (key[index] - '0' + 1) * power;
        }
        power *= constA;
    }
    return hashIndex;
}


struct HashTable createTable(size_t size, int (*hFn) (char*)) {
	struct HashTable newTable;
	newTable.arr = (struct List*) malloc(size * sizeof(struct List));
	if (newTable.arr == NULL) {
        printf("ERROR!\n");
        exit(-1);
	}
	newTable.hash = hFn;
	newTable.size = size;
	size_t i;
	for (i = 0; i < size; i++) {
		newTable.arr[i] = createList();
	}
	return newTable;
}

void insertEl(struct HashTable* table, char* key, int value) {
	size_t index = table->hash(key) % table->size;
	struct Node* element = createNode(value, key);
	insertBack(&table->arr[index], element);
}
void removeEl(struct HashTable* table, char* key) {
	size_t index = table->hash(key) % table->size;
	deleteNodeByKey(&table->arr[index], key);
}
struct Node* getEl(struct HashTable* table, char* key) {
	size_t index = table->hash(key) % table->size;
	struct Node* find = getNByKey(&table->arr[index], key);
	return find;
}
void cleanTable(struct HashTable* table) {
	size_t i = 0;
	for (i = 0; i < table->size; i++) {
		clearList(&table->arr[i]);
	}
	free(table->arr);
}

void printTable(struct HashTable* table) {
    size_t i = 0;
    for (i = 0; i < table->size; i++) {
        if (table->arr[i].length == 0) {
            continue;
		}
		printf("%d: ", i);
		struct Node* curr = table->arr[i].head;
		while (curr) {
			if (curr -> next == NULL) {
                printf("[%s | %d] ", curr->key, curr->data);
			}
			else {
                printf("[%s | %d] -> ", curr->key, curr->data);
			}
			curr = curr->next;
		}
		printf("\n");
	}
}

//statistics
int elementCount(struct HashTable* table) {
	int res = 0;
	size_t i = 0;
	for (i = 0; i < table->size; i++) {
		res += table->arr[i].length;
	}
	return res;
}
int minChainLength(struct HashTable* table) {
	int min = INFINITY;
	size_t i = 0;
	for (i = 0; i < table->size; i++) {
		if (min > table->arr[i].length && table->arr[i].length > 0) {
			min = table->arr[i].length;
		}
	}
	return min;
}
int maxChainLength(struct HashTable* table) {
	int max = 0;
	size_t i = 0;
	for (i = 0; i < table->size; i++) {
		if (max < table->arr[i].length) {
			max = table->arr[i].length;
		}
	}
	return max;
}
int averageChainLength(struct HashTable* table) {
	int cnt = 0;
	size_t i = 0;
	for (i = 0; i < table->size; i++) {
		if (table->arr[i].length > 0) {
			cnt++;
		}
	}
	return elementCount(table) / cnt;
}


