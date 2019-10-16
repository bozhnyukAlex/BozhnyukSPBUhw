#include <stdio.h>
#include <malloc.h>
#include <string.h>
#include <stdlib.h>

int main(){
    const int MAX_LENGTH = 255;
    char *str;
    str = (char*) malloc(MAX_LENGTH);
    gets(str);
    return 0;
}
