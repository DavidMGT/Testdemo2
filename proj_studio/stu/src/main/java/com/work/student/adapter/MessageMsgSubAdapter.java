package com.work.student.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.work.student.R;
import com.work.student.bean.MessageBean;
import com.work.student.tool.DateUtil;
import com.work.student.tool.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8.
 */
public class MessageMsgSubAdapter extends BaseAdapter {

    private static final String TAG = "MessageMsgSubAdapter";

    private Context mContext;
    private List<MessageBean> mList;
    /**
     * 类型,分:问卷(2)与通知(1)
     */
    private int type;

    public MessageMsgSubAdapter(Context context, int type, List<MessageBean> list) {
        this.mContext = context;
        this.type = type;
        this.mList = list;
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
        MessageBean bean = mList.get(position);
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_notice, null);
            viewHolder.tvNoticeTitle = (TextView) convertView.findViewById(R.id.tv_notice_title);
            viewHolder.tvNoticeContent = (TextView) convertView.findViewById(R.id.tv_notice_content);
            viewHolder.tvNoticeFrom = (TextView) convertView.findViewById(R.id.tv_notice_from);
            viewHolder.tvNoticeDate = (TextView) convertView.findViewById(R.id.tv_notice_date);
            viewHolder.vDot = convertView.findViewById(R.id.v_dot);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (type == 1) {
            //通知
            viewHolder.tvNoticeTitle.setText(bean.getFtitle());
            viewHolder.tvNoticeContent.setText(bean.getFcontent());
            viewHolder.tvNoticeDate.setText(DateUtil.getSimpleDate(bean.getFtime()));
            LogUtils.debug("bean,getDtime=问卷" + DateUtil.getSimpleDate(bean.getFtime()));
        } else {
            //问卷
            viewHolder.tvNoticeTitle.setText(bean.getWtitle());
            viewHolder.tvNoticeContent.setText(bean.getWcontent());
            viewHolder.tvNoticeDate.setText(DateUtil.getSimpleDate(bean.getDtime()));
            LogUtils.debug("bean,getDtime=问卷" + DateUtil.getSimpleDate(bean.getDtime()));
        }
        viewHolder.tvNoticeFrom.setText("发布人 :" + bean.getTeachername());
        //0表示未读,其它为已读
        if ("0".equals(bean.getStatus())) {
            viewHolder.vDot.setVisibility(View.VISIBLE);
        } else {
            viewHolder.vDot.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }


    class ViewHolder {
        public TextView tvNoticeTitle;
        public TextView tvNoticeDate;
        public TextView tvNoticeContent;
        public TextView tvNoticeFrom;
        public View vDot;

    }
}

