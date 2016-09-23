package com.work.teacher.more.notice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.work.teacher.R;
import com.work.teacher.adapter.NoticeAmountAdapter;
import com.work.teacher.bean.TeaStuPat;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 选择发布对象：老师，家长，学生列表
 *
 * @author 左丽姬
 */
public class AddContactsListActivity extends Activity implements OnClickListener {
    private ImageView isnet_image;
    private ServecHttp servecHttp;
    private JsonData jsonDate;
    private FinalHttp finalHttp;
    private String key;
    private String userId;
    private ArrayList<String> teachers = null;
    private ArrayList<String> students = new ArrayList<String>();
    private ArrayList<String> patriarchs = new ArrayList<String>();
    private List<TeaStuPat> teas = new ArrayList<TeaStuPat>();
    private List<TeaStuPat> stus = new ArrayList<TeaStuPat>();
    private List<TeaStuPat> pats = new ArrayList<TeaStuPat>();
    private TextView teacher_noticeamount, student_noticeamount, patriarch_noticeamount;
    private ListView student_notice_lits, teacher_notice_lits, patriarch_notice_lits;
    private FrameLayout ll_1, ll_2, ll_3;
    private NoticeAmountAdapter tea_adapter, stu_adapter, pat_adapter;
    private RadioButton rb_teacher, rb_student, rb_patriarch;
    private String classid = "";
    private boolean tea_flag = false, stu_flag = false, pat_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticeamount);
        initNoticeAmoun();
    }

    public void initNoticeAmoun() {

        classid = getIntent().getStringExtra("classid");
        teachers = getIntent().getStringArrayListExtra("sel_tea");
        students = getIntent().getStringArrayListExtra("sel_stu");
        patriarchs = getIntent().getStringArrayListExtra("sel_pat");

        if (teachers == null)
            teachers = new ArrayList<String>();
        if (students == null)
            students = new ArrayList<String>();
        if (patriarchs == null)
            patriarchs = new ArrayList<String>();

        servecHttp = new ServecHttp();
        finalHttp = new FinalHttp();
        jsonDate = new JsonData();
        key = SPUtils.get(this, "key", "").toString();
        userId = SPUtils.get(this, "userId", "").toString();

        IBaes.net_relative = (RelativeLayout) findViewById(R.id.net_relative);
        IBaes.net_relative.setVisibility(View.GONE);
        isnet_image = (ImageView) findViewById(R.id.isnet_image);
        if (!IBaes.isNet(this)) {
            // 网络不存在时显示
            IBaes.net_relative.setVisibility(View.VISIBLE);
        }
        isnet_image.setOnClickListener(this);
        IBaes.net_relative.setOnClickListener(this);
        initView();
        initListView();
        getAmunt();
    }

    private void initListView() {
        tea_adapter = new NoticeAmountAdapter(this, teas);
        stu_adapter = new NoticeAmountAdapter(this, stus);
        pat_adapter = new NoticeAmountAdapter(this, pats);
        student_notice_lits.setAdapter(stu_adapter);
        teacher_notice_lits.setAdapter(tea_adapter);
        patriarch_notice_lits.setAdapter(pat_adapter);

        teacher_notice_lits.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                TeaStuPat tsp = teas.get(position);
                if (tsp.isSelect()) {
                    if (isList(teachers, tsp.getId()))
                        teachers.remove(tsp.getId());
                    tsp.setSelect(false);
                } else {
                    tsp.setSelect(true);

                    if (!isList(teachers, tsp.getId()))
                        teachers.add(tsp.getId());
                }
                tea_adapter.notifyDataSetChanged();
                teacher_noticeamount.setText(teachers.size() + "/" + teas.size());
                if (teachers.size() == teas.size()) {
                    // 全选
                    tea_flag = false;
                    rb_student.setChecked(true);
                } else {
                    tea_flag = true;
                    rb_student.setChecked(false);
                }

            }
        });
        student_notice_lits.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                TeaStuPat tsp = stus.get(position);
                if (tsp.isSelect()) {
                    if (isList(students, tsp.getId()))
                        students.remove(tsp.getId());
                    tsp.setSelect(false);
                } else {
                    tsp.setSelect(true);
                    if (!isList(students, tsp.getId()))
                        students.add(tsp.getId());
                }
                stu_adapter.notifyDataSetChanged();
                student_noticeamount.setText(students.size() + "/" + stus.size());
                if (students.size() == stus.size()) {
                    // 全选
                    stu_flag = false;
                    rb_student.setChecked(true);
                } else {
                    stu_flag = true;
                    rb_student.setChecked(false);
                }
            }
        });
        patriarch_notice_lits.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                TeaStuPat tsp = pats.get(position);
                if (tsp.isSelect()) {
                    if (isList(patriarchs, tsp.getId()))
                        patriarchs.remove(tsp.getId());
                    tsp.setSelect(false);
                } else {
                    tsp.setSelect(true);
                    if (!isList(patriarchs, tsp.getId()))
                        patriarchs.add(tsp.getId());
                }
                pat_adapter.notifyDataSetChanged();
                patriarch_noticeamount.setText(patriarchs.size() + "/" + pats.size());
                if (patriarchs.size() == pats.size()) {
                    // 全选
                    pat_flag = false;
                    rb_patriarch.setChecked(true);
                } else {
                    pat_flag = true;
                    rb_patriarch.setChecked(false);
                }
            }
        });
    }

    private void initView() {
        // 头部设置
        ImageView top_back = (ImageView) findViewById(R.id.top_back);
        top_back.setOnClickListener(this);
        TextView top_text = (TextView) findViewById(R.id.top_text);
        top_text.setText("发布对象");
        Button top_btn = (Button) findViewById(R.id.top_btn);
        top_btn.setText("完成");
        top_btn.setOnClickListener(this);
        teacher_noticeamount = (TextView) findViewById(R.id.teacher_noticeamount);
        student_noticeamount = (TextView) findViewById(R.id.student_noticeamount);
        patriarch_noticeamount = (TextView) findViewById(R.id.patriarch_noticeamount);
        student_notice_lits = (ListView) findViewById(R.id.student_notice_lits);
        teacher_notice_lits = (ListView) findViewById(R.id.teacher_notice_lits);
        patriarch_notice_lits = (ListView) findViewById(R.id.patriarch_notice_lits);
        ll_1 = (FrameLayout) findViewById(R.id.ll_1);
        ll_2 = (FrameLayout) findViewById(R.id.ll_2);
        ll_3 = (FrameLayout) findViewById(R.id.ll_3);
        rb_teacher = (RadioButton) findViewById(R.id.rb_teacher);
        rb_student = (RadioButton) findViewById(R.id.rb_student);
        rb_patriarch = (RadioButton) findViewById(R.id.rb_patriarch);
        rb_teacher.setOnClickListener(this);
        rb_student.setOnClickListener(this);
        rb_patriarch.setOnClickListener(this);
    }

    /**
     * 判断集合里面是否已经有该数据
     */
    public boolean isList(List<String> lists, String id) {
        boolean bl = false;
        for (String string : lists) {
            if (string.equals(id)) {
                bl = true;
            }
        }
        return bl;
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
            case R.id.top_btn:
                // 退出
                exitAmount();
                break;
            case R.id.rb_teacher:
                teaAll();
                break;
            case R.id.rb_student:
                stuAll();
                break;
            case R.id.rb_patriarch:
                patAll();
                break;
        }
    }

    /**
     * 选择对象返回发布第二步
     */
    public void exitAmount() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra("tea", teachers);
        intent.putStringArrayListExtra("stu", students);
        intent.putStringArrayListExtra("pat", patriarchs);
        setResult(IBaes.NOTICEAMOUNT_NOTICETWO, intent);
        finish();
    }

    /**
     * 老师全选
     */
    public void teaAll() {
        if (tea_flag) {
            tea_flag = false;
            for (TeaStuPat t : teas) {
                if (isList(teachers, t.getId()))
                    teachers.remove(t.getId());
                t.setSelect(false);
            }
            rb_teacher.setChecked(false);
            tea_adapter.notifyDataSetChanged();
        } else {
            tea_flag = true;
            for (TeaStuPat t : teas) {
                if (!isList(teachers, t.getId()))
                    teachers.add(t.getId());
                t.setSelect(true);
            }
            rb_teacher.setChecked(true);
            tea_adapter.notifyDataSetChanged();
        }
        teacher_noticeamount.setText(patriarchs.size() + "/" + pats.size());
    }

    /**
     * 学生全选
     */
    public void stuAll() {
        if (stu_flag) {
            stu_flag = false;
            for (TeaStuPat t : stus) {
                if (isList(students, t.getId()))
                    students.remove(t.getId());
                t.setSelect(false);
            }
            rb_student.setChecked(false);
            stu_adapter.notifyDataSetChanged();
        } else {
            stu_flag = true;
            for (TeaStuPat t : stus) {
                if (!isList(students, t.getId()))
                    teachers.add(t.getId());
                t.setSelect(true);
            }
            rb_student.setChecked(true);
            stu_adapter.notifyDataSetChanged();
        }
        student_noticeamount.setText(patriarchs.size() + "/" + pats.size());
    }

    /**
     * 家长全选
     */
    public void patAll() {
        if (pat_flag) {
            pat_flag = false;
            for (TeaStuPat t : pats) {
                if (isList(patriarchs, t.getId()))
                    patriarchs.remove(t.getId());
                t.setSelect(false);
            }
            rb_patriarch.setChecked(false);
            pat_adapter.notifyDataSetChanged();
        } else {
            pat_flag = true;
            for (TeaStuPat t : pats) {
                if (!isList(patriarchs, t.getId()))
                    patriarchs.add(t.getId());
                t.setSelect(true);
            }
            rb_patriarch.setChecked(true);
            pat_adapter.notifyDataSetChanged();
        }
        patriarch_noticeamount.setText(patriarchs.size() + "/" + pats.size());
    }

    /**
     * 获取网络数据
     */
    public void getAmunt() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(AddContactsListActivity.this, "网络不给力,请稍后...");
            return;
        }
        AjaxParams params = servecHttp.noticeAmount(key, userId, classid);
        finalHttp.post(IBaes.NOTICE_AMOUNT, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object t) {
                super.onSuccess(t);
//				Log.i("test", t.toString());
                Map<String, Object> map = jsonDate.jsonNoticeAmount(t.toString());
                Message msg = new Message();
                msg.obj = map;
                msg.what = 0;
                handler.sendMessage(msg);
            }
        });
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Map<String, Object> map = (Map<String, Object>) msg.obj;
                    int status = (Integer) map.get("status");
                    String zhuce = (String) map.get("zhuce");
                    if (status == 1) {
                        List<TeaStuPat> t_lists = (List<TeaStuPat>) map.get("t_lists");
                        List<TeaStuPat> p_lists = (List<TeaStuPat>) map.get("p_lists");
                        List<TeaStuPat> s_lists = (List<TeaStuPat>) map.get("s_lists");
                        if (t_lists != null) {
                            if (teachers != null) {
                                for (TeaStuPat t : t_lists) {
                                    for (String s : teachers) {
                                        if (t.getId().equals(s)) {
                                            Log.i("test", "进入");
                                            t.setSelect(true);
                                        }
                                    }
                                }
                            }
                            teas.addAll(t_lists);
                            tea_adapter.notifyDataSetChanged();
                            IBaes.setGroupHeight(teacher_notice_lits);
                        } else {
                            ll_1.setVisibility(View.GONE);
                        }
                        if (s_lists != null) {
                            if (students != null) {
                                for (TeaStuPat t : s_lists) {
                                    for (String s : students) {
                                        if (t.getId().equals(s)) {
                                            t.setSelect(true);
                                        }
                                    }
                                }
                            }
                            stus.addAll(s_lists);
                            stu_adapter.notifyDataSetChanged();
                            IBaes.setGroupHeight(student_notice_lits);
                        } else {
                            ll_2.setVisibility(View.GONE);
                        }
                        if (p_lists != null) {
                            if (patriarchs != null) {
                                for (TeaStuPat t : p_lists) {
                                    for (String s : patriarchs) {
                                        if (t.getId().equals(s)) {

                                            t.setSelect(true);
                                        }
                                    }
                                }
                            }
                            pats.addAll(p_lists);
                            pat_adapter.notifyDataSetChanged();
                            IBaes.setGroupHeight(patriarch_notice_lits);
                        } else {
                            ll_3.setVisibility(View.GONE);
                        }
                        teacher_noticeamount.setText(teachers.size() + "/" + teas.size());
                        student_noticeamount.setText(students.size() + "/" + stus.size());
                        patriarch_noticeamount.setText(patriarchs.size() + "/" + pats.size());
                    } else {
                        IBaes.toastShow(AddContactsListActivity.this, zhuce);
                    }
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitAmount();
        }
        return true;
    }
}
