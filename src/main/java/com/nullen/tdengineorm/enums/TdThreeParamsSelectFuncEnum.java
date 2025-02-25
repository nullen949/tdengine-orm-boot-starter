package com.nullen.tdengineorm.enums;

import lombok.Getter;

/**
 * TDengine常用聚合函数
 * 3入参
 *
 * @author Nullen
 */
@Getter
public enum TdThreeParamsSelectFuncEnum {

    /**
     * 类型转换函数
     */
    CAST("CAST({} AS {}) {}");

    private final String func;

    TdThreeParamsSelectFuncEnum(String func) {
        this.func = func;
    }
}
