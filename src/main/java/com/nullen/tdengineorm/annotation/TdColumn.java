package com.nullen.tdengineorm.annotation;

import com.nullen.tdengineorm.enums.TdFieldTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TDengine列注解
 * 用于标记实体类字段对应的列信息
 *
 * @author Nullen
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TdColumn {
    /**
     * 列名
     */
    String value() default "";

    /**
     * 字段类型
     */
    TdFieldTypeEnum type() default TdFieldTypeEnum.NCHAR;

    /**
     * 字段长度
     */
    int length() default 0;

    /**
     * 列注释
     */
    String comment() default "";

    /**
     * 是否允许为空
     */
    boolean nullable() default true;
} 