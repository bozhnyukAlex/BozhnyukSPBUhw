#ifndef STACK_H_INCLUDED
#define STACK_H_INCLUDED

#define MAX_STACK_SIZE 1024 * 1024
#define STACK_OVERFLOW -1
#define STACK_UNDERFLOW -2


typedef struct Stack {
	int data[MAX_STACK_SIZE];
	size_t size; //size and edge pointer
} Stack;

void push(Stack* stack, int value);
void pop(Stack* stack);
int peek(Stack* stack);
int get(Stack* stack, size_t index);
void printStack(Stack* stack);


#endif