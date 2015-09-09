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

import static java.lang.String.format;

import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import io.gravitee.repository.api.ApplicationRepository;
import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.jpa.converter.ApplicationJpaConverter;
import io.gravitee.repository.jpa.internal.InternalJpaApplicationRepository;
import io.gravitee.repository.jpa.internal.InternalJpaTeamRepository;
import io.gravitee.repository.jpa.internal.InternalJpaUserRepository;
import io.gravitee.repository.jpa.model.ApplicationJpa;
import io.gravitee.repository.model.Application;
import io.gravitee.repository.model.OwnerType;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Repository
public class JpaApplicationRepository implements ApplicationRepository {

	@Inject
	private InternalJpaApplicationRepository internalJpaApplicationRepository;
	@Inject
	private InternalJpaUserRepository internalJpaUserRepository;
	@Inject
	private InternalJpaTeamRepository internalJpaTeamRepository;

	@Inject
	private ApplicationJpaConverter applicationJpaConverter;

	public Set<Application> findAll() throws TechnicalException {
		return applicationJpaConverter.convertAllTo(internalJpaApplicationRepository.findAll());
	}

	public Set<Application> findByTeam(String teamName) throws TechnicalException {
		return applicationJpaConverter.convertAllTo(internalJpaApplicationRepository.findByOwnerName(teamName));
	}

	public Set<Application> findByUser(String userName) throws TechnicalException {
		return applicationJpaConverter.convertAllTo(internalJpaApplicationRepository.findByOwnerName(userName));
	}

	public Application create(Application application) throws TechnicalException {
		if (internalJpaApplicationRepository.exists(application.getName())) {
			throw new TechnicalException(format("The application '%s' can not be created cause already exists", application.getName()));
		}
		final ApplicationJpa applicationJpa =
			internalJpaApplicationRepository.save(applicationJpaConverter.convertFrom(application));
		return applicationJpaConverter.convertTo(applicationJpa);
	}

	public Application update(Application application) throws TechnicalException {
		if (!internalJpaApplicationRepository.exists(application.getName())) {
			throw new TechnicalException(format("The application '%s' can not be updated cause does not exist", application.getName()));
		}
		final ApplicationJpa applicationJpa = internalJpaApplicationRepository.findOne(application.getName());

		//Update, but don't change invariant other creation information
		applicationJpa.setDescription(application.getDescription());
		applicationJpa.setUpdatedAt(application.getUpdatedAt());
		applicationJpa.setType(application.getType());

		if (OwnerType.USER.equals(application.getOwnerType())) {
			applicationJpa.setOwner(internalJpaUserRepository.findOne(application.getOwner()));
		} else {
			applicationJpa.setOwner(internalJpaTeamRepository.findOne(application.getOwner()));
		}

		return applicationJpaConverter.convertTo(internalJpaApplicationRepository.save(applicationJpa));
	}

	public Optional<Application> findByName(String applicationName) throws TechnicalException {
		return Optional.ofNullable(applicationJpaConverter.convertTo(internalJpaApplicationRepository.findOne(applicationName)));
	}

	public void delete(String applicationName) throws TechnicalException {
		internalJpaApplicationRepository.delete(applicationName);
	}

	public int countByUser(String userName) throws TechnicalException {
		return internalJpaApplicationRepository.countByOwnerName(userName);
	}

	public int countByTeam(String teamName) throws TechnicalException {
		return internalJpaApplicationRepository.countByOwnerName(teamName);
	}
}
