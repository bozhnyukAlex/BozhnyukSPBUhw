#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <malloc.h>
#include <sys/stat.h>
#include "mman1.h"
#define FILE_NAME_LENGTH 11
#define BITES_COUNT 10000
#define MAX_STR_LEN 100
#define MAX_STR_CNT 10000

int main() {
    int fd = open("content.txt", O_RDWR);
    int i = 0, j = 0;
    if (fd == -1) {
        printf("ERROR");
        return 1;
    }

    char *text = mmap(0, BITES_COUNT, PROT_READ | PROT_WRITE, MAP_PRIVATE, fd, 0);


    int tl = strlen(text);
    int strCount = 1;
    for (i = 0; i < tl; i++) {
        if (text[i] == ' ') {
            strCount++;
        }
    }
    //puts(text);
    //printf("!!! %d\n", strCount);
    char strings[MAX_STR_CNT][MAX_STR_LEN];
    int cur = 0, pos = 0;
    for (i = 0; i < strCount; i++) {
        cur = pos;
        while (text[cur] && text[cur] != ' ' && text[cur] != '\n') {
            cur++;
        }
        for (j = pos; j < cur; j++) {
            strings[i][j - pos] = text[j];
        }
        strings[i][cur] = '\0';
        pos = cur + 1;
    }

    qsort(strings, strCount, sizeof(char[MAX_STR_LEN]), (int (*)(const void *,const  void *)) strcmp);

    sprintf(text, "");
    for (i = 0; i < strCount; i++) {
        sprintf(text, "%s %s", text, strings[i]);
    }
//    puts(text);
//    for (i = 0; i < strCount; i++) {
//        puts(strings[i]);
//    }


    for (i = 0; i < strCount; i++) {
        free(strings[i]);
    }
    free(strings);
    munmap(text, BITES_COUNT);
    close(fd);
    return 0;
}
