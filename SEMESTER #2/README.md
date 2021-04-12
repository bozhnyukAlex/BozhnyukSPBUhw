# Second Semester: Interpreter, Java, OOP
## Task 1: Interpreter
1. Implement the interpreter of the [mini-assembler][1]
2. Calculate the sum of odd Fibonacci numbers not exceeding N (set by a constant in the source code). Using mini-assembler, of course.
3. There is only one kind of conditional branch in the command system - ``br``. It is easy to build bytecode for ``if (x == y) A else B``: 
```
ldc x
ldc y
cmp
br else
A
jmp endif
else: B
endif: ret
```
How to build bytecode for ``if (x < y)``, ``if (x <= y)``? 

4. In the source code of OpenJDK, find the place of the main loop - an analogue of what you had to write in the first task.

## Task 2: Classes and objects
Implement object hierarchy:
Create an abstract class "car" with some set of characteristics at your discretion (maximum speed, capacity, country of origin, etc.), also a method or property that allows you to get complete, aggregated information about the model. Implement a description of several specific car models.  
Move the base class and implementations into separate libraries. In the main program, output the required information to the console through the methods or properties of the abstract class.  
Unit tests are required (and for all tasks below).

## Task 3: Game implementation
1. Implement any board game (Battleship, Tic-tac-toe, card games). The game launch parameters must include an array of players of the appropriate size. The player can be a human or an AI player with multiple behaviors (eg smart, greedy, random).
2. Make a UML class diagram of the game
3. Implement a graphical user interface for the game: current state, select a move for the human player, display the move made by the AI player.
4. Implement plug-in support for your game (new types of AI, new languages) or an arbitrary application (new formats, new operations) written specifically for this task.
5. Instantiate your game's classes using an arbitrary IoC container.

## Task 4: UI Application
Write an application that builds an algebraic curve of at least second order on the screen from several predefined ones (coordinate axes and the curve itself). For at least one curve of order N, the coefficients for both X^N and Y^N must be nonzero. The list of curves is displayed in the combo box. Implement the ability to scale the displayed image. Classes that implement math related to curves should be placed in a separate shared library.



[1]: http://edu.vpolozov.name/arhiv/2010-i/vitasm
