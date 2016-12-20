package com.llkj.llkjhttp.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by yujunlong on 2016/12/20.
 */
@Entity
public class ShopCarBean {
    @Id
    private Long id;
    private long carId;//购物车id--》商家id
    @ToMany(joinProperties = {
            @JoinProperty(name = "carId", referencedName = "ownerId")
    })
    private List<GoodsBean> mGoodsBeen;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1591731404)
    private transient ShopCarBeanDao myDao;
    @Generated(hash = 1890683673)
    public ShopCarBean(Long id, long carId) {
        this.id = id;
        this.carId = carId;
    }
    @Generated(hash = 804662094)
    public ShopCarBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getCarId() {
        return this.carId;
    }
    public void setCarId(long carId) {
        this.carId = carId;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1026598927)
    public List<GoodsBean> getMGoodsBeen() {
        if (mGoodsBeen == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            GoodsBeanDao targetDao = daoSession.getGoodsBeanDao();
            List<GoodsBean> mGoodsBeenNew = targetDao
                    ._queryShopCarBean_MGoodsBeen(carId);
            synchronized (this) {
                if (mGoodsBeen == null) {
                    mGoodsBeen = mGoodsBeenNew;
                }
            }
        }
        return mGoodsBeen;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 525610888)
    public synchronized void resetMGoodsBeen() {
        mGoodsBeen = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 75746300)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getShopCarBeanDao() : null;
    }

}
