#include <stdio.h>
size_t strlen(char* str) {
    size_t strlength = 0;
    while (str[strlength]) {
        strlength++;
    }
    return strlength;
}
void strcpy(char* dst, char* src) {
    size_t index = 0;
    while (src[index]) {
        dst[index] = src[index];
        index++;
    }
    dst[index] = '\0';
}
char* strcat(char* dst, char* src) {
    size_t dstLen = strlen(dst);
    size_t index = 0;
    while (src[index]) {
        dst[dstLen + index] = src[index];
        index++;
    }
    dst[dstLen + index] = '\0';
    return dst;
}
int strcmp(char* s1, char* s2) {
    size_t index = 0;
    while (s1[index] == s2[index] && s1[index] != '\0' && s2[index] != '\0') {
        index++;
    }
    return s1[index] - s2[index];
}
/*
int main() {
    const size_t MAX_STRING_SIZE = 255;
    char s1[MAX_STRING_SIZE], s2[MAX_STRING_SIZE], s3[MAX_STRING_SIZE];
    gets(s1);
    gets(s2);
    strcpy(s3, "hello\0 hell");
   // puts(strcat(s1,s2));
    printf("%d\n%d\n%s", strlen(s2), strcmp(s3,s2), s3);
    return 0;
}*/
