package com.ytjr.api.utils;

import javax.servlet.http.HttpServletRequest;

public class AjaxUtils {

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(ajaxFlag);
    }
}
