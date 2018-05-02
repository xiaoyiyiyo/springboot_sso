package com.xiaoyiyiyo.server.common.util;

import com.xiaoyiyiyo.server.common.pojo.ResultDto;
import com.xiaoyiyiyo.server.common.pojo.StatusDto;
import com.xiaoyiyiyo.server.common.pojo.StatusEnum;

/**
 * Created by xiaoyiyiyo on 2018/4/24.
 */
public class RespUtils {

    private RespUtils() {}

    public static <T> ResultDto<T> success(T object, long runningMs) {
        long endMs = System.currentTimeMillis();
        return new ResultDto<T>(object, new StatusDto(StatusEnum.SUCCESS, endMs - runningMs));
    }

    public static ResultDto error(StatusEnum statusEnum) {
        return new ResultDto(null, new StatusDto(statusEnum, -1));
    }

    public static ResultDto error(Integer code, String msg) {
        return new ResultDto(null, new StatusDto(code, msg, -1));
    }
}
