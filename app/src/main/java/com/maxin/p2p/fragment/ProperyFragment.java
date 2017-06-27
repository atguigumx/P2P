package com.maxin.p2p.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maxin.p2p.R;
import com.maxin.p2p.activity.ChongZhiAcitivty;
import com.maxin.p2p.activity.IconSettingsActivity;
import com.maxin.p2p.activity.MainActivity;
import com.maxin.p2p.base.BaseFragment;
import com.maxin.p2p.common.AppNetConfig;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.InjectView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by shkstart on 2017/6/20.
 */

public class ProperyFragment extends BaseFragment {
    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_setting)
    ImageView ivTitleSetting;
    @InjectView(R.id.iv_me_icon)
    ImageView ivMeIcon;
    @InjectView(R.id.rl_me_icon)
    RelativeLayout rlMeIcon;
    @InjectView(R.id.tv_me_name)
    TextView tvMeName;
    @InjectView(R.id.rl_me)
    RelativeLayout rlMe;
    @InjectView(R.id.recharge)
    ImageView recharge;
    @InjectView(R.id.withdraw)
    ImageView withdraw;
    @InjectView(R.id.ll_touzi)
    TextView llTouzi;
    @InjectView(R.id.ll_touzi_zhiguan)
    TextView llTouziZhiguan;
    @InjectView(R.id.ll_zichan)
    TextView llZichan;
    private MainActivity mainActivity;

    @Override
    public void setContent(String url) {

    }

    @Override
    public String getChildUrl() {
        return "";
    }

    @Override
    public void initData() {
        Picasso.with(getActivity()).load(AppNetConfig.BASE_URL+"images/tx.png")
                .transform(new CropCircleTransformation())
                .into(ivMeIcon);
    }

    @Override
    public void initTitle() {
        tvTitle.setText("资产");
        ivTitleSetting.setVisibility(View.VISIBLE);

    }



    @Override
    public int getLayoutId() {
        return R.layout.fragment_propery;
    }

    //设置头像
    @Override
    public void onResume() {
        super.onResume();
        mainActivity = (MainActivity)getActivity();
        String image =mainActivity.getImage();

        /*
        * 判断加载网络图片还是本地图片
        * */
        if (TextUtils.isEmpty(image)){
            //加载头像
            Picasso.with(getActivity())
                    .load(AppNetConfig.BASE_URL+"images/tx.png")

                    .transform(new CropCircleTransformation())
                    .into(ivMeIcon);
        }else{
            //加载头像
            Picasso.with(getActivity())
                    .load(new File(image))
                    .transform(new CropCircleTransformation())
                    .into(ivMeIcon);
        }
    }
    @Override
    public void initListener() {
        super.initListener();
        ivTitleSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),IconSettingsActivity.class));
            }
        });

        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),ChongZhiAcitivty.class));
            }
        });
    }

}
