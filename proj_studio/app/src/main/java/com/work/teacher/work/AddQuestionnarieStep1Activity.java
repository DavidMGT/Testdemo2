package com.work.teacher.work;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.work.teacher.R;
import com.work.teacher.bean.QuestionnaireDetailBean;
import com.work.teacher.tool.Constants;
import com.work.teacher.tool.TextUtil;

public class AddQuestionnarieStep1Activity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questionnarie_step1);
    }

    @Override
    public void initData() {
        mHandler.sendEmptyMessage(LOADDING_SUCESS);
        Constants.questionnaireDetailBean = new QuestionnaireDetailBean();
    }

    private EditText et_question_title, et_content;

    @Override
    public void initView() {
        mTvTitle.setText("问卷发布");
        et_content = (EditText) findViewById(R.id.et_question_content);
        et_question_title = (EditText) findViewById(R.id.et_question_title);
        top_btn.setText("下一步");
        top_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = et_question_title.getText().toString();
                String content = et_content.getText().toString();
                if (TextUtil.isEmpty(title) || TextUtil.isEmpty(content)) {
                    Toast.makeText(mContext, "标题活着内容不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                Constants.questionnaireDetailBean.setWtitle(title);
                Constants.questionnaireDetailBean.setWcontent(content);
                startActivity(new Intent(mContext, QuestionNaireEditActivity.class));

            }
        });
    }

}
