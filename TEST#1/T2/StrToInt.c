#include <stdio.h>
#include <string.h>
#include <limits.h>
int strToInt(char *s){
    int index = (s[0] == '-')? 1 : 0, sLen = strlen(s);
    int res = 0;
    if ((sLen > 10 && s[0] != '-') || (sLen > 11 && s[0] == '-')) {
        return -1;
    }
    while (index < sLen) {
        int curDig = s[index]-'0';
        if (((long long)(res * 10 + curDig) > INT_MAX && s[0] != '-') || ((long long)((res * 10 + curDig)*(-1)) < INT_MIN && s[0] == '-')) {
            return -1;
        }
        res *= 10;
        res += curDig;
        index++;
    }
    return (s[0] == '-')? -res : res;
}
int main(){
    const int MAX_LENGTH = 255;
    char ERROR_INPUT[255] = "Error input!";
    char ERROR_OVERFLOW[255] = "Error overflow!";
    char *str;

    str = (char*) malloc(MAX_LENGTH);
    if (str == NULL) {
        printf("ERROR");
        return 0;
    }
    printf("Input string: ");
    gets(str);

    int stLen = strlen(str), index = (str[0] == '-')? 1 : 0; ///обработка проблем ввода
    if (strcmp(str, "-") == 0 || stLen == 0) {
        puts(ERROR_INPUT);
        return 0;
    }
    while (index < stLen){
        if (str[index] < '0' || str[index] > '9') {
            puts(ERROR_INPUT);
            return 0;
        }
        index++;
    } ///конец обработки проблем ввода
    int result = strToInt(str);
    if (result == -1) {
        puts(ERROR_OVERFLOW);
    }
    else {
        printf("%d", result);
    }
    free(str);
    return 0;
}
