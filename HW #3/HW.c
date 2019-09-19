#include <stdio.h>

int bitAnd();
int bitOr();
int bitXor();


int main(){
    int option = -1;
    printf("Hello, User! Select option. Press number from 1 to 12\n");
    printf(" 1 - bitAnd\n 2 - bitXor\n 3 - thirdBits\n 4 - fitsBits\n 5 - sign\n 6 - getByte\n 7 - logicalShift\n 8 - addOk\n 9 - bang\n 10 - conditional\n 11 - isPower2\n 12 - Exit Program");
    while(1){
        printf("\nYour option: ");
        scanf("%d", &option);
        switch(option){
            case 1:{
                printf("Input two numbers: ");
                int parA, parB, res;
                scanf("%d %d", &parA, &parB);
                res = bitAnd(parA, parB);
                printf("Result: %d \n", &res);
                break;
            }
            case 2:{
                printf("Input two numbers: ");
                int parA, parB, res;
                scanf("%d %d", &parA, &parB);
                res = bitXor(parA, parB);
                printf("Result: %d \n", &res);
                break;
            }
            case 3:{
                break;
            }
            case 4:{
                break;
            }
            case 5:{
                break;
            }
            case 6:{
                break;
            }
            case 7:{
                break;
            }
            case 8:{
                break;
            }
            case 9:{
                break;
            }
            case 10:{
                break;
            }
            case 11:{
                break;
            }
            case 12:{
                return 0;
                break;
            }


        }
    }

    return 0;
}

int bitAnd(int parA, int parB){
    return ~(~parA | ~parB);
}
int bitOr(int parA, int parB){
    return ~(~parA & ~parB);
}
int bitXor(int parA, int parB){
    return bitOr(parA & ~parB, ~parA & parB);
}
