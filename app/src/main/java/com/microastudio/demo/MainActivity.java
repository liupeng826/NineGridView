package com.microastudio.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lwkandroid.imagepicker.ImagePicker;
import com.lwkandroid.imagepicker.data.ImageBean;
import com.lwkandroid.imagepicker.data.ImagePickType;
import com.microastudio.widget.photo.NineGirdImageContainer;
import com.microastudio.widget.photo.NineGridBean;
import com.microastudio.widget.photo.NineGridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, NineGridView.onItemClickListener {
    private final int REQUEST_CODE_PICKER = 100;
    private NineGridView mNineGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckBox checkBox = (CheckBox) findViewById(R.id.ck_main_is_edit_mode);
        checkBox.setOnCheckedChangeListener(this);

        mNineGridView = (NineGridView) findViewById(R.id.ngv_demo);
        //设置图片加载器，这个是必须的，不然图片无法显示
        mNineGridView.setImageLoader(new GlideImageLoader());
        //设置显示列数，默认3列
        mNineGridView.setColumnCount(4);
        //设置是否为编辑模式，默认为false
        mNineGridView.setIsEditMode(checkBox.isChecked());
        //设置单张图片显示时的尺寸，默认100dp
        mNineGridView.setSingleImageSize(150);
        //设置单张图片显示时的宽高比，默认1.0f
        mNineGridView.setSingleImageRatio(1.0f);
        //设置最大显示数量，默认9张
        mNineGridView.setMaxNum(16);
        //设置图片显示间隔大小，默认3dp
        mNineGridView.setSpcaeSize(4);
        //设置删除图片
        mNineGridView.setIcDeleteResId(R.drawable.ic_delete);
        //设置删除图片与父视图的大小比例，默认0.25f
        mNineGridView.setRatioOfDeleteIcon(0.25f);
        //设置“+”号的图片
        mNineGridView.setIcAddMoreResId(R.drawable.ic_plus);
        //设置各类点击监听
        mNineGridView.setOnItemClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mNineGridView.setIsEditMode(b);
    }

    @Override
    public void onNineGirdAddMoreClick(int dValue) {
        //编辑模式下，图片展示数量尚未达到最大数量时，会显示一个“+”号，点击后回调这里
        new ImagePicker()
                .cachePath(Environment.getExternalStorageDirectory().getAbsolutePath())
                .pickType(ImagePickType.MULTI)
                .displayer(new ImagePickerLoader())
                .maxNum(dValue)
                .start(this, REQUEST_CODE_PICKER);
    }

    @Override
    public void onNineGirdItemClick(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {
        //点击图片的监听
        Toast.makeText(this, "点击position=" + position + "图片", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNineGirdItemDeleted(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {
        //编辑模式下，某张图片被删除后回调这里
        Toast.makeText(this, "position=" + position + "图片被删除", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null) {
            List<ImageBean> list = data.getParcelableArrayListExtra(ImagePicker.INTENT_RESULT_DATA);
            List<NineGridBean> resultList = new ArrayList<>();
            for (ImageBean imageBean : list) {
                NineGridBean nineGirdData = new NineGridBean(imageBean.getImagePath());
                resultList.add(nineGirdData);
            }
            mNineGridView.addDataList(resultList);
        }
    }
}
