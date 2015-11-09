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
package io.gravitee.repository.jdbc;

import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.jdbc.config.AbstractJdbcRepositoryTest;
import io.gravitee.repository.management.api.ApiRepository;
import io.gravitee.repository.management.model.Api;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
public class JdbcApiRepositoryTest extends AbstractJdbcRepositoryTest {

    @Inject
    private ApiRepository apiRepository;

    protected String getXmlDataSetResourceName() {
        return "/data/apis.xml";
    }

    @Test
    public void shouldFindByName() throws TechnicalException {
        final Optional<Api> api = apiRepository.findByName("teams");
        assertTrue(api.isPresent());
    }

    @Test
    public void shouldNotFindByName() throws TechnicalException {
        final Optional<Api> api = apiRepository.findByName("notExists");
        assertFalse(api.isPresent());
    }

    @Test
    public void shouldFindAll() throws TechnicalException {
        final Set<Api> apis = apiRepository.findAll();
        assertNotNull(apis);
        assertEquals(4, apis.size());
    }

    @Test
    public void shouldFindByTeam() throws TechnicalException {
        final Set<Api> apis = apiRepository.findByTeam("Gravitee", false);
        assertNotNull(apis);
        assertEquals(2, apis.size());
    }

    @Test
    public void shouldFindByTeamPublicOnly() throws TechnicalException {
        final Set<Api> apis = apiRepository.findByTeam("Gravitee", true);
        assertNotNull(apis);
        assertEquals(1, apis.size());
        assertEquals("users", apis.iterator().next().getName());
    }

    @Test
    public void shouldFindByUser() throws TechnicalException {
        final Set<Api> apis = apiRepository.findByUser("User", false);
        assertNotNull(apis);
        assertEquals(2, apis.size());
    }

    @Test
    public void shouldFindByUserPublicOnly() throws TechnicalException {
        final Set<Api> apis = apiRepository.findByUser("User", true);
        assertNotNull(apis);
        assertEquals(1, apis.size());
        assertEquals("stores", apis.iterator().next().getName());
    }

    @Test
    public void shouldCreate() throws TechnicalException {
        final int numberOfApis = apiRepository.findAll().size();
        final Api api = new Api();
        api.setName("newApi");
        final Api newApi = apiRepository.create(api);
        assertNotNull(newApi);
        assertEquals(numberOfApis + 1, apiRepository.findAll().size());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotCreate() throws TechnicalException {
        final Api existingApi = new Api();
        existingApi.setName("teams");
        apiRepository.create(existingApi);
    }

    @Test
    public void shouldUpdate() throws TechnicalException {
        final int numberOfApis = apiRepository.findAll().size();
        final Optional<Api> fetchedApi = apiRepository.findByName("teams");
        assertTrue(fetchedApi.isPresent());
        final Api api = fetchedApi.get();
        api.setDescription("toto");
        final Api updatedApi = apiRepository.update(api);
        assertNotNull(updatedApi);
        assertEquals("toto", updatedApi.getDescription());
        assertEquals("toto", apiRepository.findByName("teams").get().getDescription());
        assertEquals(numberOfApis, apiRepository.findAll().size());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotUpdate() throws TechnicalException {
        final Api newApi = new Api();
        newApi.setName("newApi");
        apiRepository.update(newApi);
    }

    @Test
    public void shouldDelete() throws TechnicalException {
        final int numberOfApis = apiRepository.findAll().size();
        apiRepository.delete("teams");
        assertFalse(apiRepository.findByName("teams").isPresent());
        assertEquals(numberOfApis - 1, apiRepository.findAll().size());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotDelete() throws TechnicalException {
        apiRepository.delete("notExists");
    }

    @Test
    public void shouldCountByTeam() throws TechnicalException {
        assertEquals(2, apiRepository.countByTeam("Gravitee", false));
    }

    @Test
    public void shouldCountByTeamPublicOnly() throws TechnicalException {
        assertEquals(1, apiRepository.countByTeam("Gravitee", true));
    }

    @Test
    public void shouldCountByUser() throws TechnicalException {
        assertEquals(2, apiRepository.countByUser("User", false));
    }

    @Test
    public void shouldCountByUserPublicOnly() throws TechnicalException {
        assertEquals(1, apiRepository.countByUser("User", true));
    }

    @Test
    public void shouldFindByCreator() throws TechnicalException {
        final Set<Api> apis = apiRepository.findByCreator("admin");
        assertNotNull(apis);
        assertEquals(3, apis.size());
    }

    @Test
    public void shouldFindByApplication() throws TechnicalException {
        final Set<Api> apis = apiRepository.findByApplication("Gravitee.IO");
        assertNotNull(apis);
        assertEquals(2, apis.size());
    }
}
