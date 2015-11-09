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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.*;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author David BRASSELY (brasseld at gmail.com)
 */
public class DataSourceFactory implements FactoryBean<DataSource> {

    private final Logger logger = LoggerFactory.getLogger(DataSourceFactory.class);

    @Autowired
    private Environment environment;

    private final String propertyPrefix;

    public DataSourceFactory(String propertyPrefix) {
        this.propertyPrefix = propertyPrefix + ".jdbc.";
    }

    @Override
    public DataSource getObject() throws Exception {
        HikariConfig dsConfig = new HikariConfig();

        dsConfig.setDataSourceClassName(readPropertyValue(propertyPrefix + "dataSourceClassName"));
        dsConfig.setUsername(readPropertyValue(propertyPrefix + "user"));
        dsConfig.setPassword(readPropertyValue(propertyPrefix + "password"));
        dsConfig.setJdbcUrl(readPropertyValue(propertyPrefix + "jdbcUrl"));
        dsConfig.setAutoCommit(readPropertyValue(propertyPrefix + "autoCommit", boolean.class, true));

        String dsPropertiesPrefix = propertyPrefix + "properties.";
        logger.info("Looking for datasource properties prefixed with {}", dsPropertiesPrefix);

        Map<String, Object> datasourceProperties = getPropertiesStartingWith((ConfigurableEnvironment) environment, dsPropertiesPrefix);
        for(Map.Entry<String, Object> property : datasourceProperties.entrySet()) {
            String dsProperty = property.getKey().substring(dsPropertiesPrefix.length());
            logger.info("\tAdd datasource property {} with value {}", dsProperty, property.getValue());
            dsConfig.addDataSourceProperty(dsProperty, property.getValue());
        }

        dsConfig.setMaximumPoolSize(readPropertyValue(propertyPrefix + "pool.maxPoolSize", int.class, 10));
        dsConfig.setMinimumIdle(readPropertyValue(propertyPrefix + "pool.minIdle", int.class, 0));
        dsConfig.setIdleTimeout(readPropertyValue(propertyPrefix + "pool.idleTimeout", long.class, 600000l));
        dsConfig.setConnectionTimeout(readPropertyValue(propertyPrefix + "pool.connectionTimeout", long.class, 30000l));

        return new HikariDataSource(dsConfig);
    }

    @Override
    public Class<?> getObjectType() {
        return DataSource.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private String readPropertyValue(String propertyName) {
        return readPropertyValue(propertyName, String.class, null);
    }

    private <T> T readPropertyValue(String propertyName, Class<T> propertyType) {
        return readPropertyValue(propertyName, propertyType, null);
    }

    private <T> T readPropertyValue(String propertyName, Class<T> propertyType, T defaultValue) {
        T value = environment.getProperty(propertyName, propertyType, defaultValue);
        logger.debug("Read property {}: {}", propertyName, value);
        return value;
    }

    public Map<String, Object> getPropertiesStartingWith( ConfigurableEnvironment aEnv,
                                                                String aKeyPrefix )
    {
        Map<String,Object> result = new HashMap<>();

        Map<String,Object> map = getAllProperties( aEnv );

        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            String key = entry.getKey();

            if ( key.startsWith( aKeyPrefix ) )
            {
                result.put( key, entry.getValue() );
            }
        }

        return result;
    }

    public Map<String,Object> getAllProperties( ConfigurableEnvironment aEnv )
    {
        Map<String,Object> result = new HashMap<>();
        aEnv.getPropertySources().forEach(ps -> addAll(result, getAllProperties(ps)));
        return result;
    }

    public Map<String,Object> getAllProperties( PropertySource<?> aPropSource )
    {
        Map<String,Object> result = new HashMap<>();

        if ( aPropSource instanceof CompositePropertySource)
        {
            CompositePropertySource cps = (CompositePropertySource) aPropSource;
            cps.getPropertySources().forEach( ps -> addAll( result, getAllProperties( ps ) ) );
            return result;
        }

        if ( aPropSource instanceof EnumerablePropertySource<?>)
        {
            EnumerablePropertySource<?> ps = (EnumerablePropertySource<?>) aPropSource;
            Arrays.asList(ps.getPropertyNames()).forEach( key -> result.put( key, ps.getProperty( key ) ) );
            return result;
        }

        // note: Most descendants of PropertySource are EnumerablePropertySource. There are some
        // few others like JndiPropertySource or StubPropertySource
        logger.debug("Given PropertySource is instanceof " + aPropSource.getClass().getName()
                + " and cannot be iterated" );

        return result;

    }

    private void addAll( Map<String, Object> aBase, Map<String, Object> aToBeAdded )
    {
        for (Map.Entry<String, Object> entry : aToBeAdded.entrySet())
        {
            if ( aBase.containsKey( entry.getKey() ) )
            {
                continue;
            }

            aBase.put( entry.getKey(), entry.getValue() );
        }
    }
}
