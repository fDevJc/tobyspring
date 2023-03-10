package tobyspring.config.autoconfig;

import java.sql.Driver;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.zaxxer.hikari.HikariDataSource;

import tobyspring.config.ConditionalMyOnClass;
import tobyspring.config.EnableMyConfigurationProperties;
import tobyspring.config.MyAutoConfiguration;

@MyAutoConfiguration
@ConditionalMyOnClass("org.springframework.jdbc.core.JdbcOperations")
@EnableMyConfigurationProperties(MyDataSourceProperties.class)
public class DataSourceConfig {

	@Bean
	@ConditionalMyOnClass("com.zaxxer.hikari.HikariDataSource")
	@ConditionalOnMissingBean
	DataSource hikariDataSource(MyDataSourceProperties dataSourceProperties) {
		HikariDataSource dataSource = new HikariDataSource();

		dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
		dataSource.setJdbcUrl(dataSourceProperties.getUrl());
		dataSource.setUsername(dataSourceProperties.getUsername());
		dataSource.setPassword(dataSourceProperties.getPassword());

		return dataSource;
	}

	@Bean
	@ConditionalOnMissingBean
	DataSource dataSource(MyDataSourceProperties dataSourceProperties) throws ClassNotFoundException {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();	//Connection pool X, 매번 새로운 connection 생성

		dataSource.setDriverClass((Class<? extends Driver>)Class.forName(dataSourceProperties.getDriverClassName()));
		dataSource.setUrl(dataSourceProperties.getUrl());
		dataSource.setUsername(dataSourceProperties.getUsername());
		dataSource.setPassword(dataSourceProperties.getPassword());

		return dataSource;
	}
}
