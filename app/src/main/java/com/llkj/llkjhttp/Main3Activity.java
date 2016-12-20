package com.llkj.llkjhttp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.llkj.llkjhttp.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Main3Activity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{

    @BindView(R.id.layFrame)
    FrameLayout mLayFrame;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;
    private List<Fragment> fragments;
    //静止,没有滚动
    public static final int SCROLL_STATE_IDLE = 0;

    //正在被外部拖拽,一般为用户正在用手指滚动
    public static final int SCROLL_STATE_DRAGGING = 1;

    //自动滚动开始
    public static final int SCROLL_STATE_SETTLING = 2;
    @Override
    protected void initView() {
        super.initView();
        assignViews();
    }

    @Override
    protected void afterSetContentView(Bundle savedInstanceState) {
        super.afterSetContentView(savedInstanceState);
        if(savedInstanceState==null){
            fragments = getFragments();
        }else{
            fragments= getSupportFragmentManager().getFragments();
        }
    }

    //添加页面
    private void assignViews(){
        //添加标签的消息数量
        BadgeItem numberBadgeItem=new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(android.R.color.holo_red_dark)
                .setText("5")
                .setHideOnSelect(true);

        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setAutoHideEnabled(false);//
        mBottomNavigationBar.clearAnimation();
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_mine_tab_checkin,"Music").setInActiveColor(R.color.colorAccent).setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.drawable.ic_mine_tab_collection,"").setInActiveColor("#282C29").setActiveColor("#32CD32"))
                .addItem(new BottomNavigationItem(R.drawable.ic_mine_tab_feed_back,"Find").setInActiveColor(R.color.colorAccent))
                .addItem(new BottomNavigationItem(R.drawable.ic_mine_tab_goods,"Favorite").setInActiveColor(R.color.colorAccent))
                .addItem(new BottomNavigationItem(R.drawable.ic_mine_tab_order,"Books").setInActiveColor(R.color.colorAccent))
                .setFirstSelectedPosition(0)
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(this); //设置监听

    }
    private List<Fragment> getFragments(){
        List<Fragment> fragments=new ArrayList<>();
        fragments.add(MyFragment.newInstance(0));
        fragments.add(MyFragment.newInstance(1));
        fragments.add(MyFragment.newInstance(2));
        fragments.add(MyFragment.newInstance(3));
        fragments.add(MyFragment.newInstance(4));
        return fragments;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main3;
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    public void onNext(Object o, int httpcode) {

    }

    @Override
    public void onTabSelected(int position) {
        if(fragments!=null){
            if(position<fragments.size()){
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                Fragment fragment=fragments.get(position);
                if(fragment.isAdded()){
                    ft.replace(R.id.layFrame,fragment);
                }else {
                    ft.add(R.id.layFrame,fragment);
                }
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {
        if(fragments!=null){
            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            Fragment fragment=fragments.get(position);
            ft.remove(fragment);
            ft.commitAllowingStateLoss();
        }
    }

    @Override
    public void onTabReselected(int position) {

    }

    public static void startActivity(Context c){
        Intent i = new Intent(c,Main3Activity.class);
        c.startActivity(i);
    }
}
