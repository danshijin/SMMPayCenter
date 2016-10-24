package com.smm.payCenter.bo.users;

import javax.servlet.http.HttpServletRequest;

import com.smm.payCenter.domain.Account;
import com.smm.payCenter.enumDef.LoginResult;

public interface UserInfoBO {

    /**
     * 匹配登录
     */
    public LoginResult login(Account account, HttpServletRequest request);

}
