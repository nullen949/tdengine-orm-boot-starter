package com.nullen.tdengineorm.wrapper;

/**
 * @author Nullen
 */
public class TdWrappers {

    public static <T> TdQueryWrapper<T> queryWrapper(Class<T> targerClass) {
        return new TdQueryWrapper<>(targerClass);
    }
}
