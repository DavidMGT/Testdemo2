package com.work.teacher.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.work.teacher.R;
import com.work.teacher.more.notice.NoticeListActivity;
import com.work.teacher.work.QuestionnarielistActivity;

/**
 * 消息Fragment
 *
 * @author 左丽姬
 */
public class MessageMsgFragment extends Fragment {
    private ListView mlistview;
    private MessageMsgAdapter mAdapter;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mlistview = (ListView) view.findViewById(R.id.lv_message);
        mlistview.setOnItemClickListener(new MyOnItemClickLisner());
        mAdapter = new MessageMsgAdapter(null, getContext());
        mlistview.setAdapter(mAdapter);
    }

    class MyOnItemClickLisner implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                //通知
                startActivity(new Intent(mContext, NoticeListActivity.class));
            } else {
                //问卷列表
                startActivity(new Intent(mContext, QuestionnarielistActivity.class));
            }
        }
    }
}
