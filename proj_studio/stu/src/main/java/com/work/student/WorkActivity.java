package com.work.student;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.work.student.fragment.FragmentFactory;

/**
 * 作业
 *
 * @author
 */
public class WorkActivity extends FragmentActivity implements OnClickListener {
    private ImageView isnet_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        initView();
    }

    private Button bt_homework, bt_errors;
    private ViewPager mViewpager;
    private MyVpAdapter mVpAdapter;

    public void initView() {
        bt_errors = (Button) findViewById(R.id.bt_errors);
        bt_errors.setOnClickListener(this);
        bt_homework = (Button) findViewById(R.id.bt_homework);
        bt_homework.setOnClickListener(this);
        mViewpager = (ViewPager) findViewById(R.id.vp_content);
        mVpAdapter = new MyVpAdapter(this.getSupportFragmentManager());
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bt_homework.setTextColor(getResources().getColor(R.color.green));
                        bt_homework.setBackgroundResource(R.drawable.bt_bg_left_normal);
                        bt_errors.setTextColor(getResources().getColor(R.color.white));
                        bt_errors.setBackgroundResource(R.drawable.bt_bg_right_pressed);
                        break;
                    case 1:
                        bt_errors.setTextColor(getResources().getColor(R.color.green));
                        bt_errors.setBackgroundResource(R.drawable.bt_bg_right_normal);
                        bt_homework.setTextColor(getResources().getColor(R.color.white));
                        bt_homework.setBackgroundResource(R.drawable.bt_bg_left_pressed);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewpager.setAdapter(mVpAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_errors:
                mViewpager.setCurrentItem(1);
                break;
            case R.id.bt_homework:
                mViewpager.setCurrentItem(0);
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            HomeActivity.exit(WorkActivity.this);
        }
        return true;
    }

    public class MyVpAdapter extends FragmentPagerAdapter {

        public MyVpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return FragmentFactory.createFragemntByType(FragmentFactory.TYPE_HOMEWORK);
                case 1:
                    return FragmentFactory.createFragemntByType(FragmentFactory.TYPE_ERRORS);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
