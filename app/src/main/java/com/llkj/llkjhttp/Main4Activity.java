package com.llkj.llkjhttp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.llkj.llkjhttp.Model.CateGoodsBean;
import com.llkj.llkjhttp.Model.CategoryBean;
import com.llkj.llkjhttp.Model.GoodsBean;
import com.llkj.llkjhttp.base.BaseActivity;
import com.llkj.llkjhttp.base.LlkjApplication;
import com.llkj.llkjhttp.test.TestData;

import java.util.List;

import butterknife.BindView;

import static com.llkj.util.DisplayUtil.dip2px;

/**
 * scrollToPosition  不在屏幕内容，在屏幕上面----如果要置顶的项小于findFirstVisibleItemPosition的值  该方法可以让置顶的项滚动到顶部
 *                    在屏幕下面---如果要置顶的项大于findLastVisibleItemPosition的值 该方法可以让置顶的项滚动到底部
 *                    该方法可以让不在屏幕内的项移到屏幕的顶部或底部
 *                    在屏幕内，该方法无效
 * scrollBy            相对于自身移动距离
 * 移动顶部  屏幕上面的直接可以用scrollToPosition 移动到顶部
 *           屏幕中的可以用scrollBy 移动自身距顶部的距离
 *           屏幕下面的利用scrollToPosition移动到屏幕内，然后再屏幕中的方式去处理。
 *
 * getChildAt,getTop  获取需要移动的控件   获取该控件距离顶部的高度，由于RecyclerView的复用，需要获取当前屏幕中的view 而不是postion对应的view，
 * 因为如果不在屏幕内（顶部上面一个position ，底部下面一个position是已经存在的。），该位置对应的view为null。
 *
 */

public class Main4Activity extends BaseActivity {
    @BindView(R.id.rv_content1)
    RecyclerView mRvContent1;
    @BindView(R.id.rv_content2)
    RecyclerView mRvContent2;
    private List<CategoryBean> cbs;
    private List<GoodsBean> gbs;
    private BaseQuickAdapter<CategoryBean,BaseViewHolder>  cadapter;
    private BaseQuickAdapter<GoodsBean,BaseViewHolder>  gadapter;
    private LinearLayoutManager llm1,llm2;
    private int selectPos1,selectPos2;
    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main4;
    }

    @Override
    protected void initListener() {
        super.initListener();
        mRvContent1.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

                    setPosition(i);
                    cadapter.notifyDataSetChanged();
                    //设置商品滚动的位置
                    moveToPosition(getPosition(cbs.get(i).categoryid));
            }
        });
        mRvContent2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int pos = llm2.findFirstVisibleItemPosition();
                //
                setPosition(gbs.get(pos).getCategoryId());
                cadapter.notifyDataSetChanged();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        CateGoodsBean cgs = TestData.getCGS();
        cbs = cgs.mCategoryBeen;
        gbs = cgs.mGoodsBeen;
        cadapter = new BaseQuickAdapter<CategoryBean, BaseViewHolder>(R.layout.item_category,cbs) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, CategoryBean categoryBean) {
                baseViewHolder.setText(R.id.tv_categoryname,categoryBean.catetoryname);
                baseViewHolder.getView(R.id.tv_categoryname).setSelected(categoryBean.isOk);
            }
        };
        gadapter = new BaseQuickAdapter<GoodsBean, BaseViewHolder>(R.layout.item_goods,gbs) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, GoodsBean categoryBean) {
                baseViewHolder.setText(R.id.tv_goodsname,categoryBean.getGoodsname());
            }
        };
        llm1 = new LinearLayoutManager(this);
        mRvContent1.setLayoutManager(llm1);
        llm2 = new LinearLayoutManager(this);
        mRvContent2.setLayoutManager(llm2);
        mRvContent1.setAdapter(cadapter);
        mRvContent2.setAdapter(gadapter);
        setPosition(0);
        cadapter.notifyDataSetChanged();
    }

    @Override
    public void onNext(Object o, int httpcode) {

    }

    public static void startActivity(Context c) {
        Intent i = new Intent(c, Main4Activity.class);
        c.startActivity(i);
    }
    public void setPosition(int pos){
        for (int i = 0; i < cbs.size(); i++) {
             cbs.get(i).isOk= pos==i;
        }
    }

    public void setPosition(String categoryid){
        for (int i = 0; i < cbs.size(); i++) {
              cbs.get(i).isOk=cbs.get(i).categoryid.equals(categoryid);
        }
    }
    //通过种类id获取该种类商品的位置
    public int getPosition(String categoryid){
        int pos = 0;
        for (int i = 0; i < gbs.size(); i++) {
            //如果传进来的id和商品的id一样就拿到该商品的位置
            if(gbs.get(i).getCategoryId().equals(categoryid)){
                pos = i;
                break;
            }
        }
        return pos;
    }

    //右侧
    private void moveToPosition(int n) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = llm2.findFirstVisibleItemPosition();
        int lastItem = llm2.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem ){
            //当要置顶的项在当前显示的第一个项的前面时
            mRvContent2.scrollToPosition(n);
        }else if ( n <= lastItem ){
            //当要置顶的项已经在屏幕上显示时
            int top = llm2.getChildAt(n - firstItem).getTop();
            mRvContent2.scrollBy(0, top-dip2px(this,28));
        }else{
            //当要置顶的项在当前显示的最后一项的后面时
            llm2.scrollToPosition(n);
            movePosition = n;
            needMove = true;
            LlkjApplication.sHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goonScroll();
                }
            },3);

        }
    }

    private boolean needMove=false;
    private int movePosition;

    public void goonScroll(){
        if(needMove){
            needMove = false;
            //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
            int n = movePosition - llm2.findFirstVisibleItemPosition();
            if ( 0 <= n && n < mRvContent2.getChildCount()){
                //获取要置顶的项顶部离RecyclerView顶部的距离
                int top = mRvContent2.getChildAt(n).getTop()-dip2px(this,28);
                //最后的移动
                mRvContent2.scrollBy(0, top);
            }
        }
    }
}
