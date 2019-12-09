#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <malloc.h>
#include <sys/stat.h>
#include "mman-win32-master/mman.h"
#define MAX_STR_LEN 100
#define MAX_STR_CNT 10000

int myStrcmp(char* s1, char* s2) {
    size_t index = 0;
    while (s1[index] == s2[index] && s1[index] != '\n' && s2[index] != '\n') {
        index++;
    }
    return s1[index] - s2[index];
}

int strCmp(const void* a, const void* b) {
  return myStrcmp(*(char**)a, *(char**)b);
}

int main() {
    int fd = open("content.txt", O_RDWR, 0);
    FILE* fout = fopen("output.txt", "w");
    size_t i = 0, j = 0;
    if (fd == -1 || fout == NULL) {
        printf("ERROR");
        return 1;
    }

    struct stat st;
	fstat(fd, &st); ///struct with information about fd (we need size - bytes count)
	size_t size = st.st_size;
    char* text = mmap(0, size, PROT_READ, MAP_PRIVATE, fd, 0);

    if (text == MAP_FAILED) {
        printf("ERROR");
        return 1;
    }
    int txtLen = strlen(text);
    int maxLen = 0, ///for malloc
        curLen = 0,
        strCount = 0;
    for (i = 0; i < txtLen; i++) {
        curLen++;
        if (maxLen < curLen) {
            maxLen = curLen;
        }
        if (text[i] == '\n') {
            curLen = 0;
            strCount++;
        }
    }

    char **strings = (char**) malloc(strCount * sizeof(char*));
    if (strings == NULL) {
        printf("ERROR");
        return 1;
    }
    int posStr = 0, posCur = 0;
    int prev = 0;
    i = 0;
    while (text[i]) {
        if (text[i] == '\n') {
            strings[posStr] = &text[posCur];
            posStr++;
            posCur = i + 1;
        }
        i++;
    }
    qsort(strings, strCount, sizeof(char*), strCmp);
    for (i = 0; i < strCount; i++) {
        size_t pos = 0;
        while (strings[i][pos] != '\n') {
            fputc(strings[i][pos], fout);
            pos++;
        }
        fputc('\n', fout);
    }
    munmap(text, txtLen);
    free(strings);
    close(fd);
    close(fout);
    return 0;
}
