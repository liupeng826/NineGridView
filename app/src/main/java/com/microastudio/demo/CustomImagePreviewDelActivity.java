package com.microastudio.demo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;

import com.lzy.imagepicker.ui.ImagePreviewDelActivity;

/**
 * @author peng
 * @date 2019/2/1
 */
public class CustomImagePreviewDelActivity extends ImagePreviewDelActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide delete button
        ImageView mBtnDel = (ImageView) findViewById(R.id.btn_del);
        mBtnDel.setOnClickListener(this);
        mBtnDel.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_del) {
            showDeleteDialog();
        } else if (id == R.id.btn_back) {
            onBackPressed();
        }
    }

    /**
     * confirm delete image
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
}
