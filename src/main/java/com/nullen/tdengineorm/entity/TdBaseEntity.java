package com.nullen.tdengineorm.entity;

import lombok.Data;

/**
 * TDengine基础实体类
 *
 * @author Nullen
 */
@Data
public class TdBaseEntity {

    /**
     * TDengine要求每个表的第一个字段, 必须为ts, 表示数据的时间戳
     * Java可以使用Long或者Timestamp都行
     */
    private Long ts;
}
