package com.lzf.wanandroidapp.ui;

import android.Manifest;
import android.app.AlarmManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Worker;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.internal.cache.CacheInterceptor;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.MemoryFile;
import android.provider.SyncStateContract;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.http.RetrofitHelper;
import com.lzf.wanandroidapp.service.WanJobService;
import com.lzf.wanandroidapp.service.WanService;
import com.lzf.wanandroidapp.ui.activity.CollectActivity;
import com.lzf.wanandroidapp.widget.FlowLayout;
import com.lzf.wanandroidapp.worker.WanWorker;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class BasicActivity extends AppCompatActivity {
    TelephonyManager mTelephonyManager;
    private String TAG = "BasicActivity";
    private ViewStub viewStub;
    private ImageView v1;
    private ImageView v2;

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: "+Math.pow(2,31));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        //跨进程传数据只能传递基本数据类型和可序列化的对象以及由他们组成的数组，所以非基本数据类型需要跨进程传递，就需要实现parcelable或者serializable
        //一个是java自带的，一个是Android自带的，安卓自带的使用稍微复杂点，因为它的实现原理是将一个完成对象分解成Intent可识别的对象类型，从而实现数据传递
        //而serializable就是一个序列化的接口了，可以将对象序列成流之后保存到文件，网络中传输或者保存，而parcelable就主要用到内存中传递数据
        //serializable实现简单，但是序列化和反序列化过程都需要大量io操作，还会利用到反射生成大量对象，很消耗资源
        Bundle bundle = new Bundle();
        Intent intent = new Intent();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_basic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        final FlowLayout flowLayout = findViewById(R.id.flowLayout);


        final TextView t = findViewById(R.id.java);
        FloatingActionButton floatingActionButton = findViewById(R.id.fButton);
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("lzf", "爱学习: ");
//
//                DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
//                int screenWidth = dm.widthPixels;
//                int screenHeight = dm.heightPixels;
                Log.d("trq", "onClick: ");
                TextView textView = new TextView(BasicActivity.this);
                FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(layoutParams);
                textView.setText("先加");
                flowLayout.addView(textView);
                flowLayout.removeView(textView);
                flowLayout.addView(textView);


            }
        });
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(WanWorker.class).build();

        setSupportActionBar(toolbar);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void doService() {
        /*
        * 1、通过JobScheduler来安排一个后台进程，在之前的进程保活自启动那边文章中就提到过可以用来作为进程唤起的手段
        * 2、处理一些耗电的任务，设置充电模式下启动，来完成一些耗电后台进程任务
        * 3、处理一些耗流量任务，设置在不计流量情况下启动，如上传失败的缓存日志，检查更新下载Apk
        * 4、处理一些耗内存的任务，设置在手机空闲模式下
        * */
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(this, WanJobService.class));  //指定哪个JobService执行操作
        builder.setMinimumLatency(TimeUnit.MILLISECONDS.toMillis(10)); //执行的最小延迟时间
        builder.setOverrideDeadline(TimeUnit.MILLISECONDS.toMillis(15));  //执行的最长延时时间
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NOT_ROAMING);  //非漫游网络状态
        builder.setBackoffCriteria(TimeUnit.MINUTES.toMillis(10), JobInfo.BACKOFF_POLICY_LINEAR);  //线性重试方案
        builder.setRequiresCharging(false); // 未充电状态
        jobScheduler.schedule(builder.build());
    }

    public void testCellInfo() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Log.d("lzf", "no permission: ");

            return;
        }

        List<CellInfo> cellInfoList = mTelephonyManager.getAllCellInfo();
        mTelephonyManager.listen(new PhoneStateListener() {
            @Override
            public void onCellInfoChanged(List<CellInfo> cellInfo) {
                super.onCellInfoChanged(cellInfo);
            }
        }, PhoneStateListener.LISTEN_CELL_INFO);

        for (CellInfo c : cellInfoList) {
            if (c instanceof CellInfoGsm) {
                CellInfoGsm cellInfoGsm = (CellInfoGsm) c;
                Log.d("lzf", "CellInfoGsm: " + cellInfoGsm.getCellIdentity().getLac());
                Log.d("lzf", "CellInfoGsm: " + cellInfoGsm.getCellIdentity().getCid());
            } else if (c instanceof CellInfoCdma) {
                CellInfoCdma cellInfoCdma = (CellInfoCdma) c;
                Log.d("lzf", "cellInfoCdma: " + cellInfoCdma.getCellIdentity().getLatitude());
                Log.d("lzf", "cellInfoCdma: " + cellInfoCdma.getCellIdentity().getNetworkId());
            } else if (c instanceof CellInfoWcdma) {
                CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) c;
                Log.d("lzf", "cellInfoWcdma: " + cellInfoWcdma.getCellIdentity().getMcc());
                Log.d("lzf", "cellInfoWcdma: " + cellInfoWcdma.getCellIdentity().getCid());
            } else if (c instanceof CellInfoLte) {
                CellInfoLte cellInfoLte = (CellInfoLte) c;
                Log.d("lzf", "CellInfoLte: " + cellInfoLte.getCellIdentity().getTac());
                Log.d("lzf", "CellInfoLte: " + cellInfoLte.getCellIdentity().getCi());
            }
        }
    }
    //匿名共享内存,传递大数据，Intent传递数据限制大小为1MB
    public void t(){
        MemoryFile memoryFile;
        Executor executor = AsyncTask.THREAD_POOL_EXECUTOR;
        executor.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
        //                TextView textView = new TextView(BasicActivity.this);
//                textView.setText("爱学习");
//                textView.setBackground(getDrawable(R.drawable.text_back));
//                textView.setPadding(3, 3, 3, 3);
//                FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
//                layoutParams.rightMargin = 0;
//                layoutParams.leftMargin = 6;
//                layoutParams.topMargin = 30;
//                layoutParams.bottomMargin = 0;
//                textView.setLayoutParams(layoutParams);
        //flowLayout.addView(textView);
        //viewStub.setVisibility(View.VISIBLE);
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inJustDecodeBounds = false;
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_error,options);
//                Log.d(TAG, "outWidth: "+options.outWidth);
//                Log.d(TAG, "outHeight: "+options.outHeight);
//                v1.setImageBitmap(bitmap);
//                options.inJustDecodeBounds = false;
//                options.inSampleSize = 2;
//                Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_error,options);
//                v2.setImageBitmap(bitmap1);
//                Log.d(TAG, "change outWidth: "+options.outWidth);
//                Log.d(TAG, "change outHeight: "+options.outHeight);
    }

    Entry entry;

    public Entry getEntry() {
        return entry;
    }

    public class Entry {
        String s;

        public String gr() {
            return s;
        }
    }
}
