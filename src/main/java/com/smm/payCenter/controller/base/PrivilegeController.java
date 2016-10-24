package com.smm.payCenter.controller.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smm.payCenter.bo.base.PrivilegeBo;
import com.smm.payCenter.domain.RoleRelation;

/** 资源权限
 * @ClassName: PrivilegeController 
 * @Description: TODO
 * @author: zhangnan
 * @date: 2015年11月9日 下午12:48:12  
 */
@Controller
@RequestMapping("/privilege")
public class PrivilegeController implements Serializable{

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(PrivilegeController.class.getName());
	
	@Resource
	private PrivilegeBo privilegeBo;
	
	/** session 中用户所具有的的菜单访问权限
	 * @Title: menuData 
	 * @Description: TODO
	 * @param request
	 * @return
	 * @return: List<RoleRelation>
	 */
	@RequestMapping("/ajaxLeft")
	@ResponseBody
	private List<RoleRelation> menuData(HttpServletRequest request) {
		List<RoleRelation> menuDatalist = null;
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			//登录时存到session里，此处是为了模拟数据
			HttpSession session = request.getSession();
			session.setAttribute("id", 1);
			
			int accountId = (int) session.getAttribute("id");
			param.put("accountId", accountId);
			menuDatalist = this.privilegeBo.menuData(param);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return menuDatalist;

	}
	
	

}
