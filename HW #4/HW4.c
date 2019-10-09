#include <stdio.h>
size_t strlen(char* str){
    size_t strlength = 0;
    int index = 0;
    while(str[index]){
        strlength++;
        index++;
    }
    return strlength;
}
void strcpy(char* dst, char* src){
    int index = 0;
    while(src[index]){
        dst[index] = src[index];
        index++;
    }
    dst[index] = '\0';
}
char* strcat(char* dst, char* src){
    size_t srcLen = strlen(src),
        dstLen = strlen(dst);
    int index = 0;
    while(index < srcLen){
        dst[dstLen+index] = src[index];
        index++;
    }
    dst[dstLen+index] = '\0';
    return dst;
}
int strcmp(char* s1, char* s2){
    int index = 0;
    while(s1[index] == s2[index] && s1[index] != '\0' && s2[index] != '\0'){
        index++;
    }
    return s1[index] - s2[index];
}
/*
int main(){
    const size_t MAX_STRING_SIZE = 255;
    char s1[MAX_STRING_SIZE], s2[MAX_STRING_SIZE];
    scanf("%s", &s1);
    scanf("%s", &s2);
    printf("%i\n", strcmp(s2,s1));
    return 0;
}*/
