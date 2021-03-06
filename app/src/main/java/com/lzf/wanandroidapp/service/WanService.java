package com.lzf.wanandroidapp.service;

import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class WanService extends Service {
    public WanService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true){
                    Log.d("WanService", "count: "+i);
                    i++;
                    if (i == 30){
                        showDialog();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //测试显示系统类型的dialog
    private void showDialog(){
        Dialog dialog = new Dialog(this);
    }
}
