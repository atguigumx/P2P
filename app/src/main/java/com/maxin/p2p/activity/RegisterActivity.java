package com.maxin.p2p.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxin.p2p.R;
import com.maxin.p2p.base.BaseActivity;
import com.maxin.p2p.common.AppNetConfig;
import com.maxin.p2p.utils.HttpUtils2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;

public class RegisterActivity extends BaseActivity {


    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_setting)
    ImageView ivTitleSetting;
    @InjectView(R.id.et_register_number)
    EditText etRegisterNumber;
    @InjectView(R.id.et_register_name)
    EditText etRegisterName;
    @InjectView(R.id.et_register_pwd)
    EditText etRegisterPwd;
    @InjectView(R.id.et_register_pwdagain)
    EditText etRegisterPwdagain;
    @InjectView(R.id.btn_register)
    Button btnRegister;
    @Override
    public void initTitle() {
        super.initTitle();
        tvTitle.setText("注册");
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

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取输入的数据
                String name = etRegisterName.getText().toString().trim();
                String number = etRegisterNumber.getText().toString().trim();
                String pwd = etRegisterPwd.getText().toString().trim();
                String pwdAgain = etRegisterPwdagain.getText().toString().trim();

                if (TextUtils.isEmpty(number)){
                    showToast("手机号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(name)){
                    showToast("用户名不能为空");
                    return;
                }
                if (!TextUtils.isEmpty(pwd)){
                    if (!pwd.equals(pwdAgain)){
                        showToast("两次密码不一致");
                        return;
                    }
                }else{
                    showToast("密码不能为空");
                    return;
                }

                //连网
                Map<String, String> map = new HashMap<String, String>();
                map.put("name",name);
                map.put("phone",number);
                map.put("password",pwd);

                HttpUtils2.getInstance().post(AppNetConfig.REGISTER, map, new HttpUtils2.OnHttpClientListener() {
                    @Override
                    public void onSuccess(String json) {
                        Log.d("register", "onSuccess: "+json);
                        try {
                            JSONObject obj = new JSONObject(json);
                            boolean isExist = obj.getBoolean("isExist");
                            if (isExist){
                                showToast("用户已存在");
                            }else{
                                showToast("注册成功");
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        Log.d("register", "onFailure: "+message);
                    }
                });
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
        return R.layout.activity_register;
    }


}
