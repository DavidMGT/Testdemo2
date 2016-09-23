package com.work.student.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.work.student.bean.MessageBean;
import com.work.student.tool.IBaes;
import com.work.student.tool.SPUtils;
import com.work.student.tool.TextUtil;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 定时发送服务
 *
 * @author 左丽姬
 */
public class NoticeService extends Service {

    private static final String TAG = "NoticeService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        // Log.i("test", "进入了服务");
        new NoticeThread().start();
        return super.onStartCommand(intent, flags, startId);
    }

    class NoticeThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    getMessage();
                    sleep(1000 *60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queryNoticeState();

            }
        }
    }

    /**
     * 根据当前时间和是否是定时发布查询通知
     */
    public void queryNoticeState() {
        String key = SPUtils.get(getApplicationContext(), "key", "").toString();
        String userId = SPUtils.get(getApplicationContext(), "userId", "").toString();
        if (!IBaes.isNet(getApplicationContext()))
            return;
        if ("".equals(key))
            return;
        if ("".equals(userId))
            return;
        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        params.put("state", "0");
        params.put("date", System.currentTimeMillis() + "");
        new FinalHttp().post(IBaes.NOTICE_DATE_STATE, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object t) {
                super.onSuccess(t);
                // Log.i("test", t.toString());
                Map<String, Object> map = jsonNoticeUpdate(t.toString());
                Message msg = new Message();
                msg.obj = map;
                msg.what = 0;
                handler.sendMessage(msg);
            }
        });

    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    Map<String, Object> map = (Map<String, Object>) msg.obj;
                    int status = (Integer) map.get("status");
                    if (status == 1) {
                        List<String> lists = (List<String>) map.get("lists");
                        for (String string : lists) {
                            updateNotice(string);
                        }
                    }
                    break;
            }
        }
    };

    /**
     * 修改通知为发布状态
     */
    public void updateNotice(String nid) {
        String key = SPUtils.get(getApplicationContext(), "key", "").toString();
        String userId = SPUtils.get(getApplicationContext(), "userId", "").toString();
        if (!IBaes.isNet(getApplicationContext()))
            return;
        if ("".equals(key))
            return;
        if ("".equals(userId))
            return;

        AjaxParams params = new AjaxParams();
        params.put("key", key);
        params.put("userId", userId);
        params.put("noticeid", nid);
        params.put("state", "2");
        new FinalHttp().post(IBaes.NOTICE_UPDATE_STATE, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
                // Log.i("test", t.toString());
            }
        });
    }

    public Map<String, Object> jsonNoticeUpdate(String resutl) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject(resutl);
            int status = object.optInt("status");
            if (status == 1) {
                List<String> lists = new ArrayList<String>();
                JSONArray array = object.optJSONArray("notice_list");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.optJSONObject(i);
                    String fid = jsonObject.optString("fid");
                    lists.add(fid);
                }
                map.put("lists", lists);
            }
            map.put("status", status);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 定时获取消息
     */
    public void getMessage() {
        Object userid = SPUtils.get(this, "userId", "");
        Log.d(TAG, userid.toString());
        if (TextUtil.isEmpty(userid.toString())) return;

        AjaxParams params = new AjaxParams("userid", userid);
        new FinalHttp().get(IBaes.ACTION_MESSAGE, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String s) {
                List<MessageBean> lists = MessageBean.parser(s);
                //当获取到数据后就通过EventBus发给MessageMsgFragment
                EventBus.getDefault().post(lists);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                Log.d(TAG, Thread.currentThread().getName());
            }
        });
    }

}
