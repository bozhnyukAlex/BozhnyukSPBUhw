#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <crtdbg.h>
#include <stdint.h>
#include "hashTable.h"
#include "stack.h"

#define MEMORY_SIZE 1024 * 1024
#define MAX_ADDR MEMORY_SIZE / sizeof(int32_t)
#define MAX_STACK_SIZE 1024 * 1024
#define MAX_LINES 1024
#define LINE_SIZE 1000
#define TABLE_SIZE 1000
#define NO_OP 0
#define INT_OP 1
#define STR_OP 2
#define INPUT_ERROR -1
#define NO_OP_ERROR -2
#define MALLOC_ERROR -3

int isGoodLiteral(char x) {
	if ((x >= 'a' && x <= 'z') || (x >= 'A' && x <= 'Z') || (x >= '0' && x <= '9') || (x == '_' || x == '&' || x == '$')) {
		return 1;
	}
	return 0;
}

void error(int line) {
	printf("INPUT ERROR IN LINE %d!\n", line);
	exit(INPUT_ERROR);
}
void mallocCheck(char* x) {
	if (x == NULL) {
		printf("MALLOC ERROR!\n");
		exit(MALLOC_ERROR);
	}
}

int isDigit(char x) {
	if (x >= '0' && x <= '9') {
		return 1;
	}
	return 0;
}

enum OpCodes {
	LD = 1, ST, LDC, ADD, SUB, CMP, JMP, BR, RET
};

struct State {
	Stack stack;
	int32_t memory[MAX_ADDR];
	size_t ip;
};

struct NO_OPERAND {
	int opCode; //from enum
};

struct INT_OPERAND {
	int opCode;
	int arg;
};

struct STRING_OPERAND {
	int opCode;
	char* label;
};

struct CMD {
	int opType; // 0 - noOp, 1 - intOp, 2 - strOp
	union {
		struct NO_OPERAND noOpCmd;
		struct INT_OPERAND intOpCmd;
		struct STRING_OPERAND strOpCmd;
	};
};

