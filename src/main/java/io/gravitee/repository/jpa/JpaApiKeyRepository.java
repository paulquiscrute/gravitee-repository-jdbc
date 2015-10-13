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

import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.jpa.converter.ApiKeyJpaConverter;
import io.gravitee.repository.jpa.internal.InternalJpaApiApplicationRepository;
import io.gravitee.repository.jpa.internal.InternalJpaApiKeyRepository;
import io.gravitee.repository.jpa.internal.InternalJpaApiRepository;
import io.gravitee.repository.jpa.internal.InternalJpaApplicationRepository;
import io.gravitee.repository.jpa.model.ApiApplicationJpa;
import io.gravitee.repository.jpa.model.ApiKeyJpa;
import io.gravitee.repository.management.api.ApiKeyRepository;
import io.gravitee.repository.management.model.ApiKey;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Repository
public class JpaApiKeyRepository implements ApiKeyRepository {

	@Inject
	private InternalJpaApiKeyRepository internalJpaApiKeyRepository;
	@Inject
	private InternalJpaApiRepository internalJpaApiRepository;
	@Inject
	private InternalJpaApplicationRepository internalJpaApplicationRepository;
	@Inject
	private InternalJpaApiApplicationRepository internalJpaApiApplicationRepository;

	@Inject
	private ApiKeyJpaConverter apiKeyJpaConverter;

	public Optional<ApiKey> retrieve(String apiKey) throws TechnicalException {
		return Optional.ofNullable(apiKeyJpaConverter.convertTo(internalJpaApiKeyRepository.findOne(apiKey)));
	}

	public ApiKey create(String applicationName, String apiName, ApiKey key) throws TechnicalException {
		final ApiApplicationJpa apiApplicationJpa = new ApiApplicationJpa();
		apiApplicationJpa.setApi(internalJpaApiRepository.findOne(apiName));
		apiApplicationJpa.setApplication(internalJpaApplicationRepository.findOne(applicationName));
		apiApplicationJpa.setKey(apiKeyJpaConverter.convertFrom(key));

		internalJpaApiApplicationRepository.save(apiApplicationJpa);

		return key;
	}

	public ApiKey update(ApiKey key) throws TechnicalException {
		final ApiApplicationJpa apiApplicationJpa = internalJpaApiApplicationRepository.findByKey(key.getKey());
		apiApplicationJpa.getKey().setCreatedAt(key.getCreatedAt());
		apiApplicationJpa.getKey().setExpiration(key.getExpiration());
		apiApplicationJpa.getKey().setRevoked(key.isRevoked());

		internalJpaApiApplicationRepository.save(apiApplicationJpa);

		return key;
	}

	public Set<ApiKey> findByApplicationAndApi(String applicationName, String apiName) throws TechnicalException {
		final List<ApiApplicationJpa> apiApplications =
			internalJpaApiApplicationRepository.findByApplicationNameAndApiName(applicationName, apiName);

		return getApiKeys(apiApplications);
	}

	public Set<ApiKey> findByApplication(String applicationName) throws TechnicalException {
		final List<ApiApplicationJpa> apiApplications =
			internalJpaApiApplicationRepository.findByApplicationName(applicationName);

		return getApiKeys(apiApplications);
	}

	public Set<ApiKey> findByApi(String apiName) throws TechnicalException {
		final List<ApiApplicationJpa> apiApplications =
			internalJpaApiApplicationRepository.findByApiName(apiName);

		return getApiKeys(apiApplications);
	}

	private Set<ApiKey> getApiKeys(List<ApiApplicationJpa> apiApplications) {
		final Set<ApiKeyJpa> apiKeys =
			apiApplications.stream().map(apiApplication -> apiApplication.getKey()).collect(Collectors.toSet());

		return apiKeyJpaConverter.convertAllTo(apiKeys);
	}
}
