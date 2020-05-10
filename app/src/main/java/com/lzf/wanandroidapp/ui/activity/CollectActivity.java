package com.lzf.wanandroidapp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.BaseActivity;
import com.lzf.wanandroidapp.entity.Collect;
import com.lzf.wanandroidapp.ui.adapter.CollectAdapter;
import com.lzf.wanandroidapp.utils.WanDatabaseHelper;

import java.util.ArrayList;

public class CollectActivity extends BaseActivity {
    private WanDatabaseHelper databaseHelper;
    private Button save;
    private Button display;
    private RecyclerView recyclerView;
    private ArrayList<Collect> collects = new ArrayList<>();
    private CollectAdapter mAdapter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_collect);
        save = findViewById(R.id.save);
        display = findViewById(R.id.display);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    @Override
    public void loaderData() {
        mAdapter = new CollectAdapter(this,collects);
        recyclerView.setAdapter(mAdapter);
        displayCollect();
    }

    @Override
    public void initVariables() {
        databaseHelper = new WanDatabaseHelper(this, WanDatabaseHelper.COLLECT_DATABASE_NAME, null, 1);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCollect();
            }
        });
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCollect();
            }
        });
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

    private void saveCollect() {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ArrayList<Collect> arrayList = new ArrayList<>();
        Collect c1 = new Collect("大厂OPPO面试— Android 开发技术面总结", "https://juejin.im/post/5c258909518825480635d0f9");
        Collect c2 = new Collect("Android 面试重难点", "https://juejin.im/entry/57466b5e71cfe40068cd862a");
        Collect c3 = new Collect("Android系统服务--WMS与AMS", "https://www.jianshu.com/p/47eca41428d6");
        Collect c4 = new Collect("2020年中高级Android大厂面试秘籍--Android基础篇", "https://juejin.im/post/5e5c5e306fb9a07cbe346d71");
        Collect c5 = new Collect("2020年中高级Android大厂面试秘籍--Android高级篇上", "https://juejin.im/post/5e5c5dea6fb9a07c8e6a36d1");
        Collect c6 = new Collect("2020年中高级Android大厂面试秘籍--Android高级篇下", "https://juejin.im/post/5e5c5fdae51d452703136c32");
        Collect c7 = new Collect("Android 高级开发面试题以及答案整理", "https://juejin.im/post/5c8b1bd56fb9a049e12b1692");
        Collect c8 = new Collect("Android布局优化（二），减少过度绘制", "https://www.jianshu.com/p/b1442924c203");
        Collect c9 = new Collect("Android 自定义 View 最少必要知识", "https://juejin.im/post/5d6c8f7cf265da03d42fbe58");
        Collect c10 = new Collect("Android性能优化（一）闪退治理、卡顿优化、耗电优化、APK瘦身", "https://blog.csdn.net/csdn_aiyang/article/details/74989318");
        arrayList.add(c1);
        arrayList.add(c2);
        arrayList.add(c3);
        arrayList.add(c4);
        arrayList.add(c5);
        arrayList.add(c6);
        arrayList.add(c7);
        arrayList.add(c8);
        arrayList.add(c9);
        arrayList.add(c10);
        sqLiteDatabase.beginTransaction();
        for (Collect c : arrayList) {
            long rowId = sqLiteDatabase.insert(Collect.TABLE_NAME, null, c.change());
            Log.d("trq", "saveCollect: "+rowId);
        }
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        sqLiteDatabase.close();
    }

    private void displayCollect() {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        collects.clear();
        Cursor cursor = sqLiteDatabase.query(Collect.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Collect collect = new Collect();
            collect.toCollect(cursor);
            collects.add(collect);
        }
        cursor.close();
        sqLiteDatabase.close();
        mAdapter.notifyDataSetChanged();
    }
    static {
        //静态代码只要加载一次就一直在虚拟机中了,调用类的静态方法也会将类加入到其中
        Log.d("lzf", "static initializer: ");
    }
    public static void testStatic(){
        Log.d("lzf", "static method initializer: ");
    }
}
