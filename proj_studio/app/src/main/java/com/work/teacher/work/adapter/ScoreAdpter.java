package com.work.teacher.work.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.work.teacher.R;
import com.work.teacher.tool.IBaes;

import java.util.ArrayList;
import java.util.List;

/**
 * 作业-》分数设置
 * Created by 左丽姬 on 2016/9/20.
 */
public class ScoreAdpter extends BaseAdapter{
    private Context context;
    private List<String> lists;

    public ScoreAdpter(Context context, List<String> lists) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ScoreHolder holder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_score,null);
            holder=new ScoreHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ScoreHolder) convertView.getTag();
        }
        String str=lists.get(position);
        String[] strs=str.split("∮∞");
        if(strs.length>1){
            if(strs.length>2) {
                holder.item_score_name.setText(strs[0] + "X" + strs[2]);
            }else{
                holder.item_score_name.setText(strs[0]);
            }
            holder.item_score_num.setText(strs[1]);
        }
        holder.item_score_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus==false){
                    if(lisntener!=null){
                        lisntener.onEditextLisntener((View) v.getParent(),position);
                    }
                }
            }
        });
        return convertView;
    }

    class ScoreHolder{
        TextView item_score_name;
        EditText item_score_num;
        public ScoreHolder(View v){
            item_score_name = (TextView) v.findViewById(R.id.item_score_name);
            item_score_num = (EditText) v.findViewById(R.id.item_score_num);
        }
    }

    private onEditextLisntener lisntener=null;

    public interface onEditextLisntener {
        void onEditextLisntener(View v,int position);
    }

    public void setOnEditLis(){
        lisntener=new onEditextLisntener() {
            @Override
            public void onEditextLisntener(View v, int position) {
                String s=lists.get(position);
                String[] strs=s.split("∮∞");
                EditText num= (EditText) v.findViewById(R.id.item_score_num);
                String score=num.getText().toString();               if("".equals(score.trim())){
                    score="0";
                }
                if(strs.length>1){
                    s=strs[0]+"∮∞"+score;
                    if(strs.length>2) {
                        s+="∮∞"+strs[2];
                    }
                    if(strs.length>3){
                        s+="∮∞"+strs[3];
                    }
                    if(!"总分".equals(strs[0])) {
                        for(int i=0;i<lists.size();i++){
                            if(i==position){
                                lists.remove(lists.get(position));
                                lists.add(i,s);
                            }
                        }
                        //计算分数
                        double v1 = Double.parseDouble(strs[1]);
                        double v2 = Double.parseDouble(score);
                        double v3 = v2 - v1;
                        for (String sg : lists) {
                            String[] sz = sg.split("∮∞");
                            if ("总分".equals(sz[0])) {
                                lists.remove(sg);
                                double v4 = Double.parseDouble(sz[1]) + v3;
                                lists.add("总分∮∞" + v4);
                            }
                        }
                    }else{
                        if(!strs[1].equals(score)) {
                            IBaes.toastShow((Activity) context, "总分不能直接修改");
                            num.setText(strs[1]);
                            return;
                        }
                    }
                    if(!strs[1].equals(score))
                        ScoreAdpter.this.notifyDataSetChanged();
                }
            }
        };
    }

}
