/**
 *
 */
package com.publishsystem.po;

import java.sql.Timestamp;

/**
 * @author fzc69 2017年7月12日
 */
public class App {
    private Long app_id;
    private Long custId;
    private String app_version;
    private String android_url;
    private String iphone_url;
    private String qrcode;
    private String description;
    private Timestamp createTimeDate;
    private Timestamp updateTime;
    private String plistUrl;
    private String iphPackageName;
    private String iphStoreUrl;
    private String publishStyle;


    /**
     * @return publishStyle
     */
    public String getPublishStyle() {
        return publishStyle;
    }

    /**
     * @param publishStyle 要设置的 publishStyle
     */
    public void setPublishStyle(String publishStyle) {
        this.publishStyle = publishStyle;
    }

    /**
     * @return iphStoreUrl
     */
    public String getIphStoreUrl() {
        return iphStoreUrl;
    }

    /**
     * @param iphStoreUrl 要设置的 iphStoreUrl
     */
    public void setIphStoreUrl(String iphStoreUrl) {
        this.iphStoreUrl = iphStoreUrl;
    }

    /**
     * @return iphPackageName
     */
    public String getIphPackageName() {
        return iphPackageName;
    }

    /**
     * @param iphPackageName 要设置的 iphPackageName
     */
    public void setIphPackageName(String iphPackageName) {
        this.iphPackageName = iphPackageName;
    }

    public String getPlistUrl() {
        return plistUrl;
    }

    public void setPlistUrl(String plistUrl) {
        this.plistUrl = plistUrl;
    }

    public Timestamp getCreateTimeDate() {
        return createTimeDate;
    }

    public void setCreateTimeDate(Timestamp createTimeDate) {
        this.createTimeDate = createTimeDate;
    }

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

    public Long getApp_id() {
        return app_id;
    }

    public void setApp_id(Long app_id) {
        this.app_id = app_id;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    /**
     * @return android_url
     */
    public String getAndroid_url() {
        return android_url;
    }

    /**
     * @param android_url 要设置的 android_url
     */
    public void setAndroid_url(String android_url) {
        this.android_url = android_url;
    }

    /**
     * @return iphone_url
     */
    public String getIphone_url() {
        return iphone_url;
    }

    /**
     * @param iphone_url 要设置的 iphone_url
     */
    public void setIphone_url(String iphone_url) {
        this.iphone_url = iphone_url;
    }

    /**
     * @return qrcode
     */
    public String getQrcode() {
        return qrcode;
    }

    /**
     * @param qrcode 要设置的 qrcode
     */
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 要设置的 description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    /**
     *
     */
    public App() {
        super();
        // TODO 自动生成的构造函数存根
    }

    /**
     * @param app_id
     * @param custId
     * @param app_version
     * @param android_url
     * @param iphone_url
     * @param qrcode
     * @param description
     */
    public App(Long app_id, Long custId, String app_version,
               String android_url, String iphone_url,
               String qrcode, String description, String iphPackageName, String iphStoreUrl) {
        super();
        this.app_id = app_id;
        this.custId = custId;
        this.app_version = app_version;
        this.android_url = android_url;
        this.iphone_url = iphone_url;
        this.qrcode = qrcode;
        this.description = description;
        this.iphPackageName = iphPackageName;
        this.iphStoreUrl = iphStoreUrl;
    }

    public App(Long app_id, Long custId, String app_version,
               String android_url, String iphone_url,
               String qrcode, String description, String iphPackageName, String iphStoreUrl, Timestamp createTime, Timestamp updateTime) {
        super();
        this.app_id = app_id;
        this.custId = custId;
        this.app_version = app_version;
        this.android_url = android_url;
        this.iphone_url = iphone_url;
        this.qrcode = qrcode;
        this.description = description;
        this.iphPackageName = iphPackageName;
        this.iphStoreUrl = iphStoreUrl;
        this.createTimeDate = createTime;
        this.updateTime = updateTime;
    }

    /**
     * @param app_id
     * @param custId
     * @param app_version
     * @param android_url
     * @param iphone_url
     * @param qrcode
     * @param description
     * @param createTimeDate
     */
    public App(Long app_id, Long custId, String app_version,
               String android_url, String iphone_url,
               String qrcode, String description, Timestamp createTimeDate, String plistUrl, String iphPackageName, String qrCode, String iphStoreUrl, String publishStyle) {
        super();
        this.app_id = app_id;
        this.custId = custId;
        this.app_version = app_version;
        this.android_url = android_url;
        this.iphone_url = iphone_url;
        this.qrcode = qrcode;
        this.description = description;
        this.createTimeDate = createTimeDate;
        this.plistUrl = plistUrl;
        this.iphPackageName = iphPackageName;
        this.qrcode = qrCode;
        this.iphStoreUrl = iphStoreUrl;
        this.publishStyle = publishStyle;
    }

    /**
     * @param app_id
     * @param app_version
     * @param android_url
     * @param iphone_url
     * @param description
     * @param updateTime
     */
    public App(Long app_id, String app_version, String android_url,
               String iphone_url, String description, Timestamp updateTime, String iphPackageName, String iphStoreUrl, String publishStyle, String plistUrl) {
        super();
        this.app_id = app_id;
        this.app_version = app_version;
        this.android_url = android_url;
        this.iphone_url = iphone_url;
        this.description = description;
        this.updateTime = updateTime;
        this.iphPackageName = iphPackageName;
        this.iphStoreUrl = iphStoreUrl;
        this.publishStyle = publishStyle;
        this.plistUrl = plistUrl;
    }


}
