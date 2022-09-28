package com.example.db2.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "db2Factory", transactionManagerRef = "db2Manager", basePackages = {
        "${datasource2.repository.packageName}"})

public class TaskConfig {

    @Value("${datasource2.entity.packageName}")
    private String entityPackageName;

    @Bean(name = "db2")
    @ConfigurationProperties(prefix = "db2.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "db2Factory")
    public LocalContainerEntityManagerFactoryBean barEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("db2") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages(entityPackageName)
                .persistenceUnit("db2")
                .properties(new HashMap<String, String>() {
                    {
                        put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
                        put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
                    }
                })
                .build();
    }

    @Bean(name = "db2Manager")
    public PlatformTransactionManager productTransactionManager(
            @Qualifier("db2Factory") EntityManagerFactory productEntityManagerFactory) {
        return new JpaTransactionManager(productEntityManagerFactory);
    }
}