package com.work.teacher;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.work.teacher.more.PersonalSettingsActivity;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;
import com.work.teacher.view.LoadingDialog;
import com.work.teacher.view.LoadingDialog.OnLoadingDialogResultListener;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 登陆界面
 *
 * @author 左丽姬
 */
public class LoginActivity extends Activity implements OnClickListener, OnLoadingDialogResultListener {

    private EditText name_login, pwd_login;
    private Button login, forget_pwd, to_reg;
    private boolean flag = false;
    private ImageView isnet_image;
    private int isRegister;
    private FinalHttp http;
    private ServecHttp sh;
    private JsonData jd;
    private LoadingDialog load;
    private static boolean isExit = false;
    private static Context context;
    private String key;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        http = new FinalHttp();
        sh = new ServecHttp();
        jd = new JsonData();
        load = new LoadingDialog(this);
        load.setOnLoadingDialogResultListener(this);
        initLogin();
    }

    public void initLogin() {
        isRegister = getIntent().getIntExtra("isRegister", 0);
        IBaes.net_relative = (RelativeLayout) findViewById(R.id.net_relative);
        IBaes.net_relative.setVisibility(View.GONE);
        isnet_image = (ImageView) findViewById(R.id.isnet_image);
        if (!IBaes.isNet(this)) {
            //网络不存在时显示
            IBaes.net_relative.setVisibility(View.VISIBLE);
        }

        name_login = (EditText) findViewById(R.id.name_login);
        String username = (String) SPUtils.get(this, "userName", "");
        if (!"".equals(username))
            name_login.setText(username);
        pwd_login = (EditText) findViewById(R.id.pwd_login);
        name_login.setHintTextColor(getResources().getColor(R.color.weakgrey));
        pwd_login.setHintTextColor(getResources().getColor(R.color.weakgrey));

        login = (Button) findViewById(R.id.login);
        forget_pwd = (Button) findViewById(R.id.forget_pwd);
        to_reg = (Button) findViewById(R.id.to_reg);
        login.setOnClickListener(this);
        forget_pwd.setOnClickListener(this);
        to_reg.setOnClickListener(this);
        isnet_image.setOnClickListener(this);
        IBaes.net_relative.setOnClickListener(this);

        name_login.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!"".equals(name_login.getText().toString()) && !"".equals(pwd_login.getText().toString())) {
                    flag = true;
                    login.setBackgroundColor(getResources().getColor(R.color.green));
                } else {
                    flag = false;
                    login.setBackgroundColor(getResources().getColor(R.color.weakgrey));
                }
            }
        });
        pwd_login.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!"".equals(name_login.getText().toString()) && !"".equals(pwd_login.getText().toString())) {
                    flag = true;
                    login.setBackgroundColor(getResources().getColor(R.color.green));
                } else {
                    flag = false;
                    login.setBackgroundColor(getResources().getColor(R.color.weakgrey));
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent;
        switch (v.getId()) {
            case R.id.login:
                //登陆
                if (!flag)
                    return;

                String name = name_login.getText().toString();
                String pwd = pwd_login.getText().toString();
                if ("".equals(name.trim()))
                    return;
                if ("".equals(pwd.trim()))
                    return;
                if (!IBaes.isNet(this)) {
                    IBaes.toastShow(this, "网络不给力,请稍后...");
                    return;
                }
                load.show();
                load.setText("正在登录...");
                AjaxParams params = sh.setLogin(name, pwd);
                http.post(IBaes.LOGIN, params, new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object t) {
                        // TODO Auto-generated method stub
                        super.onSuccess(t);
//					Log.e("test", t.toString());
                        Map<String, Object> map = jd.jsonLogin(t.toString());
                        Message message = new Message();
                        message.obj = map;
                        message.what = 0;
                        lHandler.sendMessage(message);
                    }
                });
                break;
            case R.id.forget_pwd:
                //忘记密码
                if (isRegister == 2) {
                    finish();
                    return;
                }
                intent = new Intent(this, ForgetActivity.class);
                intent.putExtra("isForget", true);
                startActivity(intent);
                break;
            case R.id.to_reg:
                //注册
                if (isRegister == 1) {
                    finish();
                    return;
                }
                intent = new Intent(this, RegisterActivity.class);
                intent.putExtra("islogin", true);
                startActivity(intent);
                break;
            case R.id.isnet_image:
                IBaes.net_relative.setVisibility(View.GONE);
                break;
            case R.id.net_relative:
                //打开设置界面
                Intent mIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                startActivity(mIntent);
                break;
        }
    }

    Handler lHandler = new Handler() {
        public void handleMessage(Message msg) {
            Map<String, Object> map = (Map<String, Object>) msg.obj;
            int status = (Integer) map.get("status");
            switch (msg.what) {
                case 0:
                    load.dismiss();
                    userId = (String) map.get("userId");
                    IBaes.USER_ID = userId;
                    String zhuce = (String) map.get("zhuce");
                    key = (String) map.get("key");
                    if (status == 1) {
                        IBaes.toastShow(LoginActivity.this, zhuce);
                        SPUtils.put(LoginActivity.this, "userName", name_login.getText().toString());
                        SPUtils.put(LoginActivity.this, "key", key);
                        SPUtils.put(LoginActivity.this, "userId", userId);
                        SPUtils.put(LoginActivity.this, "login_date", System.currentTimeMillis());
                        if (isRegister != 3) {
                            querySub();
                        } else {
                            Intent intent = new Intent(IBaes.ACTION_LOGIN);
                            sendBroadcast(intent);
                        }
                        finish();
                    } else {
                        IBaes.toastShow(LoginActivity.this, zhuce);
                    }
                    break;
                case 1:
                    if (status == 1) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(LoginActivity.this, PersonalSettingsActivity.class);
                        intent.putExtra("isSub", 1);
                        startActivity(intent);
                    }
                    break;

            }
        }
    };

    /**
     * 判断用户是否选中章节
     */
    public void querySub() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(this, "网络不给力,请稍后...");
            return;
        }
        AjaxParams params = sh.keyAndId(key, userId);
        http.post(IBaes.WORK_SUBJECT_LISTS, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
                Log.i("test", t.toString());
                Map<String, Object> map = jd.jsonAvatar(t.toString());
                Message msg = new Message();
                msg.obj = map;
                msg.what = 1;
                lHandler.sendMessage(msg);
            }
        });
    }

    @Override
    public void dialogResult(int tag, int state) {
        // TODO Auto-generated method stub
        if (state == LoadingDialog.SUCCESS) {
            setResult(100);
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isRegister == 3 && !"".equals(SPUtils.get(this, "key", ""))) {
                finish();
            } else {
                exitLogin();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void exitLogin() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(context, "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 3000);
        } else {
            ((Activity) context).finish();
            System.exit(0);
        }
    }

}
