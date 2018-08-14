/**
 *
 */
package com.publishsystem.vo;

/**
 * @author fzc69
 * 2017年7月17日
 */
public class Download {
    private Long id;//客户id
    private String logoUrl;
    private String appLogoUrl;
    private String app_version;
    private String description;
    private String android_url;
    private String iphone_url;
    private String iphStoreUrl;
    private String plistUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAndroid_url() {
        return android_url;
    }

    public void setAndroid_url(String android_url) {
        this.android_url = android_url;
    }

    public String getIphone_url() {
        return iphone_url;
    }

    public void setIphone_url(String iphone_url) {
        this.iphone_url = iphone_url;
    }

    public String getIphStoreUrl() {
        return iphStoreUrl;
    }

    public void setIphStoreUrl(String iphStoreUrl) {
        this.iphStoreUrl = iphStoreUrl;
    }

    public String getPlistUrl() {
        return plistUrl;
    }

    public void setPlistUrl(String plistUrl) {
        this.plistUrl = plistUrl;
    }

    public Download() {
    }

    public Download(Long id, String logoUrl, String appLogoUrl, String app_version, String description, String android_url, String iphone_url, String iphStoreUrl, String plistUrl) {
        this.id = id;
        this.logoUrl = logoUrl;
        this.appLogoUrl = appLogoUrl;
        this.app_version = app_version;
        this.description = description;
        this.android_url = android_url;
        this.iphone_url = iphone_url;
        this.iphStoreUrl = iphStoreUrl;
        this.plistUrl = plistUrl;
    }
}
