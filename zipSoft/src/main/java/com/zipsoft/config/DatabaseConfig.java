package com.zipsoft.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfig {
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	@Bean
	public DataSource dataSource() {
		DataSource dataSource = new HikariDataSource(hikariConfig());
		System.out.println("datasource :  " + dataSource);
		
		return dataSource;
	}
	
	
	@Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        System.out.println("DatabaseConfig sqlSessionFactory");
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        Resource[] arrResource = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml");

        sqlSessionFactoryBean.setMapperLocations(arrResource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.zipsoft.*");

        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);

        return (SqlSessionFactory) sqlSessionFactoryBean.getObject();

    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
	
}
