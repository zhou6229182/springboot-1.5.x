package com.ytjr.api.controller;

import com.ytjr.api.utils.R;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * error控制器
 * 替换默认的 http status 错误页面
 */
@RestController
public class HttpErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return ResponseEntity.ok(R.error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        } else {
            try {
                return ResponseEntity.ok(R.error(String.valueOf(statusCode), HttpStatus.valueOf(statusCode).getReasonPhrase()));
            } catch (Exception e) {
                return ResponseEntity.ok(R.error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
            }
        }
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
