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
import io.gravitee.repository.management.api.PageRepository;
import io.gravitee.repository.management.model.Page;
import io.gravitee.repository.management.model.PageType;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
public class JdbcPageRepositoryTest extends AbstractJdbcRepositoryTest {

    @Inject
    private PageRepository pageRepository;

    protected String getXmlDataSetResourceName() {
        return "/data/pages.xml";
    }

    @Test
    public void shouldCreate() throws TechnicalException {
        final Page page = new Page();
        page.setName("newPage");
        page.setType(PageType.RAML);
        final Page newPage = pageRepository.create(page);
        assertEquals(page.getName(), newPage.getName());
        assertTrue(pageRepository.findByName(page.getName()).isPresent());
    }

    @Test
    public void shouldUpdate() throws TechnicalException {
        final Page page = pageRepository.findByName("page test").get();
        page.setApiName("apiName");
        final Page updatedPage = pageRepository.update(page);
        assertEquals(page.getName(), updatedPage.getName());
        assertEquals(page.getApiName(), updatedPage.getApiName());
    }

    @Test
    public void shouldDelete() throws TechnicalException {
        final String name = "page test";
        assertTrue(pageRepository.findByName(name).isPresent());
        pageRepository.delete(name);
        assertFalse(pageRepository.findByName(name).isPresent());
    }

    @Test
    public void shouldFindByName() throws TechnicalException {
        assertTrue(pageRepository.findByName("page test").isPresent());
    }

    @Test
    public void shouldFindByApiName() throws TechnicalException {
        assertEquals(2, pageRepository.findByApi("tests").size());
    }

    @Test
    public void shouldFindMaxPageOrderByApiName() throws TechnicalException {
        assertEquals(1, pageRepository.findMaxPageOrderByApiName("tests").intValue());
    }
}
