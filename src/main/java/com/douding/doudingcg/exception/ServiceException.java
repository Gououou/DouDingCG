package com.douding.doudingcg.exception;


import com.douding.doudingcg.base.enums.BaseEnum;
import com.douding.doudingcg.constant.enums.ResponseCodeEnum;

/**
 * 统一服务自定义异常.
 */
public class ServiceException extends RuntimeException {

    private final BaseEnum baseEnum;

    public ServiceException(BaseEnum baseEnum, String message) {
        super(message);
        this.baseEnum = baseEnum;
    }

    public ServiceException(BaseEnum baseEnum) {
        super(baseEnum.getMessage());
        this.baseEnum = baseEnum;
    }

    public BaseEnum getResponseCodeEnum() {
        return baseEnum;
    }

}
