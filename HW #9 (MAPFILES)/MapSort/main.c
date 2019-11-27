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
	int size = st.st_size, osize = 0;
    char *text = mmap(0, size, PROT_READ, MAP_PRIVATE, fd, 0);
    char *res = (char*) malloc(size * sizeof(int));
   // puts(text);
    if (text == MAP_FAILED || res == NULL) {
        printf("ERROR");
        return 1;
    }
    int txtLen = strlen(text);
    int maxLen = -1, ///for malloc
        curLen = 0,
        strCount = 1;
    for (i = 0; i < txtLen; i++) {
        if (text[i] != '\n') {
            curLen++;
        }
        else {
            if (maxLen < curLen) {
                maxLen = curLen;
            }
            osize += (curLen + 1);
            curLen = 0;
            strCount++;
        }
    }

   // printf("!!! %d %d\n", strCount, maxLen);
    char **strings = (char**) malloc(strCount * sizeof(char*));
    char *cur = (char*) malloc(maxLen * sizeof(char));

    if (strings == NULL) {
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
        //j++;
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
  //  printf("\n");
    qsort(strings, strCount, sizeof(char*), strCmp);

//    for (i = 0; i < strCount; i++) {
//        puts(strings[i]);
//    }
    sprintf(res, "");
    for (i = 0; i < strCount; i++) {
        if (i == 0) {
            sprintf(res, "%s", strings[i]);
        }
        else {
            sprintf(res, "%s\n%s", res, strings[i]);
        }
        osize += strlen(strings[i]);

    }
  //  puts(res);


    write(fdout, res, osize);
    for (i = 0; i < strCount; i++) {
        free(strings[i]);
    }
    free(strings);
    free(cur);
    free(res);
    munmap(text, size);
    close(fd);
    return 0;
}