struct Program {
	struct CMD operations[MAX_LINES];
	struct HashTable lableToLine;
};
struct Interpreter {
	struct Program program;
	struct State state;
};
int main() {
	FILE* input;
	if ((input = fopen("input.txt", "r")) == NULL) {
		printf("Problem with opening file\n");
		return -1;
	}

	struct Interpreter* interpreter = (struct Interpreter*) malloc(sizeof(struct Interpreter));
	if (interpreter == NULL) {
		printf("Malloc error!\n");
		return MALLOC_ERROR;
	}
	interpreter->program.lableToLine = createTable(TABLE_SIZE, hashPolynom);
	interpreter->state.ip = 0;
	interpreter->state.stack.size = 0;
	char str[LINE_SIZE];
	int hasLabel = 0;
	int lineNum = -1;
	size_t lowPos = 0;
	int hasRet = 0;

	while (!feof(input)) {
		fgets(str, LINE_SIZE, input);
		lineNum++;
		hasLabel = 0;
		size_t i = 0;
		while (str[i] == ' ' && str[i] != 0 && str[i] != '\n') {
			i++;
		}
		if (str[i] == 0 || str[i] == '\n') {
			///Error: empty String?!
			error(lineNum);
		}
		if (!isGoodLiteral(str[i])) {
			error(lineNum);
		}
		lowPos = i;
		while (isGoodLiteral(str[i]) && str[i] != 0 && str[i] != '\n') {
			i++;
		}
		if (str[i] != ' ' && str[i] != ':' && str[i] != '\n' && str[i] != 0) {
			error(lineNum);
		}
		char* cur;
		char* lbl;
		size_t j;
		if (str[i] == ':') { //we have label
			hasLabel = 1;
			
			lbl = (char*) malloc((i + 1 - lowPos) * sizeof(char)); //free while cleaning table
			if (lbl == NULL) {
				printf("Malloc Error!\n");
				return MALLOC_ERROR;
			}
			for (j = lowPos; j < i; j++) {
				lbl[j - lowPos] = str[j];
			}
			lbl[j - lowPos] = '\0';

			if (getEl(&interpreter->program.lableToLine, lbl) != NULL) {
				printf("Error: this label already exists!\n");
				return INPUT_ERROR;
			}
			
			insertEl(&interpreter->program.lableToLine, lbl, lineNum);
			if (str[i + 1] == 0 || str[i + 1] == '\n') {
				error(lineNum);
			}
			if (str[i + 1] != ' ' && !isGoodLiteral(str[i + 1])) {
				error(lineNum);
			}
			i++;
			while (str[i] == ' ' && str[i] != 0 && str[i] != '\n') {
				i++;
				if (str[i] == 0 || str[i] == '\n') {
					error(lineNum);
				}
			}
			
			lowPos = i;
			while (isGoodLiteral(str[i])) {
				i++;
			} /// i is after cmd now
			cur = (char*) malloc((i + 1 - lowPos) * sizeof(char));
			mallocCheck(cur);
			for (j = lowPos; j < i; j++) {
				cur[j - lowPos] = str[j];
			}
			cur[j - lowPos] = '\0';
		}
		else if (str[i] == ' ' || str[i] == '\n' || str[i] == 0){ //we have command
			cur = (char*) malloc((i + 1 - lowPos) * sizeof(char));
			if (cur == NULL) {
				printf("Malloc error!\n");
				return MALLOC_ERROR;
			}
			for (j = lowPos; j < i; j++) {
				cur[j - lowPos] = str[j];
			}
			cur[j - lowPos] = '\0';
		}
		else {
			error(lineNum);
			return INPUT_ERROR;
		}
		

		int command;
		if (strcmp("ld", cur) == 0) {
			command = LD;
		}
		else if (strcmp("st", cur) == 0) {
			command = ST;
		}
		else if (strcmp("ldc", cur) == 0) {
			command = LDC;
		}
		else if (strcmp("add", cur) == 0) {
			command = ADD;
		}
		else if (strcmp("sub", cur) == 0) {
			command = SUB;
		}
		else if (strcmp("cmp", cur) == 0) {
			command = CMP;
		}
		else if (strcmp("ret", cur) == 0) {
			command = RET;
		}
		else if (strcmp("jmp", cur) == 0) {
			command = JMP;
		}
		else if (strcmp("br", cur) == 0) {
			command = BR;
		}
		else {
			error(lineNum);
		}
		if (hasRet == 1) { // ret check!
			error(lineNum);
		}
		free(cur);
		if (command == LD || command == ST || command == LDC) {
			if (hasLabel && str[i] != ' ') {
				error(lineNum);
			}
			while (str[i] == ' ' && str[i] != 0 && str[i] != '\n') {
				i++;
				if (str[i] == 0 || str[i] == '\n') {
					error(lineNum);
				}
			}
			lowPos = i;
			while (isDigit(str[i])) {
				i++;
			}
			if (str[i] != 0 && str[i] != ' ' && str[i] != '\n') {
				error(lineNum);
			}
			char* op = (char*) malloc((i - lowPos + 1) * sizeof(int));
			mallocCheck(op);

			size_t k; 
			for (k = lowPos; k < i; k++) {
				op[k - lowPos] = str[k];
			}
			op[k - lowPos] = '\0';

			int arg = atoi(op); ///we have an operand

			while (str[i] == ' ') {
				i++;
			}
			if (str[i] != 0 && str[i] != '\n') {
				error(lineNum);
			}

			interpreter->program.operations[lineNum].opType = INT_OP;
			interpreter->program.operations[lineNum].intOpCmd.arg = arg;
			interpreter->program.operations[lineNum].intOpCmd.opCode = command;
			free(op);
		}
		else if (command == ADD || command == SUB || command == CMP || command == RET) {
			if (hasLabel && str[i] != ' ' && str[i] != 0 && str[i] != '\n') {
				error(lineNum);
			}
			while (str[i] == ' ' && str[i] != 0 && str[i] != '\n') {
				i++;
			}
			if (str[i] != 0 && str[i] != '\n') {
				error(lineNum);
			}
			if (command == RET) {
				hasRet = 1;
			}
			interpreter->program.operations[lineNum].opType = NO_OP;
			interpreter->program.operations[lineNum].noOpCmd.opCode = command;
		}
		else if (command == JMP || command == BR) {
			while (str[i] == ' ' && str[i] != 0 && str[i] != '\n') {
				i++;
				if (str[i] == 0 || str[i] == '\n') {
					error(lineNum);
				}
			}
			lowPos = i;
			while (isGoodLiteral(str[i])) {
				i++;
			}
			if (str[i] != 0 && str[i] != ' ' && str[i] != '\n') {
				error(lineNum);
			}
			char* op = (char*) malloc((i - lowPos + 1) * sizeof(int));
			mallocCheck(op);

			size_t k;
			for (k = lowPos; k < i; k++) {
				op[k - lowPos] = str[k];
			}
			op[k - lowPos] = '\0';


			while (str[i] == ' ') {
				i++;
			}
			if (str[i] != 0 && str[i] != '\n') {
				error(lineNum);
			}
			interpreter->program.operations[lineNum].opType = STR_OP;
			interpreter->program.operations[lineNum].strOpCmd.label = op;
			interpreter->program.operations[lineNum].strOpCmd.opCode = command;
			
		}
		else {
			error(lineNum);
		}
	}
///	printTable(&interpreter->program.lableToLine);
	if (!hasRet) {
		printf("Your program needs to contain ret command!\n");
		return INPUT_ERROR;
	}
	while (interpreter->state.ip <= lineNum) {
		switch (interpreter->program.operations[interpreter->state.ip].opType) {
			case NO_OP: {
				if (interpreter->state.stack.size < 2 && interpreter->program.operations[interpreter->state.ip].noOpCmd.opCode != RET) {
					printf("NO SUCH OPERANDS!");
					return NO_OP_ERROR;
				}
				switch (interpreter->program.operations[interpreter->state.ip].noOpCmd.opCode) {
					case ADD: {
						int first = peek(&interpreter->state.stack),
							second = get(&interpreter->state.stack, interpreter->state.stack.size - 2);
						int sum = first + second;
						push(&interpreter->state.stack, sum); // check overflow: in stack.c
						break;
					}
					case SUB: {
						int first = peek(&interpreter->state.stack),
							second = get(&interpreter->state.stack, interpreter->state.stack.size - 2);
						int sub = first - second;
						push(&interpreter->state.stack, sub); // check overflow: in stack.c
						break;
					}
					case CMP: {
						int first = peek(&interpreter->state.stack),
							second = get(&interpreter->state.stack, interpreter->state.stack.size - 2);
						int res;
						if (first == second) {
							res = 0;
						}
						else if (first < second) {
							res = -1;
						}
						else if (first > second) {
							res = 1;
						}
						push(&interpreter->state.stack, res); // check overflow: in stack.c
						break;
					}
					case RET: {
						printStack(&interpreter->state.stack);
						break;
					}
				}
				interpreter->state.ip++;
				break;
			}
			case INT_OP: {
				int oper = interpreter->program.operations[interpreter->state.ip].intOpCmd.arg; ///32 bit
				switch (interpreter->program.operations[interpreter->state.ip].intOpCmd.opCode) {
					case LD: {
						if (oper < 0 || oper >= MAX_ADDR) {
							error(lineNum);
						}
						push(&interpreter->state.stack, interpreter->state.memory[oper]);
						break;
					}
					case ST: {
						if (oper < 0 || oper >= MAX_ADDR) {
							error(lineNum);
						}
						interpreter->state.memory[oper] = peek(&interpreter->state.stack);
						pop(&interpreter->state.stack);
						break;
					}
					case LDC: {
						push(&interpreter->state.stack, oper);
						break;
					}
				}
				interpreter->state.ip++;
				break;
			}
			case STR_OP: {
				char* label = interpreter->program.operations[interpreter->state.ip].strOpCmd.label;
				if (getEl(&interpreter->program.lableToLine, label) == NULL) { //label check
					printf("NO SUCH LABEL!\n");
					return INPUT_ERROR;
				}
				int line = getEl(&interpreter->program.lableToLine, label)->data;
				switch (interpreter->program.operations[interpreter->state.ip].strOpCmd.opCode) {
					case JMP: {
						interpreter->state.ip = line;
						break;
					}
					case BR: {
						if (peek(&interpreter->state.stack) != 0) {
							interpreter->state.ip = line;
						}
						else {
							interpreter->state.ip++;
						}
						break;
					}
				}
				break;
			}
		}
//		printf("%d ||", interpreter->state.ip - 1);
//		printStack(&interpreter->state.stack);
	}
	cleanTable(&interpreter->program.lableToLine);
	for (size_t i = 0; i <= lineNum; i++) {
		if (interpreter->program.operations[i].opType == STR_OP) {
			free(interpreter->program.operations[i].strOpCmd.label);
		}
	}
	free(interpreter);
	fclose(input);
	_CrtDumpMemoryLeaks();
	return 0;
}