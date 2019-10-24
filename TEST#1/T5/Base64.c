#include <stdio.h>
#include <string.h>
char* strToBase64(char* s) {
    char base[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    int buffer = 0,
        index,
        sL = strlen(s),
        mask = 0x3F,
        mxSize = 1000000,
        step = 3;
    char result[mxSize];
    sprintf(result, "");
    for (index = 0; index < sL - sL % 3; index += step) {
        int fChar = s[index],
            sChar = s[index + 1],
            tChar = s[index + 2];
        buffer = (fChar << 16) + (sChar << 8) + tChar;
        int firstChar = (buffer >> 18) & mask,
            secondChar = (buffer >> 12) & mask,
            thirdChar = (buffer >> 6) & mask,
            fourthChar = buffer & mask;
        sprintf(result,"%s%c%c%c%c", result, base[firstChar], base[secondChar], base[thirdChar], base[fourthChar]);
        buffer = 0;
    }

    if (sL % 3 == 1) {
        int fChar = s[sL - 1];
        buffer = fChar << 16;
        int firstChar = (buffer >> 18) & mask,
            secondChar = (buffer >> 12) & mask;
        sprintf(result,"%s%c%c%c%c", result, base[firstChar], base[secondChar], '=', '=');
    }
    else if (sL % 3 == 2) {
        int fChar = s[sL - 2],
            sChar = s[sL - 1];
        buffer = fChar << 16;
        buffer |= (sChar << 8);
        int firstChar = (buffer >> 18) & mask,
            secondChar = (buffer >> 12) & mask,
            thirdChar = (buffer >> 6) & mask;
        sprintf(result,"%s%c%c%c%c", result, base[firstChar], base[secondChar], base[thirdChar], '=');
    }
    return result;
}

char* base64ToStr(char *s) {
    char base[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    int index, Jindex,
        sL = strlen(s),
        bL = 64,
        buffer = 0,
        mask = 0xFF,
        mxSize = 1000000,
        step = 4;
    char result[mxSize];
    sprintf(result, "");
    for (index = 0; index < sL; index += step) {
        int fChar = s[index],
            sChar = s[index + 1],
            tChar = s[index + 2],
            frChar = s[index + 3],
            fI = -1,
            sI = -1,
            tI = -1,
            frI = -1;
        for (Jindex = 0; Jindex < bL; Jindex++) {
            if (base[Jindex] == fChar) {
                fI = Jindex;
            }
            if (base[Jindex] == sChar) {
                sI = Jindex;
            }
            if (base[Jindex] == tChar) {
                tI = Jindex;
            }
            if (base[Jindex] == frChar) {
                frI = Jindex;
            }
        }
        if (tI == -1) {
            buffer = (fI << 18) + (sI << 12);
        }
        else if (tI != -1 && frI == -1) {
            buffer = (fI << 18) + (sI << 12) + (tI << 6);
        }
        else if (tI != -1 && frI != -1) {
            buffer = (fI << 18) + (sI << 12) + (tI << 6) + frI;
        }
        char firstChar = (buffer >> 16) & mask,
             secondChar = (buffer >> 8) & mask,
             thirdChar = buffer & mask;
        sprintf(result, "%s%c%c%c", result, firstChar, secondChar, thirdChar);
    }
    return result;
}
int main() {
    int mxSize = 1000000;
    char s[mxSize];
    int option = 0;
    printf("Input String: ");
    gets(s);
    printf("Choose the option: \n 1 - Encode \n 2 - Decode \nYour option: ");
    scanf("%d", &option);
    switch (option) {
        case 1: {
            printf("Result: ");
            puts(strToBase64(s));
            break;
        }
        case 2: {
            printf("Result: ");
            puts(base64ToStr(s));
            break;
        }
        default: {
            printf("Wrong option!");
            break;
        }
    }
    return 0;
}
