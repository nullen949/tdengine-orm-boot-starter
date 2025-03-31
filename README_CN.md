## 介绍

> `tdengine-orm-boot-starter` 是一个基于 SpringBootJdbc 的半 ORM 框架，用于便捷操作 TDengine 数据，其设计参考了 MyBatisPlus

### 技术栈

- spring-boot-starter 2.X：主要使用 SpringBoot 的自动装配功能，虽然 SpringBoot 2.7 之后自动装配方式有所修改，但旧的方式仍然兼容
- spring-boot-starter-jdbc 2.x：主要使用 JdbcTemplate 对象
- MyBatisPlusAnnotation 3.5.x：主要使用其注解功能，如 @TableName、@TableField 等

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