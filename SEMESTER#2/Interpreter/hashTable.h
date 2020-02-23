#ifndef HASHTABLE_H_INCLUDED
#define HASHTABLE_H_INCLUDED

#include "mylist.h"


long long binpow(int a, int n);
size_t hashSum(char* key);
size_t hashConst(char* key);
size_t hashPolynom(char* key);

struct HashTable {
	struct List* arr;
	size_t size;
	int(*hash) (char*);
};

struct HashTable createTable(size_t size, int(*hFn) (char*));
void insertEl(struct HashTable* table, char* key, int value);
void removeEl(struct HashTable* table, char* key);
struct Node* getEl(struct HashTable* table, char* key);
void cleanTable(struct HashTable* table);
void printTable(struct HashTable* table);
int elementCount(struct HashTable* table);
int minChainLength(struct HashTable* table);
int maxChainLength(struct HashTable* table);
int averageChainLength(struct HashTable* table);

#endif // HASHTABLE_H_INCLUDED
