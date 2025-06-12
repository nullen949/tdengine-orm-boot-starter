package com.nullen.tdengineorm.config;

import com.nullen.tdengineorm.repository.TDengineRepository;
import com.nullen.tdengineorm.util.JdbcTemplatePlus;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author Nullen
 */
@EnableConfigurationProperties(TDengineOrmConfig.class)
public class TDengineOrmAutoConfiguration {

    @Bean
    public JdbcTemplatePlus jdbcTemplatePlus(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new JdbcTemplatePlus(namedParameterJdbcTemplate);
    }


    @Bean
    public TDengineRepository tdengineMapper(JdbcTemplatePlus jdbcTemplatePlus, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new TDengineRepository(jdbcTemplatePlus, namedParameterJdbcTemplate);
    }

}
