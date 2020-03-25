package com.lzf.wanandroidapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.TextView;

import com.lzf.wanandroidapp.R;

public class EmptyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_view);

        Intent i = getIntent();
        String action = i.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = i.getData();
            if (uri != null) {
                String id = uri.getQueryParameter("id");
                String title = uri.getQueryParameter("title");
                String data = uri.getQueryParameter("data");
                Log.d("lzf", "onCreate: " + id + " title:" + title + " data:" + data);
            }
        }
        TextView t = findViewById(R.id.tv_empty);
        startAnimation(t);
//        SpannableString sp = new SpannableString("图文混排测试图文混排测试图文混排测试图文混排测试图文混排测试图文混排测试图文混排测试");
//
//        //搞一张图片
//        Drawable drawable = getDrawable(R.drawable.empty_animator);
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        AnimationDrawable animation = (AnimationDrawable) drawable;
//        animation.start();
//        //普通Span
//        ImageSpan imageSpan = new ImageSpan(drawable);
//        sp.setSpan(imageSpan,0,1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        t.setText(sp);

        //
//        Drawable[] drawable = t.getCompoundDrawables();
//        AnimationDrawable animation = (AnimationDrawable) drawable[0];
//        animation.start();
    }
    private void startAnimation(TextView textView) {
        Drawable[] drawables = textView.getCompoundDrawables();
        for (Drawable drawable : drawables) {
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
