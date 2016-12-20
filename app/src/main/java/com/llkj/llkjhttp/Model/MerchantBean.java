package com.llkj.llkjhttp.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;


/**
 * Created by yujunlong on 2016/12/6.
 */
@Entity
public class MerchantBean {
    @Id
    private Long  id;
    private long merchantid;
    private String merchantname;
    @ToMany(joinProperties = {
            @JoinProperty(name = "merchantid", referencedName = "ownerId")
    })
    private List<GoodsBean> gbs;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 7223491)
    private transient MerchantBeanDao myDao;
    @Generated(hash = 552601780)
    public MerchantBean(Long id, long merchantid, String merchantname) {
        this.id = id;
        this.merchantid = merchantid;
        this.merchantname = merchantname;
    }
    @Generated(hash = 614060484)
    public MerchantBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getMerchantid() {
        return this.merchantid;
    }
    public void setMerchantid(long merchantid) {
        this.merchantid = merchantid;
    }
    public String getMerchantname() {
        return this.merchantname;
    }
    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1450559775)
    public List<GoodsBean> getGbs() {
        if (gbs == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            GoodsBeanDao targetDao = daoSession.getGoodsBeanDao();
            List<GoodsBean> gbsNew = targetDao._queryMerchantBean_Gbs(merchantid);
            synchronized (this) {
                if (gbs == null) {
                    gbs = gbsNew;
                }
            }
        }
        return gbs;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 140702351)
    public synchronized void resetGbs() {
        gbs = null;
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
    @Generated(hash = 1402025716)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMerchantBeanDao() : null;
    }



}
