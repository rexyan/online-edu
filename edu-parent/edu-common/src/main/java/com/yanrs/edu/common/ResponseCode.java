package com.yanrs.edu.common;

// 定义返回数据中使用的状态码
public enum ResponseCode {
    SUCCESS(90000, "成功"),
    FAIL(30010, "成功"),
    ERROR(30011, "错误"),
    NOT_FOUND(30012, "资源未找到"),
    NOT_AUTHED(30013, "无权限，访问拒绝"),
    PARAM_INVAILD(30014, "提交参数非法");

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
