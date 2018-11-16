package com.ytjr.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytjr.api.utils.R;
import com.ytjr.common.enums.ResponseEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {

    private ObjectMapper om;

    @Autowired
    public AuthenticationAccessDeniedHandler(ObjectMapper om) {
        this.om = om;
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        out.write(om.writeValueAsString(R.error(ResponseEnums.SC_FORBIDDEN)));
        out.flush();
        out.close();
    }
}
