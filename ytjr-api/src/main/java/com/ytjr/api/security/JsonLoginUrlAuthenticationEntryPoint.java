package com.ytjr.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytjr.api.utils.R;
import com.ytjr.common.enums.ResponseEnums;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JsonLoginUrlAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private ObjectMapper om;

    public JsonLoginUrlAuthenticationEntryPoint(ObjectMapper om) {
        this.om = om;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (isAjaxRequest(request)) {//判断未登录并且为ajax请求时返回JSON
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write(om.writeValueAsString(R.error(ResponseEnums.NOLOGIN)));
            out.flush();
            out.close();
        } else {
            response.sendRedirect("/login.html");
        }
    }

    private static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(ajaxFlag);
    }
}
