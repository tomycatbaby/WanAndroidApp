package com.lzf.wanandroidapp.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.BaseDialogFragment;
import com.lzf.wanandroidapp.utils.ResUtils;

public abstract class NormalDialog extends BaseDialogFragment {
    @Override
    public void onStart() {
        Window dialogWindow = getDialog().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        lp.x = 0;
        lp.y = 0;
        lp.width = ResUtils.dp2pxInOffset(334);
        lp.height = lp.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        super.onStart();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(false);
        return dialog;
    }
}
