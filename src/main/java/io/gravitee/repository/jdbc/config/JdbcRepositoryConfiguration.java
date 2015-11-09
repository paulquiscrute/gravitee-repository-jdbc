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
package io.gravitee.repository.jdbc.config;

import io.gravitee.repository.Scope;
import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Configuration
@ComponentScan("io.gravitee.repository.jdbc")
@EnableJpaRepositories(
    basePackages = "io.gravitee.repository.jdbc",
    entityManagerFactoryRef = "graviteeEntityManagerFactory",
    transactionManagerRef = "graviteeTransactionManager"
)
public class JdbcRepositoryConfiguration {

    @Value("${management.jdbc.showSql:#{false}}")
    private String showSql;

    @Bean
    public DataSourceFactory dataSourceFactory() {
        return new DataSourceFactory(Scope.MANAGEMENT.getName());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean graviteeEntityManagerFactory(DataSource dataSource) {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.show_sql", showSql);

        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPackagesToScan("io.gravitee.repository.jdbc.model");
        entityManagerFactoryBean.setJpaProperties(hibernateProperties);
        entityManagerFactoryBean.setPersistenceProvider(new HibernatePersistenceProvider());
        entityManagerFactoryBean.setPersistenceUnitName("graviteePU");
        entityManagerFactoryBean.setDataSource(dataSource);
        return entityManagerFactoryBean;
    }

    @Bean
    public AbstractPlatformTransactionManager graviteeTransactionManager(
            LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());
        return transactionManager;
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        final SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:liquibase/master.yml");
        return liquibase;
    }
}
