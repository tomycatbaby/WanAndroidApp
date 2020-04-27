package com.lzf.wanandroidapp.utils;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WanExecutor {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;
    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);
    //核心线程数满了就提交到请求队列排队，请求队列满了但是最大线程数没有满就创建新线程，最大线程数也达到了最大数，就执行满额策略
    private static final Executor sDefaultExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS, sPoolWorkQueue);

    public static void execute(Runnable runnable) {
        Log.d("lzf", "CORE_POOL_SIZE: "+CORE_POOL_SIZE+" MAXIMUM_POOL_SIZE:"+MAXIMUM_POOL_SIZE);
        sDefaultExecutor.execute(runnable);
    }

}
