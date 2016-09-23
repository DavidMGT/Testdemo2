package com.work.student.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.work.student.R;
import com.work.student.bean.InformBean;
import com.work.student.tool.DateUtil;
import com.work.student.tool.LogUtils;

import java.util.List;

/**
 * 消息页面的通知列表
 * Created by maguitao on 2016/9/8.
 */
public class NoticeListAdapter extends BaseAdapter {
    private List<InformBean> infos;

    public NoticeListAdapter(List<InformBean> infos, Context mContext) {
        this.infos = infos;
        this.mContext = mContext;
    }

    private Context mContext;

    @Override
    public int getCount() {
        return infos.size();
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
        Viewholder viewholder = null;
        InformBean bean = infos.get(position);
        if (convertView == null) {
            viewholder = new Viewholder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_notice, null);
            viewholder.tv_notice_title = (TextView) convertView.findViewById(R.id.tv_notice_title);
            viewholder.tv_notice_date = (TextView) convertView.findViewById(R.id.tv_notice_date);
            viewholder.tv_notice_content = (TextView) convertView.findViewById(R.id.tv_notice_content);
            viewholder.iv_red_point = convertView.findViewById(R.id.v_dot);
            convertView.setTag(viewholder);
        } else {
            viewholder = (Viewholder) convertView.getTag();
        }
        viewholder.iv_red_point.setVisibility(View.VISIBLE);
        viewholder.tv_notice_date.setText(DateUtil.getSimpleDate(bean.getSendtime()));
        String status = bean.getStatus();
        if (status != null && status.equalsIgnoreCase("1")) {
            viewholder.iv_red_point.setVisibility(View.GONE);
        }
        viewholder.tv_notice_title.setText(bean.getFtitle());
        viewholder.tv_notice_content.setText(bean.getFcontent());
        return convertView;
    }

    class Viewholder {
        TextView tv_notice_title, tv_notice_date, tv_notice_content;
        View iv_red_point;
    }
}
