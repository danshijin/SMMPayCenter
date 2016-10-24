package com.smm.payCenter.common.filter;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.smm.payCenter.applicationListener.InstantiationTracingBeanPostProcessor;
import com.smm.payCenter.domain.Account;
import com.smm.payCenter.domain.Menu;

/**
 * 用户操作过来器
 * 
 * @author zengshihua
 */
public class UserAuthFilter implements Filter {

    /**
     * 登录地址
     */
    private String   loginReqURL;

    /**
     * 首页地址
     */
    private String   indexReqURL;

    /**
     * 不需要登录也可访问的URL
     */
    private String[] matchURLs;
    /**
     * 需要登录后才能访问的地址
     */
    private String[] needLoginURLs;

    //不进页面直接登录key
    private String   noPageLoginKey;
    //	不进页面直接登录url
    private String   noPageLoginUrl;
    
    // 没有访问权限url
    private String noUrlPermissionUrl;

    /**
     * 定义其他规则
     */

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        //登录地址
        loginReqURL = filterConfig.getInitParameter("loginReqURL");
        //不需要登录也可访问的URL,例如登录、获取验证码
        matchURLs = initParameterFromConfig("matchURLs", filterConfig);

        indexReqURL = filterConfig.getInitParameter("indexReqURL");

        noPageLoginKey = filterConfig.getInitParameter("noPageLoginKey");

        noPageLoginUrl = filterConfig.getInitParameter("noPageLoginUrl");
        
        noUrlPermissionUrl = filterConfig.getInitParameter("noUrlPermissionUrl");

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Account user = getSessionUser(httpRequest);
        String reqURL = httpRequest.getRequestURI();

        //登录后用户不能访问登录页，直接跳转至首页
        if (reqURL.indexOf(loginReqURL) > 0) {
            if (user != null) { // 直接重定向至登录页面
                httpResponse.sendRedirect(httpRequest.getContextPath() + indexReqURL);
                return;
            }
        }
        //无需去页面进行登录
        if (noPageLoginKey.equals((String) request.getParameter("noPageLoginKey"))) {
            String uid = (String) request.getParameter("uid");
            String pwd = (String) request.getParameter("pwd");
            String destUrl = (String) request.getParameter("destUrl");
            if (StringUtils.isBlank(uid) || StringUtils.isBlank(pwd) || StringUtils.isBlank(destUrl)) {
            } else {
                // 跳转到登录方法 
                httpResponse.sendRedirect(httpRequest.getContextPath() + noPageLoginUrl + "?uid=" + uid + "&pwd=" + pwd
                        + "&destUrl=" + destUrl);
                return;
            }
        }
        
        //如果用户没有进行操作，将直接跳转到登录页面 
        if (!isMatchReq(matchURLs, new StringBuffer(reqURL))) {
            if (user == null) {
            	// 
                httpResponse.sendRedirect(httpRequest.getContextPath() + loginReqURL);
                return;
            }
        }
    	//用户没有该url权限
    	if(user != null && !hasPermission(httpRequest, user, reqURL)){
    		httpRequest.setAttribute("illegalUrl", reqURL);
            httpRequest.getRequestDispatcher(noUrlPermissionUrl).forward(httpRequest, httpResponse);
            return;
        }
        
        
        //TODO 定义其他规则..........
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }
    
    /** 判断传入参数是否需要进行权限控制
     * @param reqURL
     * @return
     */
    private boolean doesUrlNeedPermissionControl(String reqURL){
    	boolean need = Boolean.FALSE;
    	List<String> menuUrl = InstantiationTracingBeanPostProcessor.getMenuUrlList();
    	for(String url : menuUrl){
    		if(reqURL.contains(url)){
    			need = Boolean.TRUE;
    			break;
    		}
    	}
    	return need;
    }
    
    
    /** 判断登录用户有没有访问reqUrl的权限
     * @param httpRequest
     * @param user
     * @param reqURL
     * @return
     */
    private boolean hasPermission(HttpServletRequest httpRequest, Account user, String reqURL){
    	HttpSession session = httpRequest.getSession(Boolean.FALSE);
    	// 如果会话未创建，或者不需要对访问url进行权限控制
    	if(session == null || !doesUrlNeedPermissionControl(reqURL)){
    		return Boolean.TRUE;
    	}
        boolean hasPermission = Boolean.FALSE;
        // 在用户的权限列表中查找是否存在欲访问url
    	@SuppressWarnings("unchecked")
		List<Menu> parentMenuList = (List<Menu>) session.getAttribute("parentMenuList");
    	if (parentMenuList != null) {
        	for(Menu parentMenu : parentMenuList){
        		List<Menu> childMenuList = parentMenu.getChildMenu();
        		if (childMenuList != null) {
        			for(Menu childMenu: childMenuList){
            			if(reqURL!=null && reqURL.contains(childMenu.getMenuUrl())){
            				hasPermission = Boolean.TRUE;
            				break;
            			}
            		}
        		}
        	}
    	}
        return hasPermission;
    }

    public String[] initParameterFromConfig(String parameterName, FilterConfig filterConfig) {

        String Urls = filterConfig.getInitParameter(parameterName);

        return Urls.split(";");
    }

    /**
     * 获取登录实体
     * 
     * @param httpRequest
     * @return
     */
    public Account getSessionUser(HttpServletRequest httpRequest) {

        Account user = null;
        if (httpRequest.getSession().getAttribute("userInfo") != null) {
            user = (Account) httpRequest.getSession().getAttribute("userInfo");
        }

        return user;
    }

    /**
     * 是否匹配请求
     * 
     * @param urls
     * @param reqURL
     * @return
     */
    public boolean isMatchReq(String urls[], StringBuffer reqURL) {
        boolean isMatch = false;
        for (String matchURL : urls) {
            if (StringUtils.isNotBlank(matchURL)) {
                Pattern p = Pattern.compile(matchURL.trim().replaceAll("\\*", ".*"));
                Matcher m = p.matcher(reqURL);
                if (m.find()) {
                    isMatch = true;
                    break;
                }
            }
        }
        return isMatch;
    }

}
