package com.douding.doudingcg.base.controller;

import cn.hutool.core.collection.CollUtil;
import com.douding.doudingcg.base.enums.BaseEnum;
import com.douding.doudingcg.constant.enums.ResponseCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.logging.log4j.util.Strings;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author Guo
 * @create 2024-04-26 11:32
 */
@ApiModel("接口统一返回对象")
public class BaseResponseVO<T> {
    @ApiModelProperty("业务返回码")
    private Integer code;
    @ApiModelProperty("业务返回提示信息")
    private String message;
    @ApiModelProperty("响应实体")
    private T data;

    public BaseResponseVO() {
        this.code = ResponseCodeEnum.OK.getResponseCode();
    }

    public BaseResponseVO(T data) {
        this.code = ResponseCodeEnum.OK.getResponseCode();
        this.data = data;
        this.message = ValidatorUtils.message(ResponseCodeEnum.OK.getMessage(), new Object[0]);
    }

    public BaseResponseVO(BaseEnum code) {
        if (Objects.nonNull(code)) {
            this.code = code.getCode();
            this.message = ValidatorUtils.message(code.getMessage(), new Object[0]);
        } else {
            this.code = ResponseCodeEnum.OK.getResponseCode();
        }
    }

    public BaseResponseVO(BaseEnum code, T data) {
        if (Objects.nonNull(code)) {
            this.code = code.getCode();
            this.message = ValidatorUtils.message(code.getMessage(), new Object[0]);
        }
        this.data = data;
    }

    public BaseResponseVO(BaseEnum code, String message) {
        if (Objects.nonNull(code)) {
            this.code = code.getCode();
            this.message = ValidatorUtils.message(message, new Object[0]);
        } else {
            this.code = ResponseCodeEnum.OK.getResponseCode();
        }
    }

    public static <T> BaseResponseVO<T> isOk() {
        return new BaseResponseVO(ResponseCodeEnum.OK);
    }

    public static <T> BaseResponseVO<T> isOk(T data) {
        return new BaseResponseVO(data);
    }

    public static <T> BaseResponseVO<T> isOk(BaseEnum code) {
        return new BaseResponseVO(code);
    }

    public static <T> BaseResponseVO<T> isOk(BaseEnum code, T data) {
        return new BaseResponseVO(code, data);
    }

    public static <T> BaseResponseVO<T> fail(BaseEnum codeEnum) {
        return new BaseResponseVO(codeEnum);
    }

}
