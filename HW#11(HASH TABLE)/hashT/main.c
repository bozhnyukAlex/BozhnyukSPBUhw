#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <malloc.h>
#include <string.h>
#include <time.h>
#include <ctype.h>
#include "hashTable.h"

#define TABLE_SIZE_SUM 2050
#define TABLE_SIZE_CONST 10000000
#define TABLE_SIZE_POLYNOM 100000000
#define STR_SIZE 21
#define ZERO 0

int main() {
	struct HashTable table = createTable(TABLE_SIZE_POLYNOM, hashPolynom);

	FILE *file = fopen("David_Copperfield.txt", "r");
	if (file == NULL) {
		printf("ERROR");
		return 1;
	}
	char* word = (char*) malloc(STR_SIZE * sizeof(char));
	char* copy;
	if (word == NULL || copy == NULL) {
        printf("ERROR");
        return 1;
	}
	clock_t begin, end;
    double timeSpend;
    begin = clock();
	while (fscanf(file, "%s", word) != EOF) {
        if (!((word[0] <='z' && word[0] >= 'a') || (word[0] <='Z' && word[0] >= 'A') || (word[0] <='0' && word[0] >= '9'))) {
            continue;
        }
        if (strcmp(word, "I") != 0) {
            if (word[0] >= 'A' && word[0] <= 'Z') {
                word[0] = tolower(word[0]);
            }
        }
		struct Node* element = getEl(&table, word);
		if (element == NULL) {
            copy = (char*) malloc(STR_SIZE * sizeof(char));
            strcpy(copy, word);
			insertEl(&table, copy, 1);
		}
		else {
			element->data++;
		}
	}
	end = clock();
	timeSpend = (double) (end - begin) / CLOCKS_PER_SEC;
   // printTable(&table);

    int mnChainLen = minChainLength(&table),
        mxChainLen = maxChainLength(&table),
        avChainLen = averageChainLength(&table),
        elCount = elementCount(&table);
    printf("Statistics: \n Min Chain Length: %d \n Max Chain Length: %d \n Average Chain Length: %d \n Elements Count: %d \n Time to Fill Table: %f \n", mnChainLen, mxChainLen, avChainLen, elCount, timeSpend);

    cleanTable(&table);
    free(word);
    free(copy);
	fclose(file);
	return 0;
}
