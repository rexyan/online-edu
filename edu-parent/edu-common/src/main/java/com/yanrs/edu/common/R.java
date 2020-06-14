package com.yanrs.edu.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

// 定义具体的数据返回格式
@Data
public class R {
    private Boolean success;
    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    //操作成功，调用这个方法，返回成功的数据
    public static R success() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResponseCode.SUCCESS.getCode());
        r.setMessage("操作成功");
        return r;
    }

    //操作失败，调用这个方法，返回失败的数据
    public static R fail() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResponseCode.FAIL.getCode());
        r.setMessage("操作失败");
        return r;
    }

    //使用链式编程
    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}
