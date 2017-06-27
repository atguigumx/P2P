package com.maxin.p2p.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maxin.p2p.R;
import com.maxin.p2p.base.BaseActivity;
import com.maxin.p2p.bean.LoginBean;
import com.maxin.p2p.common.AppNetConfig;
import com.maxin.p2p.utils.HttpUtils2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;

public class LoaginActivity extends BaseActivity {


    @InjectView(R.id.tv_login_number)
    TextView tvLoginNumber;
    @InjectView(R.id.et_login_number)
    EditText etLoginNumber;
    @InjectView(R.id.rl_login)
    RelativeLayout rlLogin;
    @InjectView(R.id.tv_login_pwd)
    TextView tvLoginPwd;
    @InjectView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.regitster_tv)
    TextView regitsterTv;
    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_setting)
    ImageView ivTitleSetting;

    @Override
    public void initTitle() {
        super.initTitle();
        tvTitle.setText("登录");
        ivTitleBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void initListener() {
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取输入的账号和密码
                String number = etLoginNumber.getText().toString().trim();
                String pwd = etLoginPwd.getText().toString().trim();
                //校验
                if (TextUtils.isEmpty(number)){
                    showToast("账号不能为空");
                    return;
                }

                if (TextUtils.isEmpty(pwd)){
//                    if (pwd.length()>6){
//                        if ()//有没有非法字符
//                    }
                    showToast("密码不能为空");
                    return;
                }
                Map<String, String> map = new HashMap<String, String>();
                map.put("phone",number);
                map.put("password",pwd);

                HttpUtils2.getInstance().post(AppNetConfig.LOGIN, map, new HttpUtils2.OnHttpClientListener() {
                    @Override
                    public void onSuccess(String json) {
                        // Log.d("json", "onSuccess: "+json);
                        try {
                            JSONObject obj = new JSONObject(json);
                            boolean isOk = obj.getBoolean("success");
                            if (isOk){
                                //登录成功
                                JSONObject data = obj.getJSONObject("data");
                                String name = data.getString("name");
                                String imageurl = data.getString("imageurl");
                                String iscredit = data.getString("iscredit");
                                String phone = data.getString("phone");
                                LoginBean bean = new LoginBean();
                                bean.setIscredit(iscredit);
                                bean.setName(name);
                                bean.setPhone(phone);
                                bean.setImageurl(imageurl);
                                //存储数据
                                saveUser(bean);
                                //跳转
                                startActivity(new Intent(LoaginActivity.this,MainActivity.class));
                                //结束自己
                                finish();
                            }else{
                                //登录失败（账号或者密码不对）
                                showToast("账号或者密码不对");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        Log.d("json", "onFailure: "+message);
                    }
                });
            }
        });
        regitsterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoaginActivity.this,RegisterActivity.class));
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_loagin;
    }


}
