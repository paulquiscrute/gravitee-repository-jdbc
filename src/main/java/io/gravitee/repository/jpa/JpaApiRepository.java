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

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import io.gravitee.repository.api.ApiRepository;
import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.jpa.converter.ApiJpaConverter;
import io.gravitee.repository.jpa.internal.InternalJpaApiRepository;
import io.gravitee.repository.model.Api;
import io.gravitee.repository.model.PolicyConfiguration;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Repository
public class JpaApiRepository implements ApiRepository {

	@Inject
	private InternalJpaApiRepository internalJpaApiRepository;

	@Inject
	private ApiJpaConverter apiJpaConverter;

	public Optional<Api> findByName(String apiName) throws TechnicalException {
		return Optional.ofNullable(apiJpaConverter.convertTo(internalJpaApiRepository.findOne(apiName)));
	}

	public Set<Api> findAll() throws TechnicalException {
		return apiJpaConverter.convertAllTo(internalJpaApiRepository.findAll());
	}

	public Set<Api> findByTeam(String teamName, boolean publicOnly) throws TechnicalException {
		return null;
	}

	public Set<Api> findByUser(String username, boolean publicOnly) throws TechnicalException {
		return null;
	}

	public Api create(Api api) throws TechnicalException {
		return null;
	}

	public Api update(Api api) throws TechnicalException {
		return null;
	}

	public void delete(String apiName) throws TechnicalException {

	}

	public int countByUser(String username, boolean publicOnly) throws TechnicalException {
		return 0;
	}

	public int countByTeam(String teamName, boolean publicOnly) throws TechnicalException {
		return 0;
	}

	public void updatePoliciesConfiguration(String apiName, List<PolicyConfiguration> policyConfigurations) throws TechnicalException {

	}

	public void updatePolicyConfiguration(String apiName, PolicyConfiguration policyConfiguration) throws TechnicalException {

	}

	public List<PolicyConfiguration> findPoliciesByApi(String apiName) throws TechnicalException {
		return null;
	}

	public Set<Api> findByCreator(String userName) throws TechnicalException {
		return null;
	}

	public Set<Api> findByApplication(String application) throws TechnicalException {
		return null;
	}
}
