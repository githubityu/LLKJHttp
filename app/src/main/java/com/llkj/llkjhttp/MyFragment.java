package com.llkj.llkjhttp;


import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.llkj.llkjhttp.base.BaseFragment;
import com.llkj.util.Constant;

import butterknife.BindView;

import static com.llkj.util.Constant.STATE_SAVE_IS_HIDDEN;

/**
 * Created by yujunlong on 2016/12/5.
 */

public class MyFragment extends BaseFragment {
    @BindView(R.id.tv_content)
    TextView mTvContent;
    private int pos;
    public static MyFragment newInstance(int pos) {
        Bundle args = new Bundle();
        args.putInt(Constant.DATA,pos);
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void userBundle(Bundle savedInstanceState) {
        super.userBundle(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Override
    protected void initView() {
        super.initView();
        pos = getArguments().getInt(Constant.DATA);
        mTvContent.setText("pos="+pos);
    }

    @Override
    protected void initData() {
        super.initData();
    }

}
