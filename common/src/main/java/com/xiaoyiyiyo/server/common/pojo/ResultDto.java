package com.xiaoyiyiyo.server.common.pojo;

/**
 * Created by xiaoyiyiyo on 2018/4/24.
 */
public class ResultDto<T> {

    private T data;

    private StatusDto status;

    public ResultDto() {
    }

    public ResultDto(T data, StatusDto status) {
        this.data = data;
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public StatusDto getStatus() {
        return status;
    }

    public void setStatus(StatusDto status) {
        this.status = status;
    }
}
