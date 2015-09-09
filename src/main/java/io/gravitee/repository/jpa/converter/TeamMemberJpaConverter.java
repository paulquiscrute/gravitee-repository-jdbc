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
package io.gravitee.repository.jpa.converter;

import static org.springframework.beans.BeanUtils.copyProperties;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import io.gravitee.repository.jpa.internal.InternalJpaUserRepository;
import io.gravitee.repository.jpa.model.TeamMemberJpa;
import io.gravitee.repository.model.Member;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Component
public class TeamMemberJpaConverter extends AbstractConverter<TeamMemberJpa, Member> {

    @Inject
    private InternalJpaUserRepository internalJpaUserRepository;

    public Member convertTo(final TeamMemberJpa teamMemberJpa) {
        if (teamMemberJpa == null) {
            return null;
        }
        final Member member = new Member();
        copyProperties(teamMemberJpa, member);
        member.setUsername(teamMemberJpa.getMember().getName());
        return member;
    }

    public TeamMemberJpa convertFrom(final Member member) {
        if (member == null) {
            return null;
        }
        final TeamMemberJpa teamMemberJpa = new TeamMemberJpa();
        copyProperties(member, teamMemberJpa);
        teamMemberJpa.setMember(internalJpaUserRepository.findOne(member.getUsername()));
        teamMemberJpa.setRole(member.getRole().name());
        return teamMemberJpa;
    }
}
