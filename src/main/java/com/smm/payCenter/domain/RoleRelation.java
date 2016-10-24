package com.smm.payCenter.domain;

import java.io.Serializable;

/** 权限
 * @ClassName: RoleRelation 
 * @Description: TODO
 * @author: zhangnan
 * @date: 2015年11月9日 上午11:49:14  
 */
public class RoleRelation implements Serializable{
    
    /**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 711296314588525522L;

	private Integer id;

    private Integer roleid;

    private String rolename;

    private String mainmenu;

    private String submenu;

    private String childmenu;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getMainmenu() {
		return mainmenu;
	}

	public void setMainmenu(String mainmenu) {
		this.mainmenu = mainmenu;
	}

	public String getSubmenu() {
		return submenu;
	}

	public void setSubmenu(String submenu) {
		this.submenu = submenu;
	}

	public String getChildmenu() {
		return childmenu;
	}

	public void setChildmenu(String childmenu) {
		this.childmenu = childmenu;
	}
    
    

    
}