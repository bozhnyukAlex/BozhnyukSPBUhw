#include <stdio.h>
int Fibonacci(int n) { ///without recursion
    if (n == 0) {
        return 0;
    }
    if (n == 1 || n == 2) {
        return 1;
    }
    int first = 1, second = 1, temp = 0;
    int index = 3;
    while (index <= n) {
        temp = first + second;
        first = second;
        second = temp;
        index++;
    }
    return temp;
}
int main(){
    int input;
    printf("Input N: ");
    scanf("%d", &input);
    if (input < 0) {
        printf("There are no Fibonacci numbers with negative index!");
        return 0;
    }
    printf("%d\n", Fibonacci(input));
    return 0;
}
