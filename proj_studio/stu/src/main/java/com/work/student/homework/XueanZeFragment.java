package com.work.student.homework;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.work.student.R;
import com.work.student.bean.HomeWorkDetailBean;
import com.work.student.bean.JobDetail;
import com.work.student.tool.IBaes;
import com.work.student.tool.LogUtils;
import com.work.student.tool.TextUtil;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 选择题
 */
public class XueanZeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Html.ImageGetter imageGetter;

    public XueanZeFragment() {
    }

    public static XueanZeFragment newInstance(JobDetail param1, int position) {
        XueanZeFragment fragment = new XueanZeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    private JobDetail bean;
    private int mposition;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

    }

    private TextView tv_type, tv_fenshu, tv_des, tv_content;
    private ListView ll_selctions;
    private MySelectionAdapter mAdapter;
    private ImageView iv_drawable;
    PhotoViewAttacher mAttacher;
    private int mselectPosition = -1;
    private List<String> mduoxuanSelections = new ArrayList<>();//记录多选
    public FinalHttp mFinalHttp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFinalHttp = new FinalHttp();
        bean = (JobDetail) getArguments().getSerializable(ARG_PARAM1);
        mposition = getArguments().getInt(ARG_PARAM2);
        LogUtils.debug("positon=" + mposition + ":bean=" + bean);
        View view = inflater.inflate(R.layout.fragment_xuean_ze, container, false);
        tv_type = (TextView) view.findViewById(R.id.tv_type);
        tv_type.setText(mposition + 1 + ".(" + bean.getTitle() + ")");
        tv_fenshu = (TextView) view.findViewById(R.id.tv_fenshu);
        tv_fenshu.setText("(" + bean.getScore() + "/" + bean.getSum() + ")");
        final String body = bean.getBody();
        iv_drawable = (ImageView) view.findViewById(R.id.iv_drawable);
        //   mAttacher = new PhotoViewAttacher(iv_drawable);
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
        iv_drawable.setVisibility(View.GONE);
        tv_content = (TextView) view.findViewById(R.id.tv_des);
        tv_content.setText(Html.fromHtml(bean.getBody(), imageGetter, null));
        ll_selctions = (ListView) view.findViewById(R.id.ll_selctions);
        ll_selctions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (bean.getQuestype() == HomeWorkDetailBean.TYPE_DANXUAN) {
                    //单选
                    if (mselectPosition == position) {
                        mselectPosition = -1;
                    } else {
                        mselectPosition = position;
                    }
                    HomeWorkActivity.mlist.get(mposition).setSelectPosition(mselectPosition);
                    mAdapter.notifyDataSetChanged();
                } else {
                    //选择题的多选
                    if (mduoxuanSelections.contains(position + "")) {
                        mduoxuanSelections.remove(position + "");
                    } else {
                        mduoxuanSelections.add(position + "");
                    }
                    HomeWorkActivity.mlist.get(mposition).setSelectionPositions(mduoxuanSelections);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        mAdapter = new MySelectionAdapter(bean.getOption());
        ll_selctions.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
        tv_content.setText(Html.fromHtml(bean.getBody(), imageGetter, null));
    }

    class MySelectionAdapter extends BaseAdapter {
        private List<JobDetail.OptionBean> options;

        public MySelectionAdapter(List<JobDetail.OptionBean> option) {
            options = option;
        }

        @Override
        public int getCount() {
            if (options == null) {
                return 0;
            }
            return options.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            JobDetail.OptionBean option = options.get(position);
            TextView iv_selelct_state = null, tv_select_content = null;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.item_question_child, null);
            }
            iv_selelct_state = (TextView) convertView.findViewById(R.id.iv_selelct_state);
            tv_select_content = (TextView) convertView.findViewById(R.id.tv_select_content);
            iv_selelct_state.setText(TextUtil.parseIntergerToABC(position));
            tv_select_content.setText(option.getOption());
            if (mselectPosition == position) {
                iv_selelct_state.setTextColor(0XFFFFFFFF);//白色
                iv_selelct_state.setBackgroundResource(R.drawable.drawable_circle_green_bg);
                tv_select_content.setTextColor(0Xff4AC4BC);
            } else {
                iv_selelct_state.setTextColor(0XFFCBCBCB);//
                iv_selelct_state.setBackgroundResource(R.drawable.drawable_circle_whight_bg);
                tv_select_content.setTextColor(0XffCBCBCB);
            }
            if (bean.getQuestype() == HomeWorkDetailBean.TYPE_DUOXUAN && mduoxuanSelections != null && mduoxuanSelections.size() > 0) {
                //多选
                if (bean.getSelectionPositions().contains(position + "")) {
                    iv_selelct_state.setTextColor(0XFFFFFFFF);//白色
                    iv_selelct_state.setBackgroundResource(R.drawable.drawable_circle_green_bg);
                    tv_select_content.setTextColor(0Xff4AC4BC);
                } else {
                    iv_selelct_state.setTextColor(0XFFCBCBCB);//
                    iv_selelct_state.setBackgroundResource(R.drawable.drawable_circle_whight_bg);
                    tv_select_content.setTextColor(0XffCBCBCB);
                }
            }
            return convertView;
        }
    }
}
