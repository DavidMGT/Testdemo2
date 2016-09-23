package com.work.student.homework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.work.student.R;
import com.work.student.bean.HomeWorkListBean;
import com.work.student.tool.DateUtil;
import com.work.student.tool.IBaes;
import com.work.student.tool.LogUtils;
import com.work.student.tool.ServecHttp;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeworkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeworkFragment extends Fragment {
    private static final String TAG = "HomeworkFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Context mContext;
    public FinalHttp mFinalHttp;
    private SwipeRefreshLayout mRefreshLayout;

    public HomeworkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeworkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeworkFragment newInstance(String param1, String param2) {
        HomeworkFragment fragment = new HomeworkFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mFinalHttp = new FinalHttp();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private ListView ll_homeworks;
    private MyhomeListAdapter myhomeListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homework, container, false);
        ll_homeworks = (ListView) view.findViewById(R.id.ll_homeworks);
        myhomeListAdapter = new MyhomeListAdapter(mList);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mRefreshLayout.setOnRefreshListener(new MyOnRefreshListener());
        ll_homeworks.setAdapter(myhomeListAdapter);
        ll_homeworks.setOnScrollListener(new MyOnScrollListener());
        ll_homeworks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeWorkListBean bean = mList.get(position);
                if (bean.getStatus() == 2) {
                    //已经完成
                    startActivity(new Intent(mContext, AnswerdSheetActivity.class));
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("tpid", bean.getTpid());
                    intent.putExtra("title", bean.getName());
                    intent.setClass(getActivity(), HomeWorkActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });
        initData();
        return view;
    }

    private boolean isRefresh;

    private class MyOnRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            LogUtils.debug("开始刷新数据了-----");
            page = 1;
            isRefresh = true;
            initData();
        }
    }

    private class MyOnScrollListener implements AbsListView.OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            Log.d(TAG, scrollState + " onScrollStateChanged");
            if (scrollState == 0 && ll_homeworks.getLastVisiblePosition() == mList.size() - 1) {
                page++;
                initData();
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            Log.d(TAG, " onScroll:   " + ll_homeworks.getLastVisiblePosition());

        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private int page = 1;
    private List<HomeWorkListBean> mList = new ArrayList<>();

    private void initData() {
        AjaxParams params = ServecHttp.createHomeWorkListParams(IBaes.userid, page + "");
        mFinalHttp.get(IBaes.GET_HOME_WORK_LIST, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                LogUtils.debug("GET_HOME_WORK_LIST=" + s);
                if (isRefresh) {
                    mList.clear();
                    isRefresh = false;
                    Toast.makeText(mContext, "刷新完成", Toast.LENGTH_LONG).show();
                }
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int status = (int) jsonObject.get("status");
                    if (status == 0) {
                        Toast.makeText(mContext, "没有更多数据", Toast.LENGTH_LONG).show();
                        mRefreshLayout.setRefreshing(false);
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List list = HomeWorkListBean.ParseData(s);
                mRefreshLayout.setRefreshing(false);
                if (list != null) {
                    mList.addAll(list);
                    myhomeListAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    class MyhomeListAdapter extends BaseAdapter {

        public MyhomeListAdapter(List<HomeWorkListBean> mList) {
        }

        @Override
        public int getCount() {
            return mList.size();
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
            HomeWorkListBean bean = mList.get(position);
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(mContext, R.layout.item_homeworklist, null);
                viewHolder.tv_homework_status = (TextView) convertView.findViewById(R.id.tv_homework_status);
                viewHolder.tv_homework_title = (TextView) convertView.findViewById(R.id.tv_homework_title);
                viewHolder.tv_homework_timing = (TextView) convertView.findViewById(R.id.tv_homework_timing);
                viewHolder.tv_homework_des = (TextView) convertView.findViewById(R.id.tv_homework_des);
                viewHolder.tv_homework_sum = (TextView) convertView.findViewById(R.id.tv_homework_sum);
                viewHolder.tv_homework_sum_num = (TextView) convertView.findViewById(R.id.tv_homework_sum_num);
                viewHolder.tv_homework_type = (TextView) convertView.findViewById(R.id.tv_homework_type);
                viewHolder.tv_homework_type1 = (TextView) convertView.findViewById(R.id.tv_homework_type1);
             /*   viewHolder.tv_homework_type1_num = (TextView) convertView.findViewById(R.id.tv_homework_type1_num);
                viewHolder.tv_homework_type2 = (TextView) convertView.findViewById(R.id.tv_homework_type2);
                viewHolder.tv_homework_type2_num = (TextView) convertView.findViewById(R.id.tv_homework_type2_num);
                viewHolder.tv_homework_type3 = (TextView) convertView.findViewById(R.id.tv_homework_type3);
                viewHolder.tv_homework_type3_num = (TextView) convertView.findViewById(R.id.tv_homework_type3_num);*/
                viewHolder.tv_homework_time = (TextView) convertView.findViewById(R.id.tv_homework_time);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            List<HomeWorkListBean.TxBean> txBeans = bean.getTx();
            StringBuilder builder = new StringBuilder();
            int sumnum = 0;//总题数
            if (txBeans == null || txBeans.size() == 0) {
                viewHolder.tv_homework_type1.setText("暂无描述");
            } else {
                for (HomeWorkListBean.TxBean txBean : txBeans) {
                    builder.append(txBean.getTitle() + "：" + txBean.getNum() + "  ");
                    sumnum += txBean.getNum();
                }
                viewHolder.tv_homework_type1.setText(builder.toString());
            }
            viewHolder.tv_homework_title.setText(bean.getName());
            viewHolder.tv_homework_des.setText(bean.getDesc());
            viewHolder.tv_homework_sum_num.setText(sumnum + "");
            viewHolder.tv_homework_type.setText("类型：" + bean.getSubject() + "  科目:" + bean.getSubjectid() + "  发布人：" + bean.getTeachername());
            viewHolder.tv_homework_time.setText("发布时间：" + DateUtil.getSimpleDateYYMMDD(bean.getStarttime()) + "      收卷时间：" + DateUtil.getSimpleDateYYMMDD(bean.getEndtime()));
            switch (bean.getStatus()) {
                case 0://未完成
                    viewHolder.tv_homework_status.setBackgroundResource(R.drawable.bg_unfinished_);
                    viewHolder.tv_homework_status.setText("未完成");
                    break;
                case 1:
                    viewHolder.tv_homework_status.setBackgroundResource(R.drawable.bg_overtime);
                    viewHolder.tv_homework_status.setText("已过期");
                    break;
                case 2:
                    viewHolder.tv_homework_status.setBackgroundResource(R.drawable.bg_finish);
                    viewHolder.tv_homework_status.setText("已完成");
                    break;
            }
            return convertView;
        }

        class ViewHolder {
            public TextView tv_homework_status, tv_homework_title, tv_homework_t, tv_homework_des, tv_homework_type, tv_homework_sum, tv_homework_sum_num, tv_homework_type1, tv_homework_type1_num, tv_homework_type2, tv_homework_type2_num, tv_homework_type3, tv_homework_type3_num,
                    tv_homework_time, tv_homework_timing;
        }
    }
}
