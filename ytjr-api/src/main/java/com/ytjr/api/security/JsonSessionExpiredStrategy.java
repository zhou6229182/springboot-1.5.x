package com.ytjr.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytjr.api.utils.AjaxUtils;
import com.ytjr.api.utils.R;
import com.ytjr.common.enums.ResponseEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * session失效处理
 */
@Component
public class JsonSessionExpiredStrategy implements SessionInformationExpiredStrategy {

    private ObjectMapper om;

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    public JsonSessionExpiredStrategy(ObjectMapper om) {
        this.om = om;
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException {
        HttpServletRequest request = sessionInformationExpiredEvent.getRequest();
        HttpServletResponse response = sessionInformationExpiredEvent.getResponse();
        if (AjaxUtils.isAjaxRequest(request)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            PrintWriter out = response.getWriter();
            out.write(om.writeValueAsString(R.error(ResponseEnums.SESSION_EXPIRED)));
            out.flush();
            out.close();
        } else {
            redirectStrategy.sendRedirect(request, response, "/login.html");
        }
    }
}
