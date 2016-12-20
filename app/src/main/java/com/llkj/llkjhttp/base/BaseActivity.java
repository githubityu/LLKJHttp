package com.llkj.llkjhttp.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.MenuRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.llkj.app.AppManager;
import com.llkj.http.SubscriberOnNextListener;
import com.llkj.llkjhttp.R;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.llkj.util.KeyboardUtil.hideKeyboard;
import static com.llkj.util.KeyboardUtil.isShouldHideKeyboard;

/**
 * Created by yujunlong on 2016/11/27.
 */

public abstract  class BaseActivity extends AppCompatActivity implements SubscriberOnNextListener {
    //rxjava内存
    private CompositeSubscription mCompositeSubscription;
    protected String TAG = this.getClass().getSimpleName();
    protected Context mContext;
    private Toolbar mToolbar;
    private TextView toolbarTitle;
    private TextView amRightTv;
   private  Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView(savedInstanceState);
        setContentView(getLayoutId());
         bind = ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        mContext = this;
        afterSetContentView(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    protected void initView() {
    }
    protected void initData() {
    }
    protected void initListener() {
    }

    protected void beforeSetContentView(Bundle savedInstanceState) {
    }
    protected void afterSetContentView(Bundle savedInstanceState) {
    }

    protected abstract int getLayoutId();
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubscription();
        bind.unbind();
        AppManager.getAppManager().removeActivity(this);
        mContext = null;
    }
    //--------------------------------------------------toolbar相关设置----------------------

    //判断是否有头部
    public void setIsHasToolBar(boolean isHasToolBar){
        if (isHasToolBar) {
            toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
            amRightTv = (TextView) findViewById(R.id.am_right_tv);
            //不要改变下面三者的顺序
            beforeSetActionBar();
            afterSettingActionBar();
        }
    }

    public void beforeSetActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.mipmap.btn_back);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("");
        mToolbar.setEnabled(true);
    }

    private void afterSettingActionBar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            //隐藏标题栏
            getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        finish();
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

    //---------------------点击别处隐藏软键盘----------------------
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(mContext,v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    //---------------------


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
