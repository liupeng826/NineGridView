package com.microastudio.demo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.lzy.imagepicker.ui.ImagePreviewDelActivity;

/**
 * @author peng
 * @date 2019/2/1
 */
public class CustomImagePreviewDelActivity extends ImagePreviewDelActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
        topBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));


//        this.setTheme(androidR.style.AppThemeAppTheme);
//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(getResources().getColor(R.color.white));
    }

    @Override
    protected void onStart() {
        super.onStart();
//        findViewById(R.id.btn_del).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_del) {
            showDeleteDialog();
        } else if (id == R.id.btn_back) {
            onBackPressed();
        }

//        super.onClick(v);
    }

    /**
     * 是否删除此张图片
     */
    private void showDeleteDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.confirm))
                .setMessage(getString(R.string.confirm_to_delete))
                .setIcon(R.drawable.ic_delete)
                .setNegativeButton(getString(R.string.button_no), null)
                .setPositiveButton(getString(R.string.button_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //移除当前图片刷新界面
                        mImageItems.remove(mCurrentPosition);
                        if (mImageItems.size() > 0) {
                            mAdapter.setData(mImageItems);
                            mAdapter.notifyDataSetChanged();
                            mTitleCount.setText(getString(R.string.ip_preview_image_count, mCurrentPosition + 1, mImageItems.size()));
                        } else {
                            onBackPressed();
                        }
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onImageSingleTap() {
//        super.onImageSingleTap();
//        tintManager.setStatusBarTintResource(R.color.ip_color_primary_dark);
    }
}
