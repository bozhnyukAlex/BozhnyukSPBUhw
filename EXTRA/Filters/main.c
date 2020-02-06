#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#define step(x) x > 128 ? 255 : 0

#pragma pack(push, 1)
struct BITMAPFILEHEADER {
	unsigned short	bfType;
	unsigned int bfSize;
	unsigned short bfReserved1;
	unsigned short bfReserved2;
	unsigned int bfOffBits;
};

struct BITMAPINFOHEADER {
	unsigned int biSize;
	unsigned int  biWidth;
	unsigned int  biHeight;
	unsigned short  biPlanes;
	unsigned short  biBitCount;
	unsigned int biCompression;
	unsigned int biSizeImage;
	unsigned int  biXPelsPerMeter;
	unsigned int  biYPelsPerMeter;
	unsigned int biClrUsed;
	unsigned int biClrImportant;
};
#pragma pack(pop)
FILE* input;
FILE* output;

void inputCheck(int argc, char* argv[]) {
	if (argc != 4 || !(strcmp(argv[2], "Average3x3") == 0 || strcmp(argv[2], "Gauss3x3") == 0 || strcmp(argv[2], "Gauss5x5") == 0 || strcmp(argv[2], "Sobel") == 0
		|| strcmp(argv[2], "SobelX") == 0 || strcmp(argv[2], "SobelY") == 0 || strcmp(argv[2], "ColorWB") == 0) || fopen_s(&input, argv[1], "rb") != 0) {
		printf("Invalid input. Try again.");
		exit(0);
	}
}

void useFilter(unsigned char* bmpImage, int height, int width, char* mode) {
	unsigned char* bmpCopy = (unsigned char*) malloc(3 * height * width * sizeof(char));
	if (strcmp("ColorWB", mode) == 0) {
		for (int i = 0; i < width * height * 3; i += 3) {
			unsigned char result = (bmpImage[i] + bmpImage[i + 1] + bmpImage[i + 2]) / 3;
			bmpImage[i] = result;
			bmpImage[i + 1] = result;
			bmpImage[i + 2] = result;
		}
		return;
	}
	int isSobel = 0;
	if (strcmp("SobelX", mode) == 0 || strcmp("SobelY", mode) == 0) {
		isSobel = 1;
	}
	const int size = (strcmp("Gauss5x5", mode) == 0) ? 25 : 9;
	int border = (strcmp("Gauss5x5", mode) == 0) ? 2 : 1,
		divisor = 1;
	int* mask = (int*) malloc(sizeof(int) * size);
	if (strcmp("Average3x3", mode) == 0) {
		for (int i = 0; i < 9; i++) {
			mask[i] = 1;
		}
		divisor = 9;
	}
	else if (strcmp("Gauss3x3", mode) == 0) {
		int temp[9] = { 1, 2, 1, 2, 4, 2, 1, 2, 1 };
		for (int i = 0; i < 9; i++) {
			mask[i] = temp[i];
		}
		divisor = 16;
	}
	else if (strcmp("Gauss5x5", mode) == 0) {
		int temp[25] = {
			1, 4, 6, 4, 1,
			4, 16, 24, 16, 4,
			6, 24, 36, 24, 6,
			4, 16, 24, 16, 4,
			1, 4, 6, 4, 1
		};
		for (int i = 0; i < 25; i++) {
			mask[i] = temp[i];
		}
		divisor = 256;
	}
	else if (strcmp("SobelX", mode) == 0) {
		int temp[9] = { -1, -2, -1, 0, 0, 0, 1, 2, 1 };
		for (int i = 0; i < 9; i++) {
			mask[i] = temp[i];
		}
		divisor = 1;
	}
	else if (strcmp("SobelY", mode) == 0) {
		int temp[9] = { -1, 0, 1, -2, 0, 2, -1 ,0, 1 };
		for (int i = 0; i < 9; i++) {
			mask[i] = temp[i];
		}
		divisor = 1;
	}

	int redRes = 0, greenRes = 0, blueRes = 0, count = 0;
	for (int i = border; i < height - border; i++) {
		for (int j = border; j < width - border; j++) {
			redRes = greenRes = blueRes = count = 0;
			for (int v = -border; v <= border; v++) {
				for (int w = -border; w <= border; w++) {
					redRes += mask[count] * bmpImage[((i + v) * width + (j + w)) * 3];
					greenRes += mask[count] * bmpImage[((i + v) * width + (j + w)) * 3 + 1];
					blueRes += mask[count] * bmpImage[((i + v) * width + (j + w)) * 3 + 2];
					count++;
				}
			}
			redRes /= divisor;
			greenRes /= divisor;
			blueRes /= divisor;
			if (isSobel) {
				int res = step((redRes + greenRes + blueRes) / 3);
				bmpCopy[(i * width + j) * 3] = res;
				bmpCopy[(i * width + j) * 3 + 1] = res;
				bmpCopy[(i * width + j) * 3 + 2] = res;
			}
			else {
				bmpCopy[(i * width + j) * 3] = redRes;
				bmpCopy[(i * width + j) * 3 + 1] = greenRes;
				bmpCopy[(i * width + j) * 3 + 2] = blueRes;
			}
			
		}
	}
	for (int i = 0; i < height * width * 3; i++) {
		bmpImage[i] = bmpCopy[i];
	}
	free(bmpCopy);
	free(mask);
}

int main(int argc, char* argv[]) {
	inputCheck(argc, argv);

	fopen_s(&input, argv[1], "rb");
	fopen_s(&output, argv[3], "wb");
	if (input == NULL || output == NULL) {
		printf("File open error");
		return -1;
	}

	struct BITMAPFILEHEADER bmpFileHeader;
	struct BITMAPINFOHEADER bmpInfoHeader;

	fread(&bmpFileHeader, sizeof(bmpFileHeader), 1, input);
	fread(&bmpInfoHeader, sizeof(bmpInfoHeader), 1, input);

	unsigned char* bmpImage = (unsigned char*)malloc(bmpInfoHeader.biSizeImage);
	if (bmpImage == NULL) {
		printf("Malloc Error");
		return -1;
	}

	fseek(input, bmpFileHeader.bfOffBits, SEEK_SET);
	fread(bmpImage, 1, bmpInfoHeader.biSizeImage, input);

	useFilter(bmpImage, bmpInfoHeader.biHeight, bmpInfoHeader.biWidth, argv[2]);
	//SobelY(bmpImage, bmpInfoHeader.biHeight, bmpInfoHeader.biWidth);

	fwrite(&bmpFileHeader, sizeof(bmpFileHeader), 1, output);
	fwrite(&bmpInfoHeader, sizeof(bmpInfoHeader), 1, output);

	for (int i = 0; i < bmpInfoHeader.biSizeImage; i++) {
		fwrite(&bmpImage[i], 1, 1, output);
	}

	printf("OK");
	free(bmpImage);
	fclose(input);
	fclose(output);
	return 0;
}