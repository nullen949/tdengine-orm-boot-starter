package com.nullen.tdengineorm.wrapper;

import ch.qos.logback.classic.db.names.TableName;
import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.StrUtil;
import com.nullen.tdengineorm.annotation.TdTable;
import com.nullen.tdengineorm.constant.SqlConstant;
import com.nullen.tdengineorm.constant.TdSqlConstant;
import com.nullen.tdengineorm.enums.TdWrapperTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Nullen
 */
@NoArgsConstructor
public abstract class AbstractTdWrapper<T> {

    protected StringBuilder finalSql = new StringBuilder();
    protected String tbName;
    protected StringBuilder where = new StringBuilder();
    protected AtomicInteger paramNameSeq;
    @Getter
    @Setter
    private Class<T> entityClass;
    @Getter
    @Setter
    private Map<String, Object> paramsMap = new HashMap<>(16);
    /**
     * 当前层, 最内层为0, 向上递增
     */
    protected int layer;


    public AbstractTdWrapper(Class<T> entityClass) {
        this.entityClass = entityClass;
        initTbName();
    }

    protected abstract TdWrapperTypeEnum type();


    protected void buildFrom(StringBuilder sql) {
        sql.append(SqlConstant.FROM).append(tbName).append(SqlConstant.BLANK);
    }

    protected void initTbName() {
        String name = AnnotationUtil.getAnnotationValue(entityClass, TdTable.class, "value");
        if (StrUtil.isBlank(name)) {
            name = StrUtil.toUnderlineCase(entityClass.getSimpleName());
        }
        tbName = name;
    }

    protected Integer getParamNameSeq() {
        if (paramNameSeq == null) {
            paramNameSeq = new AtomicInteger(0);
            return paramNameSeq.getAndIncrement();
        }
        return paramNameSeq.incrementAndGet();
    }

    protected String genParamName() {
        return TdSqlConstant.MAP_PARAM_NAME_PREFIX + layer + "_" + getParamNameSeq();
    }
}
