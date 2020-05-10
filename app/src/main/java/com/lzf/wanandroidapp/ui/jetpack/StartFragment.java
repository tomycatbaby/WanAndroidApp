package com.lzf.wanandroidapp.ui.jetpack;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.lzf.wanandroidapp.R;

import java.util.List;


public class StartFragment extends Fragment {

    private StartViewModel mViewModel;
    Button button;

    public static StartFragment newInstance() {
        return new StartFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.start_fragment, container, false);
        button = v.findViewById(R.id.message);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFile();
            }
        });
        return v;
    }

    private void pickFile() {
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("*/*");
//        startActivityForResult(intent, 1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "asfasf", Toast.LENGTH_SHORT).show();
            }
        }).start();

        Log.d("lzf", "pickFile: ");
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StartViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getLiveData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                Log.d("lzf", "onChanged: " + strings.toString());
            }
        });
        mViewModel.getMutableLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("lzf", "onChanged: " + s);
            }
        });
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        // 屏幕的逻辑密度，是密度无关像素（dip）的缩放因子，160dpi是系统屏幕显示的基线，1dip = 1px， 所以，在160dpi的屏幕上，density = 1， 而在一个120dpi屏幕上 density = 0.75。
        float density = metrics.density;

        //  屏幕的绝对宽度（像素）
        int screenWidth = metrics.widthPixels;

        // 屏幕的绝对高度（像素）
        int screenHeight = metrics.heightPixels;

        //  屏幕上字体显示的缩放因子，一般与density值相同，除非在程序运行中，用户根据喜好调整了显示字体的大小时，会有微小的增加。
        float scaledDensity = metrics.scaledDensity;

        // X轴方向上屏幕每英寸的物理像素数。
        float xdpi = metrics.xdpi;

        // Y轴方向上屏幕每英寸的物理像素数。
        float ydpi = metrics.ydpi;

        // 每英寸的像素点数，屏幕密度的另一种表示。densityDpi = density * 160.
        float desityDpi = metrics.densityDpi;

    }

}
