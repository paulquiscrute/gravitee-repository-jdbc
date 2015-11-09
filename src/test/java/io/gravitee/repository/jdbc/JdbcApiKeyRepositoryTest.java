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
import io.gravitee.repository.management.api.ApiKeyRepository;
import io.gravitee.repository.management.model.ApiKey;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
public class JdbcApiKeyRepositoryTest extends AbstractJdbcRepositoryTest {

    @Inject
    private ApiKeyRepository apiKeyRepository;

    protected String getXmlDataSetResourceName() {
        return "/data/apiKeys.xml";
    }

    @Test
    public void createKeyTest() throws TechnicalException {
        String apiName = "teams";
        String applicationName = "Gravitee.IO";
        String key = UUID.randomUUID().toString();

        ApiKey apiKey = new ApiKey();
        apiKey.setKey(key);
        apiKey.setExpiration(new Date());

        apiKeyRepository.create(applicationName, apiName, apiKey);

        Optional<ApiKey> optional = apiKeyRepository.retrieve(key);
        assertTrue("ApiKey not found", optional.isPresent());

        ApiKey keyFound = optional.get();

        assertNotNull("ApiKey not found", keyFound);

        assertEquals("Key value saved doesn't match", apiKey.getKey(), keyFound.getKey());
        assertEquals("Key expiration doesn't match", apiKey.getExpiration(), keyFound.getExpiration());
    }

    @Test
    public void retrieveKeyTest() throws TechnicalException {
        String key = "d449098d-8c31-4275-ad59-8dd707865a33";

        Optional<ApiKey> optional = apiKeyRepository.retrieve(key);

        assertTrue("ApiKey not found", optional.isPresent());

        ApiKey keyFound = optional.get();
        assertNotNull("ApiKey not found", keyFound);

    }

    @Test
    public void retrieveMissingKeyTest() throws TechnicalException {
        String key = "d449098d-8c31-4275-ad59-000000000";

        Optional<ApiKey> optional = apiKeyRepository.retrieve(key);

        assertFalse("Invalid ApiKey found", optional.isPresent());
    }

    @Test
    public void findByApplicationTest() throws TechnicalException {
        Set<ApiKey> apiKeys = apiKeyRepository.findByApplication("Gravitee.IO");

        assertNotNull("ApiKey not found", apiKeys);
        assertEquals("Invalid number of ApiKey found", 2, apiKeys.size());

    }

    @Test
    public void findByApplicationNoResult() throws TechnicalException {
        Set<ApiKey> apiKeys = apiKeyRepository.findByApplication("application-no-api-key");
        assertNotNull("ApiKey Set is null", apiKeys);

        assertTrue("Api found on application with no api", apiKeys.isEmpty());
    }

    @Test
    public void findByApplicationAndApi() throws TechnicalException {
        Set<ApiKey> apiKeys = apiKeyRepository.findByApplicationAndApi("Gravitee.IO", "stores");

        assertNotNull("ApiKey Set is null", apiKeys);
        assertEquals("Invalid number of ApiKey found", 1, apiKeys.size());
    }
}
