/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.repository.jpa.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import liquibase.integration.spring.SpringLiquibase;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Configuration
@ComponentScan("io.gravitee.repository.jpa")
@EnableJpaRepositories(
    basePackages = "io.gravitee.repository.jpa",
    entityManagerFactoryRef = "graviteeEntityManagerFactory",
    transactionManagerRef = "graviteeTransactionManager"
)
public class JpaRepositoryConfiguration {

    @Value("${repository.jpa.hibernateDialect:#{null}}")
    private String hibernateDialect;
    @Value("${repository.jpa.showSql:#{false}}")
    private String showSql;

    @Value("${repository.jpa.driverClassName:#{null}}")
    private String driverClassName;
    @Value("${repository.jpa.url:#{null}}")
    private String url;
    @Value("${repository.jpa.username:#{null}}")
    private String username;
    @Value("${repository.jpa.password:#{null}}")
    private String password;


    @Bean
    public DataSource graviteeDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean graviteeEntityManagerFactory() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", hibernateDialect);
        hibernateProperties.put("hibernate.show_sql", showSql);

        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPackagesToScan("io.gravitee.repository.jpa.model");
        entityManagerFactoryBean.setJpaProperties(hibernateProperties);
        entityManagerFactoryBean.setPersistenceProvider(new HibernatePersistenceProvider());
        entityManagerFactoryBean.setPersistenceUnitName("graviteePU");
        entityManagerFactoryBean.setDataSource(graviteeDataSource());
        return entityManagerFactoryBean;
    }

    @Bean
    public AbstractPlatformTransactionManager graviteeTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(graviteeEntityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public SpringLiquibase liquibase() {
        final SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(graviteeDataSource());
        liquibase.setChangeLog("classpath:liquibase/master.yml");
        return liquibase;
    }
}
