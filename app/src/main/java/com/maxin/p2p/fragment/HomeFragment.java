package com.maxin.p2p.fragment;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxin.p2p.R;
import com.maxin.p2p.base.BaseFragment;
import com.maxin.p2p.bean.IndexBean;
import com.maxin.p2p.common.AppNetConfig;
import com.maxin.p2p.utils.HttpUtils;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    @Override
    protected void initData() {
        getDataFromNet(AppNetConfig.INDEX);
    }

    private void getDataFromNet(final String url) {
        HttpUtils.getInstance().get(url, new HttpUtils.OnHttpClientListener() {
            @Override
            public void onSuccess(String json) {
                processData(json);
                //int i=1/0;
            }
            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    private void processData(String json) {
        try {
            //手动解析Json
            IndexBean indexBean = new IndexBean();
            List<IndexBean.ImageArrBean> list = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray imageArr = jsonObject.optJSONArray("imageArr");
            for(int i = 0; i < imageArr.length(); i++) {
                IndexBean.ImageArrBean imageArrBean = new IndexBean.ImageArrBean();
                JSONObject jsonObject1 = imageArr.getJSONObject(i);
                String id = jsonObject1.optString("ID");
                imageArrBean.setID(id);
                String imapaurl = jsonObject1.optString("IMAPAURL");
                imageArrBean.setIMAPAURL(imapaurl);
                String imaurl = jsonObject1.optString("IMAURL");
                imageArrBean.setIMAURL(imaurl);
                list.add(imageArrBean);
            }
            indexBean.setImageArr(list);

            IndexBean.ProInfoBean proInfoBean = new IndexBean.ProInfoBean();
            JSONObject jsonObject1 = jsonObject.optJSONObject("proInfo");
            String id = jsonObject1.optString("id");
            proInfoBean.setId(id);
            String name = jsonObject1.optString("name");
            proInfoBean.setName(name);
            String money = jsonObject1.optString("money");
            proInfoBean.setMoney(money);

            indexBean.setProInfo(proInfoBean);

            //IndexBean indexBean = JSON.parseObject(json, IndexBean.class);
            List<IndexBean.ImageArrBean> imageArr2 = indexBean.getImageArr();
            List<String> list2=new ArrayList<>();
            for(int i = 0; i < imageArr2.size(); i++) {
                String imaurl = imageArr2.get(i).getIMAURL();
                list2.add(AppNetConfig.BASE_URL+imaurl);
            }
            initBanner(list2);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void initBanner(List<String> list) {
        banner.setImages(list)
                .setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Picasso.with(context).load((String) path).into(imageView);
            }
        })
                .start();
    }

    @Override
    protected void initTitle() {
        tvTitle.setText("首页");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }


}
