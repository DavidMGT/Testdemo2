package com.work.teacher.work;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.work.teacher.R;
import com.work.teacher.bean.WorkAuthor;
import com.work.teacher.bean.WorkRelseContent;
import com.work.teacher.bean.WorkRelseQuestion;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;
import com.work.teacher.tool.WorkJson;
import com.work.teacher.view.LoadingDialog;
import com.work.teacher.view.MyListView;
import com.work.teacher.work.adapter.WorkEditAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 作业--》》编辑作业
 *
 * @author 左丽姬
 */
public class WorkEditActivity extends Activity implements OnClickListener, LoadingDialog.OnLoadingDialogResultListener {
    private ImageView isnet_image;
    private Button sel_answer, import_answer, preview_answer, import_out, save_answer;
    private String sectionId, sectName, subjectCs, jiaocaiCs, wid;
    private ServecHttp servecHttp;
    private WorkJson jsonDate;
    private FinalHttp finalHttp;
    private String key;
    private String userId;

    private TextView score_workedit, hearing_workedit, no_lists_workedit;
    private FrameLayout fl_score_workedit, fl_hearing_workedit;
    private MyListView lists_workedit;
    private WorkEditReceiver receiver;
    private List<WorkRelseQuestion> lists = new ArrayList<WorkRelseQuestion>();
    private WorkEditAdapter adapter;
    private Dialog delDialog;
    private WorkRelseContent content;
    private WorkRelseQuestion question;
    private ArrayList<String> scores = new ArrayList<String>();
    private LoadingDialog loadingDialog;
    private SeekBar seekBar;
    private TextView item_hearing_start;
    private boolean flagSeek = false;
    private int poistion_index=0;
    private boolean isCache=false;
    private boolean isBorad=false;
    private boolean isDeory=false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workedit);
        initWorkedit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("test","进入了...");
        receiver = new WorkEditReceiver();
        IntentFilter filter_net = new IntentFilter(IBaes.ACTION_NET);
        registerReceiver(receiver, filter_net);
        IntentFilter filter_add = new IntentFilter(IBaes.ACTION_WORK_ADDWROK);
        registerReceiver(receiver, filter_add);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (WorkRelseQuestion q : lists) {
            if (q != null) {
                for (WorkRelseContent c : q.contents) {
                    if (c != null && c.player != null) {
                        isDeory = true;
                        c.player.stop();
                        c.player.release();
                        c.player = null;
                    }
                }
            }

        }
    }

    public void initWorkedit() {

        sectionId = getIntent().getStringExtra("sectionId");
        sectName = getIntent().getStringExtra("sectName");
        subjectCs = getIntent().getStringExtra("subjectCs");
        jiaocaiCs = getIntent().getStringExtra("jiaocaiCs");
        wid = getIntent().getStringExtra("wid");
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setOnLoadingDialogResultListener(this);

        servecHttp = new ServecHttp();
        jsonDate = new WorkJson();
        finalHttp = new FinalHttp();
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

        // 头部设置
        ImageView top_back = (ImageView) findViewById(R.id.top_back);
        top_back.setOnClickListener(this);
        TextView top_text = (TextView) findViewById(R.id.top_text);
        top_text.setText("编辑作业");
        Button top_btn = (Button) findViewById(R.id.top_btn);
        top_btn.setText("下一步");
        top_btn.setOnClickListener(this);

        // 底部菜单
        sel_answer = (Button) findViewById(R.id.sel_answer_workedit);
        import_answer = (Button) findViewById(R.id.import_answer_workedit);
        preview_answer = (Button) findViewById(R.id.preview_answer_workedit);
        import_out = (Button) findViewById(R.id.import_out_workedit);
        save_answer = (Button) findViewById(R.id.save_answer_workedit);
        sel_answer.setOnClickListener(this);
        import_answer.setOnClickListener(this);
        preview_answer.setOnClickListener(this);
        import_out.setOnClickListener(this);
        save_answer.setOnClickListener(this);

        //总分设置
        score_workedit = (TextView) findViewById(R.id.score_workedit);
        fl_score_workedit = (FrameLayout) findViewById(R.id.fl_score_workedit);
        fl_score_workedit.setOnClickListener(this);
        //听力设置
        hearing_workedit = (TextView) findViewById(R.id.hearing_workedit);
        fl_hearing_workedit = (FrameLayout) findViewById(R.id.fl_hearing_workedit);
        fl_hearing_workedit.setVisibility(View.GONE);
        fl_hearing_workedit.setOnClickListener(this);


        no_lists_workedit = (TextView) findViewById(R.id.no_lists_workedit);
        lists_workedit = (MyListView) findViewById(R.id.lists_workedit);
        adapter = new WorkEditAdapter(this, lists, 0);
        adapter.setonImageClickLinstener(new WorkEditAdapter.onImageClickLinstener() {
            @Override
            public void onImageClick(View v, final int poistion, int qpoistion, String name) {
                question = lists.get(qpoistion);
                if (question != null) {
                    poistion_index=poistion;
                    content = question.contents.get(poistion);
                    seekBar = (SeekBar) v.findViewById(R.id.item_seekBar);
                    item_hearing_start = (TextView) v.findViewById(R.id.item_hearing_start);
                    if ("删除".equals(name)) {
                        delDialog();
                    }
                    if ("item".equals(name)) {
                        Intent intent = new Intent(WorkEditActivity.this, EditorAnswerActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("answer", content.answer);
                        intent.putExtras(bundle);
                        intent.putExtra("answer_index", 2);
                        startActivity(intent);
                    }
                    if ("开始".equals(name)) {
                        Log.i("test", "点击了...开始:" + content.player.getDuration());
                        if (content.player.isPlaying()) {
                            IBaes.toastShow(WorkEditActivity.this, "音频正在播放，请勿重复操作...");
                            return;
                        }
                        isBorad=true;
                        if(!isCache){
                            IBaes.toastShow(WorkEditActivity.this,"音频缓存中...");
                            return ;
                        }
                        player();
                    }
                    if ("结束".equals(name)) {
                        Log.i("test", "点击了...结束");
                        flagSeek = true;
                        content.player.pause();
                    }
                    if("缓存".equals(name)){
                        isCache=true;
                        if(isBorad==true){
                            player();
                        }
                    }
                }
            }
        });
        lists_workedit.setAdapter(adapter);
        IBaes.setGroupHeight(lists_workedit);
        loadingDialog.show();
        loadingDialog.setText("加载中...");
        queryWork();

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
                // 打开设置界面
                mIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                startActivity(mIntent);
                break;
            case R.id.top_back:
                finish();
                break;
            case R.id.top_btn:
                // 下一步
                twoTothree();
                break;
            case R.id.sel_answer_workedit:
                // 选题
                mIntent = new Intent(WorkEditActivity.this, QuestionWorkActivity.class);
                mIntent.putExtra("question", 1);
                mIntent.putExtra("wid", wid);
                startActivity(mIntent);
                break;
            case R.id.import_answer_workedit:
                // 导题
                mIntent = new Intent(WorkEditActivity.this, EditorAnswerActivity.class);
                mIntent.putExtra("wid", wid);
                mIntent.putExtra("sectionId", sectionId);
                mIntent.putExtra("subjectCs", subjectCs);
                mIntent.putExtra("jiaocaiCs", jiaocaiCs);
                mIntent.putExtra("answer_index", 1);
                startActivity(mIntent);
                break;
            case R.id.preview_answer_workedit:
                // 预览
                mIntent = new Intent(WorkEditActivity.this, WorkpPreviewActivity.class);
                mIntent.putExtra("wid", wid);
                startActivity(mIntent);
                break;
            case R.id.import_out_workedit:
                // 导出
                break;
            case R.id.save_answer_workedit:
                // 保存
                break;
            case R.id.fl_score_workedit:
                //分数设置
                mIntent = new Intent(this, ScoreActivity.class);
                mIntent.putStringArrayListExtra("scores", scores);
                mIntent.putExtra("wid", wid);
                startActivity(mIntent);
                break;
            case R.id.fl_hearing_workedit:
                //听力设置
                mIntent = new Intent(this, HearingActivity.class);
                startActivity(mIntent);
                break;
            case R.id.cancel_dialog_del:
                //取消删除
                if (delDialog != null && delDialog.isShowing()) {
                    delDialog.dismiss();
                    delDialog.cancel();
                }
                break;
            case R.id.confirm_dialog_del:
                if (delDialog != null && delDialog.isShowing()) {
                    delDialog.dismiss();
                    delDialog.cancel();
                }
                delQuestion();
                break;
        }
    }

    /**
     * 查询作业，编辑
     */
    public void queryWork() {
        if (!IBaes.isNet(this)) {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
                loadingDialog.cancel();
            }
            IBaes.toastShow(WorkEditActivity.this, "网络不给力,请稍后...");
            return;
        }
