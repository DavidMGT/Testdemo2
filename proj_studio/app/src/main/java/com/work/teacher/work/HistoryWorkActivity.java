package com.work.teacher.work;

import com.work.teacher.R;
import com.work.teacher.bean.WorkHistory;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.tool.ServecHttp;
import com.work.teacher.tool.WorkJson;
import com.work.teacher.view.CustomListView;
import com.work.teacher.view.LoadingDialog;
import com.work.teacher.work.adapter.WorkHistoryAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

/**
 * 历史作业
 *
 * @author 左丽姬
 */
public class HistoryWorkActivity extends Activity implements OnClickListener, LoadingDialog.OnLoadingDialogResultListener {
    private FrameLayout fl_share, fl_finish, fl_release, fl_answer, fl_unlimted;
    private TextView history_unlimited, history_answer, history_release, history_finish, history_share;
    private TextView history_unlimited_select, history_answer_select, history_release_select, history_finish_select,
            history_share_select;
    private Button btn_dialog_delay, btn_dialog_delete, btn_dialog_export, btn_dialog_release, btn_dialog_rolling, btn_dialog_preview, btn_dialog_edit, btn_dialog_correct;

    private CustomListView unlitedLists;
    private UnlitedReceiver receiver;
    private String jid = "";
    private String key;
    private String userId;
    private int page = IBaes.PAGE;
    private int curpage = 1;
    private TextView unlited_nodate;
    private List<WorkHistory> lists = new ArrayList<WorkHistory>();
    private WorkHistoryAdapter adapter;

