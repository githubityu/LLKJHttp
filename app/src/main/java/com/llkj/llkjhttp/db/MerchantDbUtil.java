package com.llkj.llkjhttp.db;

import android.content.Context;

import com.llkj.llkjhttp.Model.GoodsBean;
import com.llkj.llkjhttp.Model.GoodsBeanDao;
import com.llkj.llkjhttp.Model.MerchantBean;
import com.llkj.llkjhttp.Model.MerchantBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by yujunlong on 2016/12/6.
 */

public class MerchantDbUtil {
    /**
     * 插入一条记录
     *
     * @param user
     */
    public static long insertUser(Context c, MerchantBean user) {
        MerchantBeanDao userDao = getGoodsBeanDao(c,1);
        return userDao.insert(user);
    }

    public static MerchantBeanDao getGoodsBeanDao(Context c, int type){

        return type==1?DBManager.getInstance(c).getDaoSessionWritable().getMerchantBeanDao():DBManager.getInstance(c).getDaoSessionReadable().getMerchantBeanDao();
    }
    /**
     * 插入用户集合
     *
     * @param users
     */
    public static void insertUserList(Context c,List<MerchantBean> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        MerchantBeanDao userDao = getGoodsBeanDao(c,1);
        userDao.insertInTx(users);
    }
    /**
     * 删除一条记录
     *
     * @param user
     */
    public static void deleteUser(Context c,MerchantBean user) {
        MerchantBeanDao userDao = getGoodsBeanDao(c,1);
        userDao.delete(user);
    }
    /**
     * 更新一条记录
     *
     * @param user
     */
    public static void updateUser(Context c, MerchantBean user) {
        MerchantBeanDao userDao = getGoodsBeanDao(c,1);
        userDao.update(user);
    }
    /**
     * 查询用户列表
     */
    public  static List<MerchantBean> queryUserList(Context c) {
        MerchantBeanDao userDao = getGoodsBeanDao(c,0);
        QueryBuilder<MerchantBean> qb = userDao.queryBuilder();
        List<MerchantBean> list = qb.list();
        return list;
    }


    /**
     * 查询用户列表
     */
    public static List<MerchantBean> queryUserList(Context c,String name) {
        MerchantBeanDao userDao = getGoodsBeanDao(c,0);
        QueryBuilder<MerchantBean> qb = userDao.queryBuilder();
        qb.where(MerchantBeanDao.Properties.Merchantname.eq(name));
        List<MerchantBean> list = qb.list();
        return list;
    }
}
