package com.douding.doudingcg.exception;

import com.douding.doudingcg.base.enums.BaseEnum;

/**
 * @author Guo
 * @create 2024-04-26 17:22
 */
public class PramsException extends RuntimeException {
    private BaseEnum baseEnum;

    public PramsException(BaseEnum baseEnum) {
        super(baseEnum.getMessage());
        this.baseEnum = baseEnum;
    }

    public PramsException(String message) {
        super(message);
    }

    public BaseEnum getBaseEnum() {
        return this.baseEnum;
    }

}
