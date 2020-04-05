package com.lzf.wanandroidapp.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.widget.FlowLayout;

import java.util.List;

public class BasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        final FlowLayout flowLayout = findViewById(R.id.flowLayout);
        final TextView t = findViewById(R.id.java);
        FloatingActionButton floatingActionButton = findViewById(R.id.fButton);
        final TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("lzf", "爱学习: ");
                TextView textView = new TextView(BasicActivity.this);
                textView.setText("爱学习");
                textView.setBackground(getDrawable(R.drawable.text_back));
                textView.setPadding(3, 3, 3, 3);
                FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.rightMargin = 0;
                layoutParams.leftMargin = 6;
                layoutParams.topMargin = 30;
                layoutParams.bottomMargin = 0;
                textView.setLayoutParams(layoutParams);
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                List<CellInfo> cellInfoList = mTelephonyManager.getAllCellInfo();
                mTelephonyManager.getNetworkOperator();
                for (CellInfo c : cellInfoList) {
                    if (c instanceof CellInfoGsm) {
                        CellInfoGsm cellInfoGsm = (CellInfoGsm) c;
                        Log.d("lzf", "CellInfoGsm: "+cellInfoGsm.getCellIdentity().getLac());
                        Log.d("lzf", "CellInfoGsm: "+cellInfoGsm.getCellIdentity().getCid());
                    } else if (c instanceof CellInfoCdma) {
                        CellInfoCdma cellInfoCdma = (CellInfoCdma) c;
                        Log.d("lzf", "cellInfoCdma1: "+cellInfoCdma.getCellIdentity().getLatitude());
                        Log.d("lzf", "cellInfoCdma2: "+cellInfoCdma.getCellIdentity().getLongitude());
                    } else if (c instanceof CellInfoWcdma) {
                        CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) c;
                        Log.d("lzf", "cellInfoWcdma: "+cellInfoWcdma.getCellIdentity().getLac());
                    } else if (c instanceof CellInfoLte) {
                        CellInfoLte cellInfoLte = (CellInfoLte) c;
                        Log.d("lzf", "CellInfoGsm: "+cellInfoLte.getCellIdentity().getTac());
                        Log.d("lzf", "CellInfoGsm: "+cellInfoLte.getCellIdentity().getCi());
                    }
                }
                flowLayout.addView(textView);
            }
        });
        setSupportActionBar(toolbar);
    }

}
