package com.work.student.homework;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.work.student.R;
import com.work.student.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class FeedBackActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
    }

    @Override
    public void initData() {
        mHandler.sendEmptyMessage(GET_DATA_COMPLETE);
    }

    private GridView gv_type;
    private MyGvAdapter mAdapter;
    private List<String> mSelects = new ArrayList<>();
    private Button bt_commit;

    @Override
    public void initView() {
        top_btn.setVisibility(View.GONE);
        gv_type = (GridView) findViewById(R.id.gv_type);
        bt_commit = (Button) findViewById(R.id.bt_commit);
        bt_commit.setOnClickListener(this);
        mAdapter = new MyGvAdapter();
        gv_type.setAdapter(mAdapter);
        gv_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String select = arrays[position];
                if (mSelects.contains(select)) {
                    mSelects.remove(select);
                } else {
                    mSelects.add(select);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        mTvTitle.setText("题目报错");
    }

    private String[] arrays = {"题干错误", "答案错误", "解析有误"};

    @Override
    public void onClick(View v) {

    }

    private class MyGvAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrays.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String select = arrays[position];
            convertView = View.inflate(mContext, R.layout.item_gv, null);
            TextView tv_error_des = (TextView) convertView.findViewById(R.id.tv_error_des);
            tv_error_des.setText(arrays[position]);
            if (mSelects.contains(select)) {
                tv_error_des.setTextColor(0XFF4AC4BC);//
                tv_error_des.setBackgroundResource(R.drawable.drawable_circle_white_bg_green_solid);
            } else {
                tv_error_des.setTextColor(0XFFCBCBCB);
                tv_error_des.setBackgroundResource(R.drawable.drawable_circle_whight_bg);
            }
            return convertView;
        }
    }
}
