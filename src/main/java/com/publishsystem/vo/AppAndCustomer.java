/**
 *
 */
package com.publishsystem.vo;

/**
 * @author fzc69
 * 2017年7月11日
 */
public class AppAndCustomer {
    private Long id;
    private String custName;
    private String logoUrl;
    private String appLogoUrl;
    private String app_version;
    private String description;
    private String iphStoreUrl;

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

    public String getAppLogoUrl() {
        return appLogoUrl;
    }

    public void setAppLogoUrl(String appLogoUrl) {
        this.appLogoUrl = appLogoUrl;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIphStoreUrl() {
        return iphStoreUrl;
    }

    public void setIphStoreUrl(String iphStoreUrl) {
        this.iphStoreUrl = iphStoreUrl;
    }

    public AppAndCustomer() {
        // TODO 自动生成的构造函数存根
    }

    /**
     * @param custId
     * @param custName
     * @param logo_url
     * @param app_logo_url
     * @param current_version
     * @param description
     */
    public AppAndCustomer(Long custId, String custName, String logo_url,
                          String app_logo_url, String current_version, String description, String iphStoreUrl) {
        super();
        this.id = custId;
        this.custName = custName;
        this.logoUrl = logo_url;
        this.appLogoUrl = app_logo_url;
        this.app_version = current_version;
        this.description = description;
        this.iphStoreUrl = iphStoreUrl;
    }


}
