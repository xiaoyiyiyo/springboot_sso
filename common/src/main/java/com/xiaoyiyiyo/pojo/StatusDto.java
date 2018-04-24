package com.xiaoyiyiyo.pojo;

/**
 * Created by xiaoyiyiyo on 2018/4/24.
 */
public class StatusDto {

    private Integer code;

    private String msg;

    private long runningMs;

    public StatusDto() {
    }

    public StatusDto(Integer code, String msg, long runningMs) {
        this.code = code;
        this.msg = msg;
        this.runningMs = runningMs;
    }

    public StatusDto(StatusEnum statusEnum, long runningMs) {
        this.code = statusEnum.getCode();
        this.msg = statusEnum.getMsg();
        this.runningMs = runningMs;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getRunningMs() {
        return runningMs;
    }

    public void setRunningMs(long runningMs) {
        this.runningMs = runningMs;
    }
}
