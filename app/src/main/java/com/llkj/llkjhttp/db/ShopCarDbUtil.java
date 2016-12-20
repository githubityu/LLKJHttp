package com.llkj.llkjhttp.db;

import android.content.Context;

import com.llkj.llkjhttp.Model.ShopCarBean;
import com.llkj.llkjhttp.Model.ShopCarBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by yujunlong on 2016/12/6.
 */

public class ShopCarDbUtil {
    /**
     * 插入一条记录
     *
     * @param user
     */
    public static long insertData(Context c, ShopCarBean user) {
        ShopCarBeanDao userDao = getDataDao(c,1);
        return userDao.insert(user);
    }

    public static ShopCarBeanDao getDataDao(Context c, int type){

        return type==1?DBManager.getInstance(c).getDaoSessionWritable().getShopCarBeanDao():DBManager.getInstance(c).getDaoSessionReadable().getShopCarBeanDao();
    }
    /**
     * 插入用户集合
     *
     * @param users
     */
    public static void insertDataList(Context c,List<ShopCarBean> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        ShopCarBeanDao userDao = getDataDao(c,1);
        userDao.insertInTx(users);
    }
    /**
     * 删除一条记录
     *
     * @param user
     */
    public static void deleteData(Context c,ShopCarBean user) {
        ShopCarBeanDao userDao = getDataDao(c,1);
        userDao.delete(user);
    }
    /**
     * 更新一条记录
     *
     * @param user
     */
    public static void updateData(Context c, ShopCarBean user) {
        ShopCarBeanDao userDao = getDataDao(c,1);
        userDao.update(user);
    }
    /**
     * 查询用户列表
     */
    public  static List<ShopCarBean> queryDataList(Context c) {
        ShopCarBeanDao userDao = getDataDao(c,0);
        QueryBuilder<ShopCarBean> qb = userDao.queryBuilder();
        List<ShopCarBean> list = qb.list();
        return list;
    }


    /**
     * 查询用户列表
     */
    public static List<ShopCarBean> queryDataList(Context c,String name) {
        ShopCarBeanDao userDao = getDataDao(c,0);
        QueryBuilder<ShopCarBean> qb = userDao.queryBuilder();
        qb.where(ShopCarBeanDao.Properties.CarId.eq(name));
        List<ShopCarBean> list = qb.list();
        return list;
    }
}
