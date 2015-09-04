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

import java.io.InputStreamReader;
import java.io.Reader;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaRepositoryConfiguration.class)
@Transactional(rollbackFor = Throwable.class)
public abstract class AbstractJpaRepositoryTest {

    @Inject
    private DataSource dataSource;

    private static IDatabaseConnection dbUnitConnection;
    private IDataSet dataSet;
    
    /** Specify the data path file. */
    protected abstract String getXmlDataSetResourceName();

    /**
     * Set up database with unit test data.
     * @throws Exception the exception thrown
     */
    @Before
    public void setUpDbUnit() throws Exception {
        dbUnitConnection = new DatabaseConnection(dataSource.getConnection());
        final DatabaseConfig config = dbUnitConnection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
        final String resourceName = getXmlDataSetResourceName();
        final Reader reader = new InputStreamReader(AbstractJpaRepositoryTest.class.getResourceAsStream(resourceName));
        dataSet = new XmlDataSet(reader);
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConnection, dataSet);
    }

    /**
     * Tear down unit test data in database.
     * @throws Exception the exception thrown
     */
    @AfterTransaction
    public void tearDownDbUnit() throws Exception {
        if (dataSet != null) {
             DatabaseOperation.DELETE.execute(dbUnitConnection, dataSet);
        }
        dbUnitConnection.getConnection().close();
    }
}
