#include <stdio.h>
#include <string.h>
#include <malloc.h>
#include "mylist.h"
#include "hashTable.h"
#define TABLE_SIZE 256
#define INFINITY 1000
int hash(char* key) {
	int index = 0;
	int hashIndex = 0;
	while (key[index]) {
		hashIndex += key[index++];
	}
	return hashIndex;
}


struct HashTable createTable(int size, int (*hFn) (char*)) {
	struct HashTable newTable;
	newTable.arr = (struct List*) malloc(size * sizeof(struct List));
	newTable.hash = hFn;
	newTable.arrSize = size;
	for (int i = 0; i < size; i++) {
		newTable.arr[i] = createList();
	}
	return newTable;
}

void insertEl(struct HashTable* table, char* key, int value) {
	int index = hash(key) % table->arrSize;
	struct Node* element = createNode(value, key);
	if (table->arr[index].length == 0) {
		insertToBegin(&table->arr[index], element);
	}
	else {
		insertAfterEl(&table->arr[index], table->arr[index].length - 1, value, key);
	}
}
void removeEl(struct HashTable* table, char* key) {
	int index = hash(key) % table->arrSize;
	deleteNodeByKey(&table->arr[index], key);
}
int getEl(struct HashTable* table, char* key) {
	return getNByKey(&table->arr[hash(key)], key)->data;
}
void cleanTable(struct HashTable* table) {
	for (int i = 0; i < table->arrSize; i++) {
		clearList(&table->arr[i]);
	}
	free(table->arr);
}
//statistics
int elementCount(struct HashTable* table) {
	int res = 0;
	for (int i = 0; i < table->arrSize; i++) {
		res += table->arr[i].length;
	}
	return res;
}
int minChainLength(struct HashTable* table) {
	int min = INFINITY;
	for (int i = 0; i < table->arrSize; i++) {
		if (min > table->arr[i].length && table->arr[i].length > 0) {
			min = table->arr[i].length;
		}
	}
	return min;

}
int maxChainLength(struct HashTable* table) {
	int max = 0;
	for (int i = 0; i < table->arrSize; i++) {
		if (max < table->arr[i].length) {
			max = table->arr[i].length;
		}
	}
	return max; 
}
int averageChainLength(struct HashTable* table) {
	int cnt = 0;
	for (int i = 0; i < table->arrSize; i++) {
		if (table->arr[i].length > 0) {
			cnt++;
		}
	}
	return elementCount(table) / cnt;
}


