#ifndef HASHTABLE_H_INCLUDED
#define HASHTABLE_H_INCLUDED

int hash(char* key);

struct HashTable {
	struct List* arr;
	int arrSize;
	int(*hash) (char*);
};

struct HashTable createTable(int size, int(*hFn) (char*));
void insertEl(struct HashTable* table, char* key, int value);
void removeEl(struct HashTable* table, char* key);
int getEl(struct HashTable* table, char* key);
void cleanTable(struct HashTable* table);
int elementCount(struct HashTable* table);
int minChainLength(struct HashTable* table);
int maxChainLength(struct HashTable* table);
int averageChainLength(struct HashTable* table);

#endif // HASHTABLE_H_INCLUDED
