package com.qianbaidu.sqlparser.common;

/**
 * User: Alex
 * Date: 2019/10/12
 * Time: 13:07
 **/

public enum ResponseEnum {
    SUCCESS(200, "ok"),
    PARAM_FAIL(401, "参数错误"),
    FAIL(500, "系统错误"),
    REOULT_FAIL(502, "查询结果为空"),
    STORE_NOT_EXIST(5001, "门店不存在"),
    STORE_NOT_DELIVERY(5002, "门店无配送范围");

    private int code;
    private String msg;

    public static String getMsg(int code) {
        for (ResponseEnum store : ResponseEnum.values()) {
            if (store.getCode() == code) {
                return store.msg;
            }
        }
        return null;
    }

    ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
