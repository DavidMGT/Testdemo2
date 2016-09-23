package com.work.teacher.work.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.work.teacher.R;
import com.work.teacher.bean.WorkRelseContent;
import com.work.teacher.bean.WorkRelseQuestion;
import com.work.teacher.tool.IBaes;
import com.work.teacher.tool.JsonData;
import com.work.teacher.tool.SPUtils;
import com.work.teacher.view.MyListView;
import com.work.teacher.work.WorkEditActivity;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 作业-》发布作业作业预览
 * Created by 左丽姬 on 2016/9/14.
 */
public class WorkEditAdapter extends BaseAdapter {
    private Context context;
    private List<WorkRelseQuestion> lists;
    private WorkeditHodle hodle;
    private String[] strs = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
    private WorkEditListAdapter adapter;
    private int index;

    public WorkEditAdapter(Context context, List<WorkRelseQuestion> lists, int index) {
        this.context = context;
        this.lists = lists;
        this.index = index;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        hodle = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_workedit, null);
            hodle = new WorkeditHodle(convertView);
            convertView.setTag(hodle);
        } else {
            hodle = (WorkeditHodle) convertView.getTag();
        }
        final WorkRelseQuestion question = lists.get(position);
        if (question != null) {
            String number = "";
            String str = position + "";
            char[] num = str.toCharArray();
            for (int i = 0; i < num.length; i++) {
                int inum = Integer.parseInt(String.valueOf(num[i]));
                number += strs[inum];
            }
            final int nums = position;
            hodle.item_title_type.setText(number + "、" + question.getTitle() + " (" + question.getFen() + "分)");
            if (question.contents != null && question.contents.size() > 0) {
                adapter = new WorkEditListAdapter(context, question.contents, index);
                adapter.setonDelClickLinstener(new WorkEditListAdapter.onDelClickLinstener() {
                    @Override
                    public void onImageClick(View v, int poistion, String name) {
                        if (linstener != null) {
                            linstener.onImageClick(v, poistion, nums, name);
                        }
                    }
                });
                hodle.item_edit_lists.setAdapter(adapter);
                IBaes.setGroupHeight(hodle.item_edit_lists);
                hodle.item_edit_lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (linstener != null) {
                            linstener.onImageClick(view, position, nums, "item");
                        }
                    }
                });
            }
        }
        return convertView;
    }


    class WorkeditHodle {
        TextView item_title_type;
        MyListView item_edit_lists;

        public WorkeditHodle(View v) {

            item_title_type = (TextView) v.findViewById(R.id.item_title_type);
            item_edit_lists = (MyListView) v.findViewById(R.id.item_edit_lists);


        }
    }


    private onImageClickLinstener linstener = null;

    public interface onImageClickLinstener {
        void onImageClick(View v, int poistion, int qpoistion, String name);
    }

    public void setonImageClickLinstener(onImageClickLinstener onImageClickLinstener) {
        linstener = onImageClickLinstener;
    }

}
