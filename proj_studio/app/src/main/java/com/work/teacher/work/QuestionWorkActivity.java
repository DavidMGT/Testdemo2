package com.work.teacher.work;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.work.teacher.MessageActivity;
import com.work.teacher.MoreActivity;
import com.work.teacher.TeaClassActivity;
import com.work.teacher.WorkActivity;
import com.work.teacher.bean.LeftSubject;
import com.work.teacher.bean.QuestionAnswer;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;
import com.work.teacher.tool.WorkJson;
import com.work.teacher.view.CustomListView;
import com.work.teacher.view.LoadingDialog;
import com.work.teacher.work.adapter.QuestionConditionAdapter;
import com.work.teacher.work.adapter.QuestionWorkListAdapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.w3c.dom.Text;

import com.work.teacher.R;

/**
 * 题库
 *
 * @author 左丽姬
 */
public class QuestionWorkActivity extends Activity implements OnClickListener, LoadingDialog.OnLoadingDialogResultListener {
    private ImageView isnet_image;
    private FrameLayout fl_type_question, fl_difficulty_question, fl_source_question;
    private TextView type_questionwork, difficulty_questionwork, source_questionwork;
    private ImageView iv_type_questionwork, iv_difficulty_questionwork, iv_source_questionwork;
    private LinearLayout dialog_questionwork;
    private ListView dialog_lists;
    private EditText search_questionwork;
    private CustomListView question_lists;//内容列表
    private TextView question_lists_no;
    private int question;//判断页面是否主页
    private int codititon = 0;//判断显示的调节筛选菜单

    private ServecHttp servecHttp;
    private WorkJson jsonDate;
    private FinalHttp finalHttp;
    private String key;
    private String userId;
    private List<LeftSubject> typs = new ArrayList<LeftSubject>(), difficultys = new ArrayList<LeftSubject>(),
            sources = new ArrayList<LeftSubject>(), alls = new ArrayList<LeftSubject>();
    private QuestionWorkReceiver receiver;

    private String txid = "", nyid = "", jid = "", wid;//保存题型和难易度id,章节id,发布作业ID
    private int sus = 0;//来源(0.系统库，1、共享题库2、收藏3、原创4、痕迹5、错题集);
    private List<QuestionAnswer> answers = new ArrayList<QuestionAnswer>();
    private QuestionWorkListAdapter adapter;
    private int page = IBaes.PAGE;
    private int curpage = 1;
    private boolean harmore = false;//判断是否还有下一页
    private LoadingDialog loadingDialog;

