#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "hashTable.h"
#include "stack.h"

#define MEMORY_SIZE 1024 * 1024
#define MAX_ADDR = MEMORY_SIZE / sizeof(int)
#define MAX_STACK_SIZE 1024 * 1024
#define MAX_LINES 1024
#define LINE_SIZE 1000
#define TABLE_SIZE 1000
#define NO_OP 0
#define INT_OP 1
#define STR_OP 2
#define INPUT_ERROR -1
#define NO_OP_ERROR -2

int isGoodLiteral(char x) {
	if (x >= 'a' && x <= 'z') { //rewrite!
		return 1;
	}
	if (x >= 'A' && x <= 'Z') {
		return 1;
	}
	if (x >= '0' && x <= '9') {
		return 1;
	}
	if (x == '_' || x == '&' || x == '$') {
		return 1;
	}
	return 0;
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
	int memory[MEMORY_SIZE];
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
	interpreter->program.lableToLine = createTable(TABLE_SIZE, hashPolynom);
	interpreter->state.ip = 0;
	interpreter->state.stack.size = 0;
	char str[LINE_SIZE];
	int hasLabel = 0;
	int lineNum = -1;
	int lowPos = 0;

	while (!feof(input)) {
		fgets(str, LINE_SIZE, input);
		lineNum++;
		hasLabel = 0;
		int i = 0;
		while (str[i] == ' ' && str[i] != 0 && str[i] != '\n') {
			i++;
		}
		if (str[i] == 0 || str[i] == '\n') {
			///Error: empty String?!
			printf("Error Input!103");
			return -1;
		}
		if (!isGoodLiteral(str[i])) {
			///Error: input error
			printf("Error Input!108");
			return -1;
		}
		lowPos = i;
		//add \n
		while (isGoodLiteral(str[i]) && str[i] != 0) {
			i++;
		}
		if (str[i] != ' ' && str[i] != ':' && str[i] != '\n' && str[i] != 0) {
			///Error: input error
			printf("Error Input!118");
			return -1;
		}
		char* cur = (char*)malloc((i + 1 - lowPos) * sizeof(char)); //free cur when free all operands[]
		if (cur == 0) {
			printf("Malloc error!\n");
			return -1;
		}
		char* copy;
		int j;
		for (j = lowPos; j < i; j++) {
			cur[j - lowPos] = str[j];
		}
		cur[j - lowPos] = '\0';
		if (str[i] == ':') { //cur is label
			hasLabel = 1;
			copy = (char*)malloc((i + 1 - lowPos) * sizeof(char));
			strcpy(copy, cur);
			if (getEl(&interpreter->program.lableToLine, copy) != NULL) {
				///Error: laber already exists!
				printf("Laber already exists!134");
				return -1;
			}
			insertEl(&interpreter->program.lableToLine, copy, lineNum);
			if (str[i + 1] == 0) {
				///Error: end of string?!
				printf("Input Error!140");
				return -1;
			}
			if (str[i + 1] != ' ' && !isGoodLiteral(str[i + 1])) {
				printf("Error Input!144");
				return -1;
			}
			i++;
			while (str[i] == ' ' && str[i] != 0 && str[i] != '\n') {
				i++;
				if (str[i] == 0 || str[i] == '\n') {
					///Error: no commands input error
				}
			}
			lowPos = i;
			while (isGoodLiteral(str[i])) {
				i++;
			} /// i is after cmd now
			for (j = lowPos; j < i; j++) {
				cur[j - lowPos] = str[j];
			}
			cur[j - lowPos] = '\0';
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
			///Error wrong operator
			printf("Error Input!192");
			return -1;
		}

		if (command == LD || command == ST || command == LDC) {
			///int operand
			///check argument (only numbers)
			if (hasLabel && str[i] != ' ') {
				///Error: invalid input
				printf("Error Input!202");
				return -1;
			}
			while (str[i] == ' ' && str[i] != 0 && str[i] != '\n') {
				i++;
				if (str[i] == 0 || str[i] == '\n') {
					///Error: input error
					printf("Error Input!209");
					return -1;
				}
			}
			lowPos = i;
			while (isDigit(str[i])) {
				i++;
			}
			if (str[i] != 0 && str[i] != ' ' && str[i] != '\n') {
				/// Error: invalid operand input
				printf("Error: Invalid operand input!219");
				return -1;
			}
			char* op = (char*) malloc((i - lowPos + 1) * sizeof(int));
			if (op == NULL) {
				printf("Malloc error!\n");
				return -1;
			}

			int k; 
			for (k = lowPos; k < i; k++) {
				op[k - lowPos] = str[k];
			}
			op[k - lowPos] = '\0';

			int arg = atoi(op); ///we have an operand

			while (str[i] == ' ') {
				i++;
			}
			if (str[i] != 0 && str[i] != '\n') {
				///Error: invalid input
				printf("Error Input!237");
				return -1;
			}

			interpreter->program.operations[lineNum].opType = 1;
			interpreter->program.operations[lineNum].intOpCmd.arg = arg;
			interpreter->program.operations[lineNum].intOpCmd.opCode = command;
			free(op);
		}
		else if (command == ADD || command == SUB || command == CMP || command == RET) {
			///no operand
			///check: no literal (maybe spaces only)
			if (hasLabel && str[i] != ' ' && str[i] != 0) {
				///Error: invalid input -wrong symbol
				printf("Error Input!250");
				return -1;
			}
			while (str[i] == ' ' && str[i] != 0 && str[i] != '\n') {
				i++;
			}
			if (str[i] != 0 && str[i] != '\n') {
				///Error: input error
				printf("Error Input!258");
				return -1;
			}
			interpreter->program.operations[lineNum].opType = 0;
			interpreter->program.operations[lineNum].noOpCmd.opCode = command;
		}
		else if (command == JMP || command == BR) {
			///string operand
			/// LABEL
			while (str[i] == ' ' && str[i] != 0 && str[i] != '\n') {
				i++;
				if (str[i] == 0 || str[i] == '\n') {
					///Error: input error
					printf("Error Input!271");
					return -1;
				}
			}
			lowPos = i;
			while (isGoodLiteral(str[i])) {
				i++;
			}
			if (str[i] != 0 && str[i] != ' ' && str[i] != '\n') {
				/// Error: invalid operand input
				printf("Error: Invlid operand input!281");
				return -1;
			}
			char* op = (char*) malloc((i - lowPos + 1) * sizeof(int));

			int k;
			for (k = lowPos; k < i; k++) {
				op[k - lowPos] = str[k];
			}
			op[k - lowPos] = '\0';

			/// has this label???
			///label can be in forward!
			/*if (getEl(&interpreter->program.lableToLine, op) == NULL) {
				///Error: no such  label!
				printf("Error: No such label!294");
				return -1;
			}*/

			while (str[i] == ' ') {
				i++;
			}
			if (str[i] != 0 && str[i] != '\n') {
				///Error: invalid input
				printf("Error Input!303");
				return -1;
			}
			interpreter->program.operations[lineNum].opType = 2;
			interpreter->program.operations[lineNum].strOpCmd.label = op;
			interpreter->program.operations[lineNum].strOpCmd.opCode = command;
		}
		else {
			/// Error: invalid command input
			printf("Error Input!312");
			return -1;
		}
	}
	printTable(&interpreter->program.lableToLine);
	printf("%d\n", lineNum);

	while (interpreter->state.ip <= lineNum) {
		switch (interpreter->program.operations[interpreter->state.ip].opType) {
			case NO_OP: {
				if (interpreter->state.stack.size <= 2 && interpreter->program.operations[interpreter->state.ip].noOpCmd.opCode != RET) {
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
						///ret check!
						if (interpreter->state.ip != lineNum) { ///need to fix, we need to check this earlier
							printf("INPUR ERROR!");
							return INPUT_ERROR;
						}
						///maybe all free here
						break;
					}
				}
				interpreter->state.ip++;
				break;
			}
			case INT_OP: {
				switch (interpreter->program.operations[interpreter->state.ip].intOpCmd.opCode) {
					case LD: {
						break;
					}
					case ST: {
						break;
					}
					case LDC: {
						break;
					}
				}
				break;
			}
			case STR_OP: {
				switch (interpreter->program.operations[interpreter->state.ip].strOpCmd.opCode) {
					case JMP: {
						///label check!
						break;
					}
					case BR: {
						break;
					}

				}
				break;
			}
		}
	}
	fclose(input);
	return 0;
}