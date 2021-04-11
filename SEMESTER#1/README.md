# First semester: Introduction, C language
## Task 1: Bits
Solve [problems from here][1] using simple bitwise operations.
## Task 2: Git Tutorial
Solve tasks from [tutorial][2].
## Task 3: Strings
Implement some standard string functions:
- ``size_t strlen(char*)`` - length of string 
- ``strcpy(char* dst, char* src)`` - copy string
- ``strcat(char* dst, char* src)`` - concate strings
- ``int strcmp(char* s1, char* s2)`` - lexicographic comparison of strings. Comparison result: 0 - equal, < 0 - the first is less, > 0 - the first is greater.
## Task 4: Memory
Print out several addresses of different types of entities:
- local variables,
- global variables,
- custom functions,
- system functions,
- dynamically allocated variables.  
Assess which types of entities are nearby and which are in different areas of memory.
Is the observed behavior consistent with theory?
## Task 5: Test
1. Calculate the n-th Fibonacci number with any way. Correctness within the selected data type. Overflow checking.
2. Implement a function to get a number from a string. The solution should be suitable for practical use: input data validation, error indication.
3. Input: N - a positive integer and N numbers from the range 0-255.  
Output: the unique values in the order you entered.
Example: 4 9 5 6 6 5 4 -> 4 9 5 6
4. Implement the conversion of the source text to Base64 (and vice versa) with padding support
## Task 5: Exploit and Endianness
1. Implement a buffer overflow attack by writing a known unsafe read function.
The code contains the following functions:
- ``main``,
- ``input``,
- ``other``.
``main`` calls ``input``, specially matched input results in a call to other (overwritten return address). ``other`` prints something to demonstrate that it was called. Correct continuation of work is not required.
``main`` or ``input`` print any necessary information: function addresses, stack contents.
2. Determine which platform the program is running on: little- or big-endian.
## Task 6: Sorting Algorithms and Memory Mapping
1. Implement three sorting algorithms: counting sort, based on comparisons with asymptotic complexity O(n*log (n)) and based on comparisons with asymptotic complexity O(n^2). Measure the running time and fill in the table on input data sets of different sizes: 5, 10, 100, 1k, 10k, 100k, 1M, 10M, 100M. Value n/a is allowed - did not wait for an answer.
Arrays can be filled with random numbers. For reproducibility of the result, the generator should not be initialized with the current time, but with some number.
2. Write a program to sort strings in a text file using the memory mapped files mechanism. Lines in the file are terminated according to the operating system rules (``\n`` or ``\r\n``).
## Task 7: Linked List
1. Implement data structures and basic operations for working with singly linked lists. It needs to be designed as a library suitable for reuse in subsequent tasks.
Operations: 
- create  
- insert at the beginning 
- insert at the end
- insert after 
- delete items by condition (the value is equal to the entered one) 
- print 
- clear.
2. Implement an interface of the following type:  
```
1. Print
2. Op 1
3. Op 2
...
n. Exit
```
3. Implement a function that determines the presence of a cycle in a given singly linked list with O(1) memory.  
For testing, add a ``find a cycle`` and ``create a cycle`` to the interface of the previous program.
## Task 8: Hash Table
1. Implement a set of functions for working with a Hash Table: 
- create (hash function is passed as a parameter) 
- delete table 
- set element 
- delete element
- get element 
- collect statistics (number of non-empty cells, number of elements, average chain length, minimum non-zero chain length, maximum length chains, ...)
2. Count the number of different words used in the text of any fairly large classic book from the public domain, and the number of times each has been used. A word is understood as any sequence of characters of non-zero length between spaces (tabs, line breaks, ...), stripped of punctuation marks. Different word forms are different words.  
Measure and tabulate the execution time and statistics mentioned in the previous task for different hash functions.
3. Check for memory leaks with some tool: Valgrind, gcc built-in, others. Ensure that there are no memory leaks in the final version. 


[1]: https://www.dropbox.com/s/3y18r7a7er1qj96/task_1_171_2015.pdf?dl=0
[2]: https://learngitbranching.js.org/?locale=ru_EN
