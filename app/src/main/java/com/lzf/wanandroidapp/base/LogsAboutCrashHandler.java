package com.lzf.wanandroidapp.base;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class LogsAboutCrashHandler implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler handler;
    private static LogsAboutCrashHandler instance = new LogsAboutCrashHandler();

    private LogsAboutCrashHandler() {

    }

    public static LogsAboutCrashHandler getInstance() {
        return instance;
    }


    public void init(){
        handler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);

    }
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        e.getCause().printStackTrace(printWriter);
        printWriter.close();
        Log.d("lzf", "uncaughtException: " + writer.toString());
    }
}
