package com.ytjr.api.controller;

import com.ytjr.api.utils.R;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HttpErrorController implements ErrorController {

    @RequestMapping("/error")
    public R handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return R.error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        } else {
            try {
                return R.error(String.valueOf(statusCode), HttpStatus.valueOf(statusCode).getReasonPhrase());
            } catch (Exception e) {
                return R.error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            }
        }
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
