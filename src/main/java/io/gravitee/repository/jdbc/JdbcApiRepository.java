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
import io.gravitee.repository.jdbc.converter.ApiJpaConverter;
import io.gravitee.repository.jdbc.internal.InternalJpaApiApplicationRepository;
import io.gravitee.repository.jdbc.internal.InternalJpaApiRepository;
import io.gravitee.repository.jdbc.model.ApiApplicationJpa;
import io.gravitee.repository.management.api.ApiRepository;
import io.gravitee.repository.management.model.Api;
import io.gravitee.repository.management.model.OwnerType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Repository
public class JdbcApiRepository implements ApiRepository {

	@Inject
	private InternalJpaApiRepository internalJpaApiRepository;
	@Inject
	private InternalJpaApiApplicationRepository internalJpaApiApplicationRepository;

	@Inject
	private ApiJpaConverter apiJpaConverter;

	public Optional<Api> findByName(String apiName) throws TechnicalException {
		return Optional.ofNullable(apiJpaConverter.convertTo(internalJpaApiRepository.findOne(apiName)));
	}

	public Set<Api> findAll() throws TechnicalException {
		return apiJpaConverter.convertAllTo(internalJpaApiRepository.findAll());
	}

	public Set<Api> findByTeam(String teamName, boolean publicOnly) throws TechnicalException {
		return findByOwner(teamName, OwnerType.TEAM, publicOnly);
	}

	public Set<Api> findByUser(String username, boolean publicOnly) throws TechnicalException {
		return findByOwner(username, OwnerType.USER, publicOnly);
	}

	private Set<Api> findByOwner(String name, OwnerType ownerType, boolean publicOnly) {
		if (publicOnly) {
			return apiJpaConverter.convertAllTo(
				internalJpaApiRepository.findByOwnerAndOwnerTypeAndPrivateApi(name, ownerType, false)
			);
		} else {
			return apiJpaConverter.convertAllTo(
				internalJpaApiRepository.findByOwnerAndOwnerType(name, ownerType)
			);
		}
	}

	public Api create(Api api) throws TechnicalException {
		return save(api, true);
	}

	public Api update(Api api) throws TechnicalException {
		return save(api, false);
	}

	private Api save(Api api, boolean creationMode) {
		final boolean exists = internalJpaApiRepository.exists(api.getName());
		if (creationMode && exists) {
			throw new IllegalStateException(format("The API '%s' can not be created cause already exists", api.getName()));
		} else if (!creationMode && !exists) {
			throw new IllegalStateException(format("The API '%s' can not be updated cause does not exist", api.getName()));
		}
		return apiJpaConverter.convertTo(
			internalJpaApiRepository.save(apiJpaConverter.convertFrom(api))
		);
	}

	public void delete(String apiName) throws TechnicalException {
		try {
			internalJpaApiRepository.delete(apiName);
		} catch (final EmptyResultDataAccessException e) {
			throw new IllegalStateException(format("Error while deleting api '%s'", apiName), e);
		}
	}

	public int countByUser(String username, boolean publicOnly) throws TechnicalException {
		return countByOwner(username, OwnerType.USER, publicOnly);
	}

	public int countByTeam(String teamName, boolean publicOnly) throws TechnicalException {
		return countByOwner(teamName, OwnerType.TEAM, publicOnly);
	}

	private int countByOwner(String name, OwnerType ownerType, boolean publicOnly) {
		if (publicOnly) {
			return internalJpaApiRepository.countByOwnerAndOwnerTypeAndPrivateApi(name, ownerType, false);
		} else {
			return internalJpaApiRepository.countByOwnerAndOwnerType(name, ownerType);
		}
	}

	public Set<Api> findByCreator(String userName) throws TechnicalException {
		return internalJpaApiRepository.findByCreator(userName);
	}

	public Set<Api> findByApplication(String application) throws TechnicalException {
		final List<ApiApplicationJpa> apiApplications = internalJpaApiApplicationRepository.findByApplicationName(application);

		return apiApplications.stream().map(
			apiApplication -> apiJpaConverter.convertTo(apiApplication.getApi())
		).collect(Collectors.toSet());
	}
}
