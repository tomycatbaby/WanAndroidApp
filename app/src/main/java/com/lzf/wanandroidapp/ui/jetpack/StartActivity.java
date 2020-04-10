package com.lzf.wanandroidapp.ui.jetpack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.ui.jetpack.StartFragment;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, StartFragment.newInstance())
                    .commitNow();
        }
    }
}