//        Log.i("test", "wid=" + wid + " " + key + "   " + userId);
        AjaxParams params = servecHttp.updatePwd(key, userId, wid, "id");
        finalHttp.post(IBaes.WORK_EDITTEXT_PREVIEW, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object t) {
                // TODO Auto-generated method stub
                super.onSuccess(t);
                Log.i("test", "编辑作业:" + t.toString());
                Map<String, Object> map = jsonDate.jsonAddTwo(t.toString());
                Message msg = new Message();
                msg.obj = map;
                msg.what = 0;
                handler.sendMessage(msg);
            }
        });

    }

    /**
     * 提交编辑作业，进入下一步
     */
    public void twoTothree() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(WorkEditActivity.this, "网络不给力,请稍后...");
            return;
        }
        if (lists.size() == 0) {
            IBaes.toastShow(this, "请添加题目...");
            return;
        }
        Intent intent = new Intent(this, WorkPropertyActivity.class);
        intent.putExtra("wid", wid);
        startActivity(intent);
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Map<String, Object> map = (Map<String, Object>) msg.obj;
            int status=0;
            if(map!=null){
                status = (int) map.get("status");
            }
            switch (msg.what) {
                case 0:
                    //显示数据
                    if (loadingDialog != null && loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                        loadingDialog.cancel();
                    }
                    if (status == 1) {
                        WorkAuthor author = (WorkAuthor) map.get("author");
                        score_workedit.setText("总分:" + author.getZhongfen());
                        jiaocaiCs = author.getGradeid();
                        subjectCs = author.getSubjectid();
                        sectionId = author.getChapterdid();
                        List<WorkRelseQuestion> questions = (List<WorkRelseQuestion>) map.get("wrqs");
                        if (questions.size() > 0) {
                            lists_workedit.setVisibility(View.VISIBLE);
                            no_lists_workedit.setVisibility(View.GONE);
                            lists.clear();
                            lists.addAll(questions);
                            adapter.notifyDataSetChanged();
                            IBaes.setGroupHeight(lists_workedit);
                            scores.clear();
                            for (WorkRelseQuestion w : lists) {
                                scores.add(w.getTitle() + "∮∞" + w.getFen() + "∮∞" + w.contents.size() + "∮∞" + w.getTlx());
                            }
                            scores.add("总分∮∞" + author.getZhongfen());
                        } else {
                            no_lists_workedit.setVisibility(View.VISIBLE);
                            lists_workedit.setVisibility(View.GONE);
                        }
                    } else {
                        no_lists_workedit.setVisibility(View.VISIBLE);
                        lists_workedit.setVisibility(View.GONE);
                    }
                    break;
                case 1:
                    if (status == 1) {
                        Intent case_intent = new Intent(IBaes.ACTION_WORK_ADDWROK);
                        sendBroadcast(case_intent);
                    }
                    IBaes.toastShow(WorkEditActivity.this, map.get("zhuce").toString());
                    break;
                case 2:
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.setTimeInMillis(content.player.getCurrentPosition()+1000);
                    SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
                    seekBar.setProgress((content.player.getCurrentPosition()+1000) / 1000);
                    item_hearing_start.setText((poistion_index + 1) + "、" + sdf.format(gc.getTime()));
                    break;

            }
        }
    };

    /**音频播放*/
    public void player(){
        for (WorkRelseContent c : question.contents) {
            if (c != null && c.answer.voiceurl != null && !"".equals(c.answer.voiceurl) && !"null".equals(c.answer.voiceurl)) {
                if (!flagSeek && c.player.isPlaying()) {
                    c.player.pause();
                }
            }
        }
        flagSeek = false;
        content.player.start();
        Timer mTimer = new Timer();
        TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if(isDeory){
                    return ;
                }
                if (flagSeek == true) {
                    return;
                }
                Log.i("test","content="+content.player.getCurrentPosition());
                handler.sendEmptyMessage(2);

            }
        };
        mTimer.schedule(mTimerTask, 0, 1000);
        content.player.setOnCompletionListener(completionListener);
        content.player.setOnErrorListener(errorListener);
        seekBar.setOnSeekBarChangeListener(changeListener);
    }

    /**播放完成*/
    private MediaPlayer.OnCompletionListener completionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            flagSeek = true;
            content.player.seekTo(0);
        }
    };
    /**播放错误*/
    private MediaPlayer.OnErrorListener errorListener=new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            flagSeek = true;
            content.player.seekTo(0);
            return false;
        }
    };
    /**seekbar改变事件*/
    private SeekBar.OnSeekBarChangeListener changeListener=new SeekBar.OnSeekBarChangeListener() {
        int progress = 0;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            this.progress = progress;

        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Log.i("test", "progress=" + progress);
        }
    };


    public void delDialog() {
        delDialog = new Dialog(this, R.style.PassDialog);
        delDialog.show();
        View dv = LayoutInflater.from(this).inflate(R.layout.dialog_del, null);
        delDialog.setContentView(dv);

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p = delDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.5); // 宽度设置为屏幕的0.9
        delDialog.getWindow().setAttributes(p); // 设置生效
        TextView tv_dialog_del = (TextView) dv.findViewById(R.id.tv_dialog_del);
        Button cancel_dialog_del = (Button) dv.findViewById(R.id.cancel_dialog_del);
        Button confirm_dialog_del = (Button) dv.findViewById(R.id.confirm_dialog_del);
        cancel_dialog_del.setOnClickListener(this);
        confirm_dialog_del.setOnClickListener(this);
    }

    /**
     * 删除作业单个题目
     */
    public void delQuestion() {
        if (question != null && content != null) {
            AjaxParams params = new AjaxParams();
            params.put("key", key);
            params.put("userId", userId);
            params.put("tmlx", question.getTlx());
            params.put("tmid", content.answer.getIb());
            params.put("zyid", content.getTpid());
            params.put("id", content.answer.getId());
            Log.i("test", "key=" + key + ",userId=" + userId + ",tmlx=" + question.getTlx() + ",tmid=" + content.answer.getIb() + ",zyid=" + content.getTpid() + ",id=" + content.answer.getId());
            new FinalHttp().post(IBaes.WORK_DELETE_QUESTION, params, new AjaxCallBack<Object>() {
                @Override
                public void onSuccess(Object o) {
                    super.onSuccess(o);
                    Log.i("test", o.toString());
                    Map<String, Object> map = new JsonData().jsonAvatar(o.toString());
                    Message msg = new Message();
                    msg.obj = map;
                    msg.what = 1;
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
     * 广播监听作业是否发生改变
     */
    class WorkEditReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (IBaes.ACTION_NET.equals(intent.getAction()) || IBaes.ACTION_WORK_ADDWROK.equals(intent.getAction())) {
                queryWork();
            }
        }
    }
}
