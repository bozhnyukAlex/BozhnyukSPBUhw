# Third Semester: Multithreading

## Task 1: Lock implementation
1. Write thread-unsafe code (counter / using StringBuilder / something of your own).
- Make sure the result is incorrect.
- Achieve correctness using lock from the Java standard library.
- Implement your lock using Peterson's algorithm.
- Replace the built-in lock with your own.
- Check if the correctness of the program is preserved.
2. Check by measurements (with JMH) that the performance graphs of TAS and TTAS locks are similar to those predicted by theory. Use atomic bool from the Java standard library.

## Task 2: Filters
1. Write a program that receives the following data from the command line:
- The name of the input file.
- Filter type.
- The name of the output file.
The input file is an uncompressed 24-bit or 32-bit BMP file, but possibly with a palette.  
It is required to read image data from it and apply one of the following filters to it according to the passed parameter:  
- Averaging filter 3x3.
- Gaussian averaging filter 3x3 or 5x5. It is permissible to use other filter sizes if the Gaussian parameters are selected correctly.
- Sobel filter by X. It is permissible to use the Sharr filter instead.
- Sobel filter by Y. It is permissible to use the Sharr filter instead.
- Converting an image from color to grayscale.
The resulting image must be saved to the specified path of the output file in BMP format. The resulting file should be opened with standard image viewers.
A possible example of a command line for using the program: ``MyInstagram C:\Temp\1.bmp SobelX C:\Temp\2.bmp`` 
2. Apply a filter to an image using multiple streams in parallel, splitting pixels into horizontal and vertical stripes.
3. Analyze the impact on acceleration of the number of threads, the direction of splitting and the size of the picture. For measurements, use JMH or another benchmark framework.  
Read and write should not participate in measurements.

## Task 3: Parallel Algorithms
1. Read [the article][1]
2. Implement an algorithm 3.3 (+ single-threaded version)
3. Exercise 8. Find a solution. Implement (+ single threaded version).
4. Exercise 9. Find a solution. Implement (+ single threaded version).
5. Exercise 10. Find a solution. Implement (+ single threaded version).

## Task 4: ThreadPool and Parallel Collections
1. Implement any simple numerical integration algorithm.
Tasks are read from a file. Each contains a polynomial and integration boundaries.
If the polynomial has been encountered previously, and the integration boundaries are contained within the new boundaries, then the cached value should be used.
To compute all jobs, use a thread pool and a cacheable collection from the standard library.
2. Replace the used multithreaded collections with your own implementation. The implementation should be more complex than the "rough" lock (synchronized (this) in each method).
3. Replace the standard thread pool with your implementation.
4. Measure performance. Dependence on the number of threads, standard/custom collections, standard/custom thread pool.

## Task 4.1: Reports
1. Watch ["The pragmatics of the Java Memory Model"][2]
2. Watch ["Spring-builder"][3]
3. Watch ["ForkJoinPool in Java 8"][4]

[1]: http://www.toves.org/books/distalg/distalg.pdf
[2]: https://www.youtube.com/watch?v=iB2N8aqwtxc
[3]: https://www.youtube.com/watch?v=rd6wxPzXQvo
[4]: https://www.youtube.com/watch?v=t0dGLFtRR9c
