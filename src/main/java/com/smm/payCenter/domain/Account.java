package com.smm.payCenter.domain;

import java.io.Serializable;

/**
 * 后台管理人员账号 用户(操作员)
 * 
 * @ClassName: Account
 * @Description: TODO
 * @author: zhangnan
 * @date: 2015年11月9日 上午11:42:37
 */
public class Account implements Serializable {

    /**
     * @fieldName: serialVersionUID
     * @fieldType: long
     * @Description: TODO
     */
    private static final long serialVersionUID = 7238103894030507046L;

    private Integer           id;

    private String            account;

    private String            accountName;

    private String            pwd;

    private String            salt;

    private Integer           roleid;

    private String            createdat;

    private Integer           createdby;

    private String            updatedat;

    private Integer           updatedby;

    private Integer           isenabled;

    private Integer           islocked;

    private String            lastLoginTime;                          // 最后一次登录时间

    // 拓展字段
    private String            roleName;                               // 角色名称

    private String            openAccoutName;                         //开通人

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }

    public Integer getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Integer createdby) {
        this.createdby = createdby;
    }

    public String getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(String updatedat) {
        this.updatedat = updatedat;
    }

    public Integer getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(Integer updatedby) {
        this.updatedby = updatedby;
    }

    public Integer getIsenabled() {
        return isenabled;
    }

    public void setIsenabled(Integer isenabled) {
        this.isenabled = isenabled;
    }

    public Integer getIslocked() {
        return islocked;
    }

    public void setIslocked(Integer islocked) {
        this.islocked = islocked;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getOpenAccoutName() {
        return openAccoutName;
    }

    public void setOpenAccoutName(String openAccoutName) {
        this.openAccoutName = openAccoutName;
    }

    public String toString() {

        return "ID：" + this.id + ",account：" + this.account + ",pwd:" + this.pwd + ",accountName:" + this.accountName;
    }

}
