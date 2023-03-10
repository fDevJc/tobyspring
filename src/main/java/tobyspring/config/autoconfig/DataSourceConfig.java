package tobyspring.config.autoconfig;

import java.sql.Driver;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import tobyspring.config.ConditionalMyOnClass;
import tobyspring.config.EnableMyConfigurationProperties;
import tobyspring.config.MyAutoConfiguration;

@MyAutoConfiguration
@ConditionalMyOnClass("org.springframework.jdbc.core.JdbcOperations")
@EnableMyConfigurationProperties(MyDataSourceProperties.class)
@EnableTransactionManagement
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

	@Bean
	@ConditionalOnSingleCandidate(DataSource.class)	//Bean생성시 DataSource 타입의 빈이 하나만 존재할 때 그걸 사용하겠다
	@ConditionalOnMissingBean
	JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	@ConditionalOnSingleCandidate(DataSource.class)	//Bean생성시 DataSource 타입의 빈이 하나만 존재할 때 그걸 사용하겠다
	@ConditionalOnMissingBean
	JdbcTransactionManager jdbcTransactionManager(DataSource dataSource) {
		return new JdbcTransactionManager(dataSource);
	}
}
