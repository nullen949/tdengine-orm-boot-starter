package com.nullen.tdengineorm.wrapper;

import com.nullen.tdengineorm.entity.BaseTdEntity;

/**
 * @author Nullen
 */
public class TdWrappers {

    public static <T extends BaseTdEntity> TdQueryWrapper<T> queryWrapper(Class<T> targerClass) {
        return new TdQueryWrapper<>(targerClass);
    }
}
