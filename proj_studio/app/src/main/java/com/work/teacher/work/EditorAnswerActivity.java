package com.work.teacher.work;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ant.liao.GifView;
import com.work.teacher.R;
import com.work.teacher.bean.LeftSubject;
import com.work.teacher.bean.QuestionAnswer;
import com.work.teacher.bean.SubAndGrade;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;
import com.work.teacher.tool.WorkJson;
import com.work.teacher.view.LoadingDialog;
import com.work.teacher.view.SwipeListView;
import com.work.teacher.work.adapter.EditorAnswerChoseAdapter;
import com.work.teacher.work.adapter.QuestionConditionAdapter;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作业-》题库--》》编辑题目
 * Created by 左丽姬 on 2016/9/8.
 */
public class EditorAnswerActivity extends Activity implements View.OnClickListener, LoadingDialog.OnLoadingDialogResultListener {
    private ImageView isnet_image;//取消进入设置界面（只有在没有网络的时候显示）
    private ServecHttp servecHttp;
    private WorkJson jsonDate;
    private FinalHttp finalHttp;
    private String key;
    private String userId;
    private QuestionAnswer answer;

    private FrameLayout fl_type_question, fl_difficulty_question, fl_source_question;
    private TextView type_questionwork, difficulty_questionwork, source_questionwork;
    private ImageView iv_type_questionwork, iv_difficulty_questionwork, iv_source_questionwork;
    private LinearLayout dialog_questionwork, show_recode, click_recorde;
    private ListView dialog_lists;
    private String txid = "", nyid = "", jid = "", wid, KnowId = "", sectionId, subjectCs, jiaocaiCs;
    private ArrayList<String> knows = new ArrayList<String>();
    ;//保存题型和难易度id,章节id,发布作业ID,知识点id
    private List<LeftSubject> typs = new ArrayList<LeftSubject>(), difficultys = new ArrayList<LeftSubject>(), alls = new ArrayList<LeftSubject>();
    private int codititon = 0;//判断显示的调节筛选菜单

