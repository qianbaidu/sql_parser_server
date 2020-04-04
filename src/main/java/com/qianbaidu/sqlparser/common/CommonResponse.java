package com.qianbaidu.sqlparser.common;

import java.io.Serializable;

/**
 * User: Alex
 * Date: 2019/10/12
 * Time: 12:01
 **/

public class CommonResponse<T> implements Serializable {
    private int code;
    private String message;
    private T result;


    public CommonResponse(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public CommonResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonResponse() {
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
