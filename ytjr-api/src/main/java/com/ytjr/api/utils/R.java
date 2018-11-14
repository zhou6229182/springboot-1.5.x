package com.ytjr.api.utils;

import com.ytjr.common.enums.ResponseEnums;

import java.util.HashMap;
import java.util.Map;

public class R extends HashMap<String, Object> {

    private static final long serialVersionUID = -2052479788923198052L;

    public R() {
        put("code", "0");
    }

    public static R error() {
        return error(ResponseEnums.SYSTEM_ERROR.getCode(), ResponseEnums.SYSTEM_ERROR.getMsg());
    }

    public static R error(String msg) {
        return error(ResponseEnums.SYSTEM_ERROR.getCode(), msg);
    }

    public static R error(ResponseEnums enums) {
        return error(enums.getCode(), enums.getMsg());
    }

    public static R error(String code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
