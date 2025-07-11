## 介绍

[English](README.md) | [中文](README_CN.md)

> `tdengine-orm-boot-starter` 是一个基于 SpringBootJdbc 的半 ORM 框架，用于便捷操作 TDengine 数据，其设计参考了 MyBatisPlus

### 技术栈

- spring-boot-starter 2.X：主要使用 SpringBoot 的自动装配功能，虽然 SpringBoot 2.7 之后自动装配方式有所修改，但旧的方式仍然兼容
- spring-boot-starter-jdbc 2.x：主要使用 JdbcTemplate 对象

## 快速开始

1. 拉取项目源码，执行 `mvn clean install`（或推送到你的私有仓库）
2. 在你的项目中引入依赖
    ```xml
    <dependency>
        <groupId>com.github.nullen949</groupId>
        <artifactId>tdengine-orm-boot-starter</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    ```
3. 下载示例项目源码：[示例项目仓库地址](https://github.com/nullen949/tdengine-orm-demo)
4. 修改示例项目配置文件中的连接配置，如果需要请创建对应的数据库
5. 找到测试类，按顺序执行测试方法
6. 返回你的项目，参考示例项目的使用方式进行开发

## 详细使用指南

### 支持的连接池

该 starter 支持以下连接池，按优先级排序：

1. **Druid** - 优先级最高
2. **HikariCP** - 优先级次之
3. **Apache DBCP2** - 优先级再次之
4. **Spring DriverManagerDataSource** - 兜底方案

### 使用方法

#### 1. 添加依赖

在你的项目 `pom.xml` 中添加 TDengine ORM Starter 依赖：

```xml
<dependency>
    <groupId>com.nullen</groupId>
    <artifactId>tdengine-orm-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

#### 2. 添加连接池依赖（可选）

根据需要选择一个连接池：

##### 使用 Druid 连接池
```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.2.8</version>
</dependency>
```

##### 使用 HikariCP 连接池
```xml
<dependency>
    <groupId>com.zaxxer</groupId>
    <artifactId>HikariCP</artifactId>
    <version>5.0.1</version>
</dependency>
```

##### 使用 Apache DBCP2 连接池
```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-dbcp2</artifactId>
    <version>2.9.0</version>
</dependency>
```

如果不添加任何连接池依赖，starter 将使用 Spring 的 DriverManagerDataSource 作为兜底方案。

#### 3. 配置数据库连接

在 `application.yml` 或 `application.properties` 中配置 TDengine 连接信息：

##### application.yml 示例
```yaml
tdengine-orm:
  enabled: true  # 可选，默认为 true
  url: jdbc:TAOS://localhost:6030/test
  username: root
  password: taosdata
  driver-class-name: com.taosdata.jdbc.TSDBDriver
  log-level: ERROR  # 日志级别：ERROR, WARN, INFO, DEBUG
```

##### application.properties 示例
```properties
tdengine-orm.enabled=true
tdengine-orm.url=jdbc:TAOS://localhost:6030/test
tdengine-orm.username=root
tdengine-orm.password=taosdata
tdengine-orm.driver-class-name=com.taosdata.jdbc.TSDBDriver
tdengine-orm.log-level=ERROR
```

#### 4. 使用 TDengineRepository

在你的服务类中注入和使用 `TDengineRepository`：

```java
@Service
public class IoTDataService {
    
    @Autowired
    private TDengineRepository tdengineRepository;
    
    public void saveData(SensorData data) {
        // 插入单条数据
        tdengineRepository.insert(data);
    }
    
    public List<SensorData> findData() {
        // 查询数据
        TdQueryWrapper<SensorData> wrapper = TdWrappers.queryWrapper(SensorData.class)
                .selectAll()
                .orderByDesc("ts")
                .limit(100);
        return tdengineRepository.list(wrapper);
    }
}
```

#### 5. 实体类定义示例

```java
@TableName("sensor_data")
public class SensorData extends TdBaseEntity {
    
    @TdTag
    private String deviceId;
    
    @TdTag  
    private String location;
    
    private Double temperature;
    private Double humidity;
    private Timestamp ts;
    
    // getter/setter 方法...
}
```

### 自动配置详情

#### Bean 创建

该 starter 会自动创建以下 Bean：

- `tdengineDataSource` - TDengine 数据源
- `tdengineJdbcTemplate` - TDengine 专用的 JdbcTemplate
- `tdengineNamedParameterJdbcTemplate` - TDengine 专用的 NamedParameterJdbcTemplate
- `jdbcTemplatePlus` - 封装的增强 JdbcTemplate
- `tdengineRepository` - TDengine 仓库类

#### 连接池选择逻辑

1. 检测 classpath 中是否存在 Druid 相关类，如果存在则创建 Druid DataSource
2. 如果没有 Druid，检测是否存在 HikariCP，如果存在则创建 HikariCP DataSource
3. 如果没有 HikariCP，检测是否存在 DBCP2，如果存在则创建 DBCP2 DataSource
4. 如果都没有，使用 Spring 的 DriverManagerDataSource

#### 连接池配置

#### 自定义连接池

如果你需要自定义连接池配置，可以创建自己的 DataSource bean。只要将 bean 命名为 `tdengineDataSource`，starter 就会使用你的自定义配置。以下是具体方法：

##### 示例：自定义 HikariCP 配置
```java
@Configuration
public class CustomDataSourceConfig {
    
    @Bean("tdengineDataSource")
    public DataSource tdengineDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:TAOS://localhost:6030/test");
        config.setUsername("root");
        config.setPassword("taosdata");
        config.setDriverClassName("com.taosdata.jdbc.TSDBDriver");
        
        // 自定义连接池配置
        config.setMaximumPoolSize(30);
        config.setMinimumIdle(10);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        
        return new HikariDataSource(config);
    }
}
```

##### 示例：自定义 Druid 配置
```java
@Configuration
public class CustomDataSourceConfig {
    
