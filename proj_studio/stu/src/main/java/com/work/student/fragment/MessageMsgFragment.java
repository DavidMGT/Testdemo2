package com.work.student.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.work.student.R;
import com.work.student.SubMessageActivity;
import com.work.student.adapter.MessageMsgAdapter;
import com.work.student.bean.MessageBean;
import com.work.student.tool.LogUtils;
import com.work.student.tool.SPUtils;

import net.tsz.afinal.FinalHttp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息Fragment
 *
 * @author 左丽姬
 * @author UFO
 */
public class MessageMsgFragment extends Fragment {

    private static final String TAG = "MessageMsgFragment";

    private ListView mLvMessage;
    private List<MessageBean> lists = new ArrayList<>();
    private MessageMsgAdapter mAdapter;
    private Context mContext;
    private String lastwid, lastfid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        lastfid = (String) SPUtils.get(mContext, "lastfid", "");
        lastwid = (String) SPUtils.get(mContext, "lastwid", "");
        LogUtils.debug("lastfid=" + lastfid + ":lastwid=" + lastwid);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LogUtils.debug("onViewCreated------");
        super.onViewCreated(view, savedInstanceState);
        mLvMessage = (ListView) view.findViewById(R.id.lv_message);
        mLvMessage.setOnItemClickListener(new MyOnItemClickListener());
        mAdapter = new MessageMsgAdapter(this.lists, getContext());
        mLvMessage.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 用来接收NoticeService通过EventBus发过来的事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiverEventBus(List<MessageBean> lists) {
        LogUtils.debug("MessageMsgFragment onReceiverEventBus-----------");
        if (lastwid.equalsIgnoreCase("")) {
            lastwid = (String) SPUtils.get(mContext, "lastwid", null);
        }
        if (lastfid.equalsIgnoreCase("")) {
            lastfid = (String) SPUtils.get(mContext, "lastfid", null);
        }
        this.lists.clear();
        this.lists.addAll(lists);
        if (lists.get(0).getFid().equalsIgnoreCase(lastfid)) {
            //没有最新的通知
            lists.get(0).setNum("0");
        }
        if (lists.get(1).getWid().equalsIgnoreCase(lastwid)) {
            //没有最新的通知
            lists.get(1).setNum("0");
        }
        LogUtils.debug("list0NUM" + lists.get(0).getNum() + "--list1NUm" + lists.get(1).getNum());
        LogUtils.debug(lists.toString() + "   -------------");
        mAdapter.notifyDataSetChanged();
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), SubMessageActivity.class);
            if (position == 0) {
                lists.get(0).setNum("0");
                intent.putExtra("title", "通知");
                SPUtils.put(mContext, "lastfid", lists.get(0).getFid());
            } else {
                lists.get(1).setNum("0");
                intent.putExtra("title", "问卷");
                SPUtils.put(mContext, "lastwid", lists.get(1).getWid());
                String mesg = (String) SPUtils.get(mContext, "lastwid", "");
                LogUtils.debug("取出的值为" + mesg);
            }
            mAdapter.notifyDataSetChanged();
            startActivity(intent);
        }
    }
}
