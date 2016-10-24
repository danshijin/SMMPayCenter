package com.smm.payCenter.util;

import com.smm.payCenter.tools.CommonException;

public class ExceptionUtil {
    public static CommonException newException(String retCode, String retMsg){
        return new CommonException(retCode, retMsg);
    }

}