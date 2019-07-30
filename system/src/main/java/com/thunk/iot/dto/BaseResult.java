package com.thunk.iot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
public class BaseResult implements Serializable {
    private static final long serialVersionUID = -8034221368756060801L;

    private Integer status;
    private String message;
    private Object data;
    private Object cursor;
    private List<Error> errors;

    /**
     * 成功，无数据返回
     *
     * @return
     */
    public static BaseResult ok() {
        return createResult(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), null, null, null);
    }

    /**
     * 成功，有数据返回
     *
     * @param data 数据
     * @return
     */
    public static BaseResult ok(Object data) {
        return createResult(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data, null, null);
    }

    /**
     * 成功，有数据和分页信息返回
     *
     * @param data   数据
     * @param cursor 分页信息
     * @return
     */
    public static BaseResult ok(Object data, Cursor cursor) {
        return createResult(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data, cursor, null);
    }

    /**
     * 失败，返回错误码和错误信息
     *
     * @param status  错误码
     * @param message 错误描述
     * @return
     */
    public static BaseResult notOk(Integer status, String message) {
        return createResult(status, message, null, null, null);
    }

    /**
     * 失败，返回错误码、错误信息和数据
     *
     * @param status  错误码
     * @param message 错误描述
     * @param data    数据
     * @return
     */
    public static BaseResult notOk(Integer status, String message, Object data) {
        return createResult(status, message, null, null, null);
    }

    /**
     * @param status
     * @param data
     * @param message
     * @param cursor
     * @param errors
     * @return
     */
    private static BaseResult createResult(Integer status,
                                           String message,
                                           Object data,
                                           Cursor cursor,
                                           List<Error> errors) {
        BaseResult baseResult = new BaseResult();
        baseResult.setStatus(status);
        baseResult.setMessage(message);
        if (data == null) {
            baseResult.setData(Collections.emptyMap());
        } else {
            baseResult.setData(data);
        }
        if (cursor == null) {
            baseResult.setCursor(Collections.emptyMap());
        } else {
            baseResult.setCursor(cursor);
        }
        if (errors == null) {
            baseResult.setErrors(Collections.emptyList());
        } else {
            baseResult.setErrors(errors);
        }

        return baseResult;
    }

    @Data
    public static class Cursor {
        private int total;  //总记录数
        private int offset; //当前页
        private int limit;  //每页显示条数
        private int pages;  //总页数
        private boolean firstPage; //是否为第一页
        private boolean lastPage;         //是否为最后一页
        private boolean previousPage;   //是否有前一页
        private boolean nextPage;       //是否有下一页
    }

    @Data
    @AllArgsConstructor
    public static class Error {
        private String field;
        private String message;
    }
}
