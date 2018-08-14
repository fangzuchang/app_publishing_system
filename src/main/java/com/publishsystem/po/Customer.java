/**
 *
 */
package com.publishsystem.po;

import java.sql.Timestamp;

/**
 * @author fzc69
 * 2017年7月12日
 */
public class Customer {
    private Long id;
    private String custName;
    private String logoUrl;
    private String appLogoUrl;
    private Timestamp createTime;
    private Timestamp updateTime;


    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    /**
     * @return appLogoUrl
     */
    public String getAppLogoUrl() {
        return appLogoUrl;
    }

    /**
     * @param appLogoUrl 要设置的 appLogoUrl
     */
    public void setAppLogoUrl(String appLogoUrl) {
        this.appLogoUrl = appLogoUrl;
    }

    public Customer() {
        super();
        // TODO 自动生成的构造函数存根
    }

    /**
     * @param id
     * @param custName
     * @param logoUrl
     * @param appLogoUrl
     * @param createTime
     */
    public Customer(Long id, String custName, String logoUrl,
                    String appLogoUrl, Timestamp createTime) {
        super();
        this.id = id;
        this.custName = custName;
        this.logoUrl = logoUrl;
        this.appLogoUrl = appLogoUrl;
        this.createTime = createTime;
    }

    /**
     * @param id
     * @param custName
     * @param logoUrl
     * @param appLogoUrl
     * @param updateTime
     */
    public Customer(Long id, String custName, String logoUrl,
                    Timestamp updateTime, String appLogoUrl) {
        super();
        this.id = id;
        this.custName = custName;
        this.logoUrl = logoUrl;
        this.appLogoUrl = appLogoUrl;
        this.updateTime = updateTime;
    }

    public Customer(Long id, String custName, String logoUrl) {
        super();
        this.id = id;
        this.custName = custName;
        this.logoUrl = logoUrl;
    }

}
