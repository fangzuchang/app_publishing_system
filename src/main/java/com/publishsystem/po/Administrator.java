package com.publishsystem.po;

import java.sql.Timestamp;

/**
 * @author fzc69
 * 2017年7月10日
 */
public class Administrator {
    private Long id;
    private String name;
    private String realName;
    private String passWord;
    private Timestamp createTime;
    private Timestamp updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 要设置的 name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return realName
     */
    public String getRealName() {
        return realName;
    }

    /**
     * @param realName 要设置的 realName
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * @return passWord
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * @param passWord 要设置的 passWord
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * @return createTime
     */
    public Timestamp getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime 要设置的 createTime
     */
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    /**
     * @return updateTime
     */
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime 要设置的 updateTime
     */
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    /**
     *
     */
    public Administrator() {
        // TODO 自动生成的构造函数存根
    }

}
