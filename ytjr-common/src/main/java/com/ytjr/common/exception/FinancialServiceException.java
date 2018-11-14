package com.ytjr.common.exception;

import com.ytjr.common.enums.ResponseEnums;

public class FinancialServiceException extends RuntimeException {

    private static final long serialVersionUID = 6778788648285286013L;

    private String code;
    private String msg;

    public FinancialServiceException() {
        super();
    }

    public FinancialServiceException(String msg) {
        super(msg);
        this.code = "500";
        this.msg = msg;
    }

    public FinancialServiceException(ResponseEnums enums) {
        super(enums.getMsg());
        this.code = enums.getCode();
        this.msg = enums.getMsg();
    }

    public FinancialServiceException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public FinancialServiceException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public FinancialServiceException(String code, String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
