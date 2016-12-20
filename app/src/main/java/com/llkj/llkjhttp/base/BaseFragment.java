package com.llkj.llkjhttp.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.llkj.llkjhttp.R;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.subscriptions.CompositeSubscription;

import static com.llkj.util.Constant.STATE_SAVE_IS_HIDDEN;

/**
 * Created by yujunlong on 2016/12/5.
 */

public abstract class BaseFragment extends Fragment {
    protected Context mActivity;
    //是否可见状态
    private boolean isVisible;
    //View已经初始化完成
    private boolean isPrepared;
    //是否第一次加载完
    private boolean isFirstLoad = true;
    private CompositeSubscription mCompositeSubscription;
    protected String TAG = this.getClass().getSimpleName();
    private Toolbar mToolbar;
    private TextView toolbarTitle;
    private TextView amRightTv;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isFirstLoad = true;
        this.mActivity = getActivity();
        //绑定View
        View view = inflater.inflate(getLayoutId(), container, false);
        bind =ButterKnife.bind(this, view);
        userBundle(savedInstanceState);
        isPrepared = true;
        initView();
        initData();
        //懒加载
        lazyLoad();
        initListener();
        return view;
    }

    protected void initView() {
    }
    protected void initData() {
    }
    protected void initListener() {
    }
    protected void userBundle(Bundle savedInstanceState) {
    }
    protected abstract int getLayoutId();
    @Override
    public void onDestroy() {
        super.onDestroy();
        unSubscription();
        bind.unbind();
    }
    //这个是fragment在viewpager中的
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisible = true;
            onVisible();
        }else {
            isVisible = false;
            onInvisible();
        }
    }

    //这个是不在viewpager中的隐藏和显示
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            isVisible = true;
            onVisible();
        }else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible(){
        lazyLoad();
    }

    protected void onInvisible(){}

    protected void lazyLoad(){
        if(!isPrepared || !isVisible || !isFirstLoad) return;
        isFirstLoad = false;
        lazyLoadData();
    }
    protected  void lazyLoadData(){

    };
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }
    //--------------------------------------------------toolbar相关设置----------------------

    //判断是否有头部
    public void setIsHasToolBar(boolean isHasToolBar){
        if (isHasToolBar) {
            toolbarTitle = (TextView) getView().findViewById(R.id.toolbar_title);
            amRightTv = (TextView)getView().findViewById(R.id.am_right_tv);
            //不要改变下面三者的顺序
            beforeSetActionBar();
            afterSettingActionBar();
        }
    }

    public void beforeSetActionBar() {
        mToolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.mipmap.btn_back);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("");
        mToolbar.setEnabled(true);
    }
    private void afterSettingActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            //隐藏标题栏
            ((AppCompatActivity) getActivity()). getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftDo();
            }
        });
    }

    //
    public void setToolBar(int title,boolean isshowL,int wzidL,int pidL,boolean isshowR,int wzidR,int pidR){
        setIsHasToolBar(true);
        setActivityTitle(title);
        if (isshowL) {
            if (wzidL != -1) {
                mToolbar.setTitle(wzidL);
            }
            if (pidL != -1) {
                setLeftImg(pidL);
            }

        }
        if (isshowR) {
            if (wzidR != -1) {
                setRight(wzidR);
            }
            if (pidR != -1) {
                setRightImg(pidL);
            }

        }
    }

    public void setToolBar(String title,boolean isshowL,String wzidL,int pidL,boolean isshowR,String wzidR,int pidR){
        setIsHasToolBar(true);
        setActivityTitle(title);
        if (isshowL) {
            if (!TextUtils.isEmpty(wzidL)) {
                mToolbar.setTitle(wzidL);
            }
            if (pidL!=-1) {
                setLeftImg(pidL);
            }
        }
        if (isshowR) {
            if (!TextUtils.isEmpty(wzidR)) {
                setRight(wzidR);
            }
            if (pidR != -1) {
                setRightImg(pidL);
            }
            amRightTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rightDo();
                }
            });

        }
    }
    //默认finis 否则重写该方法
    protected void leftDo() {

    }
    protected void rightDo() {
    }

    public void setLeftImg(@DrawableRes int imgId) {
        mToolbar.setNavigationIcon(imgId);
    }

    public void setActivityTitle(String text) {
        toolbarTitle.setText(text);
    }

    public void setActivityTitle(@StringRes int textId) {
        toolbarTitle.setText(textId);
    }

    public void setRight(String text) {
        amRightTv.setText(text);
    }

    public void setRight(@StringRes int textId) {
        amRightTv.setText(textId);
    }

    public void setRightImg(@DrawableRes int imgId) {
        amRightTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, imgId, 0);
    }


    public CompositeSubscription getCompositeSubscription(){
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        return mCompositeSubscription;
    }
    //界面退出等位置需要解绑观察者的位置，防止Rx内存溢出
    public void unSubscription() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
            Logger.e(TAG+"mCompositeSubscription.unsubscribe()");
        }
    }
}
