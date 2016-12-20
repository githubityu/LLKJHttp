package com.llkj.llkjhttp.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import org.greenrobot.greendao.annotation.Generated;


/**
 * Created by yujunlong on 2016/12/5.
 */
@Entity
public class GoodsBean {
    @Id
    private Long id;
    private long goodsid;
    private long ownerId;
    private String categoryId;//种类id
    private String goodsname;
    private int carnum;
    @Generated(hash = 1524347477)
    public GoodsBean(Long id, long goodsid, long ownerId, String categoryId,
            String goodsname, int carnum) {
        this.id = id;
        this.goodsid = goodsid;
        this.ownerId = ownerId;
        this.categoryId = categoryId;
        this.goodsname = goodsname;
        this.carnum = carnum;
    }
    @Generated(hash = 1806305570)
    public GoodsBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getGoodsid() {
        return this.goodsid;
    }
    public void setGoodsid(long goodsid) {
        this.goodsid = goodsid;
    }
    public long getOwnerId() {
        return this.ownerId;
    }
    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }
    public String getGoodsname() {
        return this.goodsname;
    }
    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }
    public int getCarnum() {
        return this.carnum;
    }
    public void setCarnum(int carnum) {
        this.carnum = carnum;
    }
    public String getCategoryId() {
        return this.categoryId;
    }
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

   
}
