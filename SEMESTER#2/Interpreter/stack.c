#include <stdio.h>
#include <stdlib.h>

#define MAX_STACK_SIZE 1024 * 1024
#define STACK_OVERFLOW -1
#define STACK_UNDERFLOW -2
#define GET_ERROR -3

typedef struct Stack {
	int data[MAX_STACK_SIZE];
	size_t size; //size and edge pointer
} Stack;

void push(Stack* stack, int value) {
	if (stack->size >= MAX_STACK_SIZE) {
		printf("STACK OVERFLOW!\n");
		exit(STACK_OVERFLOW);
	}
	stack->data[stack->size] = value;
	stack->size++;
}	

void pop(Stack* stack) {
	if (stack->size <= 0) {
		printf("STACK UNDERFLOW!\n");
		exit(STACK_UNDERFLOW);
	}
	stack->size--;
}
int peek(Stack* stack) {
	if (stack->size <= 0) {
		printf("STACK UNDERFLOW!\n");
		exit(STACK_UNDERFLOW);
	}
	return stack->data[stack->size -1];
}
int get(Stack* stack, size_t index) {
	if (index < 0 || (index > stack->size - 1 && stack->size != 0) || stack->size == 0) {
		printf("GET ERROR!\n");
		exit(GET_ERROR);
	}
	return stack->data[index];
}

void printStack(Stack* stack) {
	int i = 0;
	int len = stack->size - 1;
	printf("stack %d : ", stack->size);
	for (i = 0; i < len; i++) {
		printf("%d | ", stack->data[i]);
	}
	if (stack->size > 0) {
		printf("%d", stack->data[i]);
	}
	printf("\n");
}