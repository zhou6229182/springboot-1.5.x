package com.ytjr.common.exception;

import com.ytjr.common.enums.ResponseEnums;

public class FinancialServiceException extends RuntimeException {

    private static final long serialVersionUID = 6778788648285286013L;

    private String code;

    public FinancialServiceException() {
        super();
    }

    public FinancialServiceException(String msg) {
        super(msg);
        this.code = "500";
    }

    public FinancialServiceException(ResponseEnums enums) {
        super(enums.getMsg());
        this.code = enums.getCode();
    }

    public FinancialServiceException(ResponseEnums enums, Throwable e) {
        super(enums.getMsg(), e);
        this.code = enums.getCode();
    }

    public FinancialServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public FinancialServiceException(String msg, Throwable e) {
        super(msg, e);
        this.code = "500";
    }

    public FinancialServiceException(String code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
