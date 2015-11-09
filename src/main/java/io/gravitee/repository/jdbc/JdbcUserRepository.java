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
import io.gravitee.repository.jdbc.converter.UserJpaConverter;
import io.gravitee.repository.jdbc.internal.InternalJpaTeamRepository;
import io.gravitee.repository.jdbc.model.TeamMemberJpa;
import io.gravitee.repository.jdbc.internal.InternalJpaUserRepository;
import io.gravitee.repository.jdbc.model.TeamJpa;
import io.gravitee.repository.jdbc.model.UserJpa;
import io.gravitee.repository.management.api.UserRepository;
import io.gravitee.repository.management.model.User;
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
public class JdbcUserRepository implements UserRepository {

	@Inject
	private InternalJpaUserRepository internalJpaUserRepository;
	@Inject
	private InternalJpaTeamRepository internalJpaTeamRepository;

	@Inject
	private UserJpaConverter userJpaConverter;

	public User create(User user) throws TechnicalException {
		final UserJpa userJpa = internalJpaUserRepository.save(userJpaConverter.convertFrom(user));
		return userJpaConverter.convertTo(userJpa);
	}

	public Optional<User> findByUsername(String username) throws TechnicalException {
		return Optional.ofNullable(userJpaConverter.convertTo(internalJpaUserRepository.findOne(username)));
	}

	public Optional<User> findByEmail(String email) throws TechnicalException {
		return Optional.ofNullable(userJpaConverter.convertTo(internalJpaUserRepository.findByEmail(email)));
	}

	public Set<User> findAll() throws TechnicalException {
		return userJpaConverter.convertAllTo(internalJpaUserRepository.findAll());
	}

	public Set<User> findByTeam(String teamName) throws TechnicalException {
		final TeamJpa team = internalJpaTeamRepository.findOne(teamName);
		if (team == null) {
			throw new IllegalStateException(format("The team '%s' does not exist", teamName));
		}
		final List<TeamMemberJpa> members = team.getMembers();
		final Set<User> users = members.stream().map(
			member -> userJpaConverter.convertTo(member.getMember())).collect(Collectors.toSet()
		);
		return users;
	}
}
