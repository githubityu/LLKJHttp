package com.llkj.llkjhttp.test;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.llkj.llkjhttp.Model.GoodsBean;
import com.llkj.llkjhttp.Model.MerchantBean;
import com.llkj.llkjhttp.R;
import com.llkj.llkjhttp.base.BaseActivity;
import com.llkj.llkjhttp.db.GoodsDbUtil;
import com.llkj.llkjhttp.db.MerchantDbUtil;
import com.orhanobut.logger.Logger;


import butterknife.BindView;



public class DbTestActivity extends BaseActivity {
    private long id;
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }


    @Override
    public void onNext(Object o, int httpcode) {

    }

    public void a(View v){
        switch (v.getId()) {
            case R.id.button://添加一个商家
                MerchantBean mb = new MerchantBean();
                mb.setMerchantname("京客隆");
                mb.setMerchantid(2);
                id =   MerchantDbUtil.insertUser(this,mb);
                Logger.e("添加一个商家");
            break;
            case R.id.button2://添加一个商品
                if(id!=-1){
                    GoodsBean gb = new GoodsBean();
                    gb.setOwnerId(2);
                    gb.setGoodsname("苹果");
                    GoodsDbUtil.insertUser(this,gb);
                    Logger.e("添加一个商品");
                }
                break;
            case R.id.button3://获取商家数量
                int size = MerchantDbUtil.queryUserList(this).size();
                textView.setText("商家数量="+size);
                break;
            case R.id.button4://获取商品数量
                size = GoodsDbUtil.queryUserList(this).size();
                textView.setText("商品数量="+size);
                break;
            case R.id.button5://删除一个商家

                break;
            case R.id.button6://删除一个商品

                break;
            case R.id.button7://删除一个商品
                size =  MerchantDbUtil.queryUserList(this).get(0).getGbs().size();
                textView.setText("0商家的商品数量="+size);
                break;
        }
    }

    public static void startActivity(Context c){
        Intent i = new Intent(c,DbTestActivity.class);
        c.startActivity(i);
    }
}