    private Dialog dialog, delDialog,rollingDialog;
    private RelativeLayout relat_dialog_historywrok;
    private TextView tv_check_score;
    private WorkHistory history;
    private FinalHttp http;
    private boolean harmore = false;
    private String zt = "", gx = "";// 状态 (作业状态 1待发布 2答题中，3已完成)
    private LoadingDialog loadingDialog;
    private long delay_date = 0;//保存延迟交卷的时间
    private RadioButton rb_rolling_sel,rb_rolling_nosel;
    private boolean flag=false,flg=true;//判断收卷方式
    private int Modl=1;//(1代表  收卷汇总班成绩并完成作业（默认） 2.收卷待批改作业；)


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historywork);
        initHistory();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        receiver=new UnlitedReceiver();
        IntentFilter filter_net=new IntentFilter(IBaes.ACTION_NET);
        registerReceiver(receiver,filter_net);
        IntentFilter filter_login=new IntentFilter(IBaes.ACTION_LOGIN);
        registerReceiver(receiver,filter_login);
        IntentFilter filter_sub=new IntentFilter(IBaes.ACTION_WORK_HISTORY_SUBJECT);
        registerReceiver(receiver,filter_sub);
        IntentFilter filter_add=new IntentFilter(IBaes.ACTION_WORK_ADDWROK);
        registerReceiver(receiver,filter_add);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        if (dialog != null) {
            dialog.dismiss();
            dialog.cancel();
        }
    }


    public void initHistory() {

        fl_share = (FrameLayout) findViewById(R.id.fl_share);
        fl_finish = (FrameLayout) findViewById(R.id.fl_finish);
        fl_release = (FrameLayout) findViewById(R.id.fl_release);
        fl_answer = (FrameLayout) findViewById(R.id.fl_answer);
        fl_unlimted = (FrameLayout) findViewById(R.id.fl_unlimted);

        history_unlimited = (TextView) findViewById(R.id.history_unlimited);
        history_unlimited_select = (TextView) findViewById(R.id.history_unlimited_select);
        history_answer = (TextView) findViewById(R.id.history_answer);
        history_answer_select = (TextView) findViewById(R.id.history_answer_select);
        history_release = (TextView) findViewById(R.id.history_release);
        history_release_select = (TextView) findViewById(R.id.history_release_select);
        history_finish = (TextView) findViewById(R.id.history_finish);
        history_finish_select = (TextView) findViewById(R.id.history_finish_select);
        history_share = (TextView) findViewById(R.id.history_share);
        history_share_select = (TextView) findViewById(R.id.history_share_select);

        fl_share.setOnClickListener(this);
        fl_finish.setOnClickListener(this);
        fl_release.setOnClickListener(this);
        fl_answer.setOnClickListener(this);
        fl_unlimted.setOnClickListener(this);


        http = new FinalHttp();
        key = SPUtils.get(this, "key", "").toString();
        userId = SPUtils.get(this, "userId", "").toString();
        loadingDialog = new LoadingDialog(this.getParent());
        loadingDialog.setOnLoadingDialogResultListener(this);
        unlitedLists = (CustomListView) findViewById(R.id.work_unlited_lists);
        unlited_nodate = (TextView) findViewById(R.id.unlited_nodate);
        adapter = new WorkHistoryAdapter(this, lists);
        unlitedLists.setAdapter(adapter);
        unlitedLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = position - 1;
                history = lists.get(index);
                if (history != null) {
                    addDialog(history);
                }
            }
        });
        //下拉刷新
        unlitedLists.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lists.clear();
                curpage = 1;
                getUnlited();
                unlitedLists.onRefreshComplete();
            }
        });
        //上拉刷新
        unlitedLists.setOnLoadListener(new CustomListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!harmore) {
                    curpage++;
                    getUnlited();
                } else {
                    IBaes.toastShow(HistoryWorkActivity.this, "没有更多数据了");
                }
                unlitedLists.onLoadMoreComplete();
            }
        });
        getUnlited();
    }

    /**
     * 获取历史所有数据
     */
    public void getUnlited() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(this, "网络不给力,请稍后...");
            return;
        }
        AjaxParams params = new ServecHttp().queryHistoryWork(key, userId, page + "", curpage + "", jid, zt, gx);
        new FinalHttp().post(IBaes.WORK_HISTORY, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object t) {
                super.onSuccess(t);
//                Log.i("test", t.toString());
                Map<String, Object> map = new WorkJson().jsonHistoryLists(t.toString());
                Message message = new Message();
                message.obj = map;
                message.what = 0;
                handler.sendMessage(message);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Map<String, Object> map = (Map<String, Object>) msg.obj;
            int status = (int) map.get("status");
            switch (msg.what) {
                case 0:
                    if (loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                        loadingDialog.cancel();
                    }
                    List<WorkHistory> histories = (List<WorkHistory>) map.get("histories");
                    harmore = (boolean) map.get("harmore");
                    if (histories.size() > 0) {
                        unlitedLists.setVisibility(View.VISIBLE);
                        unlited_nodate.setVisibility(View.GONE);
                        lists.addAll(histories);
                        adapter.notifyDataSetChanged();
                    } else {
                        if (lists.size() == 0) {
                            unlitedLists.setVisibility(View.GONE);
                            unlited_nodate.setVisibility(View.VISIBLE);
                        } else {
                            IBaes.toastShow(HistoryWorkActivity.this, "没有更多数据");
                            curpage--;
                        }
                    }
                    break;
                case 1:
                    //删除作业
                    if (status == 1) {
                        lists.remove(history);
                        adapter.notifyDataSetChanged();
                    }
                    IBaes.toastShow(HistoryWorkActivity.this, map.get("zhuce").toString());
                    break;
                case 2:
                    //延迟交卷时间
                    if (status == 1) {
                        history.setEndtime(delay_date);
                        adapter.notifyDataSetChanged();
                    }
                    IBaes.toastShow(HistoryWorkActivity.this, map.get("zhuce").toString());
                    break;
                case 3:
                    //共享
                    if (status == 1) {
                        if(!"".equals(zt)&&"".equals(gx)){
                            lists.remove(history);
                        }else {
                            history.setTp_share(1);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    IBaes.toastShow(HistoryWorkActivity.this, map.get("zhuce").toString());
                    break;
                case 4:
                    if (status == 1) {
                        if(!"".equals(zt)&&"".equals(gx)){
                            lists.remove(history);
                        }else {
                            if(Modl==1) {
                                history.setStatus(3);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                    IBaes.toastShow(HistoryWorkActivity.this, map.get("zhuce").toString());
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent = null;
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog.cancel();
        }
        switch (v.getId()) {
            case R.id.fl_unlimted:
                // 不限
                zt = "";
                gx = "";
                setSelectHistory();
                history_unlimited.setTextColor(getResources().getColor(R.color.red));
                history_unlimited_select.setBackgroundResource(R.color.red);
                break;
            case R.id.fl_answer:
                // 答题中
                zt = "2";
                gx = "";
                setSelectHistory();
                history_answer.setTextColor(getResources().getColor(R.color.red));
                history_answer_select.setBackgroundResource(R.color.red);
                break;
            case R.id.fl_release:
                // 待发布
                zt = "1";
                gx = "";
                setSelectHistory();
                history_release.setTextColor(getResources().getColor(R.color.red));
                history_release_select.setBackgroundResource(R.color.red);
                break;
            case R.id.fl_finish:
                // 已完成
                zt = "3";
                gx = "";
                setSelectHistory();
                history_finish.setTextColor(getResources().getColor(R.color.red));
                history_finish_select.setBackgroundResource(R.color.red);
                break;
            case R.id.fl_share:
                // 共享
                zt = "";
                gx = "1";
                setSelectHistory();
                history_share.setTextColor(getResources().getColor(R.color.red));
                history_share_select.setBackgroundResource(R.color.red);
                break;
            case R.id.btn_dialog_preview:
                //预览
                intent = new Intent(this, WorkpPreviewActivity.class);
                intent.putExtra("wid", history.getId());
                startActivity(intent);
                break;
            case R.id.btn_dialog_delete:
                //删除
                if (history.getStatus() == 1) {
                    delDialog();
                } else {
                    IBaes.toastShow(this, "你只能删除未发布的作业...");
                }
                break;
            case R.id.btn_dialog_release:
                //发布
                if (history.total == 0) {
                    IBaes.toastShow(this, "你还没有添加题目，赶快去编辑吧!");
                    return;
                }
                if(history.getStatus()!=2) {
                    intent = new Intent(this, WorkPropertyActivity.class);
                    intent.putExtra("wid", history.getId());
                    startActivity(intent);
                }else{
                    IBaes.toastShow(this, "该作业已经发布!");
                }
                break;
            case R.id.btn_dialog_edit:
                //编辑
                intent = new Intent(this, WorkEditActivity.class);
                intent.putExtra("wid", history.getId());
                intent.putExtra("sectionId", history.getChapterdid());
                intent.putExtra("jiaocaiCs", history.getChapterdid());
                startActivity(intent);
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
                delWork();
                break;
            case R.id.ll_check_score:
                //查看成绩
                break;
            case R.id.btn_dialog_delay:
                //延迟
                //1待发布 2答题中，3已完成
                if (history.getStatus() == 2)
                    delayWork();
                else
                    IBaes.toastShow(this, "该作业不处于答题状态,不支持延迟交卷");
                break;
            case R.id.btn_dialog_export:
                //共享
                if (history.tp_share == 1)
                    IBaes.toastShow(this, "该作业已经共享");
                else
                    goShare();
                break;
            case R.id.btn_dialog_rolling:
                //收卷
                if (history.getStatus() == 2)
                    getRolling();
                else
                    IBaes.toastShow(this, "该作业不处于答题状态,不支持收卷");
                break;
            case R.id.btn_dialog_correct:
                //批改
                IBaes.toastShow(this, "二期功能,敬请期待...");
                break;
            case R.id.rb_rolling_nosel:
                if(flg){
                    Modl=2;
                    flag=true;//不选中
                    flg=false;
                    Drawable drawable = getResources().getDrawable(R.drawable.select_yes);
                    Drawable dr = getResources().getDrawable(R.drawable.select_pay_no);
                    drawable.setBounds(0, 0, 30,30);
                    dr.setBounds(0, 0, 30,30);
                    rb_rolling_sel.setCompoundDrawables(dr,null,null,null);
                    rb_rolling_nosel.setCompoundDrawables(drawable,null,null,null);
                }else{
                    IBaes.toastShow(this,"你必须选泽一种收卷方式");
                }
                break;
            case R.id.rb_rolling_sel:
                 if(flag){
                     Modl=1;
                     flag=false;//不选中
                     flg=true;
                     Drawable drawable = getResources().getDrawable(R.drawable.select_yes);
                     Drawable dr = getResources().getDrawable(R.drawable.select_pay_no);
                     drawable.setBounds(0, 0, 30,30);
                     dr.setBounds(0, 0, 30,30);
                     rb_rolling_sel.setCompoundDrawables(drawable,null,null,null);
                     rb_rolling_nosel.setCompoundDrawables(dr,null,null,null);
                 }else{
                    IBaes.toastShow(this,"你必须选泽一种收卷方式");
                 }
                break;
            case R.id.rolling_cancel:
                //取消
                if(rollingDialog!=null&&rollingDialog.isShowing()){
                    rollingDialog.dismiss();
                    rollingDialog.cancel();
                }
                break;
            case R.id.rolling_go:
                //确定收卷
                if(rollingDialog!=null&&rollingDialog.isShowing()){
                    rollingDialog.dismiss();
                    rollingDialog.cancel();
                }
                goRolling();
                break;
        }
    }

    public void setSelectHistory() {
        lists.clear();
        history_unlimited.setTextColor(getResources().getColor(R.color.blackgrey));
        history_answer.setTextColor(getResources().getColor(R.color.blackgrey));
        history_release.setTextColor(getResources().getColor(R.color.blackgrey));
        history_finish.setTextColor(getResources().getColor(R.color.blackgrey));
        history_share.setTextColor(getResources().getColor(R.color.blackgrey));

        history_unlimited_select.setBackgroundResource(R.color.weakgrey);
        history_answer_select.setBackgroundResource(R.color.weakgrey);
        history_release_select.setBackgroundResource(R.color.weakgrey);
        history_finish_select.setBackgroundResource(R.color.weakgrey);
        history_share_select.setBackgroundResource(R.color.weakgrey);
        loadingDialog.show();
        loadingDialog.setText("加载中...");
        getUnlited();
    }

    /**
     * 编辑操作对话框（收藏、报错、添至作业、编辑）
     */
    public void addDialog(WorkHistory answer) {
        dialog = new Dialog(this.getParent().getParent(), R.style.PassDialog);
        dialog.show();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_history_work, null);
        dialog.setContentView(view);
        if (answer != null) {
            relat_dialog_historywrok = (RelativeLayout) view.findViewById(R.id.relat_dialog_historywrok);
            tv_check_score = (TextView) view.findViewById(R.id.tv_check_score);
            btn_dialog_delay = (Button) view.findViewById(R.id.btn_dialog_delay);
            btn_dialog_delete = (Button) view.findViewById(R.id.btn_dialog_delete);
            btn_dialog_export = (Button) view.findViewById(R.id.btn_dialog_export);
            if(answer.getTp_share()==1)
                btn_dialog_export.setText("已共享");
            else
                btn_dialog_export.setText("共享");
            btn_dialog_release = (Button) view.findViewById(R.id.btn_dialog_release);
            if(answer.getStatus()==3){
                btn_dialog_release.setText("再发布");
            }
            btn_dialog_rolling = (Button) view.findViewById(R.id.btn_dialog_rolling);
            btn_dialog_preview = (Button) view.findViewById(R.id.btn_dialog_preview);
            btn_dialog_edit = (Button) view.findViewById(R.id.btn_dialog_edit);
            btn_dialog_correct = (Button) view.findViewById(R.id.btn_dialog_correct);
            LinearLayout ll_check_score = (LinearLayout) view.findViewById(R.id.ll_check_score);
            ll_check_score.setOnClickListener(this);
            btn_dialog_delay.setOnClickListener(this);
            btn_dialog_delete.setOnClickListener(this);
            btn_dialog_export.setOnClickListener(this);
            btn_dialog_release.setOnClickListener(this);
            btn_dialog_rolling.setOnClickListener(this);
            btn_dialog_preview.setOnClickListener(this);
            btn_dialog_edit.setOnClickListener(this);
            btn_dialog_correct.setOnClickListener(this);


        }
    }


    /**
     * 删除确认对话框
     */
    public void delDialog() {
        delDialog = new Dialog(this.getParent().getParent(), R.style.PassDialog);
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
     * 删除作业
     */
    public void delWork() {
        if (history != null) {
            AjaxParams params = new ServecHttp().updatePwd(key, userId, history.getId(), "zyid");
            http.post(IBaes.WORK_DELETE, params, new AjaxCallBack<Object>() {
                @Override
                public void onSuccess(Object o) {
                    super.onSuccess(o);
//                    Log.i("test",o.toString());
                    Map<String, Object> map = new JsonData().jsonAvatar(o.toString());
                    Message msg = new Message();
                    msg.obj = map;
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
            });
        }
    }


    /**
     * 延迟收卷
     */
    public void delayWork() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog.cancel();
        }
        GregorianCalendar gc = new GregorianCalendar();
        if (history.getCreatetime() > System.currentTimeMillis()) {
            gc.setTimeInMillis(history.getCreatetime());
        } else {
            gc.setTimeInMillis(System.currentTimeMillis());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date t_d = gc.getTime();
        int year = t_d.getYear() + 1900;
        int month = t_d.getMonth() + 1;
        int day = t_d.getDate();
        String strDate = year + "-" + month + "-" + (day + 1) + " 8:00";
        Intent intent = new Intent(this, WorkStatActivity.class);
        intent.putExtra("re_date", strDate);
        intent.putExtra("re_get", sdf.format(gc.getTime()));
        getParent().startActivityForResult(intent, IBaes.NOTICETWO_GET);
    }

    /**
     * 延迟收卷提交
     */
    public void goDelay(String str) {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(this, "网络不给力,请稍后...");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = sdf.parse(str);
            delay_date = date.getTime() / 1000;
            AjaxParams params = new AjaxParams();
            params.put("key", key);
            params.put("userId", userId);
            params.put("zyid", history.getId());
            params.put("endtimes", date.getTime() + "");
            new FinalHttp().post(IBaes.WORK_DELAY_DATE, params, new AjaxCallBack<Object>() {
                @Override
                public void onSuccess(Object o) {
                    super.onSuccess(o);
//                    Log.i("test", "延迟交卷:" + o.toString());
                    Map<String, Object> map = new JsonData().jsonAvatar(o.toString());
                    Message msg = new Message();
                    msg.obj = map;
                    msg.what = 2;
                    handler.sendMessage(msg);
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * 共享
     */
    public void goShare() {
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(this, "网络不给力,请稍后...");
            return;
        }
        AjaxParams params = new ServecHttp().updatePwd(key, userId, history.getId(), "zyid");
        new FinalHttp().post(IBaes.WORK_SHARE_WORK, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.i("test", "共享:" + o.toString());
                Map<String, Object> map = new JsonData().jsonAvatar(o.toString());
                Message msg = new Message();
                msg.obj = map;
                msg.what = 3;
                handler.sendMessage(msg);
            }
        });

    }

    /**收卷*/
    public void getRolling(){
        rollingDialog = new Dialog(this.getParent().getParent(), R.style.PassDialog);
        rollingDialog.show();
        View rv = LayoutInflater.from(this).inflate(R.layout.dialog_rolling, null);
        rollingDialog.setContentView(rv);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p = rollingDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.9
        rollingDialog.getWindow().setAttributes(p); // 设置生效

        rb_rolling_sel = (RadioButton) rv.findViewById(R.id.rb_rolling_sel);
        rb_rolling_nosel = (RadioButton) rv.findViewById(R.id.rb_rolling_nosel);
        rb_rolling_sel.setOnClickListener(this);
        rb_rolling_nosel.setOnClickListener(this);
        Drawable drawable = getResources().getDrawable(R.drawable.select_yes);
        Drawable dr = getResources().getDrawable(R.drawable.select_pay_no);
        drawable.setBounds(0, 0, 30,30);
        dr.setBounds(0, 0, 30,30);
        rb_rolling_sel.setCompoundDrawables(drawable,null,null,null);
        rb_rolling_nosel.setCompoundDrawables(dr,null,null,null);

        Button rolling_cancel= (Button) rv.findViewById(R.id.rolling_cancel);
        Button rolling_go= (Button) rv.findViewById(R.id.rolling_go);
        rolling_cancel.setOnClickListener(this);
        rolling_go.setOnClickListener(this);
    }
    /**确认收卷*/
    public void goRolling(){
        if (!IBaes.isNet(this)) {
            IBaes.toastShow(this, "网络不给力,请稍后...");
            return;
        }
        AjaxParams params=new AjaxParams();
        params.put("key",key);
        params.put("userId",userId);
        params.put("zyid",history.getId());
        params.put("modl",Modl+"");
        new FinalHttp().post(IBaes.WORK_ROLLING_WORK, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
//                Log.i("test","收卷:"+o.toString());
                Map<String,Object> map=new JsonData().jsonAvatar(o.toString());
                Message msg=new Message();
                msg.obj=map;
                msg.what=4;
                handler.sendMessage(msg);
            }
        });
    }


    @Override
    public void dialogResult(int tag, int state) {
        if (state == LoadingDialog.SUCCESS) {
            setResult(100);
            finish();
        }
    }

    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IBaes.NOTICETWO_GET) {
            //接受时间
            int state = data.getIntExtra("state_status", 2);
            String time = data.getStringExtra("state_date");
            int deyle = data.getIntExtra("deyle", 0);
            if (deyle == 1) {
                goDelay(time);
            }
        }
    }

    /**
     * 广播
     */
    class UnlitedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            curpage = 1;
            if (IBaes.ACTION_WORK_HISTORY_SUBJECT.equals(intent.getAction())
                    || IBaes.ACTION_NET.equals(intent.getAction()) || IBaes.ACTION_LOGIN.equals(intent.getAction())) {
                // 章节选择广播
                jid = intent.getStringExtra("sectionId");
                key = SPUtils.get(HistoryWorkActivity.this, "key", "").toString();
                userId = SPUtils.get(HistoryWorkActivity.this, "userId", "").toString();
                getUnlited();
            }
            if (IBaes.ACTION_WORK_ADDWROK.equals(intent.getAction())) {
                //作业数据发生变化
                lists.clear();
                getUnlited();
            }
        }

    }

}
