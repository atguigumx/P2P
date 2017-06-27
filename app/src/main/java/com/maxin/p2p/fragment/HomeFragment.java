package com.maxin.p2p.fragment;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.maxin.p2p.R;
import com.maxin.p2p.base.BaseFragment;
import com.maxin.p2p.bean.IndexBean;
import com.maxin.p2p.common.AppNetConfig;
import com.maxin.p2p.view.ProgressView;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by shkstart on 2017/6/20.
 */

public class HomeFragment extends BaseFragment {
    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_setting)
    ImageView ivTitleSetting;
    @InjectView(R.id.banner)
    Banner banner;
    @InjectView(R.id.tv_home_product)
    TextView tvHomeProduct;
    @InjectView(R.id.tv_home_yearrate)
    TextView tvHomeYearrate;
    @InjectView(R.id.proView)
    ProgressView proView;

    @Override
    public void setContent(String json) {
        //解析数据

        IndexBean indexBean = JSON.parseObject(json, IndexBean.class);
        initBanner(indexBean);
        initProgressView(indexBean);
    }

    @Override
    public String getChildUrl() {
        return AppNetConfig.INDEX;
    }

    @Override
    public void initData() {

    }

    private void initProgressView(IndexBean indexBean) {
        String progress = indexBean.getProInfo().getProgress();
        int i=Integer.parseInt(progress);
        proView.setSweepAngle(i);
    }

    private void initBanner(IndexBean indexBean) {
        List list=new ArrayList();
        List<IndexBean.ImageArrBean> imageArr = indexBean.getImageArr();
        for (int i = 0; i < imageArr.size(); i++) {
            String imaurl = imageArr.get(i).getIMAURL();
            list.add(AppNetConfig.BASE_URL + imaurl);
        }
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(list);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void initTitle() {
        tvTitle.setText("首页");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }
    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            //Picasso 加载图片简单用法
            Picasso.with(context).load((String) path).into(imageView);
        }
    }

}
