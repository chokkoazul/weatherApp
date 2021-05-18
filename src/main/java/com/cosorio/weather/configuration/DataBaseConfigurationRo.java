package com.cosorio.weather.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
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

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryRO",
        transactionManagerRef = "transactionManagerRO",
        basePackages = {"com.cosorio.weather.repository.ro"}
)
public class DataBaseConfigurationRo {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.ro")
    public DataSource dataSourceRo() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "entityManagerFactoryRO")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryRO(EntityManagerFactoryBuilder builder, @Qualifier("dataSourceRo") DataSource dataSourceRO) {
        return builder
                .dataSource(dataSourceRO)
                .packages("com.cosorio.weather.entity")
                .persistenceUnit("persistenceUnitRO")
                .build();
    }

    @Primary
    @Bean(name = "transactionManagerRO")
    public PlatformTransactionManager transactionManagerRO(@Qualifier("entityManagerFactoryRO") EntityManagerFactory entityManagerFactoryRO) {
        return new JpaTransactionManager(entityManagerFactoryRO);
    }

}
