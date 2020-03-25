package com.lzf.wanandroidapp;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzf.wanandroidapp.base.BaseActivity;
import com.lzf.wanandroidapp.ui.BasicActivity;
import com.lzf.wanandroidapp.ui.H5Activity;
import com.lzf.wanandroidapp.ui.home.HomeFragment;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends BaseActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private int FRAGMENT_HOME = 0x01;
    private int FRAGMENT_KNOWLEDGE = 0x02;
    private int FRAGMENT_NAVIGATION = 0x03;
    private int FRAGMENT_PROJECT = 0x04;
    private int FRAGMENT_WECHAT = 0x05;
    private boolean isBottomShow = true;
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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
                Intent intent = new Intent(MainActivity.this, H5Activity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_score:
                        Intent intent = new Intent(MainActivity.this, BasicActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
        fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
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

    }

    @Override
    public void initVariables() {

    }

    void Test() {
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
                        Log.d("lzf", "apply: " + integer);
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

    /**
     * 切换碎片
     *
     * @param index
     */
    public void showFragment(int index) {
        switch (index) {
            case 1:
                toolbar.setTitle(getString(R.string.app_name));
                break;
            case 2:
                toolbar.setTitle(getString(R.string.knowledge_system));
                break;
            case 3:
                toolbar.setTitle(getString(R.string.navigation));
                break;
            case 4:
                toolbar.setTitle(getString(R.string.project));
                break;
            case 5:
                toolbar.setTitle(getString(R.string.wechat));
                break;
        }
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
