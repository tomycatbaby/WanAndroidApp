package com.lzf.wanandroidapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.lzf.wanandroidapp.base.BaseActivity;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MainActivity extends BaseActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private int FRAGMENT_HOME = 0x01;
    private int FRAGMENT_KNOWLEDGE = 0x02;
    private int FRAGMENT_NAVIGATION = 0x03;
    private int FRAGMENT_PROJECT = 0x04;
    private int FRAGMENT_WECHAT = 0x05;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));

        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.floating_action_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        initView();
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
}