    private Button add_choice, record_editoranswer, commit_editoranswer, del_editoranswer, add_image_editoranswer, bunexapin_image_editoranswer, bunanswer_image_editoranswer, section_editoranswer;
    private SwipeListView choice_lists;
    private EditText body_editoranswer, pack_editoranswer, exapin_editoranswer;
    private LinearLayout choice_answer_editoranswer, pack_answer_editoranswer;
    private ImageView iv_pack_editoranswer, iv_body_editoranswer, iv_exapin_editoranswer;//保存答案的图片、题干的图片、解析
    private ImageView iv_record_gif;
    private int easy = 1;//难易度等级
    private int answer_index;//0、原创编辑、1、作业发布选题，2、作业题目编辑、3、添加原创
    private String fileName = "";
    private String pathName = "";
    private MediaRecorder recorder;
    private boolean record_flag = false;// 开始录音
    private String path = "";// 保存录音路径
    private List<SubAndGrade> Choes = new ArrayList<>();
    private List<SubAndGrade> Alls = new ArrayList<>();
    private String[] letter = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"};
    private EditorAnswerChoseAdapter adapter;
    private List<ImagePathPosition> imgs_body = new ArrayList<ImagePathPosition>();
    private List<ImagePathPosition> imgs_answer = new ArrayList<ImagePathPosition>();
    private List<ImagePathPosition> imgs_exping = new ArrayList<ImagePathPosition>();
    private String imageName;// 图片名字
    private int show_prick = 0;
    private int isChose = 0;//是否是选择题(0,是选择题，1不是选择题)
    private int show_chose = 0;
    private boolean isAdd = false;//是否已经添加
    private LoadingDialog loadingDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editoranswer);
        initWork();
    }

    public void initWork() {

        key = SPUtils.get(this, "key", "").toString();
        userId = SPUtils.get(this, "userId", "").toString();//用户登录保存的key和userid
        servecHttp = new ServecHttp();
        jsonDate = new WorkJson();
        finalHttp = new FinalHttp();
        loadingDialog = new LoadingDialog(EditorAnswerActivity.this);
        loadingDialog.setOnLoadingDialogResultListener(this);

        answer = (QuestionAnswer) getIntent().getSerializableExtra("answer");
        sectionId = getIntent().getStringExtra("sectionId");
        subjectCs = getIntent().getStringExtra("subjectCs");
        jiaocaiCs = getIntent().getStringExtra("jiaocaiCs");
        wid = getIntent().getStringExtra("wid");

        answer_index = getIntent().getIntExtra("answer_index", 0);

        IBaes.net_relative = (RelativeLayout) findViewById(R.id.net_relative);
        IBaes.net_relative.setVisibility(View.GONE);
        isnet_image = (ImageView) findViewById(R.id.isnet_image);
        if (!IBaes.isNet(this)) {
            //网络不存在时显示
            IBaes.net_relative.setVisibility(View.VISIBLE);
        }
        isnet_image.setOnClickListener(this);
        IBaes.net_relative.setOnClickListener(this);

        // 头部设置
        ImageView top_back = (ImageView) findViewById(R.id.top_back);
        top_back.setOnClickListener(this);
        TextView top_text = (TextView) findViewById(R.id.top_text);
        top_text.setText("学科:" + SPUtils.get(this, "xuke", "").toString());
        Button top_btn = (Button) findViewById(R.id.top_btn);
        top_btn.setVisibility(View.GONE);

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
        // 知识点
        fl_source_question = (FrameLayout) findViewById(R.id.fl_source_question);
        source_questionwork = (TextView) findViewById(R.id.source_questionwork);
        source_questionwork.setText("知识点");
        iv_source_questionwork = (ImageView) findViewById(R.id.iv_source_questionwork);
        fl_source_question.setOnClickListener(this);
        selTypeAnswer();
        //题型、难易度、来源列表
        dialog_lists = (ListView) findViewById(R.id.dialog_lists_question);
        dialog_questionwork = (LinearLayout) findViewById(R.id.dialog_questionwork);
        dialog_questionwork.setOnClickListener(this);
        dialog_lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LeftSubject obj = alls.get(position);
                if (obj == null)
                    return;
                dialog_questionwork.setVisibility(View.GONE);
                setImageAnswer();
                switch (codititon) {
                    case 0:
                        //题型
                        txid = obj.getCs();
                        type_questionwork.setText("题型-" + obj.getCname());
                        if (obj.getCname().indexOf("选择题") != -1) {
                            isChose = 0;
                            choice_answer_editoranswer.setVisibility(View.VISIBLE);
                            pack_answer_editoranswer.setVisibility(View.GONE);
                            if (Choes.size() == 0) {
                                defaultChose();
                            }
                        } else {
                            pack_answer_editoranswer.setVisibility(View.VISIBLE);
                            choice_answer_editoranswer.setVisibility(View.GONE);
                            isChose = 1;
                        }
                        if (obj.getCname().indexOf("听力") != -1) {
                            show_recode.setVisibility(View.VISIBLE);
                        } else {
                            show_recode.setVisibility(View.GONE);
                        }
                        break;
                    case 1:
                        //难易度
                        nyid = obj.getCs();
                        if ("不限".equals(obj.getCname().trim())) {
                            nyid = "";
                            easy = position + 1;
                        }
                        easy = position;
                        difficulty_questionwork.setText("易难度-" + obj.getCname());
                        break;
                }
            }
        });

        section_editoranswer = (Button) findViewById(R.id.section_editoranswer);
        section_editoranswer.setEnabled(false);
        if (answer_index == 3) {
            section_editoranswer.setEnabled(true);
            section_editoranswer.setOnClickListener(this);
        }

        show_recode = (LinearLayout) findViewById(R.id.show_recode);
        show_recode.setVisibility(View.GONE);
        click_recorde = (LinearLayout) findViewById(R.id.click_recorde);
        click_recorde.setOnClickListener(this);
        record_editoranswer = (Button) findViewById(R.id.record_editoranswer);
        iv_record_gif = (ImageView) findViewById(R.id.iv_record_gif);
        del_editoranswer = (Button) findViewById(R.id.del_editoranswer);
        commit_editoranswer = (Button) findViewById(R.id.commit_editoranswer);
        del_editoranswer.setOnClickListener(this);
        commit_editoranswer.setOnClickListener(this);


        body_editoranswer = (EditText) findViewById(R.id.body_editoranswer);
        body_editoranswer.addTextChangedListener(watcher_body);
        body_editoranswer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        iv_body_editoranswer = (ImageView) findViewById(R.id.iv_body_editoranswer);
        add_image_editoranswer = (Button) findViewById(R.id.add_image_editoranswer);
        bunexapin_image_editoranswer = (Button) findViewById(R.id.bunexapin_image_editoranswer);
        bunanswer_image_editoranswer = (Button) findViewById(R.id.bunanswer_image_editoranswer);
        add_image_editoranswer.setOnClickListener(this);
        bunexapin_image_editoranswer.setOnClickListener(this);
        bunanswer_image_editoranswer.setOnClickListener(this);
        //选择题布局
        choice_answer_editoranswer = (LinearLayout) findViewById(R.id.choice_answer_editoranswer);
        add_choice = (Button) findViewById(R.id.add_choice);
        add_choice.setOnClickListener(this);
        choice_lists = (SwipeListView) findViewById(R.id.choice_lists);
        adapter = new EditorAnswerChoseAdapter(this, Choes);
        adapter.setOnRightItemClickListener(choice_lists);
        adapter.onFouse();
        choice_lists.setAdapter(adapter);
        choice_lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (SubAndGrade s : Choes) {
                    if (s != null) {
                        s.ischeck = false;
                    }
                }
                SubAndGrade sag = Choes.get(position);
                if (sag != null) {
                    sag.ischeck = true;
                }
                adapter.notifyDataSetChanged();
            }
        });
        //填空题布局
        pack_answer_editoranswer = (LinearLayout) findViewById(R.id.pack_answer_editoranswer);
        iv_pack_editoranswer = (ImageView) findViewById(R.id.iv_pack_editoranswer);
        pack_editoranswer = (EditText) findViewById(R.id.pack_editoranswer);
        pack_editoranswer.addTextChangedListener(watcher_answer);
        pack_editoranswer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        exapin_editoranswer = (EditText) findViewById(R.id.exapin_editoranswer);
        exapin_editoranswer.addTextChangedListener(watcher_exapin);
        exapin_editoranswer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        iv_exapin_editoranswer = (ImageView) findViewById(R.id.iv_exapin_editoranswer);
        del_editoranswer.setVisibility(View.GONE);
        if (answer_index == 1 || answer_index == 3) {
            choice_answer_editoranswer.setVisibility(View.VISIBLE);
            defaultChose();
            if (sectionId != null && !"".equals(sectionId))
                querySection(sectionId);
        } else {
            if (answer != null) {
                editorAnswer();
            }
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent mIntent = null;
        switch (v.getId()) {
            case R.id.isnet_image:
                IBaes.net_relative.setVisibility(View.GONE);
                break;
            case R.id.net_relative:
                //打开设置界面
                mIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                startActivity(mIntent);
                break;
            case R.id.top_back:
                finish();
                break;
            case R.id.fl_type_question:
                // 题型
                setImageAnswer();
                if (codititon == 0 && dialog_questionwork.getVisibility() == View.VISIBLE) {
                    dialog_questionwork.setVisibility(View.GONE);
                    return;
                }
                codititon = 0;
                dialog_questionwork.setVisibility(View.GONE);
                iv_type_questionwork.setImageResource(R.drawable.question_sel);
                type_questionwork.setTextColor(getResources().getColor(R.color.blackgrey));
                setConditionAnswer(typs);
                break;
            case R.id.fl_difficulty_question:
                // 易难度
                setImageAnswer();
                if (codititon == 1 && dialog_questionwork.getVisibility() == View.VISIBLE) {
                    dialog_questionwork.setVisibility(View.GONE);
                    return;
                }
                codititon = 1;
                dialog_questionwork.setVisibility(View.GONE);
                iv_difficulty_questionwork.setImageResource(R.drawable.question_sel);
                difficulty_questionwork.setTextColor(getResources().getColor(R.color.blackgrey));
                setConditionAnswer(difficultys);
                break;
            case R.id.fl_source_question:
                // 知识点
                setImageAnswer();
                dialog_questionwork.setVisibility(View.GONE);
                iv_source_questionwork.setImageResource(R.drawable.question_sel);
                source_questionwork.setTextColor(getResources().getColor(R.color.blackgrey));
                mIntent = new Intent(this, KnowledgeDialog.class);
                mIntent.putExtra("knowid", KnowId);
                mIntent.putStringArrayListExtra("knows", knows);
                startActivityForResult(mIntent, IBaes.WORK_EDITORANSWER_TREE);
                break;
            case R.id.dialog_questionwork:
                //隐藏筛选对话框
                setImageAnswer();
                dialog_questionwork.setVisibility(View.GONE);
                break;
            case R.id.click_recorde:
                //录音
                if (!record_flag) {
                    record_flag = true;
                    // 得到焦点
                    record_editoranswer.setText("停止    说话");
                    iv_record_gif.setImageResource(R.drawable.record_spenk);
                    record_editoranswer.setTextColor(getResources().getColor(R.color.green));
                    initRecordAnswer();
                } else {
                    // 失去焦点
                    record_flag = false;
                    iv_record_gif.setImageResource(R.drawable.record_finish);
                    record_editoranswer.setText("开始    说话");
                    record_editoranswer.setTextColor(getResources().getColor(R.color.weakgrey));
                    if (recorder != null) {
                        recorder.stop();// 停止刻录
                        recorder.release();// 释放资源
                    }
                }
                break;
            case R.id.del_editoranswer:
                //删除
                break;
            case R.id.commit_editoranswer:
                Log.i("test","fileName="+fileName);
                if (!"".equals(fileName))
                    uploadRecordAnswer();
                else
                    editorCommit();
                break;
            case R.id.add_choice:
                //添加选项
                addChoise();
                break;
            case R.id.add_image_editoranswer:
                // 从相册中选择
                show_prick = 0;
                mIntent = new Intent(Intent.ACTION_PICK, null);
                // 如果朋友们要限制上传到服务器的图片类型时可以直接写如：image/jpeg 、 image/png等的类型
                mIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(mIntent, IBaes.REQUESTCODE_PICK);
                break;
            case R.id.bunanswer_image_editoranswer:
                // 从相册中选择
                show_prick = 1;
                if (isChose == 0) {
                    show_chose = 0;
                }
                mIntent = new Intent(Intent.ACTION_PICK, null);
                // 如果朋友们要限制上传到服务器的图片类型时可以直接写如：image/jpeg 、 image/png等的类型
                mIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(mIntent, IBaes.REQUESTCODE_PICK);
                break;
            case R.id.bunexapin_image_editoranswer:
                // 从相册中选择
                show_prick = 2;
                mIntent = new Intent(Intent.ACTION_PICK, null);
                // 如果朋友们要限制上传到服务器的图片类型时可以直接写如：image/jpeg 、 image/png等的类型
                mIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(mIntent, IBaes.REQUESTCODE_PICK);
                break;
            case R.id.section_editoranswer:
                mIntent = new Intent(EditorAnswerActivity.this, TreeDialog.class);
                startActivityForResult(mIntent, IBaes.WORK_ARRT_TREE);
                break;
        }
    }

    /**
     * 编辑题目提交-》题干与答案选项合并
     */
    public void editorCommit() {
        getNet();
        body_editoranswer.getText().toString();
        String body = body_editoranswer.getText().toString();
        if ("".equals(body)) {
            IBaes.toastShow(this, "请编写题干...");
            return;
        }
        String qBody = bodyDate(body).trim();
        String qPack = "";
        String strs = "";
        if (isChose == 0) {
            if (Choes.size() > 0) {
                for (int i = 0; i < Choes.size(); i++) {
                    if(Choes.get(i).isEdit){
                        IBaes.toastShow(this,"你只是有一个选择未保存，赶快去保存下吧");
                        return ;
                    }
                    if (Choes.get(i).ischeck) {
                        int num = i + 1;
                        qPack += num + ",";
                    }
                    strs += Choes.get(i).getName() + "#$%@#$#$";
                }
            } else {
                IBaes.toastShow(this, "请添加选项...");
                return;
            }
            strs = strs.substring(0, strs.length() - 8);
        } else {
            String pack = pack_editoranswer.getText().toString();
            if ("".equals(pack)) {
                IBaes.toastShow(this, "请编写答案...");
                return;
            }
            qPack = resultDate(pack).trim();
        }
        if ("".equals(txid)) {
            IBaes.toastShow(this, "题目类型不能为空");
            return;
        }
//        //解析
        String exapin = exapin_editoranswer.getText().toString();
        String qExapin = exapinDate(exapin).trim();
//        Log.i("test", "key=" + key + ",userId=" + userId + ",知识点ID=" + KnowId + ",type=" + txid );
//        Log.i("test", "qBody=" + qBody);
//        Log.i("test", "correctanswer=" + qPack);
//        Log.i("test", "qExapin=" + qExapin);
//        Log.i("test", "strs=" + strs);
        //key userid 解析 答案 内容  题目类型 难易度
//        String key, String userId, String type,String tid,String body,String explain,String correctanswer,String diff,String options,String id
        AjaxParams params = null;
        String url = "";
        String xuke_cs = SPUtils.get(this, "xuke_cs", "").toString();
        switch (answer_index) {
            case 0:
                //题库题目编辑
                url = IBaes.WORK_QUESTION_EDITSAVE;
                params = servecHttp.workQuestionEdit(key, userId, txid, answer.getId(), qBody, qExapin, qPack, easy + "", strs, KnowId,"","");
                break;
            case 1:
                //添加题目
                url = IBaes.WORK_ADDWROK_ADDSAVE;
                params = servecHttp.workAddWork(key, userId, txid, qBody, qExapin, qPack, easy + "", strs, KnowId, wid, xuke_cs, jiaocaiCs, sectionId, pathName);
                break;
            case 2:
                //作业题目编辑
                url = IBaes.WORK_EDIT_QUEREION;
                Log.i("test","tid="+answer.getIb());
                params = servecHttp.workQuestionEdit(key, userId, txid, answer.getIb(), qBody, qExapin, qPack, easy + "", strs, KnowId,answer.getTpid(),answer.getIfrom()+"");
                break;
            case 3:
                //原创题目添加
                if ("".equals(sectionId)) {
                    IBaes.toastShow(this, "请选择章节...");
                    return;
                }
                url = IBaes.WORK_QUEREION_ADD;
                params = servecHttp.workAddWork(key, userId, txid, qBody, qExapin, qPack, easy + "", strs, KnowId, "", xuke_cs, jiaocaiCs, sectionId, pathName);
                break;
        }

        loadingDialog.show();
        loadingDialog.setText("提交中...");
        finalHttp.post(url, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
//                Log.i("test", "题目编辑:" + o.toString());
                Map<String, Object> map = new JsonData().jsonAvatar(o.toString());
                Message msg = new Message();
                msg.obj = map;
                msg.what = 4;
                handler.sendMessage(msg);
            }
        });

    }

    /**
     * 查询类型:难易度，题目类型
     */
    public void selTypeAnswer() {
        getNet();
        finalHttp.post(IBaes.WORK_QUESTION_TYPE, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
//                Log.i("test", t.toString());
                Map<String, Object> map = jsonDate.jsonMode_Type(t.toString());
                Message msg = new Message();
                msg.obj = map;
                msg.what = 0;
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * 上传语音
     */
    public void uploadRecordAnswer() {
        if (!"".equals(path)) {
            // Log.i("test", path);
            if(record_flag){
                Log.i("test","record_flag="+record_flag);
                record_flag = false;
                iv_record_gif.setImageResource(R.drawable.record_finish);
                record_editoranswer.setText("开始    说话");
                record_editoranswer.setTextColor(getResources().getColor(R.color.weakgrey));
                if (recorder != null) {
                    recorder.stop();// 停止刻录
                    recorder.release();// 释放资源
                }
            }
            File file = new File(fileName);
            AjaxParams params = new AjaxParams();
            params.put("key", key);
            params.put("userId", userId);
            try {
                params.put("voice", file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            finalHttp.post(IBaes.WORK_RECORD_UPLOAD, params, new AjaxCallBack<Object>() {
                @Override
                public void onSuccess(Object t) {
                    // TODO Auto-generated method stub
                    super.onSuccess(t);
                    Log.i("test", "音频上传:" + t.toString());
                    Map<String, Object> map = new JsonData().jsonNoticeUpload(t.toString());
                    Message msg = new Message();
                    msg.obj = map;
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
            });
        }
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
                            if ("单项选择题".equals(l.getCname().trim()) || "填空题".equals(l.getCname()) || l.getCname().indexOf("听力") != -1) {
                                typs.add(l);
                                if (answer != null) {
                                    if (l.getCs().equals(answer.getType())) {
                                        txid = l.getCs();
                                        type_questionwork.setText(l.getCname());
                                    }
                                } else {
                                    if ("单项选择题".equals(l.getCname())) {
                                        txid = l.getCs();
                                        type_questionwork.setText(l.getCname());
                                    }
                                }
                            }
                        }
                        if (answer != null) {
                            for (int i = 1; i < difficultys.size(); i++) {
                                if (i == answer.getDiff()) {
                                    nyid = difficultys.get(i).getCs();
                                    difficulty_questionwork.setText(difficultys.get(i).getCname());
                                    break;
                                }
                            }
                        }


                    }
                    break;
                case 1:
                    if (status == 1) {
                        pathName = map.get("tape").toString();
                        Log.i("test", "上传:" + pathName);
                        editorCommit();
                    } else {
                        IBaes.toastShow(EditorAnswerActivity.this, map.get("zhuce").toString());
                    }
                    break;
                case 2:
                    if (status == 1) {
                        String name = (String) map.get("name");
                        section_editoranswer.setText(name);
                        jid = (String) map.get("id");
                    } else {
                        IBaes.toastShow(EditorAnswerActivity.this, map.get("zhuce").toString());
                    }
                    break;
                case 3:
                    if (status == 1) {
                        String data = (String) map.get("data");
                        switch (show_prick) {
                            case 0:
                                String body = body_editoranswer.getText().toString();
                                body = bodyDate(body);
                                if (show_flag) {
                                    show_flag = false;
                                    body = "<div>" + body.trim() + "</div>";
                                } else {
                                    body = "<div>" + body.trim() + "<img src=\"/" + data + "\" style=\"vertical-align:middle;\" /></div>";
                                }
                                imgs_body.clear();
                                getBodyDate(body.replace("<br />", ""));
                                break;
                            case 1:
                                String pack = pack_editoranswer.getText().toString();
                                if (isChose != 0) {//填空
                                    pack = resultDate(pack);
                                    if (show_flag) {
                                        show_flag = false;
                                        pack = "<div>" + pack.trim() + "</div>";
                                    } else {
                                        pack = "<div>" + pack.trim() + "<img src=\"/" + data + "\" style=\"vertical-align:middle;\" /></div>";
                                    }
                                    imgs_answer.clear();
                                    getAnswerDate(pack.replace("<br />", ""));
                                } else {
                                    //选择题
                                    for (SubAndGrade s : Choes) {
                                        if (s != null && s.isEdit) {
                                            if (show_chose == 1)
                                                s.setName(s.getName() + "<img src=\"/" + data + "\" style=\"vertical-align:middle;\" />");
                                        }
                                    }
                                    Alls.clear();
                                    Alls.addAll(Choes);
                                    adapter.notifyDataSetChanged();
                                    IBaes.setGroupHeight(choice_lists);
                                    if (show_chose == 1) {
                                        Upload();
                                        return;
                                    }
                                }
                                break;
                            case 2:
                                String exapin = exapin_editoranswer.getText().toString();
                                exapin = exapinDate(exapin);
                                if (show_flag) {
                                    show_flag = false;
                                    exapin = "<div>" + exapin.trim() + "</div>";
                                } else {
                                    exapin = "<div>" + exapin.trim() + "<img src=\"/" + data + "\" style=\"vertical-align:middle;\" /></div>";
                                }
                                imgs_exping.clear();
                                getExapinDate(exapin.replace("<br />", ""));
                                break;
                        }
                    }
                    IBaes.toastShow(EditorAnswerActivity.this, map.get("zhuce").toString());
                    break;
                case 4:
                    if (status == 1) {
                        Intent intent = null;
                        switch (answer_index) {
                            case 0:
                                //原创编辑
                                intent = new Intent(IBaes.ACTION_QUESTION_UPDATE);
                                break;
                            case 1:
                                //作业导题
                                intent = new Intent(IBaes.ACTION_WORK_ADDWROK);
                                break;
                            case 2:
                                //作业编辑
                                intent = new Intent(IBaes.ACTION_WORK_ADDWROK);
                                break;
                            case 3:
                                //原创添加
                                intent = new Intent(IBaes.ACTION_QUESTION_UPDATE);
                                break;
                        }
                        sendBroadcast(intent);
                    }
                    if (loadingDialog != null && loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                        loadingDialog.cancel();
                    }
                    IBaes.toastShow(EditorAnswerActivity.this, map.get("zhuce").toString());
                    break;
            }
        }
    };


    /**
     * 编辑题目
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void editorAnswer() {
        if ("null".equals(answer.getAnswertype())) {
            isChose = 0;
            defaultChose();
        } else {
            isChose = answer.getAnswertype();
            if (answer.getAnswertype() == 0) {
                choice_answer_editoranswer.setVisibility(View.VISIBLE);
                showAnswerContent();
            } else {
                //显示答案
                pack_answer_editoranswer.setVisibility(View.VISIBLE);
                getAnswerDate(answer.getCorrectanswer().replace("<br />", ""));
            }
        }
        getBodyDate(answer.getBody().replace("<br />", ""));
        getExapinDate(answer.getExplain().replace("<br />", ""));
        easy = answer.getDiff();
        pathName = answer.voiceurl;
        if (!"".equals(pathName)) {
            show_recode.setVisibility(View.VISIBLE);
        }
        querySection(answer.getFidi());//获取章节
    }


    /**
     * 转换html题干数据
     */
    public String bodyDate(String tby) {
        String body = tby;//原数据
        while (true) {
            if (tby.indexOf("￼") != -1) {
                for (ImagePathPosition i : imgs_body) {
//                    Log.i("test", body.indexOf("￼") + "=" + i.position);
                    if (tby.indexOf("￼") == i.position) {
                        body = body.substring(0, body.indexOf("￼")) + "<img " + i.paht + "style=\"vertical-align:middle;\" />" + body.substring(body.indexOf("￼") + 1, body.length());
//                        Log.i("test", "body=" + body);
                        tby = tby.substring(0, tby.indexOf("￼")) + "☃" + tby.substring(tby.indexOf("￼") + 1, tby.length());
                    }
                }
            } else
                break;
        }
        return body;
    }

    /**
     * 转换html答案
     */
    public String resultDate(String result) {
        String pack = result;
        while (true) {
            if (result.indexOf("￼") != -1) {
                for (ImagePathPosition i : imgs_answer) {
                    if (result.indexOf("￼") == i.position) {
                        pack = pack.substring(0, pack.indexOf("￼")) + "<img " + i.paht + "style=\"vertical-align:middle;\" />" + pack.substring(pack.indexOf("￼") + 1, pack.length());
                        result = result.substring(0, result.indexOf("￼")) + "☃" + result.substring(result.indexOf("￼") + 1, result.length());
                    }
                }
            } else
                break;
        }
        return pack;
    }

    /**
     * 转换html解析文本
     */
    public String exapinDate(String exping) {
        String exapin = exping;
//        Log.i("test", "imgs_exping=" + exapin.length());
        while (true) {
//            Log.i("test", "exapin.indexOf(\"￼\")=" + exping.indexOf("￼"));
            if (exping.indexOf("￼") != -1) {
                for (ImagePathPosition i : imgs_exping) {
//                    Log.i("test", exping.indexOf("￼") + "=" + i.position);
                    if (exping.indexOf("￼") == i.position) {
                        exapin = exapin.substring(0, exapin.indexOf("￼")) + "<img " + i.paht + "style=\"vertical-align:middle;\" />" + exapin.substring(exapin.indexOf("￼") + 1, exapin.length());
//                        Log.i("test", "exapin=" + exapin);
                        exping = exping.substring(0, exping.indexOf("￼")) + "☃" + exping.substring(exping.indexOf("￼") + 1, exping.length());
                    }
                }
            } else {
                break;
            }
        }
        return exapin;
    }

    boolean show_flag = false;//是否是第一次上传图片

    /**
     * 获取题干数据
     */
    public void getBodyDate(String body) {
        //显示题干
        Html.ImageGetter imageGetter = new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
                FinalBitmap.create(EditorAnswerActivity.this).display(iv_body_editoranswer, IBaes.URL + source);
                Drawable drawable = iv_body_editoranswer.getDrawable();
                if (drawable.getIntrinsicWidth() == -1) {
                    show_flag = true;
                    Upload();
                    return null;
                }
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            }
        };
        Spanned spanned = Html.fromHtml(body, imageGetter, null);
        body_editoranswer.setText(spanned);
        //得到题干里面包含的图片
        String title = body.replace("<div>", "").replace("</div>", "").replace("<br />", "");
        while (true) {
            if (title.indexOf("<img") != -1) {
                String s = title.substring(title.indexOf("<img"), title.indexOf("/>") + 2);
                int poistion = title.indexOf(s);//得到图片的位置
                if (s.indexOf("src=") != -1 && s.indexOf("style=") != -1) {
                    String src = s.substring(s.indexOf("src="), s.indexOf("style="));//得到图片路径
                    ImagePathPosition imagePathPosition = new ImagePathPosition();
                    imagePathPosition.position = poistion;
                    imagePathPosition.paht = src;
                    imgs_body.add(imagePathPosition);//将图片添加到集合
                }
                title = title.replace(s, "*");
            } else
                break;
        }
    }

    /**
     * 获取答案数据
     */
    public void getAnswerDate(String correctanswer) {
        //显示答案
        Html.ImageGetter imageGette = new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
                FinalBitmap.create(EditorAnswerActivity.this).display(iv_pack_editoranswer, IBaes.URL + source);
                Drawable drawable = iv_pack_editoranswer.getDrawable();
                if (drawable.getIntrinsicWidth() == -1) {
                    show_flag = true;
                    Upload();
                    return null;
                }
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            }

            ;
        };
        Spanned pack = Html.fromHtml(correctanswer, imageGette, null);
        pack_editoranswer.setText(pack);
        String ans = correctanswer.replace("<div>", "").replace("</div>", "").replace("<br />", "");
        //得到答案里面包含的图片
        while (true) {
            if (ans.indexOf("<img") != -1) {
                String s = ans.substring(ans.indexOf("<img"), ans.indexOf("/>") + 2);
                int poistion = ans.indexOf(s);//得到图片的位置
                if (s.indexOf("src=") != -1 && s.indexOf("style=") != -1) {
                    String src = s.substring(s.indexOf("src="), s.indexOf("style="));//得到图片路径
                    ImagePathPosition imagePathPosition = new ImagePathPosition();
                    imagePathPosition.position = poistion;
                    imagePathPosition.paht = src;
                    imgs_answer.add(imagePathPosition);//将图片添加到集合
                }
                ans = ans.replace(s, "*");
            } else
                break;
        }
    }

    /**
     * 获取解析数据
     */
    public void getExapinDate(String ex) {
        //解析
        Html.ImageGetter imageGetterExplain = new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
                FinalBitmap.create(EditorAnswerActivity.this).display(iv_exapin_editoranswer, IBaes.URL + source);
                Drawable drawable = iv_exapin_editoranswer.getDrawable();
                if (drawable.getIntrinsicWidth() == -1) {
                    show_flag = true;
                    Upload();
                    return null;
                }
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            }
        };
        Spanned explain = Html.fromHtml(ex, imageGetterExplain, null);
        exapin_editoranswer.setText(explain);
        String exping = ex.replace("<div>", "").replace("</div>", "").replace("<br />", "");
        //得到解析里面包含的图片
        while (true) {
            if (exping.indexOf("<img") != -1) {
                String s = exping.substring(exping.indexOf("<img"), exping.indexOf("/>") + 2);
                int poistion = exping.indexOf(s);//得到图片的位置
                if (s.indexOf("src=") != -1 && s.indexOf("style=") != -1) {
                    String src = s.substring(s.indexOf("src="), s.indexOf("style="));//得到图片路径
                    ImagePathPosition imagePathPosition = new ImagePathPosition();
                    imagePathPosition.position = poistion;
                    imagePathPosition.paht = src;
                    imgs_exping.add(imagePathPosition);//将图片添加到集合
                }
                exping = exping.replace(s, "*");
            } else
                break;
        }
    }

    /**
     * 设置添加选择的样式
     */
    public void setImageAnswer() {
        iv_type_questionwork.setImageResource(R.drawable.question_nosel);
        iv_difficulty_questionwork.setImageResource(R.drawable.question_nosel);
        iv_source_questionwork.setImageResource(R.drawable.question_nosel);
        type_questionwork.setTextColor(getResources().getColor(R.color.weakgrey));
        difficulty_questionwork.setTextColor(getResources().getColor(R.color.weakgrey));
        source_questionwork.setTextColor(getResources().getColor(R.color.weakgrey));
    }

    /**
     * 条件弹出框
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setConditionAnswer(List<LeftSubject> lf) {
        dialog_questionwork.setVisibility(View.VISIBLE);
        alls.clear();
        alls.addAll(lf);
        QuestionConditionAdapter conAdapter = new QuestionConditionAdapter(alls, this);
        dialog_lists.setAdapter(conAdapter);
        IBaes.setGroupHeight(dialog_lists);
    }


    /**
     * 网络判断
     */
    public void getNet() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(EditorAnswerActivity.this, "网络不给力,请稍后...");
            return;
        }
    }

    /**
     * 录音
     */
    public void initRecordAnswer() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // 设置MediaRecorder的音频源为麦克风
        recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
        // 设置MediaRecorder录制的音频格式
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/teacherRecord";
        Log.e("test", path);
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹

        fileName = path + "/hearing_" + System.currentTimeMillis() + ".aac";
        // 设置MediaRecorder录制音频的编码为amr.
        recorder.setOutputFile(fileName);
        // 设置录制好的音频文件保存路径
        try {
            recorder.prepare();// 准备录制
            recorder.start();// 开始录制
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 编辑题目显示题目的内容
     */
    public void showAnswerContent() {
        if (answer != null) {
            if ("".equals(answer.getXuanxiang())) {
                defaultChose();
            } else if ("null".equals(answer.getXuanxiang())) {
                defaultChose();
            } else {
                //A . 允许工商业者入仕为官<br>B . 准许土地自由买卖<br>C . 承认土地归私人所有<br>D . 规定按军功授爵赐田<br>
                String[] chs = answer.getXuanxiang().split("<br>");
                Choes.clear();
                for (int i = 0; i < chs.length; i++) {
                    String[] str = chs[i].split(" . ");
                    if (str.length > 0) {
                        SubAndGrade andGrade = new SubAndGrade();
                        andGrade.id = str[0];
                        andGrade.name = str[1];
//                        Log.i("test",andGrade.name);
                        String[] strs = answer.getCorrectanswer().split(",");
                        for (int s = 0; s < strs.length; s++) {
                            int index = i + 1;
//                            Log.i("test", strs[s] + "=" + index + "==" + (Integer.parseInt(strs[s]) == (index)));
                            if (!"".equals(strs[s])) {
                                //判断strs[s]是否为数字
                                if (Character.isDigit(strs[s].charAt(0))) {
                                    if (Integer.parseInt(strs[s]) == index) {
                                        andGrade.ischeck = true;
                                    }
                                }
                            }
                        }

                        Choes.add(andGrade);
                    }
                }
                Alls.addAll(Choes);
                adapter.notifyDataSetChanged();
                IBaes.setGroupHeight(choice_lists);
            }
        }
    }

    /**
     * 根据章节id查询章节的名称
     */
    public void querySection(String fidi) {
        getNet();
        AjaxParams params = servecHttp.updatePwd(key, userId, fidi, "jd");
        finalHttp.post(IBaes.WORK_SECTION_TIGAN, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
//                    Log.i("test",o.toString());
                Map<String, Object> map = jsonDate.jsonSection(o.toString());
                Message msg = new Message();
                msg.obj = map;
                msg.what = 2;
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * 添加时选择的题答案
     */
    public void defaultChose() {
        //实例化前四个
        for (int i = 0; i < 4; i++) {
            SubAndGrade andGrade = new SubAndGrade();
            andGrade.id = letter[i];
            andGrade.name = "请输入选项内容";
            if(i==0){
                andGrade.ischeck=true;
            }
            Choes.add(andGrade);
        }
        Alls.addAll(Choes);
        adapter.notifyDataSetChanged();
        IBaes.setGroupHeight(choice_lists);
    }

    /**
     * 添加选项
     */
    public void addChoise() {
        if (Choes.size() < 20) {
            List<SubAndGrade> grades = new ArrayList<>();
            boolean flag = false;
            SubAndGrade andGrade = new SubAndGrade();
            if (Choes.size() != Alls.size()) {
                flag = true;
                for (int i = 0; i < Choes.size(); i++) {
                    if (!Alls.get(i).getId().equals(Choes.get(i).getId())) {
                        andGrade.id = Alls.get(i).getId();
                        andGrade.name = "请输入选项内容";
                        break;
                    }
                }
            } else {
                andGrade.id = letter[Choes.size()];
                andGrade.name = "请输入选项内容";
            }
            if (flag) {
                //将集合排序
                for (int i = 0; i < letter.length; i++) {
                    if (andGrade.getId().equals(letter[i])) {
                        grades.add(andGrade);
                    }
                    for (SubAndGrade s : Choes) {
                        if (s.getId().equals(letter[i])) {
                            grades.add(s);
                        }
                    }
                }
                Choes.clear();
                Choes.addAll(grades);
            } else {
                //直接添加到集合中
                Choes.add(andGrade);
                Alls.add(andGrade);
            }
            adapter.notifyDataSetChanged();
            IBaes.setGroupHeight(choice_lists);
        } else
            IBaes.toastShow(this, "选项个数已经达到最大极限...");
    }

    /**
     * 题干edittext监听
     */
    TextWatcher watcher_body = new TextWatcher() {
        int begin = 0;//光标开始的位置
        int end = 0;//光标结束的位置

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            begin = body_editoranswer.getSelectionStart();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            end = body_editoranswer.getSelectionStart();
//            Log.i("test", "begin=" + begin + ",end=" + end + ",=" + body_editoranswer.getSelectionEnd());
            if (begin > end) {
                //删除了数据
                if (imgs_body.size() > 0) {
                    //判断是否是图片所在的位置
                    for (int p = 0; p < imgs_body.size(); p++) {
                        int b = begin - 1;
                        if (b == imgs_body.get(p).position) {
                            imgs_body.remove(imgs_body.get(p));
                        }
                    }
                }
            } else {
                if (imgs_body.size() > 0) {
                    for (int p = 0; p < imgs_body.size(); p++) {
                        int b = begin - 1;
                        if (b < imgs_body.get(p).position) {
                            imgs_body.get(p).position = imgs_body.get(p).position + (end - begin);
                        }
                    }
                }
            }
        }
    };

    /**
     * 答案改变事件
     */
    TextWatcher watcher_answer = new TextWatcher() {
        int begin = 0;//光标开始的位置
        int end = 0;//光标结束的位置

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            begin = pack_editoranswer.getSelectionStart();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            end = pack_editoranswer.getSelectionStart();
            if (begin > end) {
                //删除了数据
                if (imgs_answer.size() > 0) {
                    //判断是否是图片所在的位置
                    for (ImagePathPosition p : imgs_answer) {
                        int b = begin - 1;
                        if (b == p.position) {
                            imgs_answer.remove(p);
                        }
                    }
                }
            } else {
                if (imgs_answer.size() > 0) {
                    for (ImagePathPosition p : imgs_answer) {
                        int b = begin - 1;
                        if (b < p.position) {
                            p.position = p.position + (end - begin);
                        }
                    }
                }
            }
        }
    };
    /**
     * 解析改变事件
     */
    TextWatcher watcher_exapin = new TextWatcher() {
        int begin = 0;//光标开始的位置
        int end = 0;//光标结束的位置

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            begin = exapin_editoranswer.getSelectionStart();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            end = exapin_editoranswer.getSelectionStart();
            String ss = exapin_editoranswer.getText().toString();
//            Log.i("test", ss);
//            Log.i("test", ss.replace(" ", ""));
//            Log.i("test", "begin=" + begin + ",end=" + end + ",=" + body_editoranswer.getSelectionEnd());
            if (begin > end) {
                //删除了数据
                if (imgs_exping.size() > 0) {
                    //判断是否是图片所在的位置
                    for (ImagePathPosition p : imgs_exping) {
                        int b = begin - 1;
                        if (b == p.position) {
                            imgs_exping.remove(p);
                        }
                    }
                }
            } else {
                if (imgs_exping.size() > 0) {
                    for (ImagePathPosition p : imgs_exping) {
                        int b = begin - 1;
                        if (b < p.position) {
                            p.position = p.position + (end - begin);
                        }
                    }
                }
            }
        }
    };

    private Bitmap head;
    private Uri imageUri = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IBaes.WORK_EDITORANSWER_TREE) {
            KnowId = data.getStringExtra("KnowId");// 知识点id
            setImageAnswer();
        }
        if (requestCode == IBaes.WORK_ARRT_TREE) {
            //选择章节
            sectionId = data.getStringExtra("sectionId");// 章节id
            String sectName = data.getStringExtra("sectName");// 章节的名称
            subjectCs = data.getStringExtra("subjectCs");// 学科Cs
            jiaocaiCs = data.getStringExtra("jiaocaiCs");// 教材CS;
            section_editoranswer.setText(sectName);
        }
        if (requestCode == IBaes.REQUESTCODE_PICK) {
            // 从相机中选择
            try {
                cropPhoto(data.getData());
            } catch (NullPointerException e) {
                e.printStackTrace();// 用户点击取消操作
            }
        }
        if (requestCode == IBaes.REQUESTCODE_TAKE) {
            // 拍照
            File temp = new File(Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".jpg");
            cropPhoto(Uri.fromFile(temp));// 裁剪图片
        }

        if (requestCode == IBaes.SYS_CROPPHOTO) {
            /*if (data != null) {
                Bundle extras = data.getExtras();
                head = extras.getParcelable("data");
                if (head != null) {
                    *//**
             * 上传服务器代码
             *//*
                    setPicToView(head);// 保存在SD卡中
                }
            }*/
            if (imageUri != null) {
                try {
                    head = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (head != null) {
                    /**
                     * 上传服务器代码
                     */
                    setPicToView(head);// 保存在SD卡中
                }
            }
        }
    }

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        imageUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 100);
//        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, IBaes.SYS_CROPPHOTO);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     */
    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/teacherPhoto";
        Log.e("test", path);
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        imageName = path + "/" + System.currentTimeMillis() + ".png";
        try {
            b = new FileOutputStream(imageName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            Upload();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 上传图片
     */
    public void Upload() {
        if (imageName != null) {
            show_chose++;
            File fi = new File(imageName);
            getNet();
            AjaxParams params = servecHttp.updatePersonalAvatar(key, userId, fi);
            finalHttp.post(IBaes.WORK_EDITOR_UPLOAD, params, new AjaxCallBack<Object>() {
                @Override
                public void onSuccess(Object t) {
                    // TODO Auto-generated method stub
                    super.onSuccess(t);
//                     Log.i("test", "图片上传:"+t.toString());
                    Map<String, Object> map = jsonDate.jsonWorkUpload(t.toString());
                    Message msg = new Message();
                    msg.obj = map;
                    msg.what = 3;
                    handler.sendMessage(msg);
                }
            });
        }
    }

    @Override
    public void dialogResult(int tag, int state) {
        if (state == LoadingDialog.SUCCESS) {
            setResult(100);
            finish();
        }
    }

    /**
     * 图片路径和位置
     */
    class ImagePathPosition implements Serializable {
        public int position;
        public String paht;
    }
}