    @Bean("tdengineDataSource")
    public DataSource tdengineDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:TAOS://localhost:6030/test");
        dataSource.setUsername("root");
        dataSource.setPassword("taosdata");
        dataSource.setDriverClassName("com.taosdata.jdbc.TSDBDriver");
        
        // 自定义连接池配置
        dataSource.setInitialSize(10);
        dataSource.setMaxActive(50);
        dataSource.setMinIdle(10);
        dataSource.setMaxWait(30000);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestWhileIdle(true);
        
        return dataSource;
    }
}
```

##### 示例：自定义 DBCP2 配置
```java
@Configuration
public class CustomDataSourceConfig {
    
    @Bean("tdengineDataSource")
    public DataSource tdengineDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:TAOS://localhost:6030/test");
        dataSource.setUsername("root");
        dataSource.setPassword("taosdata");
        dataSource.setDriverClassName("com.taosdata.jdbc.TSDBDriver");
        
        // 自定义连接池配置
        dataSource.setInitialSize(10);
        dataSource.setMaxTotal(50);
        dataSource.setMinIdle(10);
        dataSource.setMaxIdle(20);
        dataSource.setMaxWaitMillis(30000);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestWhileIdle(true);
        
        return dataSource;
    }
}
```

#### 默认配置

如果不提供自定义的 DataSource bean，starter 将使用以下默认值：

##### Druid 默认配置
- initialSize: 5
- maxActive: 20
- minIdle: 5
- maxWait: 60000ms
- validationQuery: "SELECT 1"
- testWhileIdle: true

##### HikariCP 默认配置
- maximumPoolSize: 20
- minimumIdle: 5
- connectionTimeout: 30000ms
- idleTimeout: 600000ms
- maxLifetime: 1800000ms

##### DBCP2 默认配置
- initialSize: 5
- maxTotal: 20
- minIdle: 5
- maxIdle: 10
- maxWaitMillis: 60000ms

### 禁用自动配置

如果需要禁用 TDengine ORM 的自动配置，可以在配置文件中设置：

```yaml
tdengine-orm:
  enabled: false
```

或者在启动类上排除自动配置：

```java
@SpringBootApplication(exclude = {TDengineOrmAutoConfiguration.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### 注意事项

1. 确保 TDengine 服务正在运行并且连接配置正确
2. 如果同时引入多个连接池，将按照优先级选择一个
3. 连接池的高级配置可以通过自定义 DataSource Bean 来覆盖默认配置
4. 该 starter 与 Spring Boot 的自动配置兼容，不会冲突