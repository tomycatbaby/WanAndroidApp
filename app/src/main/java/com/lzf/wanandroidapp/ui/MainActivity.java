package com.lzf.wanandroidapp.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AppCompatDelegate;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import com.google.android.material.navigation.NavigationView;


import android.view.Menu;

import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.BaseActivity;
import com.lzf.wanandroidapp.base.Constant;
import com.lzf.wanandroidapp.base.SettingUtil;
import com.lzf.wanandroidapp.entity.Article;
import com.lzf.wanandroidapp.entity.Rank;
import com.lzf.wanandroidapp.ui.activity.CollectActivity;
import com.lzf.wanandroidapp.ui.home.HomeFragment;
import com.lzf.wanandroidapp.ui.jetpack.StartViewModel;
import com.lzf.wanandroidapp.ui.knowledge.KnowledgeFragment;
import com.lzf.wanandroidapp.ui.mine.MineFragment;
import com.lzf.wanandroidapp.ui.share.ShareFragment;
import com.lzf.wanandroidapp.ui.wxarticle.WXArticleFragment;
import com.lzf.wanandroidapp.utils.CalendarReminderUtil;
import com.lzf.wanandroidapp.utils.WanExecutor;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.SubscribeMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.message.PushAgent;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends BaseActivity implements IWXAPIEventHandler {
    String TAG = "MainActivity";
    private AppBarConfiguration mAppBarConfiguration;
    private final int FRAGMENT_HOME = 0x01;
    private final int FRAGMENT_KNOWLEDGE = 0x02;
    private final int FRAGMENT_WECHAT = 0x03;
    private final int FRAGMENT_NAVIGATION = 0x04;
    private final int FRAGMENT_MINE = 0x05;
    private HomeFragment homeFragment;
    private KnowledgeFragment knowledgeFragment;
    private WXArticleFragment wxArticleFragment;
    private ShareFragment shareFragment;
    private MineFragment mineFragment;
    private int mIndex = 1;
    private boolean isBottomShow = true;

    private Article article = new Article();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        Log.d(TAG, "onCreate: "+savedInstanceState);
    }

    public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    public static SimpleDateFormat SDF_FULL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    public static SimpleDateFormat SDF_DOT_MINUTE = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.CHINA);

    private IWXAPI api;

    @Override
    public void initView() {
        setContentView(R.layout.app_bar_main);
        PushAgent.getInstance(this).onAppStart();
        FloatingActionButton fab = findViewById(R.id.floating_action_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reg();
//                try {
//                    Intent intent = new Intent(MainActivity.this,KtActivity.class);
//                    startActivity(intent);
//                }catch (Exception e){
//                    Log.d("lzf", "onClick: "+e.getMessage());
//                    e.printStackTrace();
//                }
                Rank r = new Rank();
            }
        });
        api = WXAPIFactory.createWXAPI(getApplicationContext(), Constant.APP_ID, true);
        api.registerApp(Constant.APP_ID);
        try {
            Intent intent = getIntent();
            api.handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //final NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.getHeaderView(0).findViewById(R.id.iv_rank).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, RankActivity.class);
//                startActivity(intent);
//            }
//        });

//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.nav_score:
//                        Intent intent = new Intent(MainActivity.this, BasicActivity.class);
//                        startActivity(intent);
//                        break;
//                    case R.id.nav_night_mode:
//                        if (SettingUtil.getIsNightMode()) {
//                            SettingUtil.setNightMode(false);
//                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                        } else {
//                            SettingUtil.setNightMode(true);
//                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                        }
//                        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
//                        recreate();
//                        break;
//                    case R.id.nav_collect:
//                        Intent i = new Intent(MainActivity.this, CollectActivity.class);
//                        startActivity(i);
//                        break;
//                    case R.id.nav_about_us:
//                        Intent i1 = new Intent(MainActivity.this, TestActivity.class);
//                        startActivity(i1);
//                        break;
//
//                }
//                return false;
//            }
//        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        showFragment(FRAGMENT_HOME);
                        break;
                    case R.id.action_knowledge_system:
                        showFragment(FRAGMENT_KNOWLEDGE);
                        break;
                    case R.id.action_navigation:
                        showFragment(FRAGMENT_NAVIGATION);
                        break;
                    case R.id.action_project:
                        showFragment(FRAGMENT_MINE);
                        break;
                    case R.id.action_wechat:
                        showFragment(FRAGMENT_WECHAT);
                }
                return true;
            }

        });
    }

    private void reg() {
        //邮政编码
        String postCode = "[1-9]\\d{5,}?";
        //区号-座机号码
        String areaCode = "\\d{3}-\\d{8}|\\d{4}-\\d{7}";
        //手机号码
        String phone = "(?:13\\d|15\\d|18\\d)\\d{5}(\\d{3}|\\*{3})";
        String text = "邮政编码：440834"+
                "区号-座机号码: 020-12345678"+
                "手机号：13536373839"+
                "邮政编码：440833"+
                "区号-座机号码: 010-12345678"+
                "手机号：13536373739";

        Pattern p = Pattern.compile(postCode);
        Matcher m = p.matcher(text);
        Log.d(TAG, "文本中包含邮政编码：");
        while (m.find()){
            Log.d(TAG, m.group());
        }

        p = Pattern.compile(areaCode);
        m= p.matcher(text);
        Log.d(TAG, "文本中包含区号-座机号码：");
        while (m.find()){
            Log.d(TAG, m.group());
        }

        p = Pattern.compile(phone);
        m= p.matcher(text);
        Log.d(TAG, "文本中包含手机号：");
        while (m.find()){
            Log.d(TAG, m.group());
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        SubscribeMessage.Resp resp = (SubscribeMessage.Resp) baseResp;
        Log.d("wechat", "onResp: " + resp.action);
    }

    private void TestWechat() {
        SubscribeMessage.Req req = new SubscribeMessage.Req();
        req.scene = 100;
        req.templateID = "lf91KWrKDU9Whdg3gxXrz_za1Y9J30Cemp6pL7dF4k4";
        boolean result = api.sendReq(req);
        Log.d("wechat", "TestWechat: " + result);
    }


    @Override
    public void loaderData() {
        showFragment(FRAGMENT_HOME);
    }

    @Override
    public void initVariables() {

    }


    @SuppressLint("CheckResult")
    void test() {
        final PublishSubject<Integer> mCityPublish = PublishSubject.create();
        //订阅者，去订阅消息，distinctUntilChanged可以对结果进行过滤，如果重复则不会通知订阅者
        final Observable<Integer> observable = mCityPublish.distinctUntilChanged().doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer aLong) throws Exception {
                Log.d("lzf", "accept: " + aLong);
            }
        });
        //observable
        Flowable<String> flowable = Flowable.just("");
        observable.subscribe();//观察者开始观察
        ArrayList<String> l;
        Objects.equals("", "");
        new Thread(() -> {

            int[] s = new int[]{1, 2, 3, 3, 2, 1, 1};
            for (int i = 0; i < s.length; i++) {
                mCityPublish.onNext(s[i]);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        observable
                .flatMap(new Function<Integer, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        return Observable.just(integer);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                });
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

    }

    void testCalendar() {
        long time = Calendar.getInstance().getTimeInMillis() + 120000;
        CalendarReminderUtil.addCalendarEvent(getApplicationContext(), "高铁行程", "深圳到广州", time, 1);
    }

    void testStartActivity() {
        //启动其他应用的activity
        //隐式启动,同一应用可以存在相同的action，会提示用户打开哪个activity,文件清单中必须要category，否则报错
//                Intent intent = new Intent("det");
//                startActivity(intent);
        //用intent设置className或component的办法启动
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setClassName("com.example.mybook","com.example.mybook.DetailActivity");
//                //component方法
//                //intent.setComponent(new ComponentName("com.example.mybook","com.example.mybook.DetailActivity"));
//                startActivity(intent);
    }

    @Subscribe
    public void testNotification() {
        //在Android 8.0 也就是API26以上的通知适配，升级项目的targetSdkVersion到26，也就是Android 8.0开始你会发现曾经的通知代码会进行废弃方法提示
        //那好我们必须的学习下通知渠道的适配了
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "subscribe";
            String channelName = "订阅消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);
        }

        showNotification();
    }

    void testThread() {
        final String a = "";
        final String b = "";
        //wait的意思是持有synchronized 锁的线程去等待，然后释放锁
        //notify前提也是当前持有一个锁，如果想要释放这个synchronized 锁就可以调用这个
        final ReentrantLock reentrantLock = new ReentrantLock(true);//公平锁，谁申请时间长分配给谁
        WanExecutor.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    reentrantLock.lock();
                    Log.d("lzf", "A线程: " + i);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    reentrantLock.unlock();
                }
            }
        });
        WanExecutor.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    reentrantLock.lock();
                    Log.d("lzf", "B线程: " + i);
                    reentrantLock.unlock();

                }
            }
        });
    }

    private void showNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, BasicActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, "subscribe")
                .setContentTitle("收到一条订阅消息")
                .setContentText("地铁沿线30万商铺抢购中！")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .build();
        manager.notify(1, notification);

    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setShowBadge(true);
        channel.enableLights(true);//呼吸灯
        //channel.enableVibration(true);
        //channel.setSound();
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    public void hideFragments(FragmentTransaction ft) {
        if (homeFragment != null) {
            ft.hide(homeFragment);
        }
        if (knowledgeFragment != null) {
            ft.hide(knowledgeFragment);
        }
        if (wxArticleFragment != null) {
            ft.hide(wxArticleFragment);
        }
        if (shareFragment != null) {
            ft.hide(shareFragment);
        }
        if (mineFragment != null) {
            ft.hide(mineFragment);
        }
    }

    /**
     * 切换碎片
     *
     * @param index
     */
    public void showFragment(int index) {
//        HashMap h;//允许为null的key或value
//        h.get()
//        ConcurrentHashMap k;//不允许key或value为空,
//        Hashtable kl;//不允许value为空，
//        kl.put()

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        mIndex = index;
        switch (index) {
            case 1:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.content_main, homeFragment, "home");
                } else {
                    fragmentTransaction.show(homeFragment);
                }
                break;
            case 2:
                if (knowledgeFragment == null) {
                    knowledgeFragment = new KnowledgeFragment();
                    fragmentTransaction.add(R.id.content_main, knowledgeFragment, "knowledge");
                } else {
                    fragmentTransaction.show(knowledgeFragment);
                }
                break;
            case 3:
                if (wxArticleFragment == null) {
                    wxArticleFragment = new WXArticleFragment();
                    fragmentTransaction.add(R.id.content_main, wxArticleFragment, "navigation");

                } else {
                    fragmentTransaction.show(wxArticleFragment);
                }
                break;
            case 4:
                if (shareFragment == null) {
                    shareFragment = new ShareFragment();
                    fragmentTransaction.add(R.id.content_main, shareFragment, "share");
                } else {
                    fragmentTransaction.show(shareFragment);
                }
                break;
            case 5:
                if (Objects.nonNull(mineFragment)){
                    fragmentTransaction.show(mineFragment);
                } else {
                    mineFragment = new MineFragment();
                    fragmentTransaction.add(R.id.content_main, mineFragment, "mine");
                }
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showDefaultMsg(String msg) {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showError(String errorMsg) {

    }

}
