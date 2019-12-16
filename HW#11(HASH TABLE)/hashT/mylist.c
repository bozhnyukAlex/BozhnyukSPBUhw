#include <stdio.h>
#include <malloc.h>
#include <stdlib.h>
#include <string.h>
#include "mylist.h"

struct List createList() {
    struct List list;
    list.head = NULL;
    list.end = NULL;
    list.length = 0;
    return list;
}

struct Node* createNode(int value, char* key) {
    struct Node* node = (struct Node*) malloc(sizeof(struct Node));
    if (node == NULL) {
        printf("ERROR");
        exit(0);
    }
    node->data = value;
	node->key = key;
    node->next = NULL;
    return node;
}
struct Node* getN(struct List* list, size_t n) {
    struct Node* curr = list->head;
    size_t index = 0;
    while (curr) {
        if (index == n) {
            return curr;
        }
        curr = curr->next;
        index++;
    }
    return NULL;
}
struct Node* getNByKey(struct List* list, char* key) {
	struct Node* curr = list->head;
	while (curr) {
		if (strcmp(key, curr->key) == 0) {
			return curr;
		}
		curr = curr->next;
	}
	return NULL;
}
void insertToBegin(struct List* list, struct Node* node) {
    node->next = list->head;
    list->head = node;
    list->length++;
    if (list->length == 1) {
        list->end = node; ///if not, list->end already exists
    }
}
void insertBack(struct List* list, struct Node* node) {
	if (list->length == 0) {
		insertToBegin(list, node);
	}
	else {
		list->end->next = node;
		list->end = node;
		list->length++;
	}
}
void insertAfterEl(struct List* list, size_t afterNum, int newValue, char* newKey) {
    struct Node* after = getN(list, afterNum);
    struct Node* newNode = createNode(newValue, newKey);
    newNode->next = after->next;
    after->next = newNode;
    list->length++;
    if (list->end == after) {
        list->end = newNode;
    }
}
void deleteNode(struct List* list, size_t numDelete) {
    struct Node* toDelete = getN(list, numDelete);
    struct Node* curr = list->head;
    if (curr == toDelete) {
        list->head = curr->next;
        free(toDelete);
		list->length--;
        return;
    }
    while (curr) {
        if (curr->next == toDelete) {
            curr->next = curr->next->next;
            free(toDelete);
			list->length--;
            return;
        }
        curr = curr->next;
    }
}
void deleteNodeByKey(struct List* list, char* key) {
	struct Node* toDelete = getNByKey(list, key);
	struct Node* curr = list->head;
	if (curr == toDelete) {
		list->head = curr->next;
		free(toDelete);
		list->length--;
		return;
	}
	while (curr) {
		if (curr->next == toDelete) {
			curr->next = curr->next->next;
			free(toDelete);
			list->length--;
			return;
		}
		curr = curr->next;
	}
}
void clearList(struct List* list) {
    if (list->length == 0) {
        return;
    }
    struct Node* curr = list->head;
    while (curr) {
        free(curr->key);
        struct Node *tmp = curr;
        curr = curr->next;
        free(tmp);
    }
    list->head = NULL;
    list->end = NULL;
    list->length = 0;
}
