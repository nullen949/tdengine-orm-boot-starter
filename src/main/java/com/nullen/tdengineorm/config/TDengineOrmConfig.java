package com.nullen.tdengineorm.config;

import com.nullen.tdengineorm.enums.TdLogLevelEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author Silas
 */
@Data
@ConfigurationProperties(prefix = TDengineOrmConfig.PREFIX)
public class TDengineOrmConfig {

    public static final String PREFIX = "tdengine-orm";

    /**
     * 连接地址
     */
    private String url;

    private String username = "root";

    private String password = "taosdata";

    private String driverClassName = "com.taosdata.jdbc.TSDBDriver";

    /**
     * 日志级别
     */
    private TdLogLevelEnum logLevel = TdLogLevelEnum.ERROR;


}
