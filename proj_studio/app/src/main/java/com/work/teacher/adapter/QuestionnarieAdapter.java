package com.work.teacher.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.work.teacher.R;
import com.work.teacher.bean.QuestionnarieBean;
import com.work.teacher.tool.DateUtil;
import com.work.teacher.tool.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8.
 */
public class QuestionnarieAdapter extends BaseAdapter {

    private static final String TAG = "MessageMsgSubAdapter";

    private Context mContext;
    private List<QuestionnarieBean> mList;
    /**
     * 类型,分:问卷(2)与通知(1)
     */
    private int type;

    public QuestionnarieAdapter(Context context, List<QuestionnarieBean> list) {
        this.mContext = context;
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
        QuestionnarieBean bean = mList.get(position);
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_questionnaire, null);
            viewHolder.tvNoticeTitle = (TextView) convertView.findViewById(R.id.tv_notice_title);
            viewHolder.tvNoticeContent = (TextView) convertView.findViewById(R.id.tv_notice_content);
            viewHolder.tvNoticeFrom = (TextView) convertView.findViewById(R.id.tv_notice_from);
            viewHolder.vDot = convertView.findViewById(R.id.v_dot);
            viewHolder.iv_status = (ImageView) convertView.findViewById(R.id.iv_status);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvNoticeTitle.setText(bean.getWtitle());
        LogUtils.debug("bean.getSendtime()--" + bean.getSendtime() + "  bean.getDtime= " + bean.getDtime());
        // viewHolder.tvNoticeFrom.setText("发布时间：" + DateUtil.getSimpleDateFromString(bean.getSendtime() + "   截至时间：" + DateUtil.getSimpleDateFromString(bean.getDtime())));
        return convertView;
    }


    class ViewHolder {
        public TextView tvNoticeTitle;
        public TextView tvNoticeDate;
        public TextView tvNoticeContent;
        public TextView tvNoticeFrom, tv_publish_status;
        public View vDot;
        public ImageView iv_status;

    }
}

