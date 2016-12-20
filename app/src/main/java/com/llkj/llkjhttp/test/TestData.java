package com.llkj.llkjhttp.test;

import com.llkj.llkjhttp.Model.CateGoodsBean;
import com.llkj.llkjhttp.Model.CategoryBean;
import com.llkj.llkjhttp.Model.GoodsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yujunlong on 2016/12/20.
 */

public class TestData {
    public static CateGoodsBean getCGS(){
        CateGoodsBean cgb = new CateGoodsBean();
        List<GoodsBean> goodsBeen = new ArrayList<>();
        List<CategoryBean> cbs = new ArrayList<>();
        cgb.mCategoryBeen = cbs;
        cgb.mGoodsBeen = goodsBeen;
        CategoryBean cb = new CategoryBean();
        cb.categoryid = "1";
        cb.catetoryname = "苹果";
        cb.pos = goodsBeen.size();
        cbs.add(cb);
        for (int i = 0; i < 5; i++) {
            GoodsBean gb = new GoodsBean();
            gb.setGoodsname("苹果"+i);
            gb.setGoodsid(i+1);
            gb.setCategoryId(cb.categoryid);
            goodsBeen.add(gb);

        }
        cb = new CategoryBean();
        cb.categoryid = "2";
        cb.catetoryname = "香蕉";
        cb.pos = goodsBeen.size();
        cbs.add(cb);
        for (int i = 0; i < 5; i++) {
            GoodsBean gb = new GoodsBean();
            gb.setGoodsname("香蕉"+i);
            gb.setGoodsid(i+1+5);
            gb.setCategoryId(cb.categoryid);
            goodsBeen.add(gb);
        }
        cb = new CategoryBean();
        cb.categoryid = "3";
        cb.catetoryname = "橘子";
        cb.pos = goodsBeen.size();
        cbs.add(cb);
        for (int i = 0; i < 10; i++) {
            GoodsBean gb = new GoodsBean();
            gb.setGoodsname("橘子"+i);
            gb.setGoodsid(i+1+10);
            gb.setCategoryId(cb.categoryid);
            goodsBeen.add(gb);
        }
        return cgb;
    }


}
