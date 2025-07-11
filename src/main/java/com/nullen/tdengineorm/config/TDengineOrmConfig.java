package com.nullen.tdengineorm.config;

import com.nullen.tdengineorm.enums.TdLogLevelEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * TDengine ORM 配置类
 * 
 * @author Silas
 */
@Data
@ConfigurationProperties(prefix = TDengineOrmConfig.PREFIX)
public class TDengineOrmConfig {

    public static final String PREFIX = "tdengine-orm";

    /**
     * 是否启用 TDengine ORM 自动配置
     */
    private boolean enabled = true;

    /**
     * 连接地址
     */
    private String url;

    /**
     * 用户名
     */
    private String username = "root";

    /**
     * 密码
     */
    private String password = "taosdata";

    /**
     * 驱动类名
     */
    private String driverClassName = "com.taosdata.jdbc.TSDBDriver";

    /**
     * 日志级别
     */
    private TdLogLevelEnum logLevel = TdLogLevelEnum.ERROR;

}
