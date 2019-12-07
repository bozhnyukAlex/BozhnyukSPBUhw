#ifndef MYLIST_H_INCLUDED
#define MYLIST_H_INCLUDED
struct Node {
    int data;
	char* key;
    struct Node* next;
};
struct List {
    struct Node* head;
    struct Node* end;
    size_t length;
};

struct List createList();
struct Node* createNode(int value, char* key);
struct Node* getN(struct List* list, size_t n);
struct Node* getNByKey(struct List* list, char* key);
void insertToBegin(struct List* list, struct Node* node);
void insertAfterEl(struct List* list, size_t afterNum, int newValue, char* key);
void deleteNode(struct List* list, size_t numDelete);
void deleteNodeByKey(struct List* list, char* key);
void clearList(struct List* list);


#endif // MYLIST_H_INCLUDED
