package com.work.teacher.work.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.work.teacher.R;
import com.work.teacher.bean.QuestionAnswer;
import com.work.teacher.tool.IBaes;

import net.tsz.afinal.FinalBitmap;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 作业-》题库-》》列表适配器
 * Created by 左丽姬 on 2016/9/7.
 */
public class QuestionWorkListAdapter extends BaseAdapter {

    private Context context;
    private int question;
    private List<QuestionAnswer> answers;
    QuestionHolder holder;
    private String[] letter = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"};

    public QuestionWorkListAdapter(Context context, int question, List<QuestionAnswer> answers) {
        this.context = context;
        this.question = question;
        this.answers = answers;
    }

    @Override
    public int getCount() {
        return answers.size();
    }

    @Override
    public Object getItem(int position) {
        return answers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_question_lists, null);
            holder = new QuestionHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (QuestionHolder) convertView.getTag();
        }
        QuestionAnswer answer = answers.get(position);
        if (answer != null) {
            holder.tv_question_ino.setText("题" + answer.getIno());
            holder.ratingBar.setRating(answer.getDiff());
            if (answer.getSus() != 2) {
                holder.use_question.setText("使用(" + answer.getUsedcnt() + ")");
                holder.collect_question.setText("收藏(" + answer.getFavcnt() + ")");
                holder.use_question.setVisibility(View.VISIBLE);
                holder.collect_question.setVisibility(View.VISIBLE);
            } else {
                holder.use_question.setVisibility(View.GONE);
                holder.collect_question.setVisibility(View.GONE);
            }

            //显示题干
            Html.ImageGetter imageGetter = new Html.ImageGetter() {
                public Drawable getDrawable(String source) {
//                    Log.i("test","题干立即:"+IBaes.URL + source);
                    FinalBitmap.create(context).display(holder.image_question, IBaes.URL + source);
                    Drawable drawable = holder.image_question.getDrawable();
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    return drawable;
                }

                ;
            };
            Spanned body = Html.fromHtml(answer.getBody().replace("<br />", "").trim(), imageGetter, null);
            if (!"null".equals(answer.getBody()))
                holder.body_question.setText(body);
            else
                holder.body_question.setText("");
            if (!"null".equals(answer.getCfrom()))
                holder.cfrom_question.setText(answer.getCfrom());
            else
                holder.cfrom_question.setText("");
            Html.ImageGetter imageGette_xx = new Html.ImageGetter() {
                public Drawable getDrawable(String source) {
                    FinalBitmap.create(context).display(holder.xximage_question, IBaes.URL + source);
                    Drawable drawable = holder.xximage_question.getDrawable();
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    return drawable;
                }
            };
            Spanned xuanxiang = Html.fromHtml(answer.xuanxiang, imageGette_xx, null);
            if (!"null".equals(answer.xuanxiang)) {
                holder.xx_question.setText(xuanxiang);
            } else {
                holder.xx_question.setVisibility(View.GONE);
            }
            if (answer.getAnswertype() == 1||answer.getCorrectanswer().indexOf("<div>")!=-1) {
                Html.ImageGetter imageGetterCorrectanswer = new Html.ImageGetter() {
                    public Drawable getDrawable(String source) {
//                    Log.i("test","答案:"+IBaes.URL+source);
                        FinalBitmap.create(context).display(holder.correctanswerimage_question, IBaes.URL + source);
                        Drawable drawable = holder.correctanswerimage_question.getDrawable();
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                        return drawable;
                    }

                    ;
                };
                String as = answer.getCorrectanswer();
                if (!"null".equals(as) && as.indexOf("<div>") != -1) {
                    as = as.substring(0, as.indexOf("<div>") + 5) + "答案：" + as.substring(as.indexOf("<div>") + 5, as.length());
                } else {
                    as = "答案：" + as;
                }
                Spanned correctanswer = Html.fromHtml(as.replace("<br />", "").trim(), imageGetterCorrectanswer, null);
                if (!"null".equals(answer.getCorrectanswer())) {
                    holder.correctanswer_question.setText(correctanswer);
                } else {
                    holder.correctanswer_question.setText("答案：未设定");
                }
            } else {
                String[] strs = answer.getCorrectanswer().split(",");
                String result="";
                for (int s = 0; s < strs.length; s++) {
                    if (!"".equals(strs[s])) {
                        //判断strs[s]是否为数字
                        if (Character.isDigit(strs[s].charAt(0))) {
                            result+=letter[Integer.parseInt(strs[s])]+",";
                        }
                    }
                }
                if(!"".equals(result)){
                    result=result.substring(0,result.length()-1);
                }else{
                    result="未设定";
                }
                holder.correctanswer_question.setText("答案："+result);
            }

            //显示解析
            Html.ImageGetter imageGetterExplain = new Html.ImageGetter() {
                public Drawable getDrawable(String source) {
                    FinalBitmap.create(context).display(holder.iv_explain_question, IBaes.URL + source);
                    Drawable drawable = holder.iv_explain_question.getDrawable();
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    return drawable;
                }

                ;
            };
            Spanned explain = Html.fromHtml(answer.getExplain().replace("<br />", "").trim(), imageGetterExplain, null);
            if (!"null".equals(answer.getExplain())) {
                holder.explain_question.setText(explain);
            } else {
                holder.explain_question.setText("");
            }

        }
        return convertView;
    }

    class QuestionHolder {
        TextView tv_question_ino, use_question, collect_question, body_question, correctanswer_question, explain_question, cfrom_question, xx_question;
        RatingBar ratingBar;
        ImageView image_question, iv_explain_question, xximage_question, correctanswerimage_question;

        public QuestionHolder(View v) {
            tv_question_ino = (TextView) v.findViewById(R.id.tv_question_ino);
            use_question = (TextView) v.findViewById(R.id.use_question);
            collect_question = (TextView) v.findViewById(R.id.collect_question);
            xx_question = (TextView) v.findViewById(R.id.xx_question);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingBar_question);
            body_question = (TextView) v.findViewById(R.id.body_question);
            cfrom_question = (TextView) v.findViewById(R.id.cfrom_question);
            correctanswer_question = (TextView) v.findViewById(R.id.correctanswer_question);
            explain_question = (TextView) v.findViewById(R.id.explain_question);

            image_question = (ImageView) v.findViewById(R.id.image_question);
            correctanswerimage_question = (ImageView) v.findViewById(R.id.correctanswerimage_question);
            iv_explain_question = (ImageView) v.findViewById(R.id.iv_explain_question);
            xximage_question = (ImageView) v.findViewById(R.id.xximage_question);
        }
    }
}
