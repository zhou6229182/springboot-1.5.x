package com.ytjr.common.enums;

public enum ResponseEnums {

    SYSTEM_ERROR("500", "未知异常，请联系管理员"),
    SC_FORBIDDEN("403", "权限不足，请联系管理员"),
    BAD_REQUEST("-002", "错误的请求参数"),
    CONNECTION_ERROR("-004", "网络连接请求失败！"),
    METHOD_NOT_ALLOWED("-005", "不合法的请求方式"),
    DATABASE_ERROR("-006", "数据库异常"),
    BOUND_STATEMENT_NOT_FOUNT("-007", "找不到方法！"),
    REPEAT_REGISTER("001", "重复注册"),
    USER_DISABLED("002", "账户被禁用，登录失败，请联系管理员!"),
    INVALID_USER_OR_PASSWORD("003", "用户名或密码输入错误，登录失败!"),
    NO_PERMISSION("004", "非法请求！"),
    SUCCESS_OPTION("005", "操作成功！"),
    FAIL_GETDATA("-008", "获取信息失败"),
    BAD_REQUEST_TYPE("-009", "错误的请求类型"),
    SESSION_EXPIRED("-010", "session已过期"),
    INVALID_MOBILE("010", "无效的手机号码"),
    INVALID_EMAIL("011", "无效的邮箱"),
    LOGIN_FAILED("012", "登录失败"),
    REPEAT_MOBILE("014", "已存在此手机号"),
    REPEAT_EMAIL("015", "已存在此邮箱地址"),
    NO_RECORD("016", "没有查到相关记录"),
    LOGIN_SUCCESS("017", "登陆成功"),
    LOGOUT_SUCCESS("018", "已退出登录"),
    SENDEMAIL_SUCCESS("019", "邮件已发送，请注意查收"),
    EDITPWD_SUCCESS("020", "修改密码成功"),
    NO_FILESELECT("021", "未选择文件"),
    FILEUPLOAD_SUCCESS("022", "上传成功"),
    NOLOGIN("401", "未登陆"),
    ILLEGAL_ARGUMENT("024", "参数不合法"),
    ERROR_IDCODE("025", "验证码不正确");

    private String code;
    private String msg;

    ResponseEnums(String code, String msg) {

        this.code = code;
        this.msg = msg;
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
