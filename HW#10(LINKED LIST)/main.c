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
    char opt[OPTION_LENGTH] = "";
    int option = -1;
    int hasCycle = 0;
    int checked = 0;
    printf("Choose option: \n 1 - Print\n 2 - InsertToBegin\n 3 - InsertAfterEl\n 4 - GetN\n 5 - DeleteNode\n 6 - ClearList\n 7 - CreateCycle\n 8 - CheckCycle\n 9 - Exit\n");
    while (1) {
        printf("Your option: ");
        scanf("%s", &opt);
        if (strlen(opt) > MIN_STR_LEN) {
            printf("Wrong option!\n");
            continue;
        }
        if (opt[0] < MIN_OPT || opt[0] > MAX_OPT) {
            printf("Wrong option!\n");
            continue;
        }
        option = opt[0] - '0';

        switch (option) {
            case PRINT: {
                if (checked == 0) {
                    printf("Check the list on the subject of cycles!\n");
                    continue;
                }
                if (list.length == 0) {
                    printf("List is empty.");
                }
                else {
                    printf("List: ");
                    struct Node* curr = list.head;
                    while (curr) {
                        printf("%d ", curr->data);
                        curr = curr->next;
                    }
                }
                printf("\n\n");
                break;
            }
            case INSERT_TO_BEGIN: {
                printf("Input integer value: ");
                int data;
                scanf("%d", &data);
                struct Node* node = createNode(data);
                insertToBegin(&list, node);
                printf("\n");
                break;
            }
            case INSERT_AFTER_ELEMENT: {
                printf("Input number of previous node and current node value: ");
                int currData = -1;
                size_t afterNum = -1;
                scanf("%d %d", &afterNum, &currData);
                if (afterNum < 0 || afterNum >= list.length) {
                    printf("There are no nodes with this number!\n");
                }
                else {
                    insertAfterEl(&list, afterNum, currData);
                }
                printf("\n");
                break;
            }
            case GET_N: {
                if (checked == 0) {
                    printf("Check the list on the subject of cycles!\n");
                    continue;
                }
                size_t num = -1;
                printf("Input integer number of node: ");
                scanf("%d", &num);
                if (num < 0 || num >= list.length) {
                    printf("There is no node with this number in our List!\n");
                }
                else {
                    struct Node* node = getN(&list, num);
                    printf("Node: %d value: %d", num, node->data);
                }
                printf("\n");
                break;

            }
            case DELETE_NODE: {
                if (checked == 0) {
                    printf("Check the list on the subject of cycles!\n");
                    continue;
                }
                size_t num = -1;
                printf("Input number of node you want to delete: ");
                scanf("%d", &num);
                if (num < 0 || num >= list.length) {
                    printf("There is no node with this number in our List!\n");
                }
                else {
                    deleteNode(&list, num);
                }
                printf("\n");
                break;
            }
            case CLEAR_LIST: {
                clearList(&list);
                printf("\n");
                break;
            }
            case CREATE_CYCLE: {
                printf("Where do you want to create a cycle? Input: ");
                size_t num = -1;
                scanf("%d", &num);
                if (num < 0 || num >= list.length) {
                    printf("There is no node with this number in our List!\n");
                }
                checked = 0;
                printf("Cycle was created!\n");
                break;
            }
            case CHECK_CYCLE: {
                hasCycle = checkCycle(&list);
                if (hasCycle) {
                    printf("Attention! List has got a cycle! Deleting...\n");
                    deleteCycle(&list);
                }
                else {
                    printf("List has not got a cycle!\n");
                }
                checked = 1;
                break;

            }
            case EXIT: {
                if (checked == 0) {
                    printf("Check the list on the subject of cycles!\n");
                    continue;
                }
                clearList(&list);

                return 0;
            }
        }
    }
    clearList(&list);
    return 0;
}
