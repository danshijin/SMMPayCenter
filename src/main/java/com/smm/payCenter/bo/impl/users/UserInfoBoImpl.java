package com.smm.payCenter.bo.impl.users;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.users.UserInfoBO;
import com.smm.payCenter.dao.FnlRoleRelationDao;
import com.smm.payCenter.dao.MenuDao;
import com.smm.payCenter.dao.RoleDao;
import com.smm.payCenter.dao.Account.AccountDao;
import com.smm.payCenter.domain.Account;
import com.smm.payCenter.domain.Menu;
import com.smm.payCenter.enumDef.LoginResult;
import com.smm.payCenter.util.DateUtil;

/**
 * 用户登录业务处理
 */
@Service
public class UserInfoBoImpl implements UserInfoBO {

    private static Logger      logger = Logger.getLogger(UserInfoBoImpl.class.getName());

    @Resource
    private AccountDao         accountDao;
    @Resource
    private RoleDao            roleDao;

    @Resource
    private FnlRoleRelationDao fnlRoleRelationDao;
    
    @Resource
    private MenuDao menuDao;

    public LoginResult login(Account user, HttpServletRequest request) {

        LoginResult loginResult = LoginResult.FAILD;

        try {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("account", user.getAccount());
            paramMap.put("pwd", user.getPwd());
            List<Account> listUser = this.accountDao.userLogin(paramMap);
            if (listUser != null && listUser.size() > 0) {
                if (listUser.size() > 1) {
                    loginResult = LoginResult.EXE;
                    logger.info("用户登录失败，数据库存在相同用户.");
                } else {
                	//将用户信息放进Session
                    Account account = listUser.get(0);
                    
                    //用户能看到的菜单
                   // List<Menu> menuList = fnlRoleRelationDao.getFnlMenuByRoleId(account.getRoleid());
                    
                    Map<String,Object> param = new HashMap<String, Object>();
                    
                    param.put("roleId", account.getRoleid());
                    //父菜單，菜單只有兩級
                    List<Menu> parentMenuList = this.menuDao.queryParentMenu(param);
                    
                    if (parentMenuList != null && parentMenuList.size() > 0) {
                    	for (int i = 0; i < parentMenuList.size(); i++) {
                    		Map<String,Object> map = new HashMap<String, Object>();
                        	map.put("roleId", account.getRoleid());
                        	map.put("parentId", parentMenuList.get(i).getId());
    						List<Menu>  childMenuList = this.menuDao.queryChildMenu(map);
    						if (childMenuList != null && childMenuList.size() > 0) {
    							 parentMenuList.get(i).setChildMenu(childMenuList);
							}
						}
					}
                    if (parentMenuList.size() != 0) {
                    	//權限
                    	request.getSession().setAttribute("parentMenuList", parentMenuList);
                    	
                        request.getSession().setAttribute("userInfo", account);
                      //  request.getSession().setAttribute("menuList", menuList);
                        request.getSession().setAttribute("role", roleDao.getRoleById(account.getId()));
                        
                        Map<String,Object> temp = new HashMap<String, Object>();
                        temp.put("lastLoginTime", DateUtil.doFormatDate(new Date(), ""));
                        temp.put("id", account.getId());
                        accountDao.updateLastLoginTime(temp);
                        loginResult = LoginResult.SUCC;
                        logger.info("用户登录成功，用户信息：" + listUser.get(0).toString());

                    } else { 
                        loginResult = LoginResult.NO_POWER;
                        logger.info("登录失败没有该用户没有配置权限，用户信息：" + listUser.get(0).toString());
                    }
                }
            } else {
                loginResult = LoginResult.FAILD;
                logger.info("用户登录失败，请查看具体原因.");
            }
        } catch (Exception e) {
            loginResult = LoginResult.EXE;
            logger.info("用户登录失败，程序发生异常." + e.getMessage());
        }
        return loginResult;
    }
    
   

}
