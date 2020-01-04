package com.evosys.db2.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "db2Factory", transactionManagerRef = "db2Manager", basePackages = {
        "${datasource2.repository.packageName}" })

public class TaskConfig {
    
    @Value("${datasource2.entity.packageName}")
    private String entityPackageName;
    
    @Bean(name = "productDataSource")
    @ConfigurationProperties(prefix = "db2.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean(name = "db2Factory")
    public LocalContainerEntityManagerFactoryBean barEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("productDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages(entityPackageName)
                .persistenceUnit("db2")
                .build();
    }
    
    @Bean(name = "db2Manager")
    public PlatformTransactionManager productTransactionManager(
            @Qualifier("db2Factory") EntityManagerFactory productEntityManagerFactory) {
        return new JpaTransactionManager(productEntityManagerFactory);
    }
}
