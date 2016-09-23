package com.work.teacher.work.adapter;

import com.work.teacher.R;
import com.work.teacher.bean.WorkHistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 作业-》历史-->>adapter
 *
 * @author 左丽姬
 */
public class WorkHistoryAdapter extends BaseAdapter {

    private WorkHistoryHold hold;
    private Context context;
    private List<WorkHistory> lists;

    public WorkHistoryAdapter(Context context, List<WorkHistory> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        hold = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_workhistory_lists, null);
            hold = new WorkHistoryHold(convertView);
            convertView.setTag(hold);
        } else {
            hold = (WorkHistoryHold) convertView.getTag();
        }
        WorkHistory history = lists.get(position);
        //状态 作业状态 1待发布 2答题中，3已完成
        switch (history.status) {
            case 1:
                hold.ll_release_work.setBackgroundResource(R.drawable.work_finsh);
                hold.iv_hurs_work.setImageResource(R.drawable.wark_dengda);
                hold.tv_hurs_work.setText("待发布");
                break;
            case 2:
                hold.ll_release_work.setBackgroundResource(R.drawable.notice_release_hurs);
                hold.iv_hurs_work.setImageResource(R.drawable.hourglass_easyicon);
                hold.tv_hurs_work.setText("答题中");
                break;
            case 3:
                hold.ll_release_work.setBackgroundResource(R.drawable.notice_release_yes);
                hold.iv_hurs_work.setImageResource(R.drawable.time_easyicon);
                hold.tv_hurs_work.setText("已完成");
                break;
        }
        hold.tv_title_work.setText(history.getName());
        hold.tv_type_work.setText("类型:"+history.getType());
        hold.tv_allnum_work.setText(history.getTotal() + "");
        if ("null".equals(history.getJudge()))
            hold.tv_selectAnswer_work.setText("未添加");
        else
            hold.tv_selectAnswer_work.setText(history.getJudge());
        GregorianCalendar gc = new GregorianCalendar();
//		Log.i("test", "时间:"+notice.getFtime());
        gc.setTimeInMillis(history.getCreatetime() * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        hold.tv_release_work.setText("发布时间: " + sdf.format(gc.getTime()));
        if (history.getEndtime() != 0) {
            GregorianCalendar gc_end = new GregorianCalendar();
            gc_end.setTimeInMillis(history.getEndtime() * 1000);
            SimpleDateFormat sdf_end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            hold.tv_rolling_work.setText("截止时间: " + sdf_end.format(gc_end.getTime()));
        } else {
            hold.tv_rolling_work.setText("截止时间: 不限");
        }
        return convertView;
    }

    class WorkHistoryHold {
        /**
         * 作业标题
         */
        private TextView tv_title_work;
        /**
         * 作业状态布局LinearLayout
         */
        private LinearLayout ll_release_work;
        /**
         * 作业状态布局ImageView
         */
        private ImageView iv_hurs_work;
        /**
         * 作业状态布局TextView
         */
        private TextView tv_hurs_work;
        /**
         * 类型
         */
        private TextView tv_type_work;
        /**
         * 总题数
         */
        private TextView tv_allnum_work;
        /**
         * 选择题
         */
        private TextView tv_selectAnswer_work;
//		/** 阅读理解 */
//		private TextView tv_understand_work;
//		/** 填空题 */
//		private TextView tv_cloze_work;
        /**
         * 发布时间
         */
        private TextView tv_release_work;
        /**
         * 收卷时间
         */
        private TextView tv_rolling_work;

        public WorkHistoryHold(View v) {
            tv_title_work = (TextView) v.findViewById(R.id.tv_title_work);
            ll_release_work = (LinearLayout) v.findViewById(R.id.ll_release_work);
            iv_hurs_work = (ImageView) v.findViewById(R.id.iv_hurs_work);
            tv_hurs_work = (TextView) v.findViewById(R.id.tv_hurs_work);
            tv_type_work = (TextView) v.findViewById(R.id.tv_type_work);
            tv_allnum_work = (TextView) v.findViewById(R.id.tv_allnum_work);
            tv_selectAnswer_work = (TextView) v.findViewById(R.id.tv_selectAnswer_work);
//			tv_understand_work = (TextView) v.findViewById(R.id.tv_understand_work);
//			tv_cloze_work = (TextView) v.findViewById(R.id.tv_cloze_work);
            tv_release_work = (TextView) v.findViewById(R.id.tv_release_work);
            tv_rolling_work = (TextView) v.findViewById(R.id.tv_rolling_work);
        }
    }
}
