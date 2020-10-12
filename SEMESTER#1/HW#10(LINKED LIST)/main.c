#include <stdio.h>
#include <malloc.h>
#include <stdlib.h>
#include <string.h>
#include "mylist.h"
#define OPTION_LENGTH 1000
#define MIN_STR_LEN 1
#define MIN_OPT '1'
#define MAX_OPT '9'
enum {
    PRINT = 1,
    INSERT_TO_BEGIN,
    INSERT_AFTER_ELEMENT,
    GET_N,
    DELETE_NODE,
    CLEAR_LIST,
    CREATE_CYCLE,
    CHECK_CYCLE,
    EXIT
};

int main () {
    struct List list = createList();
    push(&list, 2);
    push(&list, 3);
    push(&list, 4);
    push(&list, 5);
    push(&list, 6);
    push(&list, 7);

    printf("!!!!!!!!!!!!!!!!!!!!!!!!!\n");
    printList(&list);
    
    shiftRight(&list, 0, 20);
    printList(&list);
    
    shiftRight(&list, 0, 2);
    printList(&list);
    
    shiftRight(&list, 1, 3);
    printList(&list);
    
    clearList(&list);
    return 0;
}
