package com.nullen.tdengineorm.util;

import cn.hutool.extra.spring.SpringUtil;
import com.nullen.tdengineorm.enums.TdLogLevelEnum;
import org.springframework.core.env.Environment;

/**
 * @author Nullen
 */
public class TdOrmUtil {


    /**
     * 获取TdOrm日志等级, 可能为空
     *
     * @return {@link TdLogLevelEnum }
     */
    public static TdLogLevelEnum getLogLevel() {
        Environment environment = SpringUtil.getBean(Environment.class);
        String tdLogLevel = environment.getProperty("td-orm.log-level");
        return TdLogLevelEnum.match(tdLogLevel);
    }

}
