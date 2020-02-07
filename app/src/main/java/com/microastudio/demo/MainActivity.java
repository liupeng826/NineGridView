package com.microastudio.demo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.microastudio.widget.photo.NineGirdImageContainer;
import com.microastudio.widget.photo.NineGridBean;
import com.microastudio.widget.photo.NineGridView;

import java.util.ArrayList;
import java.util.List;

import static com.microastudio.demo.Constants.REQUEST_CODE_PREVIEW;

/**
 * @author peng
 */
public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, NineGridView.onItemClickListener {
    private List<ImageItem> selImageList;
    private NineGridView mNineGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initImagePicker();
        initNineGridView();

        selImageList = new ArrayList<>();
    }

    private void initNineGridView() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.ck_main_is_edit_mode);
        checkBox.setOnCheckedChangeListener(this);

        mNineGridView = (NineGridView) findViewById(R.id.ngv_demo);
        //设置图片加载器，这个是必须的，不然图片无法显示
        mNineGridView.setImageLoader(new ImagePickerLoader());
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

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(Constants.MAX_PHOTO_COUNT);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mNineGridView.setIsEditMode(b);
    }

    @Override
    public void onNineGirdAddMoreClick(int dValue) {
        //编辑模式下，图片展示数量尚未达到最大数量时，会显示一个“+”号，点击后回调这里
        ImagePicker.getInstance().setSelectLimit(Constants.MAX_PHOTO_COUNT - selImageList.size());
        Intent intent1 = new Intent(MainActivity.this, ImageGridActivity.class);
        // 如果需要进入选择的时候显示已经选中的图片
        // intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES, images);
        startActivityForResult(intent1, Constants.REQUEST_IMAGE_PICKER);
    }

    @Override
    public void onNineGirdItemClick(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {
        //点击图片的监听 - 打开预览
        Intent intentPreview = new Intent(MainActivity.this, CustomImagePreviewDelActivity.class);
        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) selImageList);
        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
    }

    @Override
    public void onNineGirdItemDeleted(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {
        //编辑模式下，某张图片被删除后回调这里
        Toast.makeText(this, "position=" + position + "图片被删除", Toast.LENGTH_SHORT).show();
    }

    ArrayList<ImageItem> images = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_IMAGE_PICKER
                && resultCode == ImagePicker.RESULT_CODE_ITEMS
                && data != null) {
            images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images != null) {
                selImageList.addAll(images);
            }

            List<NineGridBean> resultList = new ArrayList<>();
            for (ImageItem imageBean : images) {
                NineGridBean nineGirdData = new NineGridBean(imageBean.path);
                resultList.add(nineGirdData);
            }
            mNineGridView.addDataList(resultList);
        }
    }
}
