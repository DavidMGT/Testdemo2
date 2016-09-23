package com.work.teacher.work.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.work.teacher.R;
import com.work.teacher.bean.LeftSubject;

import java.util.List;

/**
 * 作业-》题库-》》条件查询列表适配器
 * Created by 左丽姬 on 2016/9/7.
 */
public class QuestionConditionAdapter extends BaseAdapter{

    private List<LeftSubject> lists;
    private Context context;

    public QuestionConditionAdapter(List<LeftSubject> lists, Context context) {
        this.lists = lists;
        this.context = context;
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
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_condition_question,null);
        }
       TextView item_condition_name= (TextView) convertView.findViewById(R.id.item_condition_name);
        LeftSubject obj=lists.get(position);
        if(obj!=null){
            item_condition_name.setText(obj.getCname());
        }
        return convertView;
    }
}
