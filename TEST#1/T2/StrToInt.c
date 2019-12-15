#include <stdio.h>
#include <string.h>
#include <limits.h>
#include <malloc.h>
int strToInt(char *s, int *retCode) {
    int index = (s[0] == '-')? 1 : 0, sLen = strlen(s);
    long long res = 0;
    while (s[index]) {
        int curDig = s[index]-'0';
        if ((s[0] != '-' && res * 10 + curDig > INT_MAX) || (s[0] == '-' && (res * 10 + curDig) * (-1) < INT_MIN)) {
            *retCode = 0;
            return -1;
        }
        res *= 10;
        res += curDig;
        index++;
    }
    return (s[0] == '-')? -res : res;
}
int main() {
    const int MAX_LENGTH = 255;
    char ERROR_INPUT[] = "Error input!";
    char ERROR_OVERFLOW[] = "Error overflow!";
    char *str;

    str = (char*) malloc(MAX_LENGTH);
    if (str == NULL) {
        printf("ERROR");
        return 1;
    }
    printf("Input string: ");
    gets(str);


    int stLen = strlen(str),
    index = 0;

    while (str[index] == ' ') {
        str[index] = '0';
        index++;
    }
    if (str[index] == '-') {
        str[index] = '0';
        str[0] = '-';
        index++;
    }

    if (strcmp(str, "-") == 0 || stLen == 0 || str[index] == '\0') {
        puts(ERROR_INPUT);
        return 1;
    }
    while (str[index] >= '0' && str[index] <= '9') {
        index++;
    }
    int index1 = index;
    if (str[index] != '\0') {
        if (str[index] != ' ') {
            puts(ERROR_INPUT);
            return 1;
        }
        else {
            while (str[index1] == ' ') {
                index1++;
            }
            if (str[index1] == '\0') {
                str[index] = '\0';
            }
            else {
                puts(ERROR_INPUT);
                return 1;
            }

        }
    }
    int rtCode = 1;
    int result = strToInt(str, &rtCode);
    if (rtCode == 0) {
        puts(ERROR_OVERFLOW);
    }
    else {
        printf("%d", result);
    }
    free(str);
    return 0;
}
