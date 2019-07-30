package com.thunk.iot.dto;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description: 自定义返回状态码
 * @Date:     2018/12/30 12:05
 * Copyright (C), 2016-2018, EasyLinker V3
 */

public enum ResultEnum {
    /**
     * 请求成功
     */
    SUCCESS(0, "请求成功"),

    SystemException(-1,"系统异常"),

    UnknownException(40000,"未知异常"),

    ParamNull(40001, "参数有误或不完整"),

    SaveError(40001, "保存失败"),
    DeleteError(40002, "删除失败");

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResultEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
