package com.llkj.llkjhttp.db;

import android.content.Context;

import com.llkj.llkjhttp.Model.GoodsBean;
import com.llkj.llkjhttp.Model.GoodsBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by yujunlong on 2016/12/6.
 */

public class GoodsDbUtil {
    /**
     * 插入一条记录
     *
     * @param user
     */
    public static void insertUser(Context c, GoodsBean user) {
        GoodsBeanDao userDao = getGoodsBeanDao(c,1);
        userDao.insert(user);
    }

    public static  GoodsBeanDao getGoodsBeanDao(Context c,int type){

        return type==1?DBManager.getInstance(c).getDaoSessionWritable().getGoodsBeanDao():DBManager.getInstance(c).getDaoSessionReadable().getGoodsBeanDao();
    }
    /**
     * 插入用户集合
     *
     * @param users
     */
    public static void insertUserList(Context c,List<GoodsBean> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        GoodsBeanDao userDao = getGoodsBeanDao(c,1);
        userDao.insertInTx(users);
    }
    /**
     * 删除一条记录
     *
     * @param user
     */
    public static void deleteUser(Context c,GoodsBean user) {
        GoodsBeanDao userDao = getGoodsBeanDao(c,1);
        userDao.delete(user);
    }
    /**
     * 更新一条记录
     *
     * @param user
     */
    public static void updateUser(Context c, GoodsBean user) {
        GoodsBeanDao userDao = getGoodsBeanDao(c,1);
        userDao.update(user);
    }
    /**
     * 查询用户列表
     */
    public  static List<GoodsBean> queryUserList(Context c) {
        GoodsBeanDao userDao = getGoodsBeanDao(c,0);
        QueryBuilder<GoodsBean> qb = userDao.queryBuilder();
        List<GoodsBean> list = qb.list();
        return list;
    }


    /**
     * 查询用户列表
     */
    public static List<GoodsBean> queryUserList(Context c,String name) {
        GoodsBeanDao userDao = getGoodsBeanDao(c,0);
        QueryBuilder<GoodsBean> qb = userDao.queryBuilder();
        qb.where(GoodsBeanDao.Properties.Goodsname.eq(name));
        List<GoodsBean> list = qb.list();
        return list;
    }
}
