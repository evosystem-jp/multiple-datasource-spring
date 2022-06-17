package com.example.db1.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "db1Factory", transactionManagerRef = "db1Manager", basePackages = {
        "${datasource1.repository.packageName}" })
public class UserConfig {

    @Value("${datasource1.entity.packageName}")
    private String entityPackageName;

    @Primary
    @Bean(name = "db1")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "db1Factory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("db1") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages(entityPackageName)
                .persistenceUnit("db1")
                .build();
    }

    @Primary
    @Bean(name = "db1Manager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("db1Factory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}
