package com.douding.doudingcg.constant.enums;

import com.douding.doudingcg.base.enums.BaseEnum;

/**
 * @author Guo
 * @create 2024-04-26 14:12
 */
public enum ResponseCodeEnum implements BaseEnum {

    SPECIAL_ERROR(-1, "前端做特殊处理错误"),
    OK(0, "成功"),
    FAIL(1, "逻辑错误"),
    ITEM_NOT_FOUND(2, "未找到对应的数据"),
    SYSTEM_ERROR(3, "系统未知错误"),
    ERR_SIGN(4, "签名错误"),
    ERR_MISSING_REQUEST_PARAM(5, "请求参数缺失"),
    ERR_REQUEST_PARAM_FORMAT(6, "请求参数格式异常"),

    SYS_ASSERT_PARAM_EMPTY(200, "断言参数为空"),
    SYS_ASSERT_PARAM_NOT_PASS(201, "断言参数不通过"),


    ;
    private int code;
    private String message;

    private ResponseCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getResponseCode() {
        return this.code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
