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
package io.gravitee.repository.jpa;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import io.gravitee.repository.api.ApplicationRepository;
import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.jpa.config.AbstractJpaRepositoryTest;
import io.gravitee.repository.model.Application;
import io.gravitee.repository.model.OwnerType;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
public class JpaApplicationRepositoryTest extends AbstractJpaRepositoryTest {

    @Inject
    private ApplicationRepository applicationRepository;

    protected String getXmlDataSetResourceName() {
        return "/data/applications.xml";
    }

    @Test
    public void findAllTest() throws TechnicalException {
        Set<Application> applications = applicationRepository.findAll();

        Assert.assertNotNull(applications);
        Assert.assertEquals("Fail to resolve application in findAll", 3, applications.size());
    }


    @Test
    public void createTest() throws TechnicalException {
        String name = "created-app";

        Application application = new Application();

        application.setName(name);
        application.setDescription("Application description");
        application.setCreator("creator");
        application.setOwnerType(OwnerType.USER);
        application.setOwner("creator");
        application.setType("type");
        application.setCreatedAt(new Date());
        application.setUpdatedAt(new Date());

        applicationRepository.create(application);

        Optional<Application> optionnal = applicationRepository.findByName(name);

        Assert.assertNotNull(optionnal);
        Assert.assertTrue("Application saved not found", optionnal.isPresent());

        Application appSaved = optionnal.get();

        Assert.assertEquals("Invalid application name.", application.getName(), appSaved.getName());
        Assert.assertEquals("Invalid application description.", application.getDescription(), appSaved.getDescription());
        Assert.assertEquals("Invalid application type.", application.getType(), appSaved.getType());
        Assert.assertEquals("Invalid application creator.", application.getCreator(), appSaved.getCreator());
        Assert.assertEquals("Invalid application createdAt.", application.getCreatedAt(), appSaved.getCreatedAt());
        Assert.assertEquals("Invalid application updateAt.", application.getUpdatedAt(), appSaved.getUpdatedAt());
        Assert.assertEquals("Invalid application Owner.", application.getOwner(), appSaved.getOwner());
        Assert.assertEquals("Invalid application OwnerType.", application.getOwnerType(), appSaved.getOwnerType());
    }

    @Test
    public void updateTest() throws TechnicalException {
        String applicationName = "Gravitee.IO";

        Application application = new Application();
        application.setName(applicationName);
        application.setCreator("updater");
        application.setDescription("Updated description");
        //application.setName(name);
        application.setOwner("updater");
        application.setOwnerType(OwnerType.USER);
        application.setType("update-type");
        application.setUpdatedAt(new Date());

        applicationRepository.update(application);


        Optional<Application> optionnal = applicationRepository.findByName(applicationName);
        Assert.assertTrue("Application updated not found", optionnal.isPresent());

        Application appUpdated = optionnal.get();

        Assert.assertEquals("Invalid updated application name.", application.getName(), appUpdated.getName());
        Assert.assertEquals("Invalid updated application description.", application.getDescription(), appUpdated.getDescription());
        Assert.assertEquals("Invalid updated application type.", application.getType(), appUpdated.getType());
        Assert.assertEquals("Invalid updated application updateAt.", application.getUpdatedAt(), appUpdated.getUpdatedAt());
        Assert.assertEquals("Invalid updated application Owner.", application.getOwner(), appUpdated.getOwner());
        Assert.assertEquals("Invalid updated application OwnerType.", application.getOwnerType(), appUpdated.getOwnerType());
        //Check invariant field
        Assert.assertNotEquals("Invalid updated application creator.", application.getCreator(), appUpdated.getCreator());
        Assert.assertNotEquals("Invalid updated application createdAt.", application.getCreatedAt(), appUpdated.getCreatedAt());
    }

    @Test
    public void deleteTest() throws TechnicalException {
        String applicationName = "Gravitee.IO";

        int nbApplicationBefore = applicationRepository.findAll().size();
        applicationRepository.delete(applicationName);

        Optional<Application> optional = applicationRepository.findByName(applicationName);
        int nbApplicationAfter = applicationRepository.findAll().size();

        Assert.assertFalse("Deleted application always present", optional.isPresent());
        Assert.assertEquals("Invalid number of applications after deletion", nbApplicationBefore - 1, nbApplicationAfter);
    }


    @Test
    public void findByNameTest() throws TechnicalException {
        Optional<Application> optional = applicationRepository.findByName("Sirius");
        Assert.assertTrue("Find application by name return no result ", optional.isPresent());
    }

    @Test
    public void findByTeamTest() throws TechnicalException {
        Set<Application> applications = applicationRepository.findByTeam("Gravitee");
        Assert.assertNotNull(applications);
        Assert.assertEquals("Invalid application result in findByOwnerName", 2, applications.size());
    }

    @Test
    public void findByUserTest() throws TechnicalException {
        Set<Application> applications = applicationRepository.findByUser("owner");
        Assert.assertNotNull(applications);
        Assert.assertEquals("Invalid application result in findByUser", 1, applications.size());
    }


    @Test
    public void countByTeamTest() throws TechnicalException {
        int nbApplications = applicationRepository.countByTeam("Gravitee");
        Assert.assertEquals("Invalid application result in countByTeam", 2, nbApplications);
    }

    @Test
    public void countByUserTest() throws TechnicalException {
        int nbApplications = applicationRepository.countByUser("owner");
        Assert.assertEquals("Invalid application result in countByUser", 1, nbApplications);
    }
}
