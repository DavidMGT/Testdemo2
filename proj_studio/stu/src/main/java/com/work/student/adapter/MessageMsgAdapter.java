package com.work.student.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.work.student.R;
import com.work.student.bean.MessageBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8.
 */
public class MessageMsgAdapter extends BaseAdapter {
    private List<MessageBean> mList;
    private Context mContext;

    public MessageMsgAdapter(List<MessageBean> list, Context context) {
        this.mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_lv_msg, null);
            viewHolder.ivImg = (ImageView) convertView.findViewById(R.id.iv_img);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tvlastMsg = (TextView) convertView.findViewById(R.id.tv_last_msg);
            viewHolder.tvUnRead = (TextView) convertView.findViewById(R.id.tv_unRead);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position == 0) {
            viewHolder.tvTitle.setText("通和");
            viewHolder.ivImg.setImageResource(R.drawable.ic_launcher);
            viewHolder.tvlastMsg.setText(mList.get(position).getFcontent());
            String num = mList.get(position).getNum();
            int iNum = Integer.parseInt(num);
            if (iNum == 0) {
                viewHolder.tvUnRead.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.tvUnRead.setVisibility(View.VISIBLE);
                viewHolder.tvUnRead.setText(num + "");
            }
        } else {
            viewHolder.tvTitle.setText("问卷");
            viewHolder.tvlastMsg.setText(mList.get(position).getWintro());
            String num = mList.get(position).getNum();
            int iNum = Integer.parseInt(num);
            viewHolder.ivImg.setImageResource(R.drawable.ic_message);
            if (iNum == 0) {
                viewHolder.tvUnRead.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.tvUnRead.setVisibility(View.VISIBLE);
                viewHolder.tvUnRead.setText(num + "");
            }
        }

        return convertView;
    }


    class ViewHolder {
        public ImageView ivImg;
        public TextView tvTitle;
        public TextView tvTime;
        public TextView tvUnRead;
        public TextView tvlastMsg;
    }
}
