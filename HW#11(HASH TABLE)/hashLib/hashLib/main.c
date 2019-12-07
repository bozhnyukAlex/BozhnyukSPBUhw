#include <stdio.h>
#include <malloc.h>
#include <string.h>
#include "hashTable.h"
#define TABLE_SIZE 256
#define STR_SIZE 15
int main() {
	struct HashTable table = createTable(TABLE_SIZE, hash);
	char* t1 = malloc(STR_SIZE * sizeof(char));
	char* t2 = malloc(STR_SIZE * sizeof(char));
	char* t3 = malloc(STR_SIZE * sizeof(char));
	char* t4 = malloc(STR_SIZE * sizeof(char));
	char* t5 = malloc(STR_SIZE * sizeof(char));
	char* t6 = malloc(STR_SIZE * sizeof(char));
	gets_s(t1, STR_SIZE);
	gets_s(t2, STR_SIZE);
	gets_s(t3, STR_SIZE);
	gets_s(t4, STR_SIZE);
	gets_s(t5, STR_SIZE);
	gets_s(t6, STR_SIZE);
	insertEl(&table, t1, 1);
	insertEl(&table, t2, 2);
	insertEl(&table, t3, 3);
	insertEl(&table, t4, 4);
	insertEl(&table, t5, 5);
	insertEl(&table, t6, 6);
	printf("%d %d %d %d\n", elementCount(&table), minChainLength(&table), maxChainLength(&table), averageChainLength(&table));
	removeEl(&table, t1);
	printf("%d %d %d %d\n", elementCount(&table), minChainLength(&table), maxChainLength(&table), averageChainLength(&table));
	printf("%d", getEl(&table, t4));
	_getch();
	return 0;
}