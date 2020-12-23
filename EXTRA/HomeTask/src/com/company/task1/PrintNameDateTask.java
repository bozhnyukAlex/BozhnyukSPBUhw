package com.company.task1;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PrintNameDateTask implements Runnable {
    @Override
    public void run() {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        System.out.println("ThreadName: " + Thread.currentThread().getName() + " Date: " + date);
    }
}
