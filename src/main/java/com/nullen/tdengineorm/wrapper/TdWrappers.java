package com.nullen.tdengineorm.wrapper;

import com.nullen.tdengineorm.entity.TdBaseEntity;

/**
 * @author Nullen
 */
public class TdWrappers {

    public static <T extends TdBaseEntity> TdQueryWrapper<T> queryWrapper(Class<T> targerClass) {
        return new TdQueryWrapper<>(targerClass);
    }
}
