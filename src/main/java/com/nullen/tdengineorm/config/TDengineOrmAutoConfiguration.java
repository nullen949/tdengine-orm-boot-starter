package com.nullen.tdengineorm.config;

import cn.hutool.core.util.StrUtil;
import com.nullen.tdengineorm.repository.TDengineRepository;
import com.nullen.tdengineorm.util.JdbcTemplatePlus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.dialect.MySqlDialect;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * TDengine ORM 自动配置类
 *
 * @author Nullen
 */
@Configuration
@EnableConfigurationProperties(TDengineOrmConfig.class)
@ConditionalOnProperty(prefix = TDengineOrmConfig.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class TDengineOrmAutoConfiguration {

    public static final String TDENGINE_DATA_SOURCE = "tdengineDataSource";
    public static final String TDENGINE_JDBC_TEMPLATE = "tdengineJdbcTemplate";
    public static final String TDENGINE_NAMED_PARAMETER_JDBC_TEMPLATE = "tdengineNamedParameterJdbcTemplate";

    @Bean
    public Dialect jdbcDialect() {
        return new MySqlDialect() {
        };
    }

    /**
     * 当 Druid 连接池存在时，创建 Druid DataSource
     * 优先级最高
     */
    @Bean(TDENGINE_DATA_SOURCE)
    @ConditionalOnClass(name = "com.alibaba.druid.pool.DruidDataSource")
    @ConditionalOnMissingBean(name = TDENGINE_DATA_SOURCE)
    public DataSource druidDataSource(TDengineOrmConfig tdengineOrmConfig) {
        try {
            Class<?> druidDataSourceClass = Class.forName("com.alibaba.druid.pool.DruidDataSource");
            Object druidDataSource = druidDataSourceClass.getDeclaredConstructor().newInstance();

            // 设置基本连接信息
            if (StrUtil.isNotBlank(tdengineOrmConfig.getUrl())) {
                druidDataSourceClass.getMethod("setUrl", String.class).invoke(druidDataSource, tdengineOrmConfig.getUrl());
            }
            druidDataSourceClass.getMethod("setUsername", String.class).invoke(druidDataSource, tdengineOrmConfig.getUsername());
            druidDataSourceClass.getMethod("setPassword", String.class).invoke(druidDataSource, tdengineOrmConfig.getPassword());
            druidDataSourceClass.getMethod("setDriverClassName", String.class).invoke(druidDataSource, tdengineOrmConfig.getDriverClassName());

            // 设置 Druid 特有配置
            druidDataSourceClass.getMethod("setInitialSize", int.class).invoke(druidDataSource, 5);
            druidDataSourceClass.getMethod("setMaxActive", int.class).invoke(druidDataSource, 20);
            druidDataSourceClass.getMethod("setMinIdle", int.class).invoke(druidDataSource, 5);
            druidDataSourceClass.getMethod("setMaxWait", long.class).invoke(druidDataSource, 60000L);
            druidDataSourceClass.getMethod("setValidationQuery", String.class).invoke(druidDataSource, "SELECT 1");
            druidDataSourceClass.getMethod("setTestOnBorrow", boolean.class).invoke(druidDataSource, false);
            druidDataSourceClass.getMethod("setTestOnReturn", boolean.class).invoke(druidDataSource, false);
            druidDataSourceClass.getMethod("setTestWhileIdle", boolean.class).invoke(druidDataSource, true);

            return (DataSource) druidDataSource;
        } catch (Exception e) {
            throw new RuntimeException("创建 Druid DataSource 失败", e);
        }
    }

    /**
     * 当 HikariCP 连接池存在时，创建 HikariCP DataSource
     * 优先级次之
     */
    @Bean(TDENGINE_DATA_SOURCE)
    @ConditionalOnClass(name = "com.zaxxer.hikari.HikariDataSource")
    @ConditionalOnMissingBean(name = TDENGINE_DATA_SOURCE)
    public DataSource hikariDataSource(TDengineOrmConfig tdengineOrmConfig) {
        try {
            Class<?> hikariConfigClass = Class.forName("com.zaxxer.hikari.HikariConfig");
            Class<?> hikariDataSourceClass = Class.forName("com.zaxxer.hikari.HikariDataSource");

            Object hikariConfig = hikariConfigClass.getDeclaredConstructor().newInstance();

            // 设置基本连接信息
            if (StrUtil.isNotBlank(tdengineOrmConfig.getUrl())) {
                hikariConfigClass.getMethod("setJdbcUrl", String.class).invoke(hikariConfig, tdengineOrmConfig.getUrl());
            }
            hikariConfigClass.getMethod("setUsername", String.class).invoke(hikariConfig, tdengineOrmConfig.getUsername());
            hikariConfigClass.getMethod("setPassword", String.class).invoke(hikariConfig, tdengineOrmConfig.getPassword());
            hikariConfigClass.getMethod("setDriverClassName", String.class).invoke(hikariConfig, tdengineOrmConfig.getDriverClassName());

            // 设置 HikariCP 特有配置
            hikariConfigClass.getMethod("setMaximumPoolSize", int.class).invoke(hikariConfig, 20);
            hikariConfigClass.getMethod("setMinimumIdle", int.class).invoke(hikariConfig, 5);
            hikariConfigClass.getMethod("setConnectionTimeout", long.class).invoke(hikariConfig, 30000L);
            hikariConfigClass.getMethod("setIdleTimeout", long.class).invoke(hikariConfig, 600000L);
            hikariConfigClass.getMethod("setMaxLifetime", long.class).invoke(hikariConfig, 1800000L);

            return (DataSource) hikariDataSourceClass.getDeclaredConstructor(hikariConfigClass).newInstance(hikariConfig);
        } catch (Exception e) {
            throw new RuntimeException("创建 HikariCP DataSource 失败", e);
        }
    }

    /**
     * 当 Apache DBCP2 连接池存在时，创建 DBCP2 DataSource
     * 优先级再次之
     */
    @Bean(TDENGINE_DATA_SOURCE)
    @ConditionalOnClass(name = "org.apache.commons.dbcp2.BasicDataSource")
    @ConditionalOnMissingBean(name = TDENGINE_DATA_SOURCE)
    public DataSource dbcp2DataSource(TDengineOrmConfig tdengineOrmConfig) {
        try {
            Class<?> basicDataSourceClass = Class.forName("org.apache.commons.dbcp2.BasicDataSource");
            Object basicDataSource = basicDataSourceClass.getDeclaredConstructor().newInstance();

            // 设置基本连接信息
            if (StrUtil.isNotBlank(tdengineOrmConfig.getUrl())) {
                basicDataSourceClass.getMethod("setUrl", String.class).invoke(basicDataSource, tdengineOrmConfig.getUrl());
            }
            basicDataSourceClass.getMethod("setUsername", String.class).invoke(basicDataSource, tdengineOrmConfig.getUsername());
            basicDataSourceClass.getMethod("setPassword", String.class).invoke(basicDataSource, tdengineOrmConfig.getPassword());
            basicDataSourceClass.getMethod("setDriverClassName", String.class).invoke(basicDataSource, tdengineOrmConfig.getDriverClassName());

            // 设置 DBCP2 特有配置
            basicDataSourceClass.getMethod("setInitialSize", int.class).invoke(basicDataSource, 5);
            basicDataSourceClass.getMethod("setMaxTotal", int.class).invoke(basicDataSource, 20);
            basicDataSourceClass.getMethod("setMinIdle", int.class).invoke(basicDataSource, 5);
            basicDataSourceClass.getMethod("setMaxIdle", int.class).invoke(basicDataSource, 10);
            basicDataSourceClass.getMethod("setMaxWaitMillis", long.class).invoke(basicDataSource, 60000L);
            basicDataSourceClass.getMethod("setValidationQuery", String.class).invoke(basicDataSource, "SELECT 1");
            basicDataSourceClass.getMethod("setTestOnBorrow", boolean.class).invoke(basicDataSource, false);
            basicDataSourceClass.getMethod("setTestOnReturn", boolean.class).invoke(basicDataSource, false);
            basicDataSourceClass.getMethod("setTestWhileIdle", boolean.class).invoke(basicDataSource, true);

            return (DataSource) basicDataSource;
        } catch (Exception e) {
            throw new RuntimeException("创建 DBCP2 DataSource 失败", e);
        }
    }

    /**
     * 兜底方案：使用 Spring Boot 默认的简单 DataSource
     * 优先级最低
     */
    @Bean(TDENGINE_DATA_SOURCE)
    @ConditionalOnMissingBean(name = TDENGINE_DATA_SOURCE)
    public DataSource defaultDataSource(TDengineOrmConfig tdengineOrmConfig) {
        try {
            // 使用 Spring 的 DriverManagerDataSource 作为兜底方案
            Class<?> driverManagerDataSourceClass = Class.forName("org.springframework.jdbc.datasource.DriverManagerDataSource");
            Object dataSource = driverManagerDataSourceClass.getDeclaredConstructor().newInstance();

            if (StrUtil.isNotBlank(tdengineOrmConfig.getUrl())) {
                driverManagerDataSourceClass.getMethod("setUrl", String.class).invoke(dataSource, tdengineOrmConfig.getUrl());
            }
            driverManagerDataSourceClass.getMethod("setUsername", String.class).invoke(dataSource, tdengineOrmConfig.getUsername());
            driverManagerDataSourceClass.getMethod("setPassword", String.class).invoke(dataSource, tdengineOrmConfig.getPassword());
            driverManagerDataSourceClass.getMethod("setDriverClassName", String.class).invoke(dataSource, tdengineOrmConfig.getDriverClassName());

            return (DataSource) dataSource;
        } catch (Exception e) {
            throw new RuntimeException("创建默认 DataSource 失败", e);
        }
    }

    /**
     * 创建 TDengine 专用的 JdbcTemplate
     */
    @Bean(TDENGINE_JDBC_TEMPLATE)
    @ConditionalOnMissingBean(name = TDENGINE_JDBC_TEMPLATE)
    public JdbcTemplate tdengineJdbcTemplate(@Qualifier(TDENGINE_DATA_SOURCE) DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * 创建 TDengine 专用的 NamedParameterJdbcTemplate
     */
    @Bean(TDENGINE_NAMED_PARAMETER_JDBC_TEMPLATE)
    @ConditionalOnMissingBean(name = TDENGINE_NAMED_PARAMETER_JDBC_TEMPLATE)
    public NamedParameterJdbcTemplate tdengineNamedParameterJdbcTemplate(@Qualifier(TDENGINE_JDBC_TEMPLATE) JdbcTemplate jdbcTemplate) {
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    /**
     * 创建 JdbcTemplatePlus
     */
    @Bean
    @ConditionalOnMissingBean(JdbcTemplatePlus.class)
    public JdbcTemplatePlus jdbcTemplatePlus(@Qualifier(TDENGINE_NAMED_PARAMETER_JDBC_TEMPLATE) NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new JdbcTemplatePlus(namedParameterJdbcTemplate);
    }

    /**
     * 创建 TDengineRepository
     */
    @Bean
    @ConditionalOnMissingBean(TDengineRepository.class)
    public TDengineRepository tdengineRepository(JdbcTemplatePlus jdbcTemplatePlus,
                                                 @Qualifier(TDENGINE_NAMED_PARAMETER_JDBC_TEMPLATE) NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new TDengineRepository(jdbcTemplatePlus, namedParameterJdbcTemplate);
    }
}
