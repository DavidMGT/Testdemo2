package com.work.teacher.more.notice;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.work.teacher.R;
import com.work.teacher.adapter.NoticeClassAdapter;
import com.work.teacher.bean.NoticeClass;
import com.work.teacher.bean.QuestionnaireDetailBean;
import com.work.teacher.tool.Constants;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.LogUtils;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;
import com.work.teacher.view.LoadingDialog;
import com.work.teacher.view.LoadingDialog.OnLoadingDialogResultListener;
import com.work.teacher.work.QuestionnarielistActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 选择发布对象班级
 *
 * @author 左丽姬
 */
public class SelectClassActivity extends Activity implements OnClickListener, OnLoadingDialogResultListener {
    private ImageView isnet_image;
    private ListView class_notice_lists;
    private FrameLayout fl_state_neotice;
    private TextView tv_state_notice;
    private NoticeClassAdapter adapter;
    private List<NoticeClass> notices = new ArrayList<NoticeClass>();
    private ServecHttp servecHttp;
    private JsonData jsonDate;
    private FinalHttp finalHttp;
    private String key;
    private String userId;
    private List<NoticeClass> ncs = new ArrayList<NoticeClass>();
    private NoticeClass nc = null;
    private int state = 2;//=0代表定时发布，1代表马上发布
    private String time = "";
    private String noticeid;
    private LoadingDialog dialog;
    private QuestionnaireDetailBean questionnaireDetailBean;
    private FrameLayout fl_state_notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnoticetwo);
        initAddTwo();
    }

    boolean isFromAddQuestion;

    public void initAddTwo() {
        servecHttp = new ServecHttp();
        jsonDate = new JsonData();
        finalHttp = new FinalHttp();
        key = SPUtils.get(this, "key", "").toString();
        LogUtils.debug("key=" + key);
        userId = SPUtils.get(this, "userId", "").toString();
        noticeid = getIntent().getStringExtra("nid");
        isFromAddQuestion = getIntent().getBooleanExtra("isFromAddQuestion", false);//如果是问卷调查跳转过来，应该是第三步
        LogUtils.debug("isFromAddQuestion" + isFromAddQuestion);
        if (isFromAddQuestion) {
            questionnaireDetailBean = Constants.questionnaireDetailBean;
            ((ImageView) (findViewById(R.id.iv_step))).setImageResource(R.drawable.add_work_three);

        }
        if (noticeid == null) {
            noticeid = "177";
        }
        dialog = new LoadingDialog(this);
        dialog.setOnLoadingDialogResultListener(this);
        IBaes.net_relative = (RelativeLayout) findViewById(R.id.net_relative);
        IBaes.net_relative.setVisibility(View.GONE);
        isnet_image = (ImageView) findViewById(R.id.isnet_image);
        if (!IBaes.isNet(this)) {
            // 网络不存在时显示
            IBaes.net_relative.setVisibility(View.VISIBLE);
        }
        isnet_image.setOnClickListener(this);
        IBaes.net_relative.setOnClickListener(this);

        // 头部设置
        ImageView top_back = (ImageView) findViewById(R.id.top_back);
        top_back.setOnClickListener(this);
        ((TextView) findViewById(R.id.top_text)).setText(isFromAddQuestion ? "发布问卷" : "发布通知");
        Button top_btn = (Button) findViewById(R.id.top_btn);
        top_btn.setText("发布");
        top_btn.setOnClickListener(this);

        //内容

        class_notice_lists = (ListView) findViewById(R.id.class_notice_lists);
        fl_state_notice = (FrameLayout) findViewById(R.id.fl_state_notice);
        fl_state_notice.setOnClickListener(this);
        tv_state_notice = (TextView) findViewById(R.id.tv_state_notice);
        adapter = new NoticeClassAdapter(this, notices);
        class_notice_lists.setAdapter(adapter);
        class_notice_lists.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                nc = notices.get(position);
                nc.setSelect(true);
                adapter.notifyDataSetChanged();
                Intent intent = new Intent(SelectClassActivity.this, AddContactsListActivity.class);
                intent.putExtra("classid", nc.getCid());
                intent.putStringArrayListExtra("sel_tea", nc.teas);
                intent.putStringArrayListExtra("sel_stu", nc.stus);
                intent.putStringArrayListExtra("sel_pat", nc.pats);
                startActivityForResult(intent, IBaes.NOTICETWO_NOTICEAMOUNT);
            }
        });
        getClassLists();
    }

    /**
     * 获取班级列表
     */
    public void getClassLists() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(SelectClassActivity.this, "网络不给力,请稍后...");
            return;
        }
        AjaxParams params = servecHttp.keyAndId(key, userId);
        finalHttp.post(IBaes.NOTICE_CLASSLISTS, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
                LogUtils.debug("NOTICE_CLASSLISTS--" + t.toString());
                Map<String, Object> map = jsonDate.jsonNoticeClass(t.toString());
                Message msg = new Message();
                msg.obj = map;
                msg.what = 0;
                handler.sendMessage(msg);
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.isnet_image:
                IBaes.net_relative.setVisibility(View.GONE);
                break;
            case R.id.net_relative:
                // 打开设置界面
                Intent mIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                startActivity(mIntent);
                break;
            case R.id.top_back:
                finish();
                break;
            case R.id.top_btn:
                //发布
                if (isFromAddQuestion) {
                    commitQuestins();
                    break;
                }
                commitNotice();
                break;
            case R.id.fl_state_notice:
                //选择时间
                Intent intent = new Intent(this, StateActivit.class);
                startActivityForResult(intent, IBaes.NOTICETWO_STATE);
                break;
        }
    }

    /**
     * 发布通知
     */
    public void commitNotice() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(SelectClassActivity.this, "网络不给力,请稍后...");
            return;
        }
        if (ncs.size() == 0) {
            IBaes.toastShow(SelectClassActivity.this, "请至少选则一个发布对象");
            return;
        }
        StringBuffer teacher = new StringBuffer("");
        StringBuffer student = new StringBuffer("");
        StringBuffer patr = new StringBuffer("");
        for (NoticeClass n : ncs) {
            if (n.teas != null)
                for (String s : n.teas) {
                    teacher.append(s + ",");
                }
            if (n.stus != null)
                for (String s : n.stus) {
                    student.append(s + ",");
                }
            if (n.pats != null)
                for (String s : n.pats) {
                    patr.append(s + ",");
                }
        }
        String str_tea = "";
        if (teacher.length() > 0) {
            str_tea = teacher.substring(0, teacher.length() - 1);
        }
        String str_stu = "";
        if (student.length() > 0) {
            str_stu = student.substring(0, student.length() - 1);
        }
        String str_pat = "";
        if (patr.length() > 0) {
            str_pat = patr.substring(0, patr.length() - 1);
        }
        if ("".equals(str_tea) && "".equals(str_stu) && "".equals(str_pat)) {
            IBaes.toastShow(SelectClassActivity.this, "请至少选则一个发布对象");
            return;
        }
        long ldate = 0;
        if (state == 0) {
            //得到定时时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date d = sdf.parse(time);
                ldate = d.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        dialog.show();
        dialog.setText("提交中...");
        AjaxParams params = servecHttp.noticeTwo(key, userId, noticeid, str_tea, str_stu, str_pat, state, ldate);
        finalHttp.post(IBaes.NOTICE_TWO, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
                Log.i("test", t.toString());
                Map<String, Object> map = jsonDate.jsonAvatar(t.toString());
                Message msg = new Message();
                msg.obj = map;
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });

    }

    /**
     * 发布通知
     */
    public void commitQuestins() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(SelectClassActivity.this, "网络不给力,请稍后...");
            return;
        }
        if (ncs.size() == 0) {
            IBaes.toastShow(SelectClassActivity.this, "请至少选则一个发布对象");
            return;
        }
        for (NoticeClass n : ncs) {
            if (n.teas != null)
                for (String s : n.teas) {
                    questionnaireDetailBean.getTeachers().add(s);
                }
            if (n.stus != null)
                for (String s : n.stus) {
                    questionnaireDetailBean.getStudents().add(s);
                }
            if (n.pats != null)
                for (String s : n.pats) {
                    questionnaireDetailBean.getJiaZhangs().add(s);
                }
        }
        if (questionnaireDetailBean.getJiaZhangs().size() == 0 && questionnaireDetailBean.getStudents().size() == 0 && questionnaireDetailBean.getTeachers().size() == 0) {
            IBaes.toastShow(SelectClassActivity.this, "请至少选则一个发布对象");
            return;
        }
        long ldate = 0;
        if (state == 0) {
            //得到定时时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date d = sdf.parse(time);
                ldate = d.getTime();
                questionnaireDetailBean.setDstype(2);
                questionnaireDetailBean.setSendtime(ldate + "");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Type type = new TypeToken<QuestionnaireDetailBean>() {
        }.getType();
        String jsonstr = new Gson().toJson(questionnaireDetailBean, type);
        jsonstr = "[" + jsonstr + "]";
        LogUtils.debug("发送问卷调查的jsonstr" + jsonstr);
        dialog.show();
        dialog.setText("提交中...");
        AjaxParams params = new AjaxParams();
        params.put("params", jsonstr);
        finalHttp.post(IBaes.POST_QUESTION, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String t) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(t);
                    int status = (int) jsonObject.get("status");
                    LogUtils.debug("POST_QUESTION" + t);
                    dialog.dismiss();
                    if (status == 1) {
                        Toast.makeText(SelectClassActivity.this, "发布问卷成功", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SelectClassActivity.this, QuestionnarielistActivity.class));
                    } else {
                        Toast.makeText(SelectClassActivity.this, "发布问卷失败", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Map<String, Object> map = null;
            switch (msg.what) {
                case 0:
                    // 班级列表
                    map = (Map<String, Object>) msg.obj;
                    int status = (Integer) map.get("status");
                    if (status == 1) {
                        List<NoticeClass> list = (List<NoticeClass>) map.get("data");
                        LogUtils.debug("NOTICE_CLASSLISTS--" + list.toString());
                        if (list.size() > 0) {
                            notices.addAll(list);
                            adapter.notifyDataSetChanged();
                            IBaes.setGroupHeight(class_notice_lists);
                        }
                    }
                    break;
                case 1:
                    dialog.dismiss();
                    dialog.cancel();
                    map = (Map<String, Object>) msg.obj;
                    IBaes.toastShow(SelectClassActivity.this, map.get("zhuce").toString());
                    if ((Integer) map.get("status") == 1) {
                        Intent intent = new Intent(IBaes.ACTION_NOTICE);
                        sendBroadcast(intent);
                        setResult(IBaes.NOTICETWO_NOTICEONE);
                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        //选择班级
        if (requestCode == IBaes.NOTICETWO_NOTICEAMOUNT && resultCode == IBaes.NOTICEAMOUNT_NOTICETWO) {
            if (nc != null) {
                nc.teas = data.getStringArrayListExtra("tea");
                nc.stus = data.getStringArrayListExtra("stu");
                nc.pats = data.getStringArrayListExtra("pat");
                nc.setT_select(nc.teas.size() + "");
                nc.setX_select(nc.stus.size() + "");
                nc.setP_select(nc.pats.size() + "");
                if (nc.teas.size() == 0 && nc.stus.size() == 0 && nc.pats.size() == 0) {
                    nc.setSelect(false);//如果在该班级里面没有选择发布对象，则设置该班级不在选中状态
                }
                if (ncs.size() == 0 && nc.isSelect()) {
                    ncs.add(nc);
                } else {
                    for (int i = 0; i < ncs.size(); i++) {
                        if (!ncs.get(i).getCid().equals(nc.getCid()) && nc.isSelect()) {
                            ncs.add(nc);
                            break;
                        }
                        if (ncs.get(i).getCid().equals(nc.getCid()) && !nc.isSelect()) {
                            ncs.remove(nc);
                            break;
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }

        //现在时间
        if (requestCode == IBaes.NOTICETWO_STATE) {
            Log.i("test", "今日。。。NOTICETWO_STATE");
            state = data.getIntExtra("state_status", 2);
            tv_state_notice.setText("暂无");
            if (state == 0) {
                time = data.getStringExtra("state_date");
                tv_state_notice.setText(time);
            }
        }
    }


    @Override
    public void dialogResult(int tag, int state) {
        // TODO Auto-generated method stub
        if (state == LoadingDialog.SUCCESS) {
            setResult(100);
            finish();
        }
    }

}
