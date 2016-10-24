package com.smm.payCenter.domain;

import java.io.Serializable;
import java.util.List;

/**菜单
 * @ClassName: Menu 
 * @Description: TODO
 * @author: zhangnan
 * @date: 2015年11月16日 上午9:43:31  
 */
public class Menu implements Serializable{
    /**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
    
	private String  menuName; //菜单名
    
	private Integer level;    //菜单级别  0：一级菜单  1：二级菜单
    
	private Integer order;    //排序序列
   
	private String  menuUrl;  //菜单url
   
	private Integer parentId; //父级菜单
   
	private String  cssClass; //组件 样式
	
	private List<Menu> childMenu;//子菜單
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

	public List<Menu> getChildMenu() {
		return childMenu;
	}

	public void setChildMenu(List<Menu> childMenu) {
		this.childMenu = childMenu;
	}
}
