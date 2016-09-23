package com.work.teacher.work.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;
import android.text.Selection;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.work.teacher.R;
import com.work.teacher.bean.ClassPersionDetails;
import com.work.teacher.bean.SubAndGrade;
import com.work.teacher.tool.IBaes;
import com.work.teacher.view.AvatarView;
import com.work.teacher.view.SwipeListView;

import net.tsz.afinal.FinalBitmap;

import java.util.List;

/**
 * 添加题目选择题答案选择
 * Created by 左丽姬 on 2016/9/9.
 */
public class EditorAnswerChoseAdapter extends BaseAdapter {
    private Context context;
    private List<SubAndGrade> choses;
    private boolean flag = false;//false 删除，true 编辑
    private SwipeListView lv;
    private View vp;

    public EditorAnswerChoseAdapter(Context context, List<SubAndGrade> choses) {
        this.context = context;
        this.choses = choses;
    }

    @Override
    public int getCount() {
        return choses.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.item_editoranswer_choeslists, null);
        TextView btn_chose_code = (TextView) convertView.findViewById(R.id.btn_chose_code);
        TextView tv_chose_name = (TextView) convertView.findViewById(R.id.tv_chose_name);
        EditText et_chose_name = (EditText) convertView.findViewById(R.id.et_chose_name);
        final ImageView iv_chose_name= (ImageView) convertView.findViewById(R.id.iv_chose_name);
        TextView item_right_del = (TextView) convertView.findViewById(R.id.item_right_txt);
        TextView item_right_edit = (TextView) convertView.findViewById(R.id.item_right_top);
        TextView item_right_no = (TextView) convertView.findViewById(R.id.item_right_add);
        RelativeLayout item_left = (RelativeLayout) convertView.findViewById(R.id.item_left);
        LinearLayout item_right = (LinearLayout) convertView.findViewById(R.id.item_right);
        LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        item_left.setLayoutParams(lp1);
        // 测试让item右侧显示不同的个数
        item_right_no.setVisibility(View.GONE);
        LayoutParams lp2 = new LayoutParams(220, LayoutParams.MATCH_PARENT);
        item_right.setLayoutParams(lp2);


        final SubAndGrade andGrade = choses.get(position);
        if (andGrade != null) {
            if (andGrade.ischeck) {
                btn_chose_code.setTextColor(context.getResources().getColor(R.color.green));
                btn_chose_code.setBackgroundResource(R.drawable.shape_rating_selbut);
            } else {
                btn_chose_code.setTextColor(context.getResources().getColor(R.color.weakgrey));
                btn_chose_code.setBackgroundResource(R.drawable.shape_rating_noselbut);
            }
            if (andGrade.isEdit) {
                et_chose_name.setVisibility(View.VISIBLE);
                tv_chose_name.setVisibility(View.GONE);
                item_right_edit.setText("确定");
                Editable etext = et_chose_name.getText();
                Selection.setSelection(etext, etext.length());
            } else {
                et_chose_name.setVisibility(View.GONE);
                tv_chose_name.setVisibility(View.VISIBLE);
            }
            btn_chose_code.setText(" " + andGrade.getId() + " ");
            Html.ImageGetter imageGette = new Html.ImageGetter() {
                public Drawable getDrawable(String source) {
//                    Log.i("test",IBaes.URL + source);
                    FinalBitmap.create(context).display(iv_chose_name, IBaes.URL + source);
                    Drawable drawable = iv_chose_name.getDrawable();
//                    Log.i("test"," drawable.getIntrinsicWidth()="+ drawable.getIntrinsicWidth());
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    return drawable;
                }

                ;
            };
            Spanned name = Html.fromHtml(andGrade.getName(), imageGette, null);
            if(andGrade.getName().equals("请输入选项内容")){
                et_chose_name.setHint(name);
                et_chose_name.setText("");
            }else{
                et_chose_name.setText(name);
            }
            tv_chose_name.setText(name);
        }

        item_right_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    flag = false;
                    mListener.onRightItemClick((View) v.getParent(), position);
                }
            }
        });

        item_right_edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mListener != null) {
                    flag = true;
                    mListener.onRightItemClick((View) v.getParent(), position);
                }
            }
        });
        et_chose_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (fouseChangeListener != null) {
                        fouseChangeListener.onFouseChangeListener((View) v.getParent(), position);
                    }
                }
            }
        });

        return convertView;
    }

    /**
     * 单击事件监听器
     */
    private onRightItemClickListener mListener = null;

    public void setOnRightItemClickListener(final SwipeListView lv) {
        mListener = new onRightItemClickListener() {
            @Override
            public void onRightItemClick(View v, int position) {
                // TODO Auto-generated method stub
                SubAndGrade message = (SubAndGrade) choses.get(position);
                EditorAnswerChoseAdapter.this.lv = lv;
                EditorAnswerChoseAdapter.this.vp = v;
                if (flag) {
                    //编辑选项
                    for (SubAndGrade s:choses ) {//清除其他正在编辑的数据
                        if(s!=null&&!s.getId().equals(message.getId())){
                            s.isEdit=false;
                        }
                    }
                    if (message.isEdit) {//确定保存数据
                        message.isEdit = false;
                    } else {
                        message.isEdit = true;
                    }
                }else
                    choses.remove(message);
                EditorAnswerChoseAdapter.this.notifyDataSetChanged();
                IBaes.setGroupHeight(lv);
                lv.hiddenRight((View) vp.getParent(), true);

            }
        };
    }

    public interface onRightItemClickListener {
        void onRightItemClick(View v, int position);
    }

    public interface onFouseChangeListener {
        void onFouseChangeListener(View v, int position);
    }

    private onFouseChangeListener fouseChangeListener = null;

    public void onFouse() {
        fouseChangeListener = new onFouseChangeListener() {
            @Override
            public void onFouseChangeListener(View v, int position) {
                EditText ed = (EditText) v.findViewById(R.id.et_chose_name);
                SubAndGrade message = (SubAndGrade) choses.get(position);
                message.setName(ed.getText().toString());
            }
        };
    }

}