    private Dialog dialog;
    private RelativeLayout relat_dialog_addwrok;
    private TextView tv_collect_dialog, tv_error_dialog, tv_add_dialog, tv_editor_dialog;
    private ImageView iv_collect_dialog, iv_add_dialog, iv_editor_dialog, iv_error_dialog;
    private QuestionAnswer answer = null;//选择的题目
    private String xuke_cs;
    private Button btn_addques;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionwork);
        initQuestion();
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver = new QuestionWorkReceiver();
        IntentFilter filter = new IntentFilter(IBaes.ACTION_NET);
        registerReceiver(receiver, filter);
        IntentFilter filter1 = new IntentFilter(IBaes.ACTION_LOGIN);
        registerReceiver(receiver, filter1);
        IntentFilter filter2 = new IntentFilter(IBaes.ACTION_WORK_HISTORY_SUBJECT);
        registerReceiver(receiver, filter2);
        IntentFilter filter3 = new IntentFilter(IBaes.ACTION_UPDATE_PERSONAL);
        registerReceiver(receiver, filter3);
        IntentFilter filter4 = new IntentFilter(IBaes.ACTION_QUESTION_UPDATE);
        registerReceiver(receiver, filter4);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public void initQuestion() {

        servecHttp = new ServecHttp();
        jsonDate = new WorkJson();
        finalHttp = new FinalHttp();
        key = SPUtils.get(this, "key", "").toString();
        userId = SPUtils.get(this, "userId", "").toString();
        xuke_cs=SPUtils.get(this,"xuke_cs","").toString();
        wid = getIntent().getStringExtra("wid");
        question = getIntent().getIntExtra("question", 0);
        if (question == 0)
            loadingDialog = new LoadingDialog(this.getParent());
        else
            loadingDialog = new LoadingDialog(QuestionWorkActivity.this);
        loadingDialog.setOnLoadingDialogResultListener(this);
        RelativeLayout top_relat = (RelativeLayout) findViewById(R.id.top_relat);
        top_relat.setVisibility(View.GONE);
        if (question != 0) {
            top_relat.setVisibility(View.VISIBLE);
            // 头部设置
            ImageView top_back = (ImageView) findViewById(R.id.top_back);
            top_back.setOnClickListener(this);
            TextView top_text = (TextView) findViewById(R.id.top_text);
            top_text.setText("设置属性");
            Button top_btn = (Button) findViewById(R.id.top_btn);
            top_btn.setVisibility(View.GONE);

            IBaes.net_relative = (RelativeLayout) findViewById(R.id.net_relative);
            IBaes.net_relative.setVisibility(View.GONE);
            isnet_image = (ImageView) findViewById(R.id.isnet_image);
            if (!IBaes.isNet(this)) {
                // 网络不存在时显示
                IBaes.net_relative.setVisibility(View.VISIBLE);
            }
            ImageView left_menu_questionwork = (ImageView) findViewById(R.id.left_menu_questionwork);
            left_menu_questionwork.setOnClickListener(this);
        }

        // 题型
        fl_type_question = (FrameLayout) findViewById(R.id.fl_type_question);
        type_questionwork = (TextView) findViewById(R.id.type_questionwork);
        type_questionwork.setText("题型-不限");
        iv_type_questionwork = (ImageView) findViewById(R.id.iv_type_questionwork);
        fl_type_question.setOnClickListener(this);
        // 易难度
        fl_difficulty_question = (FrameLayout) findViewById(R.id.fl_difficulty_question);
        difficulty_questionwork = (TextView) findViewById(R.id.difficulty_questionwork);
        difficulty_questionwork.setText("易难度-不限");
        iv_difficulty_questionwork = (ImageView) findViewById(R.id.iv_difficulty_questionwork);
        fl_difficulty_question.setOnClickListener(this);
        // 来源
        fl_source_question = (FrameLayout) findViewById(R.id.fl_source_question);
        source_questionwork = (TextView) findViewById(R.id.source_questionwork);
        iv_source_questionwork = (ImageView) findViewById(R.id.iv_source_questionwork);
        fl_source_question.setOnClickListener(this);
        // 搜索
        search_questionwork = (EditText) findViewById(R.id.search_questionwork);
        search_questionwork.setHintTextColor(getResources().getColor(R.color.weakgrey));
        search_questionwork.setGravity(Gravity.CENTER_HORIZONTAL);
        Button btn_secrch = (Button) findViewById(R.id.btn_secrch);
        btn_addques = (Button) findViewById(R.id.btn_addques);
        btn_secrch.setOnClickListener(this);
        btn_addques.setOnClickListener(this);

        //内容列表
        question_lists = (CustomListView) findViewById(R.id.question_lists);
        question_lists_no = (TextView) findViewById(R.id.question_lists_no);
        adapter = new QuestionWorkListAdapter(this, question, answers);
        question_lists.setAdapter(adapter);
        //下拉刷新
        question_lists.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                answers.clear();
                curpage = 1;
                selAnswer();
                question_lists.onRefreshComplete();
            }
        });
        //上拉刷新
        question_lists.setOnLoadListener(new CustomListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (harmore) {
                    curpage++;
                    selAnswer();
                } else {
                    IBaes.toastShow(QuestionWorkActivity.this, "没有更多数据了");
                }
                question_lists.onLoadMoreComplete();
            }
        });

        //题型、难易度、来源列表
        dialog_lists = (ListView) findViewById(R.id.dialog_lists_question);
        dialog_questionwork = (LinearLayout) findViewById(R.id.dialog_questionwork);
        dialog_questionwork.setOnClickListener(this);
        dialog_lists.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LeftSubject obj = alls.get(position);
                if (obj == null)
                    return;
                dialog_questionwork.setVisibility(View.GONE);
                setImage();
                switch (codititon) {
                    case 0:
                        //题型
                        txid = obj.getCs();
                        type_questionwork.setText("题型-" + obj.getCname());
                        break;
                    case 1:
                        //难易度
                        nyid = obj.getCs();
                        if ("不限".equals(obj.getCname().trim())) {
                            nyid = "";
                        }
                        difficulty_questionwork.setText("易难度-" + obj.getCname());
                        break;
                    case 2:
                        //来源
                        sus = position;
                        if(position==3){
                            btn_addques.setVisibility(View.VISIBLE);
                        }else{
                            btn_addques.setVisibility(View.GONE);
                        }
                        source_questionwork.setText("来源-" + obj.getCname());
                        break;
                }
                curpage = 1;
                answers.clear();
                selAnswer();
            }
        });

        selType();
        search_questionwork.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                String search = search_questionwork.getText().toString();
                if (search.length() == 0)
                    search_questionwork.setGravity(Gravity.CENTER_HORIZONTAL);
                else
                    search_questionwork.setGravity(Gravity.LEFT);

            }
        });

        question_lists.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int item = position - 1;
                answer = answers.get(item);
                addDialog(answer);
            }
        });

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent mIntent = null;
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.net_relative:
                // 打开设置界面
                mIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                startActivity(mIntent);
            case R.id.fl_type_question:
                // 题型
                setImage();
                if (codititon == 0 && dialog_questionwork.getVisibility() == View.VISIBLE) {
                    dialog_questionwork.setVisibility(View.GONE);
                    return;
                }
                codititon = 0;
                dialog_questionwork.setVisibility(View.GONE);
                iv_type_questionwork.setImageResource(R.drawable.question_sel);
                type_questionwork.setTextColor(getResources().getColor(R.color.blackgrey));
                setCondition(typs);
                break;
            case R.id.fl_difficulty_question:
                // 易难度
                setImage();
                if (codititon == 1 && dialog_questionwork.getVisibility() == View.VISIBLE) {
                    dialog_questionwork.setVisibility(View.GONE);
                    return;
                }
                codititon = 1;
                dialog_questionwork.setVisibility(View.GONE);
                iv_difficulty_questionwork.setImageResource(R.drawable.question_sel);
                difficulty_questionwork.setTextColor(getResources().getColor(R.color.blackgrey));
                setCondition(difficultys);
                break;
            case R.id.fl_source_question:
                // 来源
                setImage();
                if (codititon == 2 && dialog_questionwork.getVisibility() == View.VISIBLE) {
                    dialog_questionwork.setVisibility(View.GONE);
                    return;
                }
                codititon = 2;
                dialog_questionwork.setVisibility(View.GONE);
                iv_source_questionwork.setImageResource(R.drawable.question_sel);
                source_questionwork.setTextColor(getResources().getColor(R.color.blackgrey));
                setCondition(sources);
                break;
            case R.id.dialog_questionwork:
                //隐藏筛选对话框
                setImage();
                dialog_questionwork.setVisibility(View.GONE);
                break;
            case R.id.ll_collect_dialog:
                //收藏
                setButton();
                relat_dialog_addwrok.setBackgroundResource(R.drawable.dialiog_collect_bg);
                tv_collect_dialog.setTextColor(getResources().getColor(R.color.white));
                iv_collect_dialog.setImageResource(R.drawable.collect_yes);
                if (sus == 2) {
                    //移除收藏
                    removeCollect();
                } else {
                    //收藏
                    addCollect();
                }
                break;
            case R.id.ll_error_dialog:
                //报错
                setButton();
                relat_dialog_addwrok.setBackgroundResource(R.drawable.dialiog_error_bg);
                tv_error_dialog.setTextColor(getResources().getColor(R.color.white));
                iv_error_dialog.setImageResource(R.drawable.error_yes);
                break;
            case R.id.ll_add_dialog:
                //添至作业
                setButton();
                relat_dialog_addwrok.setBackgroundResource(R.drawable.dialiog_addwork_bg);
                tv_add_dialog.setTextColor(getResources().getColor(R.color.white));
                iv_add_dialog.setImageResource(R.drawable.addwork_yes);
                if (question != 0) {
                    addWork();
                } else {
                    IBaes.toastShow(this, "添加失败，请布置作业和编辑作业后操作");
                }
                break;
            case R.id.ll_edtior_dialog:
                //编辑
                setButton();
                relat_dialog_addwrok.setBackgroundResource(R.drawable.dialiog_editor_bg);
                tv_editor_dialog.setTextColor(getResources().getColor(R.color.white));
                iv_editor_dialog.setImageResource(R.drawable.edtior_yes);
                //关闭对话框
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    dialog.cancel();
                }
                if(sus==3) {
                    mIntent = new Intent(this, EditorAnswerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("answer", answer);
                    mIntent.putExtras(bundle);
                    mIntent.putExtra("answer_index", 0);
                    startActivity(mIntent);
                }else{
                    IBaes.toastShow(this,"你不能编辑别人题目...");
                }
                break;
            case R.id.btn_secrch:
                //搜索
                answers.clear();
                curpage = 1;
                selAnswer();
                break;
            case R.id.left_menu_questionwork:
                //添加作业侧滑菜单
                mIntent = new Intent(this, TreeDialog.class);
                startActivityForResult(mIntent, IBaes.WORK_QUESTION_TREE);
                break;
            case R.id.btn_addques:
                //添加题目
                mIntent = new Intent(this, EditorAnswerActivity.class);
                mIntent.putExtra("answer_index", 3);
                startActivity(mIntent);
                break;
        }
    }

    /**
     * 查询类型:难易度，题目类型
     */
    public void selType() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(QuestionWorkActivity.this, "网络不给力,请稍后...");
            return;
        }
        finalHttp.post(IBaes.WORK_QUESTION_TYPE, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
//				Log.i("test", t.toString());
                Map<String, Object> map = jsonDate.jsonMode_Type(t.toString());
                Message msg = new Message();
                msg.obj = map;
                msg.what = 0;
                handler.sendMessage(msg);
            }
        });
        setSources();
    }

    /**
     * 查询题库数据
     */
    public void selAnswer() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(QuestionWorkActivity.this, "网络不给力,请稍后...");
            return;
        }
        String url = "";
        switch (sus) {
            case 0:
                url = IBaes.WORK_QUESTION_SYSTEM;
                break;
            case 1:
                url = IBaes.WORK_QUESTION_SHARE;
                break;
            case 2:
                url = IBaes.WORK_QUESTION_COLLECT;
                break;
            case 3:
                url = IBaes.WORK_QUESTION_OLD;
                break;
            case 4:
                url = IBaes.WORK_QUESTION_MARK;
                break;
            case 5:
                url = IBaes.WORK_QUESTION_ERROR;
                break;
        }
