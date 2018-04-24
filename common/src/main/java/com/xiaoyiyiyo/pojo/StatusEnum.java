package com.xiaoyiyiyo.pojo;

/**
 * Created by xiaoyiyiyo on 2018/4/24.
 */
public enum StatusEnum {
    SUCCESS(0, "success."),

    SERVICE_EXCEPTION(-1, "service exception."),

    RESULT_DATA_EMPTY_ERROR(-2, "Result data is empty or null."),

    PARAM_EMPTY_ERROR(-3, "Param is empty or null."),

    PARAM_TYPE_ERROR(-4, "Param type is error."),

    PARAM_VALUE_ERROR(-5, "Param value is error.")
    ;

    //状态码
    private Integer code;

    //状态信息
    private String msg;

    StatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
