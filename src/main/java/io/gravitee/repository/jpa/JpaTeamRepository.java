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

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import io.gravitee.repository.api.management.TeamRepository;
import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.jpa.converter.TeamJpaConverter;
import io.gravitee.repository.jpa.internal.InternalJpaTeamRepository;
import io.gravitee.repository.jpa.model.TeamJpa;
import io.gravitee.repository.model.management.Team;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Repository
public class JpaTeamRepository implements TeamRepository {

	@Inject
	private InternalJpaTeamRepository internalJpaTeamRepository;

	@Inject
	private TeamJpaConverter teamJpaConverter;

	public Set<Team> findAll(boolean publicOnly) throws TechnicalException {
		final List<TeamJpa> teams;
		if (publicOnly) {
			teams = internalJpaTeamRepository.findByPrivateTeam(false);
		} else{
			teams = internalJpaTeamRepository.findAll();
		}
		return teamJpaConverter.convertAllTo(teams);
	}

	public Optional<Team> findByName(String name) throws TechnicalException {
		return Optional.ofNullable(teamJpaConverter.convertTo(internalJpaTeamRepository.findOne(name)));
	}

	public Team create(Team team) throws TechnicalException {
		if (internalJpaTeamRepository.exists(team.getName())) {
			throw new IllegalStateException(format("The team '%s' can not be created cause already exists", team.getName()));
		}
		final TeamJpa teamJpa = internalJpaTeamRepository.save(teamJpaConverter.convertFrom(team));
		return teamJpaConverter.convertTo(teamJpa);
	}

	public Team update(Team team) throws TechnicalException {
		if (!internalJpaTeamRepository.exists(team.getName())) {
			throw new IllegalStateException(format("The team '%s' can not be updated cause does not exist", team.getName()));
		}
		final TeamJpa teamJpa = internalJpaTeamRepository.findOne(team.getName());
		teamJpa.setDescription(team.getDescription());
		teamJpa.setEmail(team.getEmail());
		teamJpa.setPrivateTeam(team.isPrivateTeam());
		teamJpa.setUpdatedAt(team.getUpdatedAt());

		final TeamJpa savedTeam = internalJpaTeamRepository.save(teamJpaConverter.convertFrom(team));
		return teamJpaConverter.convertTo(savedTeam);

	}

	public void delete(String name) throws TechnicalException {
		internalJpaTeamRepository.delete(name);
	}
}
