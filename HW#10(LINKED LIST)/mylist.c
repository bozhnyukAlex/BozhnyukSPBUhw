#include <stdio.h>
#include <malloc.h>
#include <stdlib.h>
#include "mylist.h"

struct List createList() {
    struct List list;
    list.head = NULL;
    list.end = NULL;
    list.length = 0;
    return list;
}

struct Node* createNode(int value) {
    struct Node* node = (struct Node*) malloc(sizeof(struct Node));
    if (node == NULL) {
        printf("ERROR");
        exit(0);
    }
    node->data = value;
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
void insertToBegin(struct List* list, struct Node* node) {
    node->next = list->head;
    list->head = node;
    list->length++;
    if (list->length == 1) {
        list->end = node; ///if not, list->end already exists
    }
}
void insertAfterEl(struct List* list, size_t afterNum, int newValue) {
    struct Node* after = getN(list, afterNum);
    struct Node* newNode = createNode(newValue);
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
        return;
    }
    while (curr) {
        if (curr->next == toDelete) {
            curr->next = curr->next->next;
            free(toDelete);
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
        struct Node *tmp = curr;
        curr = curr->next;
        free(tmp);
    }
    list->head = NULL;
    list->end = NULL;
    list->length = 0;
}
