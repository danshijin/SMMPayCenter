package com.smm.payCenter.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.smm.payCenter.bo.users.UserInfoBO;
import com.smm.payCenter.domain.Account;
import com.smm.payCenter.enumDef.LoginResult;

/**
 * @author zengshihua 用户登录控制器 先简单匹配，后续采用Spring Security
 */
@Controller
public class UserInfoController {

    @Resource
    private UserInfoBO userInfoBO;
    
    private Logger logger = Logger.getLogger(UserInfoController.class);
    
    /**
     * 登录页
     * 
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request) {

        ModelAndView view = new ModelAndView("login");
        return view;
    }

    /**
     * 实现用户登录
     * 
     * @param request
     * @param user
     * @param mode
     * @return
     */
    @RequestMapping("/user/userLogin")
    @ResponseBody
    public Map<String, Object> userLogin(HttpServletRequest request, Account user, String vCode, Model mode) {

        String code = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        Map<String, Object> resultMap = new HashMap<>();
        LoginResult loginResult = LoginResult.FAILD;
        if (code != null) {

            if (StringUtils.isNotBlank(vCode)) {
                if (code.equals(vCode)) {
                    //登录时，把用户主要信息存到session中
                    loginResult = userInfoBO.login(user, request);

                } else {
                    loginResult = LoginResult.VCODE_ERROR;
                }

            } else {
                loginResult = LoginResult.VCODE_NULL;
            }
        } else {
            loginResult = LoginResult.VCODE_INVALID;
        }
        resultMap.put("code", loginResult.getCode());
        resultMap.put("msg", loginResult.getMessage());

        return resultMap;
    }

    /**
     * 获取用户登录信息
     * 
     * @param request
     * @param mode
     * @return
     */
    @RequestMapping("/user/getUserLoginInfo")
    @ResponseBody
    public Map<String, Object> getUserLoginInfo(HttpServletRequest request, Model mode) {
        Map<String, Object> resultMap = new HashMap<>();
        try {

            Account user = (Account) request.getSession().getAttribute("userInfo");
            if (user != null) {
                resultMap.put("userName", user.getAccountName());
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return resultMap;
    }

    /**
     * 登录成功
     */
    @RequestMapping("/user/loginSucc")
    public String loginSucc(HttpServletRequest request, Model mode) {

        //		Map<String, Object> param=new HashMap<>();
        //		param.put("warehouseName", "");
        //		List<Warehouse> warehouse=iApplyBO.queryWarehouse(param);
        //		if(warehouse.size()>0){
        //			
        //			if(request.getSession().getAttribute("warehouseNO")==null){
        //				request.getSession().setAttribute("warehouseNO",warehouse.get(0).getCode());
        //				mav.addObject("depotno", warehouse.get(0).getCode());
        //			}else{
        //				mav.addObject("depotno", request.getSession().getAttribute("warehouseNO"));
        //			}
        //			
        //		}
        //返回页面处理
        return "redirect:/home/welcome.do";
    }
    
    @RequestMapping("/home/welcome")
    public ModelAndView welcome(){
    	return new ModelAndView("/home/welcome");
    }

    /**
     * 实现用户退出登录
     */
    @RequestMapping("/user/loginOut")
    public String loginOut(HttpServletRequest request, Model mode) {

        //清空session
        request.getSession().invalidate();

        return "redirect:/login.do";
    }
    

	@RequestMapping(value = "/user/noUrlPermission")
	public void saveChangeInfo(HttpServletRequest request, HttpServletResponse response) {
		String rltMsg = "";
		Account user = (Account) request.getSession().getAttribute("userInfo");
		logger.warn("用户：" + user.getAccount() + "，尝试非法访问，URL："+ request.getAttribute("illegalUrl"));
		//返回处理结果
		rltMsg = "Warning! Access Denied!";
		rltMsg = "alert('"+rltMsg+"');window.history.back();";   //返回处理结果
//		document.location.reload();
		outMessage(response, rltMsg); // 返回处理结果
	}
    
	 /** 返回js处理结果信息
	 * @param response
	 * @param msg 可执行的js代码
	 */
	private void outMessage(HttpServletResponse response, String msg){
		
		msg = "<script>" + msg + "</script>";
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}
    
}
