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
//        Collect c1 = new Collect("大厂OPPO面试— Android 开发技术面总结", "https://juejin.im/post/5c258909518825480635d0f9");
//        Collect c2 = new Collect("Android 面试重难点", "https://juejin.im/entry/57466b5e71cfe40068cd862a");
//        arrayList.add(c1);
//        arrayList.add(c2);
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
}