//        Log.i("test", "url=" + url);
        String search = search_questionwork.getText().toString();
        loadingDialog.show();
        loadingDialog.setText("加载中...");
        //key userid 题型ID 难易度ID 搜索索引 章节ID 第几页 每页多少行
//        Log.i("test", "txid=" + txid);
        AjaxParams params = servecHttp.queryQuestionWork(key, userId, txid, nyid, search, jid, page + "", curpage + "");
        finalHttp.post(url, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.i("test", "原创:"+o.toString());
                Map<String, Object> map = jsonDate.jsonQuestionList(o.toString());
                Message msg = new Message();
                msg.obj = map;
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });

    }

    /**
     * 添加收藏
     */
    public void addCollect() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(QuestionWorkActivity.this, "网络不给力,请稍后...");
            return;
        }
        if (answers != null) {
            //key、userId、zyid  作业的id、tid  题目id、ifrom //题目来源 0系统 1个人、diff //题目难度、tixing //题目类型
            // zid //章节id、 ino //题目编号、 grade //年级、 subject //科目
            AjaxParams params = servecHttp.workAddCollect(key, userId, answer, 0);
            finalHttp.post(IBaes.WORK_QUESTION_ADDCOLLECT, params, new AjaxCallBack<Object>() {
                @Override
                public void onSuccess(Object o) {
                    super.onSuccess(o);
//                    Log.i("test","添加收藏:"+o.toString());
                    Map<String, Object> map = new JsonData().jsonAvatar(o.toString());
                    Message msg = new Message();
                    msg.obj = map;
                    msg.what = 2;
                    handler.sendMessage(msg);
                }
            });
        }
    }

    /**
     * 移除收藏
     */
    public void removeCollect() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(QuestionWorkActivity.this, "网络不给力,请稍后...");
            return;
        }
        Log.i("test", "answers=" + answers);
        if (answers != null) {
            //key、userId、zyid  作业的id、tid  题目id、ifrom //题目来源 0系统 1个人、diff //题目难度、tixing //题目类型
            // zid //章节id、 ino //题目编号、 grade //年级、 subject //科目
            AjaxParams params = servecHttp.workAddCollect(key, userId, answer, 1);
            finalHttp.post(IBaes.WORK_QUESTION_REMOVECOLLECT, params, new AjaxCallBack<Object>() {
                @Override
                public void onSuccess(Object o) {
                    super.onSuccess(o);
//                    Log.i("test","添加收藏:"+o.toString());
                    Map<String, Object> map = new JsonData().jsonAvatar(o.toString());
                    Message msg = new Message();
                    msg.obj = map;
                    msg.what = 3;
                    handler.sendMessage(msg);
                }
            });
        }
    }

    /**
     * 添加至作业
     */
    public void addWork() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(QuestionWorkActivity.this, "网络不给力,请稍后...");
            return;
        }
        if("".equals(xuke_cs)){
            IBaes.toastShow(QuestionWorkActivity.this,"你未选择年级学科，赶快去个人资料设置吧！");
            return ;
        }
        //参数：key ,userId,题目对象，学科cs，作业id
        AjaxParams params=servecHttp.workQuesAdd(key,userId,answer,xuke_cs,wid);
        finalHttp.post(IBaes.WORK_ADDQORK_SELECT, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.i("test","添加至作业："+o.toString());
                Map<String,Object> map=new JsonData().jsonAvatar(o.toString());
                Message msg=new Message();
                msg.obj=map;
                msg.what=4;
                handler.sendMessage(msg);
            }
        });

    }

    Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            Map<String, Object> map = (Map<String, Object>) msg.obj;
            int status = (int) map.get("status");
            switch (msg.what) {
                case 0:
                    if (status == 1) {
                        List<LeftSubject> ms = (List<LeftSubject>) map.get("modes");//难易度
                        difficultys.addAll(ms);
                        LeftSubject leftSubject = new LeftSubject();
                        leftSubject.setId("");
                        leftSubject.setCname("不限");
                        typs.add(leftSubject);
                        List<LeftSubject> ts = (List<LeftSubject>) map.get("types");//题型
                        for (LeftSubject l : ts) {
                            if ("单项选择题".equals(l.getCname().trim()) || "填空题".equals(l.getCname())) {
                                typs.add(l);
                            }
                        }
                        //获取章节目录
                        selAnswer();
                    }
                    break;
                case 1:
                    //列表
                    if (status == 1) {
                        question_lists_no.setVisibility(View.GONE);
                        question_lists.setVisibility(View.VISIBLE);
                        List<QuestionAnswer> lists = (List<QuestionAnswer>) map.get("answers");
                        harmore = (boolean) map.get("harmore");
                        answers.addAll(lists);
                        if (answers.size() == 0) {
                            question_lists_no.setVisibility(View.VISIBLE);
                            question_lists.setVisibility(View.GONE);
                        }
                        for (QuestionAnswer q : answers) {
                            q.setSus(sus);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        question_lists_no.setVisibility(View.VISIBLE);
                        question_lists.setVisibility(View.GONE);
                    }
                    if (loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                        loadingDialog.cancel();
                    }
                    break;
                case 2:
                    //添加至收藏
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                        dialog.cancel();
                    }
                    IBaes.toastShow(QuestionWorkActivity.this, map.get("zhuce").toString());
                    break;
                case 3:
                    //移除收藏
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                        dialog.cancel();
                    }
                    if (status == 1) {
                        answers.remove(answer);
                        adapter.notifyDataSetChanged();
                    }
                    IBaes.toastShow(QuestionWorkActivity.this, map.get("zhuce").toString());
                    break;
                case 4:
                    //添加至作业
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                        dialog.cancel();
                    }
                    if (status == 1) {
                        Intent intent=new Intent(IBaes.ACTION_WORK_ADDWROK);
                        sendBroadcast(intent);
                    }
                    IBaes.toastShow(QuestionWorkActivity.this, map.get("zhuce").toString());
                    break;
            }
        }
    };

    /**
     * 设置添加选择的样式
     */
    public void setImage() {
        iv_type_questionwork.setImageResource(R.drawable.question_nosel);
        iv_difficulty_questionwork.setImageResource(R.drawable.question_nosel);
        iv_source_questionwork.setImageResource(R.drawable.question_nosel);
        type_questionwork.setTextColor(getResources().getColor(R.color.weakgrey));
        difficulty_questionwork.setTextColor(getResources().getColor(R.color.weakgrey));
        source_questionwork.setTextColor(getResources().getColor(R.color.weakgrey));
    }

    /**
     * 添加题库来源
     */
    public void setSources() {
        LeftSubject l1 = new LeftSubject();
        l1.setId("0");
        l1.setCname("系统题库");
        sources.add(l1);

        LeftSubject l2 = new LeftSubject();
        l2.setId("1");
        l2.setCname("共享题库");
        sources.add(l2);

        LeftSubject l3 = new LeftSubject();
        l3.setId("2");
        l3.setCname("收藏");
        sources.add(l3);

        LeftSubject l4 = new LeftSubject();
        l4.setId("3");
        l4.setCname("原创");
        sources.add(l4);

        LeftSubject l5 = new LeftSubject();
        l5.setId("4");
        l5.setCname("痕迹");
        sources.add(l5);

        LeftSubject l6 = new LeftSubject();
        l6.setId("5");
        l6.setCname("错题集");
        sources.add(l6);
    }

    /**
     * 条件弹出框
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setCondition(List<LeftSubject> lf) {
        dialog_questionwork.setVisibility(View.VISIBLE);
        alls.clear();
        alls.addAll(lf);
        QuestionConditionAdapter conAdapter = new QuestionConditionAdapter(alls, this);
        dialog_lists.setAdapter(conAdapter);
        IBaes.setGroupHeight(dialog_lists);
    }

    /**
     * 编辑操作对话框（收藏、报错、添至作业、编辑）
     */
    public void addDialog(QuestionAnswer answer) {
        if (question == 0)
            dialog = new Dialog(this.getParent(), R.style.PassDialog);
        else
            dialog = new Dialog(this, R.style.PassDialog);
        dialog.show();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_work, null);
        dialog.setContentView(view);
        if (answer != null) {
            relat_dialog_addwrok = (RelativeLayout) view.findViewById(R.id.relat_dialog_addwrok);
            tv_collect_dialog = (TextView) view.findViewById(R.id.tv_collect_dialog);
            iv_collect_dialog = (ImageView) view.findViewById(R.id.iv_collect_dialog);
            tv_error_dialog = (TextView) view.findViewById(R.id.tv_error_dialog);
            iv_error_dialog = (ImageView) view.findViewById(R.id.iv_error_dialog);
            tv_add_dialog = (TextView) view.findViewById(R.id.tv_add_dialog);
            iv_add_dialog = (ImageView) view.findViewById(R.id.iv_add_dialog);
            tv_editor_dialog = (TextView) view.findViewById(R.id.tv_editor_dialog);
            iv_editor_dialog = (ImageView) view.findViewById(R.id.iv_editor_dialog);
            LinearLayout ll_collect_dialog = (LinearLayout) view.findViewById(R.id.ll_collect_dialog);
            LinearLayout ll_error_dialog = (LinearLayout) view.findViewById(R.id.ll_error_dialog);
            LinearLayout ll_edtior_dialog = (LinearLayout) view.findViewById(R.id.ll_edtior_dialog);
            LinearLayout ll_add_dialog = (LinearLayout) view.findViewById(R.id.ll_add_dialog);
            ll_collect_dialog.setOnClickListener(this);
            ll_error_dialog.setOnClickListener(this);
            ll_edtior_dialog.setOnClickListener(this);
            ll_add_dialog.setOnClickListener(this);
            if (sus == 2) {
                //已经收藏
                tv_collect_dialog.setText("移除");
                relat_dialog_addwrok.setBackgroundResource(R.drawable.dialiog_collect_bg);
                iv_collect_dialog.setImageResource(R.drawable.collect_yes);
                tv_collect_dialog.setTextColor(getResources().getColor(R.color.white));
            }
        }
    }

    /**
     * 题库-》编辑题目
     */
    public void setButton() {
        tv_collect_dialog.setTextColor(getResources().getColor(R.color.weakgrey));
        tv_error_dialog.setTextColor(getResources().getColor(R.color.weakgrey));
        tv_add_dialog.setTextColor(getResources().getColor(R.color.weakgrey));
        tv_editor_dialog.setTextColor(getResources().getColor(R.color.weakgrey));
        iv_collect_dialog.setImageResource(R.drawable.collect_no);
        iv_error_dialog.setImageResource(R.drawable.error_no);
        iv_add_dialog.setImageResource(R.drawable.addwork_no);
        iv_editor_dialog.setImageResource(R.drawable.edtior_no);
    }

    @Override
    public void dialogResult(int tag, int state) {
        if (state == LoadingDialog.SUCCESS) {
            setResult(100);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IBaes.WORK_QUESTION_TREE) {
            jid = data.getStringExtra("sectionId");
            answers.clear();
            curpage = 1;
            selAnswer();
        }
    }

    class QuestionWorkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (IBaes.ACTION_NET.equals(intent.getAction()) || IBaes.ACTION_LOGIN.equals(intent.getAction())) {
                //登陆和网络发生变化的广播
                typs.clear();
                difficultys.clear();
                sources.clear();
                answers.clear();
                key = SPUtils.get(QuestionWorkActivity.this, "key", "").toString();
                userId = SPUtils.get(QuestionWorkActivity.this, "userId", "").toString();
                if (question == 0) {
                    IBaes.net_relative.setVisibility(View.VISIBLE);
                }
                selType();
            }
            if (IBaes.ACTION_WORK_HISTORY_SUBJECT.equals(intent.getAction())) {
                //章节发生变化
                jid = intent.getStringExtra("sectionId");
                curpage = 1;
                answers.clear();
                selAnswer();
            }
            if (IBaes.ACTION_UPDATE_PERSONAL.equals(intent.getAction())||IBaes.ACTION_QUESTION_UPDATE.equals(intent.getAction())) {
                answers.clear();
                curpage = 1;
                selAnswer();
            }
        }
    }
}
