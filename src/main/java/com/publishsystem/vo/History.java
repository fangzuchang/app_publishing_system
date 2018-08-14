/**
 *
 */
package com.publishsystem.vo;

/**
 * @author fzc69 2017年7月18日
 */
public class History {
    private Long custId;
    private String appVersion;
    private String logoUrl;
    private String appDescription;


    /**
     * @return custId
     */
    public Long getCustId() {
        return custId;
    }

    /**
     * @param custId 要设置的 custId
     */
    public void setCustId(Long custId) {
        this.custId = custId;
    }

    /**
     * @return appVersion
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * @param appVersion 要设置的 appVersion
     */
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    /**
     * @return logoUrl
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * @param logoUrl 要设置的 logoUrl
     */
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    /**
     * @return appDescription
     */
    public String getAppDescription() {
        return appDescription;
    }

    /**
     * @param appDescription 要设置的 appDescription
     */
    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    /**
     *
     */
    public History() {
        super();
        // TODO 自动生成的构造函数存根
    }

    /**
     * @param custId
     * @param appVersion
     * @param logoUrl
     * @param appDescription
     */
    public History(Long custId, String appVersion, String logoUrl, String appDescription) {
        super();
        this.custId = custId;
        this.appVersion = appVersion;
        this.logoUrl = logoUrl;
        this.appDescription = appDescription;
    }


}
