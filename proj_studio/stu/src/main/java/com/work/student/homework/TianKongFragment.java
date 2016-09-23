package com.work.student.homework;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.work.student.R;
import com.work.student.bean.JobDetail;
import com.work.student.tool.IBaes;
import com.work.student.tool.LogUtils;

import net.tsz.afinal.FinalBitmap;

public class TianKongFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private int mposition;

    public TianKongFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TianKongFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TianKongFragment newInstance(JobDetail param1, int param2) {
        TianKongFragment fragment = new TianKongFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    TextView tv_type, tv_des;
    JobDetail bean;
    ImageView iv_drawable;
    EditText et_answers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bean = (JobDetail) getArguments().getSerializable(ARG_PARAM1);
        final String body = bean.getBody();
        mposition = getArguments().getInt(ARG_PARAM2);
        View view = inflater.inflate(R.layout.fragment_tian_kong, container, false);
        tv_type = (TextView) view.findViewById(R.id.tv_type);
        tv_type.setText(mposition + 1 + ".(" + bean.getTitle() + ")");
        tv_des = (TextView) view.findViewById(R.id.tv_des);
        iv_drawable = (ImageView) view.findViewById(R.id.iv_drawable);
        Html.ImageGetter imageGetter = new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
//                    Log.i("test",IBaes.URL + source);
                LogUtils.debug("source=" + source);
                FinalBitmap.create(getActivity()).display(iv_drawable, IBaes.URL + source);
                Drawable drawable = iv_drawable.getDrawable();
//                    Log.i("test"," drawable.getIntrinsicWidth()="+ drawable.getIntrinsicWidth());
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            }
        };
        tv_des.setText(Html.fromHtml(body, imageGetter, null));
        et_answers = (EditText) view.findViewById(R.id.et_answers);
        et_answers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtils.debug("et_answers.getText().toString()--"+et_answers.getText().toString());
                HomeWorkActivity.mlist.get(mposition).setCommitanswer(et_answers.getText().toString() == null ? "" : et_answers.getText().toString());
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        Html.ImageGetter imageGetter = new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
//                    Log.i("test",IBaes.URL + source);
                LogUtils.debug("source=" + source);
                FinalBitmap.create(getActivity()).display(iv_drawable, IBaes.URL + source);
                Drawable drawable = iv_drawable.getDrawable();
//                    Log.i("test"," drawable.getIntrinsicWidth()="+ drawable.getIntrinsicWidth());
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            }
        };
        tv_des.setText(Html.fromHtml(bean.getBody(), imageGetter, null));
        super.onResume();
    }
}
