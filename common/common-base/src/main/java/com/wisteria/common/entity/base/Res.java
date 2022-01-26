package com.wisteria.common.entity.base;

import lombok.Data;

@Data
public class Res<T> {
    private int code;
    private String result;
    private String message;
    private T data;
    private Page page;

    public Res(int code, String result, String message, T data, Page page) {
        this.code = code;
        this.result = result;
        this.message = message;
        this.data = data;
        this.page = page;
    }

    public static Res error() {
        return error(DicConstant.RESULT_ERROR);
    }

    public static Res error(String message) {
        return result(Code.HTTP_BAD_REQUEST, DicConstant.RESULT_SUCCESS, message, null, Page.firstPage());
    }

    public static Res success() {
        return success("");
    }

    public static Res success(String message) {
        return result(Code.HTTP_OK, DicConstant.RESULT_SUCCESS, message, null, Page.firstPage());
    }

    public static <T> Res success(T data) {
        return success(data, Page.firstPage());
    }

    public static <T> Res success(T data, Page page) {
        return result(Code.HTTP_OK, DicConstant.RESULT_SUCCESS, DicConstant.RESULT_SUCCESS, data, page);
    }

    public static <T> Res result(int code, String result, String message, T data, Page page) {
        return new Res(code, result, message, data, page);
    }
}