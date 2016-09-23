package com.work.teacher.work;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.work.teacher.R;
import com.work.teacher.bean.QuestionnaireDetailBean;
import com.work.teacher.tool.Constants;
import com.work.teacher.tool.LogUtils;
import com.work.teacher.tool.TextUtil;

import java.util.ArrayList;
import java.util.List;

public class AddQuestionnarieStep2Activity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bean = Constants.questionnaireDetailBean;
        mtaskList = bean.getTask();
        if (mtaskList == null)
            mtaskList = new ArrayList<>();
        setContentView(R.layout.activity_add_questionnarie);
    }

    private QuestionnaireDetailBean.TaskBean taskBean;

    @Override
    public void initData() {
        mHandler.sendEmptyMessage(LOADDING_SUCESS);
    }

    private QuestionnaireDetailBean bean;
    private EditText et_question_title, et_content;
    private TextView tv_add_selections;
    private Button bt_delete_question, bt_save;
    private ListView ll_add_selection;
    private MySelectionAdatpter mySelectionAdatpter;

    @Override
    public void initView() {
        mTvTitle.setText("问卷发布");
        top_btn.setText("完成");
        top_btn.setOnClickListener(this);
        bt_delete_question = (Button) findViewById(R.id.bt_delete_question);
        tv_add_selections = (TextView) findViewById(R.id.tv_add_selections);
        tv_add_selections.setOnClickListener(this);
        bt_save = (Button) findViewById(R.id.bt_save);
        bt_save.setOnClickListener(this);
        bt_delete_question.setOnClickListener(this);
        et_question_title = (EditText) findViewById(R.id.et_question_title);
        ll_add_selection = (ListView) findViewById(R.id.ll_add_selection);
        mySelectionAdatpter = new MySelectionAdatpter(mLists);
        ll_add_selection.setAdapter(mySelectionAdatpter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_selections:
                //添加选项
                mLists.add(new QuestionnaireDetailBean.TaskBean.OptionBean());
                mySelectionAdatpter.notifyDataSetChanged();
                break;
            case R.id.bt_delete_question:
                mLists.clear();
                et_question_title.setHint("其输入题目内容");
                et_question_title.setText("");
                mySelectionAdatpter.notifyDataSetChanged();
                break;
            case R.id.bt_save:
                saveQuestion();
                break;
            case R.id.top_btn:
                //进入问卷预览
                saveQuestion();
                startActivity(new Intent(mContext, QuestionNaireEditActivity.class));
                finish();
                break;
        }
    }

    /**
     * 保存题目
     */
    public void saveQuestion() {
        QuestionnaireDetailBean.TaskBean taskBean = new QuestionnaireDetailBean.TaskBean();
        LogUtils.debug("保存题目的标题--" + et_question_title.getText().toString());
        taskBean.setTitle(et_question_title.getText().toString());
        et_question_title.setHint("其输入题目内容");
        et_question_title.setText("");
        //增加该题
        List<QuestionnaireDetailBean.TaskBean.OptionBean> listhistry = new ArrayList();
        listhistry.addAll(mLists);
        taskBean.setOption(listhistry);
        mtaskList.add(taskBean);
        bean.setTask(mtaskList);
        Constants.questionnaireDetailBean = bean;
        //清空
        mLists.clear();
        mySelectionAdatpter.notifyDataSetChanged();
    }

    private List<QuestionnaireDetailBean.TaskBean.OptionBean> mLists = new ArrayList<>();//保存每一个题目的选项
    private List<QuestionnaireDetailBean.TaskBean> mtaskList = new ArrayList<>();//保存每一个题目的集合

    private class ViewHolder {
        TextView tv_selct_position, tv_delete_selection;
        EditText et_select_content;
    }

    private class MySelectionAdatpter extends BaseAdapter {
        public MySelectionAdatpter(List<QuestionnaireDetailBean.TaskBean.OptionBean> mLists) {
        }

        @Override
        public int getCount() {
            return mLists.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final QuestionnaireDetailBean.TaskBean.OptionBean optionBean = mLists.get(position);
            LogUtils.debug("mlist" + mLists.toString());
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(mContext, R.layout.item_add_question, null);
                viewHolder.tv_delete_selection = (TextView) convertView.findViewById(R.id.tv_delete_selection);
                viewHolder.tv_selct_position = (TextView) convertView.findViewById(R.id.tv_selct_position);
                viewHolder.et_select_content = (EditText) convertView.findViewById(R.id.et_select_content);
                viewHolder.et_select_content.setTag(position);
                class MyTextWatcher implements TextWatcher {
                    public MyTextWatcher(ViewHolder holder) {
                        mHolder = holder;
                    }

                    private ViewHolder mHolder;

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s != null && !"".equals(s.toString())) {
                            int position = (Integer) mHolder.et_select_content.getTag();
                            mLists.get(position).setContent(s.toString());
                            LogUtils.debug("afterTextChanged" + position);
                        }
                    }
                }
                viewHolder.et_select_content.addTextChangedListener(new MyTextWatcher(viewHolder));
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                viewHolder.et_select_content.setTag(position);
            }
            viewHolder.tv_selct_position.setText(TextUtil.parseNumToString(position));
            viewHolder.et_select_content.setText(optionBean.getContent() == null ? "" : optionBean.getContent());
            viewHolder.tv_delete_selection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.debug("删除的下标是" + position);
                    LogUtils.debug("mlist.ize" + mLists.size());
                    mLists.remove(position);
                    mySelectionAdatpter.notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }
}
