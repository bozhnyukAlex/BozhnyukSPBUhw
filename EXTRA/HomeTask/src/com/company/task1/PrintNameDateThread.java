package com.company.task1;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PrintNameDateThread extends Thread {
    @Override
    public void run() {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        System.out.println("ThreadName: " + this.getName() + " Date: " + date);
    }
}
