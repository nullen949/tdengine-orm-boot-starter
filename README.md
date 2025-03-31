## Introduction

> `tdengine-orm-boot-starter` is a semi-ORM framework based on SpringBootJdbc for convenient operation of TDengine data, inspired by MyBatisPlus design

### Tech Stack

- spring-boot-starter 2.X Mainly uses SpringBoot's auto-configuration feature. After SpringBoot 2.7, the auto-configuration method has changed, but the old way remains compatible
- spring-boot-starter-jdbc 2.x Mainly uses the JdbcTemplate object
- MyBatisPlusAnnotation 3.5.x Mainly uses a few annotations, such as @TableName @TableField

## Quick Start

1. Clone the project source code, run `mvn clean install` (or push to your private repository)
2. Add the dependency to your project
    ```xml
    <dependency>
        <groupId>com.github.nullen949</groupId>
        <artifactId>tdengine-orm-boot-starter</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    ```
3. Download Demo project source code: [Demo Repository Link](https://github.com/nullen949/tdengine-orm-demo)
4. Modify the connection configuration in the Demo project's configuration file, create the corresponding database (if not exists)
5. Find the test class and execute the test methods in sequence
6. Return to your project and refer to the Demo's usage pattern for implementation
