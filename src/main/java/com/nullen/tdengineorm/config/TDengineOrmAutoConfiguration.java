package com.nullen.tdengineorm.config;

import com.nullen.tdengineorm.mapper.TDengineMapper;
import com.nullen.tdengineorm.util.JdbcTemplatePlus;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author Nullen
 */
public class TDengineOrmAutoConfiguration {

    @Bean
    public JdbcTemplatePlus jdbcTemplatePlus(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new JdbcTemplatePlus(namedParameterJdbcTemplate);
    }


    @Bean
    public TDengineMapper tdengineMapper(JdbcTemplatePlus jdbcTemplatePlus, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new TDengineMapper(jdbcTemplatePlus, namedParameterJdbcTemplate);
    }

}
