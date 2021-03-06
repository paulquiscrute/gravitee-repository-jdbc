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

import java.util.Properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@Import(JdbcRepositoryConfiguration.class)
public class JdbcRepositoryConfigurationTest {

    @Bean
    public static PropertySourcesPlaceholderConfigurer graviteePropertyPlaceholderConfigurer() {
        final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();

        propertySourcesPlaceholderConfigurer.setProperties(graviteeProperties());
        propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);

        return propertySourcesPlaceholderConfigurer;
    }

    @Bean
    public static Properties graviteeProperties() {
        final YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        final Resource yamlResource = new ClassPathResource("gravitee.yml");
        yaml.setResources(yamlResource);
        return yaml.getObject();
    }

    @Bean
    public static PropertySourceBeanProcessor propertySourceBeanProcessor(Environment environment) {
        // Using this we are now able to use {@link org.springframework.core.env.Environment} in Spring beans
        return new PropertySourceBeanProcessor(graviteeProperties(), environment);
    }
}
