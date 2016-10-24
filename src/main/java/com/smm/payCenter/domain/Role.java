package com.smm.payCenter.domain;

import java.io.Serializable;

/** 角色
 * @ClassName: Role 
 * @Description: TODO
 * @author: zhangnan
 * @date: 2015年11月9日 上午11:47:14  
 */
public class Role implements Serializable{
   
    /**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 6300589030092900370L;

	private Integer id;

    private String roleName;//角色名称

    private String description;//角色描述
    
    private String createTime;//开通时间
    
    private Integer roleStatus;//状态

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getRoleStatus() {
		return roleStatus;
	}

	public void setRoleStatus(Integer roleStatus) {
		this.roleStatus = roleStatus;
	}
    
    

    
}