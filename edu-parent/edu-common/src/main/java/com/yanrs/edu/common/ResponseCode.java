package com.yanrs.edu.common;

// 定义返回数据中使用的状态码
public enum ResponseCode {
    SUCCESS(90000, "成功"),
    FAIL(30010, "成功"),
    ERROR(30011, "错误"),
    NOT_FOUND(30012, "资源未找到"),
    NOT_AUTHED(30013, "无权限，访问拒绝"),
    PARAM_INVAILD(30014, "提交参数非法"),
    IMPORT_SUBJECT_ERROR(30015, "导入分类信息错误"),
    DB_INSERT_ERROR(30016, "数据写入异常"),
    DB_UPDATE_ERROR(30017, "数据更新异常"),
    DB_DELETE_ERROR(30018, "数据删除异常"),
    SEARCH_RESULT_IS_NULL(30020, "查询结果为空"),
    DB_SEARCH_ERROR(30019, "数据查询异常");

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
