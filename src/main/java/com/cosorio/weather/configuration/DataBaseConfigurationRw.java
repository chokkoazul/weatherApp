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
        entityManagerFactoryRef = "entityManagerFactoryRW",
        transactionManagerRef = "transactionManagerRW",
        basePackages = {"com.cosorio.weather.repository.rw"}
)
public class DataBaseConfigurationRw {

    @Bean
    @ConfigurationProperties("spring.datasource.rw")
    public DataSource dataSourceRw() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "entityManagerFactoryRW")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryRW(EntityManagerFactoryBuilder builder, @Qualifier("dataSourceRw") DataSource dataSourceRW) {
        return builder
                .dataSource(dataSourceRW)
                .packages("com.cosorio.weather.entity")
                .persistenceUnit("persistenceUnitRW")
                .build();
    }

    @Bean(name = "transactionManagerRW")
    public PlatformTransactionManager transactionManagerRW(@Qualifier("entityManagerFactoryRW") EntityManagerFactory entityManagerFactoryRW) {
        return new JpaTransactionManager(entityManagerFactoryRW);
    }


}
