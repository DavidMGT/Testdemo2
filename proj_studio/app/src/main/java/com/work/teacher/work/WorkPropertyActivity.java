package com.work.teacher.work;

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

import com.work.teacher.R;
import com.work.teacher.adapter.NoticeClassAdapter;
import com.work.teacher.bean.NoticeClass;
import com.work.teacher.more.notice.AddContactsListActivity;
import com.work.teacher.more.notice.StateActivit;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;
import com.work.teacher.view.LoadingDialog;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

/**
 * 作业-》第三步--》》设置属性
 *
 * @author 左丽姬
 */
public class WorkPropertyActivity extends Activity implements OnClickListener, LoadingDialog.OnLoadingDialogResultListener {
    private ImageView isnet_image;
    private ListView class_workpropert_lists;
    private FrameLayout fl_state_workpropert,fl_get_workpropert;
    private TextView tv_state_workpropert,tv_get_workpropert;
    private NoticeClassAdapter adapter;
    private List<NoticeClass> notices = new ArrayList<NoticeClass>();
    private ServecHttp servecHttp;
    private JsonData jsonDate;
    private FinalHttp finalHttp;
    private String key;
    private String userId;
    private List<NoticeClass> ncs = new ArrayList<NoticeClass>();
    private NoticeClass nc = null;
    private int state = 2;
    private String time = "";
    private String wid="";
    private LoadingDialog loadingDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workpropert);
        initWorkpropert();
    }

    public void initWorkpropert() {

        servecHttp = new ServecHttp();
        jsonDate = new JsonData();
        finalHttp = new FinalHttp();
        key = SPUtils.get(this, "key", "").toString();
        userId = SPUtils.get(this, "userId", "").toString();
        wid=getIntent().getStringExtra("wid");
        loadingDialog=new LoadingDialog(this);
        loadingDialog.setOnLoadingDialogResultListener(this);

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
        TextView top_text = (TextView) findViewById(R.id.top_text);
        top_text.setText("作业发布");
        Button top_btn = (Button) findViewById(R.id.top_btn);
        top_btn.setText("确定");
        top_btn.setOnClickListener(this);

        class_workpropert_lists = (ListView) findViewById(R.id.class_workpropert_lists);
        fl_state_workpropert = (FrameLayout) findViewById(R.id.fl_state_workpropert);
        fl_state_workpropert.setOnClickListener(this);
        tv_state_workpropert = (TextView) findViewById(R.id.tv_state_workpropert);
        fl_get_workpropert = (FrameLayout) findViewById(R.id.fl_get_workpropert);
        fl_get_workpropert.setOnClickListener(this);
        tv_get_workpropert = (TextView) findViewById(R.id.tv_get_workpropert);
        adapter = new NoticeClassAdapter(this, notices);
        class_workpropert_lists.setAdapter(adapter);
        class_workpropert_lists.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                nc = notices.get(position);
                nc.setSelect(true);
                adapter.notifyDataSetChanged();
                Intent intent = new Intent(WorkPropertyActivity.this, AddContactsListActivity.class);
                intent.putExtra("classid", nc.getCid());
                intent.putStringArrayListExtra("sel_tea", nc.teas);
                intent.putStringArrayListExtra("sel_stu", nc.stus);
                intent.putStringArrayListExtra("sel_pat", nc.pats);
                startActivityForResult(intent, IBaes.WORKPROPERT_NOTICEAMOUNT);
            }
        });
        getClassLists();
        setDefaultDateRes();
        setDefaultDateAccopt();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent mIntent=null;
        switch (v.getId()) {
            case R.id.isnet_image:
                IBaes.net_relative.setVisibility(View.GONE);
                break;
            case R.id.net_relative:
                // 打开设置界面
                mIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                startActivity(mIntent);
                break;
            case R.id.top_btn:
                // 确认发布
                addWork();
                break;
            case R.id.top_back:
                finish();
                break;
            case R.id.fl_state_workpropert:
                // 选择发送时间
                mIntent = new Intent(this, WorkStatActivity.class);
                String text=tv_state_workpropert.getText().toString().trim().replace("(默认)","");
                mIntent.putExtra("re_date",text);
                mIntent.putExtra("re_get", "1990-01-01 00:00");
                startActivityForResult(mIntent, IBaes.WORKPROPERT_STATE);
                break;
            case R.id.fl_get_workpropert:
                // 选择接受时间
                mIntent = new Intent(this, WorkStatActivity.class);
                String t_s=tv_state_workpropert.getText().toString().trim().replace("(默认)","");
                String get=tv_get_workpropert.getText().toString().trim().replace("(默认)","");
                mIntent.putExtra("re_date",get);
                mIntent.putExtra("re_get",t_s);
                startActivityForResult(mIntent, IBaes.NOTICETWO_GET);
                break;

        }
    }

    /**设置默认接受时间*/
    public void setDefaultDateRes(){
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        tv_state_workpropert.setText(sdf.format(gc.getTime())+"(默认)");

    }
    /**接受时间*/
    public void setDefaultDateAccopt(){
        try {
            String text=tv_state_workpropert.getText().toString().trim().replace("(默认)","");
            SimpleDateFormat asdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date t_d=asdf.parse(text);
            int year = t_d.getYear()+1900;
            int month = t_d.getMonth()+1;
            int day =t_d.getDate();
            String strDate=year+"-"+month+"-"+(day+1)+" 8:00:00";
            SimpleDateFormat acctop_sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date=new Date();
            date=acctop_sdf.parse(strDate);
            tv_get_workpropert.setText(acctop_sdf.format(date)+"(默认)");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



    /**
     * 作业发布提交
     */
    public void addWork() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(WorkPropertyActivity.this, "网络不给力,请稍后...");
            return;
        }
        String stu_id="";
        String tea_id="";
        for (NoticeClass nc:ncs){
            for (String s:nc.teas){
                tea_id+=s+",";
            }
            for (String s:nc.stus){
                stu_id+=s+",";
            }
        }
        if("".equals(stu_id)){
            IBaes.toastShow(WorkPropertyActivity.this, "至少选择一个学生...");
            return;
        }
        if(!"".equals(stu_id)){
            stu_id=stu_id.substring(0,stu_id.length()-1);
        }
        if(!"".equals(tea_id)){
            tea_id=tea_id.substring(0,tea_id.length()-1);
        }
        String text=tv_state_workpropert.getText().toString().trim().replace("(默认)","");
        String get=tv_get_workpropert.getText().toString().trim().replace("(默认)","");
        SimpleDateFormat asdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date t_d=asdf.parse(text);
            Date t_g=asdf.parse(get);
            loadingDialog.show();
            loadingDialog.setText("提交中");
            AjaxParams params=servecHttp.workRelease(key,userId,wid,stu_id,tea_id,t_d.getTime()+"",t_g.getTime()+"");
            finalHttp.post(IBaes.WORK_RESELT_COMMIT, params, new AjaxCallBack<Object>() {
                @Override
                public void onSuccess(Object o) {
                    super.onSuccess(o);
//                    Log.i("test","作业发布:"+o.toString());
                    Map<String,Object> map=jsonDate.jsonAvatar(o.toString());
                    Message message=new Message();
                    message.obj=map;
                    message.what=1;
                    handler.sendMessage(message);
                }
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取班级列表
     */
    public void getClassLists() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(WorkPropertyActivity.this, "网络不给力,请稍后...");
            return;
        }
        AjaxParams params = servecHttp.keyAndId(key, userId);
        finalHttp.post(IBaes.NOTICE_CLASSLISTS, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
                // Log.i("test", t.toString());
                Map<String, Object> map = jsonDate.jsonNoticeClass(t.toString());
                Message msg = new Message();
                msg.obj = map;
                msg.what = 0;
                handler.sendMessage(msg);
            }
        });
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Map<String, Object> map = (Map<String, Object>) msg.obj;
            int status = (Integer) map.get("status");
            switch (msg.what) {
                case 0:
                    // 班级列表
                    if (status == 1) {
                        List<NoticeClass> list = (List<NoticeClass>) map.get("data");
                        if (list.size() > 0) {
                            notices.addAll(list);
                            adapter.notifyDataSetChanged();
                            IBaes.setGroupHeight(class_workpropert_lists);
                        }
                    }
                    break;
                case 1:
                    if(loadingDialog.isShowing()){
                        loadingDialog.dismiss();
                        loadingDialog.cancel();
                    }
                    if(status==1){
                        Intent intent=new Intent(IBaes.ACTION_WORK_ADDWROK);
                        sendBroadcast(intent);
                        finish();
                    }
                    IBaes.toastShow(WorkPropertyActivity.this,map.get("zhuce").toString());
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        // 选择班级
        if (requestCode == IBaes.WORKPROPERT_NOTICEAMOUNT && resultCode == IBaes.NOTICEAMOUNT_NOTICETWO) {
            if (nc != null) {
                nc.teas = data.getStringArrayListExtra("tea");
                nc.stus = data.getStringArrayListExtra("stu");
                nc.pats = data.getStringArrayListExtra("pat");
                nc.setT_select(nc.teas.size() + "");
                nc.setX_select(nc.stus.size() + "");
                nc.setP_select(nc.pats.size() + "");
                if (nc.teas.size() == 0 && nc.stus.size() == 0 && nc.pats.size() == 0) {
                    nc.setSelect(false);// 如果在该班级里面没有选择发布对象，则设置该班级不在选中状态
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

        // 现在时间
        if (requestCode == IBaes.WORKPROPERT_STATE) {
            state = data.getIntExtra("state_status", 2);
            time = data.getStringExtra("state_date");
            if (state == 0) {
                tv_state_workpropert.setText(time);
                setDefaultDateAccopt();
            }else{
                tv_state_workpropert.setText(time+"(默认)");
            }
        }
        if(requestCode==IBaes.NOTICETWO_GET){
            //接受时间
            state = data.getIntExtra("state_status", 2);
            time = data.getStringExtra("state_date");
            if (state == 0) {
                tv_get_workpropert.setText(time);
            }else{
                setDefaultDateAccopt();
            }
        }
    }

    @Override
    public void dialogResult(int tag, int state) {
        if(state==LoadingDialog.SUCCESS){
            setResult(100);
            finish();
        }
    }
}
