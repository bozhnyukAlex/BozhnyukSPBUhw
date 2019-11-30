#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <malloc.h>
#include <sys/stat.h>
#include "mman-win32-master/mman.h"
#define MAX_STR_LEN 100
#define MAX_STR_CNT 10000

int strCmp (const void * a, const void * b) {
  return strcmp(*(char**)a, *(char**)b);
}
int main() {
    int fd = open("content.txt", O_RDWR, 0);
    int fdout = open("output.txt", O_WRONLY | O_CREAT | O_TRUNC, S_IWRITE);
    int i = 0, j = 0;
    if (fd == -1) {
        printf("ERROR");
        return 1;
    }
    struct stat st;
	fstat(fd, &st); ///struct with information about fd (we need size - bytes count)
	int size = st.st_size;
    char* text = mmap(0, size, PROT_READ, MAP_PRIVATE, fd, 0);

   // puts(text);
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

   // printf("!!! %d %d\n", strCount, maxLen);
    char **strings = (char**) malloc(strCount * sizeof(char*));
    char *cur = (char*) malloc(maxLen * sizeof(char));

    if (strings == NULL || cur == NULL) {
        printf("ERROR");
        return 1;
    }
    int pos = 0;
    for (i = 0; i < strCount;) {
        j = 0;
        for (; text[pos] != '\n' && pos < txtLen; pos++) {
            cur[j] = text[pos];
            j++;
        }
        cur[j] = '\0';
        pos++;
        strings[i] = strdup(cur);
        i++;
        if (pos >= txtLen) {
            break;
        }
    }

//    for (i = 0; i < strCount; i++) {
//        puts(strings[i]);
//    }
//    printf("\n");
    qsort(strings, strCount, sizeof(char*), strCmp);
//    for (i = 0; i < strCount; i++) {
//        puts(strings[i]);
//    }
    char *res = (char*) malloc(txtLen * sizeof(char));
    if (res == NULL) {
        printf("ERROR");
        return 1;
    }
    sprintf(res, "");
    for (i = 0; i < strCount; i++) {
        if (i == 0) {
            sprintf(res, "%s", strings[i]);
        }
        else {
            sprintf(res, "%s\n%s", res, strings[i]);
        }
    }

    write(fdout, res, txtLen);
    for (i = 0; i < strCount; i++) {
        free(strings[i]);
    }
    munmap(text, txtLen);
    free(strings);
    free(cur);
    free(res);
    close(fd);
    close(fdout);
    return 0;
}
