package com.work.teacher;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.work.teacher.bean.PersonalBean;
import com.work.teacher.more.FeedBackActivity;
import com.work.teacher.more.PersonalSettingsActivity;
import com.work.teacher.more.SettingActivity;
import com.work.teacher.more.notice.NoticeListActivity;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;
import com.work.teacher.view.AvatarView;
import com.work.teacher.work.QuestionnarielistActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 更多
 *
 * @author 左丽姬
 */
public class MoreActivity extends Activity implements OnClickListener {

    private ImageView isnet_image;
    private Button top_btn;
    private RelativeLayout rel_more;
    private AvatarView avatar_more;
    private TextView more_teaname, more_subject, more_school;
    private ListView more_list;
    private List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
    private int[] ints = {R.drawable.tiny_icon, R.drawable.notice_icon, R.drawable.problem_icon,
            R.drawable.setting_icon, R.drawable.help_icon};
    private String[] strs = {"微课", "通知", "问卷", "设置", "求助/反馈"};
    private ServecHttp servecHttp;
    private JsonData jsonDate;
    private FinalHttp finalHttp;
    private String key;
    private String userId;
    private Bitmap bitmap;
    private PersonalBean pb = null;
    private MoreBroadCase mbc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        initMore();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        mbc = new MoreBroadCase();
        IntentFilter filter = new IntentFilter(IBaes.ACTION_UPDATE_PERSONAL);
        registerReceiver(mbc, filter);
        IntentFilter filter1 = new IntentFilter(IBaes.ACTION_LOGIN);
        registerReceiver(mbc, filter1);
        IntentFilter filter2 = new IntentFilter(IBaes.ACTION_NET);
        registerReceiver(mbc, filter2);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(mbc);
    }

    public void initMore() {

        servecHttp = new ServecHttp();
        jsonDate = new JsonData();
        finalHttp = new FinalHttp();
        key = SPUtils.get(this, "key", "").toString();
        userId = SPUtils.get(this, "userId", "").toString();

        addMoreList();
        IBaes.net_relative = (RelativeLayout) findViewById(R.id.net_relative);
        IBaes.net_relative.setVisibility(View.GONE);
        isnet_image = (ImageView) findViewById(R.id.isnet_image);
        if (!IBaes.isNet(this)) {
            // 网络不存在时显示
            IBaes.net_relative.setVisibility(View.VISIBLE);
        }
        isnet_image.setOnClickListener(this);
        IBaes.net_relative.setOnClickListener(this);
        ImageView top_back = (ImageView) findViewById(R.id.top_back);
        top_back.setVisibility(View.GONE);
        top_btn = (Button) findViewById(R.id.top_btn);
        rel_more = (RelativeLayout) findViewById(R.id.rel_more);
        avatar_more = (AvatarView) findViewById(R.id.avatar_more);
        more_teaname = (TextView) findViewById(R.id.more_teaname);
        more_subject = (TextView) findViewById(R.id.more_subject);
        more_school = (TextView) findViewById(R.id.more_school);
        more_list = (ListView) findViewById(R.id.more_list);
        more_list.setAdapter(new MoreListAdapter());
        IBaes.setGroupHeight(more_list);
        more_list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = null;
                switch (position) {
                    case 0:
                        // 微课
                        break;
                    case 1:
                        // 通知
                        intent = new Intent(MoreActivity.this, NoticeListActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        // 问卷
                        startActivity(new Intent(MoreActivity.this, QuestionnarielistActivity.class));
                        break;
                    case 3:
                        // 设置
                        intent = new Intent(MoreActivity.this, SettingActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        // 求助与反馈
                        break;
                }

            }

        });

        top_btn.setOnClickListener(this);
        rel_more.setOnClickListener(this);
        getLoginKey();
        // getPerson();
    }

    /**
     * 判断是否是登陆状态
     */
    public void getLoginKey() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(MoreActivity.this, "网络不给力,请稍后...");
            return;
        }
        AjaxParams params = new AjaxParams();
        params.put("userId", userId);
        finalHttp.post(IBaes.IS_LOGIN, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
                // Log.i("test", t.toString());
                Map<String, Object> map = jsonDate.jsonAvatar(t.toString());
                Message msg = new Message();
                msg.obj = map;
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * 获取个人信息
     */
    public void getPerson() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(MoreActivity.this, "网络不给力,请稍后...");
            return;
        }
        AjaxParams params = servecHttp.keyAndId(key, userId);
        finalHttp.post(IBaes.PERSONAL_SETTING, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object t) {
                super.onSuccess(t);
                Log.i("test", t.toString());
                PersonalBean pb = jsonDate.getPersonal(t.toString());
                Message msg = new Message();
                msg.obj = pb;
                msg.what = 0;
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * 添加数据
     */
    public void addMoreList() {
        for (int i = 0; i < ints.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("icon", ints[i]);
            map.put("text", strs[i]);
            lists.add(map);
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    pb = (PersonalBean) msg.obj;
                    if (pb == null) {
                        return;
                    }
                    if (pb != null && pb.getStatus() == 1) {
                        new Thread() {
                            public void run() {
                                URL fileUrl = null;
                                try {
                                    fileUrl = new URL(IBaes.URL + pb.getAvatar());
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
                                    conn.setDoInput(true);
                                    conn.connect();
                                    InputStream is = conn.getInputStream();
                                    bitmap = BitmapFactory.decodeStream(is);
                                    is.close();
                                    handler.post(new Runnable() {

                                        @Override
                                        public void run() {
                                            avatar_more.setImageBitmap(bitmap);
                                        }
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        int grade = 0;
                        if (!"".equals(pb.getGrade())) {
                            grade = Integer.parseInt(pb.getGrade()) / 100;
                        }
                        if ("".equals(pb.getUserName().trim())) {
                            pb.setUserName("未填写");
                        }
                        more_teaname.setText(pb.getUserName());
                        if ("".equals(pb.getSubject().trim())) {
                            pb.setSubject("未添加");
                        }
                        more_subject.setText("科目：" + pb.getSubject());
                        if ("".equals(pb.getSchool().trim())) {
                            pb.setSchool("未添加");
                        }
                        more_school.setText("学校：" + pb.getSchool());
                    } else {
                        IBaes.toastShow(MoreActivity.this, pb.getZhuce());
                    }
                    break;
                case 1:
                    Map<String, Object> map = (Map<String, Object>) msg.obj;
                    int status = (Integer) map.get("status");
                    if (status == 1) {
                        getPerson();
                    } else {
                        SPUtils.remove(MoreActivity.this, "key");
                        SPUtils.remove(MoreActivity.this, "userId");
                        Intent intent = new Intent(MoreActivity.this, LoginActivity.class);
                        intent.putExtra("isRegister", 3);
                        startActivity(intent);
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent;
        switch (v.getId()) {
            case R.id.isnet_image:
                IBaes.net_relative.setVisibility(View.GONE);
                break;
            case R.id.net_relative:
                // 打开设置界面
                intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                startActivity(intent);
                break;
            case R.id.top_btn:
                // 反馈
                intent = new Intent(this, FeedBackActivity.class);
                startActivity(intent);
                break;
            case R.id.rel_more:
                // 个人设置
                intent = new Intent(this, PersonalSettingsActivity.class);
                intent.putExtra("isSub", 0);
                startActivity(intent);
                break;
        }
    }

    class MoreListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            MoreListTag moreListTag = null;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.more_list_item, null);
                moreListTag = new MoreListTag(convertView);
                convertView.setTag(moreListTag);
            } else {
                moreListTag = (MoreListTag) convertView.getTag();
            }

            Map<String, Object> map = lists.get(position);
            moreListTag.more_list_image.setImageResource((Integer) map.get("icon"));
            moreListTag.more_list_text.setText((String) map.get("text"));
            return convertView;
        }
    }

    class MoreListTag {
        private ImageView more_list_image;
        private TextView more_list_text;

        public MoreListTag(View v) {
            more_list_image = (ImageView) v.findViewById(R.id.more_list_image);
            more_list_text = (TextView) v.findViewById(R.id.more_list_text);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            HomeActivity.exit(MoreActivity.this);
        }
        return true;
    }

    class MoreBroadCase extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (IBaes.ACTION_UPDATE_PERSONAL.equals(intent.getAction()) || IBaes.ACTION_LOGIN.equals(intent.getAction())
                    || IBaes.ACTION_NET.equals(intent.getAction())) {
                key = SPUtils.get(MoreActivity.this, "key", "").toString();
                userId = SPUtils.get(MoreActivity.this, "userId", "").toString();
                getPerson();
            }
        }

    }
}
