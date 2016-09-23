package com.work.student.view;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.print.PageRange;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mingle.widget.LoadingView;
import com.work.student.R;
import com.work.student.bean.MessageBean;
import com.work.student.bean.NoticeDetail;
import com.work.student.bean.NoticeDetails;
import com.work.student.tool.DateUtil;
import com.work.student.tool.IBaes;
import com.work.student.tool.LogUtils;
import com.work.student.tool.ServecHttp;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * create by maguitao
 */
public class NoticeDetailActivity extends Activity implements View.OnClickListener {
    private String noticeId;
    private TextView mTvTitle, tv_voice_length, tv_notice_title, tv_notice_content, tv_notice_publishtime, tv_notice_publisher;
    private ImageView bt_play;
    private Button bt_accept, bt_reject;
    private LinearLayout ll_botoom;
    private FinalHttp mFinalHttp;
    private ServecHttp mServecHttp;
    private LoadingView mLoddingView;
    private LinearLayout ll_voice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice2);
        initData();
        initView();
    }

    private NoticeDetail mNoticeBean;

    private void initData() {
        noticeId = getIntent().getStringExtra("noticeId");
        LogUtils.debug("noticeId=" + noticeId);
        //发送网络请求获取数据
        mServecHttp = new ServecHttp();
        mFinalHttp = new FinalHttp();
        AjaxParams params = mServecHttp.createNoticeDetailParams(IBaes.userid, IBaes.TYPE_NOTICE, noticeId);
        mFinalHttp.get(IBaes.GET_NOTICE_DETAIL, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                LogUtils.debug("GET_NOTICE_DETAIL onSuccess---" + s.toString());
                try {
                    JSONObject jsonobj = new JSONObject(s.toString());
                    String msg = jsonobj.getString("status");
                    if (!msg.equalsIgnoreCase("1")) {
                        Toast.makeText(NoticeDetailActivity.this, "获取详情失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mNoticeBean = new Gson().fromJson(jsonobj.getString("data").toString(), new TypeToken<NoticeDetail>() {
                    }.getType());
                    updateUI();
                    LogUtils.debug("mNoticeBean=" + mNoticeBean.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                LogUtils.debug("GET_NOTICE_DETAIL onFailure---" + strMsg);
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }

    /**
     * 更细界面
     */
    private void updateUI() {
        //    private TextView mTvTitle, tv_voice_length, tv_notice_title, tv_notice_content, tv_notice_publishtime, tv_notice_publisher;
        tv_notice_title.setText(mNoticeBean.getFtitle());
        tv_notice_content.setText(mNoticeBean.getFcontent());
        tv_notice_publisher.setText("发布人 ：" + mNoticeBean.getTeachername());
        tv_notice_publishtime.setText("发布时间：" + DateUtil.getSimpleDate(mNoticeBean.getSendtime()));
        mLoddingView.setVisibility(View.GONE);
        if (mNoticeBean.getTapeurl() == null || mNoticeBean.getTapeurl().equalsIgnoreCase("")) {
            ll_voice.setVisibility(View.INVISIBLE);
        }
        if (mNoticeBean.getAgree().equals("1")) {
            tv_myfeedback.setVisibility(View.VISIBLE);
            tv_myfeedback.setTextColor(getResources().getColor(R.color.green));
            tv_myfeedback.setText(getString(R.string.feedback_accept));
            ll_botoom.setVisibility(View.INVISIBLE);
        } else if (mNoticeBean.getAgree().equals("0")) {
            tv_myfeedback.setVisibility(View.VISIBLE);
            tv_myfeedback.setTextColor(getResources().getColor(R.color.red));
            tv_myfeedback.setText(getString(R.string.feedback_reject));
            ll_botoom.setVisibility(View.INVISIBLE);
        }
        LogUtils.debug("mNoticeBean.getAgree=" + mNoticeBean.getAgree());
    }

    private TextView tv_myfeedback;

    private void initView() {
        initHeader();
        tv_myfeedback = (TextView) findViewById(R.id.tv_myfeedback);
        ll_voice = (LinearLayout) findViewById(R.id.ll_voice);
        mLoddingView = (LoadingView) findViewById(R.id.loadView);
        tv_voice_length = (TextView) findViewById(R
                .id.tv_voice_length);
        tv_notice_content = (TextView) findViewById(R
                .id.tv_notice_content);
        tv_notice_publishtime = (TextView) findViewById(R
                .id.tv_notice_publishtime);
        tv_notice_title = (TextView) findViewById(R
                .id.tv_notice_title);
        tv_notice_publisher = (TextView) findViewById(R
                .id.tv_notice_publisher);
        bt_play = (ImageView) findViewById(R.id.bt_play);
        bt_play.setOnClickListener(this);
        ll_botoom = (LinearLayout) findViewById(R.id.ll_botoom);
        findViewById(R.id.bt_accept).setOnClickListener(this);
        findViewById(R.id.bt_reject).setOnClickListener(this);

    }

    private void initHeader() {
        mTvTitle = (TextView) findViewById(R.id.top_text);
        mTvTitle.setText("详情页");
        findViewById(R.id.top_btn).setVisibility(View.GONE);
        findViewById(R.id.top_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_accept:
                //点击了我要参加，发送请求1：同意  0：不同意
                SendRequset("1");
                break;
            case R.id.bt_reject:
                SendRequset("0");
                //点击了我不能出席


                break;
            case R.id.bt_play:
                String url = mNoticeBean.getTapeurl();
                // url = "http://sc.111ttt.com/up/mp3/347508/FCAF062BECD1C24FAED2A355EF51EBDD.mp3";
                play(url);
                //     play("http:\\/\\/teacher.wezuoye.com\\/Uploads\\/186T1472285170.mp3");
                break;

        }
    }

    private void SendRequset(final String type) {
        AjaxParams params = mServecHttp.createNoticStateParams(IBaes.userid, IBaes.TYPE_NOTICE, noticeId, type);
        mFinalHttp.get(IBaes.GET_NOTICE_STATE, params, new AjaxCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            LogUtils.debug("GET_NOTICE_STATE" + s);
                            JSONObject jsonObj = new JSONObject(s);
                            String mes = (String) jsonObj.get("mes");

                            if (mes != null && mes.equalsIgnoreCase("success")) {
                                //1.同意
                                if (type.equalsIgnoreCase("1")) {
                                    tv_myfeedback.setVisibility(View.VISIBLE);
                                    tv_myfeedback.setTextColor(getResources().getColor(R.color.green));
                                    tv_myfeedback.setText(getString(R.string.feedback_accept));
                                    ll_botoom.setVisibility(View.INVISIBLE);
                                } else {
                                    tv_myfeedback.setVisibility(View.VISIBLE);
                                    tv_myfeedback.setTextColor(getResources().getColor(R.color.red));
                                    tv_myfeedback.setText(getString(R.string.feedback_reject));
                                    ll_botoom.setVisibility(View.INVISIBLE);
                                }
                            } else {
                                Toast.makeText(NoticeDetailActivity.this, "提交数据失败", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        super.onSuccess(s);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                    }
                }
        );
    }

    private void play(String url) {   //使用此方法可以直接在后台获取音频文件的播放时间，而不会真的播放音频
        MediaPlayer player = new MediaPlayer();  //首先你先定义一个mediaplayer
        Uri uri = Uri.parse(url);
        try {
            player.setDataSource(url);  //String是指音频文件的路径
            player.prepare();        //这个是mediaplayer的播放准备 缓冲
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {//监听准备

            @Override
            public void onPrepared(MediaPlayer player) {
                player.start();
                double size = player.getDuration();//得到音频的时间
                LogUtils.debug("时长timelong=+" + size);
                String timelong1 = (int) Math.ceil((size / 1000)) + "''";//转换为秒 单位为''
            }

        });
        player.stop();//暂停播放
        player.release();//释放资源
    }
}
