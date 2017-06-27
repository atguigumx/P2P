package com.maxin.p2p.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxin.p2p.R;
import com.maxin.p2p.base.BaseActivity;
import com.maxin.p2p.common.AppManager;
import com.maxin.p2p.common.AppNetConfig;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.InjectView;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class IconSettingsActivity extends BaseActivity {


    @InjectView(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @InjectView(R.id.tv_user_change)
    TextView tvUserChange;
    @InjectView(R.id.btn_user_logout)
    Button btnUserLogout;
    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_setting)
    ImageView ivTitleSetting;

    @Override
    public void initTitle() {
        super.initTitle();
        tvTitle.setText("头像设置");
        ivTitleBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void initListener() {
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnUserLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSp();
                AppManager.getInstance().removeAll();
                finish();
                startActivity(new Intent(IconSettingsActivity.this, LoaginActivity.class));
            }
        });

        tvUserChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(IconSettingsActivity.this)
                        .setItems(new String[]{"相机", "相册"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {
                                    showCamera();
                                } else if (i == 1) {
                                    showPhoto();
                                }
                            }
                        }).show();
            }
        });
    }

    private void showPhoto() {
        GalleryFinal.openGallerySingle(02, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {

                Log.d("image", "onHanlderSuccess: "
                        + resultList.get(0).getPhotoPath());
                makeImage(resultList.get(0).getPhotoPath());

            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }

    private void showCamera() {
        GalleryFinal.openCamera(01, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {

                Log.d("image", "onHanlderSuccess: "
                        +resultList.get(0).getPhotoPath());
                makeImage(resultList.get(0).getPhotoPath());
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

                Log.d("image", "onHanlderFailure: "+errorMsg);

            }
        });
    }
    private void makeImage(String photoPath) {

        //展示图片
        Picasso.with(this)
                .load(new File(photoPath))
                .transform(new CropCircleTransformation())
                .into(ivUserIcon);
        //上传图片

        //保存到Sp中
        saveImage(photoPath);
    }

    @Override
    public void initData() {
        String image = getImage();
        if (TextUtils.isEmpty(image)){
            //加载头像
            Picasso.with(IconSettingsActivity.this)
                    .load(AppNetConfig.BASE_URL+"images/tx.png")

                    .transform(new CropCircleTransformation())
                    .into(ivUserIcon);
        }else{
            //加载头像
            Picasso.with(IconSettingsActivity.this)
                    .load(new File(image))

                    .transform(new CropCircleTransformation())
                    .into(ivUserIcon);
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_icon_settings;
    }


}
