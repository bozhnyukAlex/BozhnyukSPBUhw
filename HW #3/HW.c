 #include <stdio.h>

int bitAnd();
int bitOr();
int bitXor();
int thirdBits();
int fitsBits();
int getByte();
int logicalShift();
int addOk();
int bang();
int conditional();
int isPower2();
/*
int main(){
    int option = -1;
    printf("Hello, User! Select option. Press number from 1 to 12\n");
    printf(" 1 - bitAnd\n 2 - bitXor\n 3 - thirdBits\n 4 - fitsBits\n 5 - sign\n 6 - getByte\n 7 - logicalShift\n 8 - addOk\n 9 - bang\n 10 - conditional\n 11 - isPower2\n 12 - Exit Program");
    while(1){
        printf("\nYour option: ");
        scanf("%d", &option);
        if(option <= 0 || option >= 13){
            printf("Wrong option! Try Again!\n");
            continue;
        }
        switch(option){
            case 1:{
                printf("Input two numbers: ");
                int parA, parB, res;
                scanf("%i %i", &parA, &parB);
                res = bitAnd(parA, parB);
                printf("Result: %i \n", res);
                break;
            }
            case 2:{
                printf("Input two numbers: ");
                int parA, parB, res;
                scanf("%i %i", &parA, &parB);
                res = bitXor(parA, parB);
                printf("Result: %i \n", res);
                break;
            }
            case 3:{
                int res = thirdBits();
                printf("Result: %i \n", res);
                break;
            }
            case 4:{
                int num, btCount;
                printf("Input number and bits count: ");
                scanf("%i %i", &num, &btCount);
                int res = fitsBits(num, btCount);
                printf("Result: %i \n", res);
                break;
            }
            case 5:{
                int num;
                printf("Input number: ");
                scanf("%i", &num);
                int res = sign(num);
                printf("Result: %i \n", res);
                break;
            }
            case 6:{
                int num, byte;
                printf("Input number and byte: ");
                scanf("%i %i", &num, &byte);
                int res = getByte(num, byte);
                printf("Result: %i \n", res);
                break;
            }
            case 7:{
                int num, delta;
                printf("Input number and shift distance: ");
                scanf("%i %i", &num, &delta);
                int res = logicalShift(num, delta);
                printf("Result:  %i \n", res);
                break;
            }
            case 8:{
                int num1, num2;
                printf("Input two numbers: ");
                scanf("%i %i", &num1, &num2);
                int res = addOk(num1, num2);
                printf("Result: %i", res);
                break;
            }
            case 9:{
                int num;
                printf("Input number: ");
                scanf("%i", &num);
                int res = bang(num);
                printf("Result: %i", res);
                break;
            }
            case 10:{
                int num1, num2, num3;
                printf("Input three numbers: ");
                scanf("%i %i %i", &num1, &num2, &num3);
                int res = conditional(num1, num2, num3);
                printf("Result: %i \n", res);
                break;
            }
            case 11:{
                int num;
                printf("Input number: ");
                scanf("%i", &num);
                int res = isPower2(num);
                printf("Result: %i \n", res);
                break;
            }
            case 12:{
                return 0;
                break;
            }

        }
    }
    return 0;
}*/

int bitAnd(int parA, int parB){
    return ~(~parA | ~parB);
}
int bitOr(int parA, int parB){
    return ~(~parA & ~parB);
}
int bitXor(int parA, int parB){
    return bitOr(parA & ~parB, ~parA & parB);
}
int thirdBits(){
    int res = 36, step = 6, first = 36;
    int step2 = step + step; //I can't use * operator;
    int step4 = step2 + step2;
    res |= (res << step);
    res |= (res << step2);
    res |= (first << step4);
    return res;
}
int fitsBits(int num, int btCount){
    int shift = 32 + (~btCount + 1);
    int newNum = num << shift;
    newNum >>= shift;
    return !(num ^ newNum);
}
int sign(int num){
    int shift = 31;
    int is0 = !(num ^ 0);
    return !is0 | (num >> shift);
}
int getByte(int num, int nbyte){
    int mask = 0xFF;
    int shift = nbyte << 3;
    mask <<= shift;
    int res = num & mask;
    return (res >> shift) & 0xFF;
}
int logicalShift(int num, int delta){
    int mask = (1 << (32 + (~delta + 1)));
    mask += (~1 + 1);
    num >>= delta;
    return num & mask;
}
int addOk(int numA, int numB){
    int sgnA = (numA >> 31) & 1,
        sgnB = (numB >> 31) & 1;
    int sum = numA + numB;
    int sgnAB = (sum >> 31) & 1;
    return (!sgnA | !sgnB | sgnAB) & (sgnA | sgnB | !sgnAB);

}
int bang(int num){
    int is0 = ~((num >> 31) & 1) & ((num ^ (num + (~1 + 1))) >> 31) & 1 ;
    return is0;

}
int conditional(int x, int y, int z){
    int is0 = !(x ^ 0);
    return ((~is0+1) & z) | (~(~is0+1) & y);
}
int isPower2(int num){
    int isMore0 = (num >> 31) & 1;
    int is0 = !(num ^ 0);
    return !isMore0 & !is0 & !(num & (num + (~1 + 1)));
}


