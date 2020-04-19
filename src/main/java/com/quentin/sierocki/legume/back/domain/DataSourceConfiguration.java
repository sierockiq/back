package com.quentin.sierocki.legume.back.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.quentin.sierocki.legume.back.domain.entity.UserDAO;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
@PropertySource({"classpath:datasource.properties"})
@EnableTransactionManagement
public class DataSourceConfiguration {
	
	@Autowired
	EntityManagerFactoryBuilder builder;
	
	@Bean
	@Primary
	@ConfigurationProperties("legume.datasource")
	public HikariConfig legumeDataSourceProperties() {
		return new HikariConfig();
	}

	@Bean
	@Primary
	public HikariDataSource legumeDataSource() {
		HikariDataSource ds = new HikariDataSource(legumeDataSourceProperties());
		return ds;
	}
	

	
	@Bean("entityManagerFactory")
	@Primary
	public LocalContainerEntityManagerFactoryBean legumeEntityManagerFactory() {
		return builder
				.dataSource(legumeDataSource())
				.packages(UserDAO.class)
				.persistenceUnit("legumePU")
				.build();
	}
	

	
	@Bean(name="transactionManager")
	@Primary
    public PlatformTransactionManager robynTransactionManager() {
        JpaTransactionManager transactionManager  = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(legumeEntityManagerFactory().getObject());
        return transactionManager;
    }
	

	
}
