package com.lzf.wanandroidapp.ui;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.UiModeManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
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

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.BaseActivity;
import com.lzf.wanandroidapp.base.SettingUtil;
import com.lzf.wanandroidapp.entity.Article;
import com.lzf.wanandroidapp.ui.activity.CollectActivity;
import com.lzf.wanandroidapp.ui.home.HomeFragment;
import com.lzf.wanandroidapp.ui.knowledge.KnowledgeFragment;
import com.lzf.wanandroidapp.ui.share.ShareFragment;
import com.lzf.wanandroidapp.ui.slideshow.SlideshowFragment;
import com.lzf.wanandroidapp.ui.tools.ToolsFragment;
import com.lzf.wanandroidapp.utils.CalendarReminderUtil;
import com.lzf.wanandroidapp.utils.WanExecutor;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends BaseActivity {
    String TAG = "MainActivity";
    private AppBarConfiguration mAppBarConfiguration;
    private int FRAGMENT_HOME = 0x01;
    private int FRAGMENT_KNOWLEDGE = 0x02;
    private int FRAGMENT_WECHAT = 0x03;
    private int FRAGMENT_NAVIGATION = 0x04;
    private int FRAGMENT_PROJECT = 0x05;
    private HomeFragment homeFragment;
    private KnowledgeFragment knowledgeFragment;
    private SlideshowFragment slideshowFragment;
    private ShareFragment shareFragment;
    private ToolsFragment toolsFragment;
    private Fragment currentFragment;
    private int mIndex = 1;
    private boolean isBottomShow = true;
    private NavHostFragment fragment;
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;

    private Article article = new Article();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        Log.d(TAG, "onCreate: " + article.getEnvelopePic());
        t(article);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    public void t(Article article) {
        article.setEnvelopePic("lzf");
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.floating_action_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, H5Activity.class);
//                startActivity(intent);

                //testThread();
            }
        });
        ScrollView scrollView = new ScrollView(this);
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_score:
                        Intent intent = new Intent(MainActivity.this, BasicActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_night_mode:
                        if (SettingUtil.getIsNightMode()) {
                            SettingUtil.setNightMode(false);
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        } else {
                            SettingUtil.setNightMode(true);
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        }
                        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                        recreate();
                        break;
                    case R.id.nav_collect:
                        Intent i = new Intent(MainActivity.this, CollectActivity.class);
                        startActivity(i);
                        break;
                    case R.id.nav_about_us:
                        Intent i1 = new Intent(MainActivity.this, TestActivity.class);
                        startActivity(i1);
                        break;

                }
                return false;
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                        showFragment(FRAGMENT_PROJECT);
                        break;
                    case R.id.action_wechat:
                        showFragment(FRAGMENT_WECHAT);
                }
                return true;
            }

        });
    }

    @Override
    public void loaderData() {
        showFragment(FRAGMENT_HOME);
    }

    @Override
    public void initVariables() {

    }


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
        ;
        Objects.equals("", "");
        new Thread(new Runnable() {
            @Override
            public void run() {

                int[] s = new int[]{1, 2, 3, 3, 2, 1, 1};
                for (int i = 0; i < s.length; i++) {
                    mCityPublish.onNext(s[i]);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
        if (slideshowFragment != null) {
            ft.hide(slideshowFragment);
        }
        if (shareFragment != null) {
            ft.hide(shareFragment);
        }
        if (toolsFragment != null) {
            ft.hide(toolsFragment);
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
                toolbar.setTitle(getString(R.string.app_name));
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.content_main, homeFragment, "home");
                } else {
                    fragmentTransaction.show(homeFragment);
                }
                break;
            case 2:
                toolbar.setTitle(getString(R.string.knowledge_system));
                Class s = this.getClass();
                if (knowledgeFragment == null) {
                    knowledgeFragment = new KnowledgeFragment();
                    fragmentTransaction.add(R.id.content_main, knowledgeFragment, "knowledge");
                } else {
                    fragmentTransaction.show(knowledgeFragment);
                }
                break;
            case 3:
                toolbar.setTitle(getString(R.string.navigation));
                if (slideshowFragment == null) {
                    slideshowFragment = new SlideshowFragment();
                    fragmentTransaction.add(R.id.content_main, slideshowFragment, "navigation");

                } else {
                    fragmentTransaction.show(slideshowFragment);
                }
                break;
            case 4:
                toolbar.setTitle(getString(R.string.project));
                if (shareFragment == null) {
                    shareFragment = new ShareFragment();
                    fragmentTransaction.add(R.id.content_main, shareFragment, "share");

                } else {
                    fragmentTransaction.show(shareFragment);
                }
                break;
            case 5:
                toolbar.setTitle(getString(R.string.wechat));
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
//
//    给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
//
//    设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
//
//    注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
//
//    示例 1:
//
//    输入: [7,1,5,3,6,4]
//    输出: 7
//    解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
//    随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
//    示例 2:
//
//    输入: [1,2,3,4,5]
//    输出: 4
//    解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
//    注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。
//    因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
//    示例 3:
//
//    输入: [7,6,4,3,1]
//    输出: 0
//    解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。

    class Solution {
        public int maxProfit(int[] prices) {
            int chiyou = 0;
            boolean maile = false;
            int sum = 0;
            for (int i = 0; i < prices.length - 1; i++) {
                //小于后一个，去持有股票
                if (prices[i] < prices[i + 1] && !maile) {
                    chiyou = prices[i];
                    maile = true;
                }
                //降了，卖出股票
                if (prices[i] > prices[i + 1] && maile) {
                    int chajia = prices[i] - chiyou;
                    sum += chajia;
                    chiyou = 0;
                    maile = false;
                }
            }
            if (maile) {
                int chajia = prices[prices.length - 1] - chiyou;
                sum += chajia;
            }
            return sum;
        }
    }

    /*    class Solution {
            public ListNode removeElements(ListNode head, int val) {
                ListNode sentinel = new ListNode(0);//前序节点，它的后面的那个就是完成的链表的头结点
                sentinel.next = head;//伪节点的后序节点的引用指向的是链表头

                ListNode prev = sentinel, curr = head;//当前节点持有需要遍历的链表的头节点引用，前序节点持有伪节点的引用
                while (curr != null) {
                    if (curr.val == val)//如果当前节点是需要删除的那个节点
                        prev.next = curr.next;//就将前序节点指向当前节点的下一个节点
                    else prev = curr;//不是当前节点，就将前序节点指向当前节点
                    curr = curr.next;//当前节点向下移一位
                }
                return sentinel.next;
            }

        }
    */
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }


}
