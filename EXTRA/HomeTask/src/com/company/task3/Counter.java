package com.company.task3;

import java.util.concurrent.locks.ReentrantLock;

public class Counter {
    private int counter;


    public Counter(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }

    public synchronized void inc() {
        counter++;
    }
    /*
        Для того, чтобы программа заработало верно, нужно добавить ключевое слово syncronized.
        Благодаря нему доступ к методу inc() будет только у одного потока, и только один поток сможет сделать counter++.
        Если syncronized убрать, программа будет работать некорректно, так как counter++ - это на самом деле 3 операции,
        и при доступе к ним нескольких потоков сразу получается совсем не то поведение, которое мы можем ожидать.
     */
}
