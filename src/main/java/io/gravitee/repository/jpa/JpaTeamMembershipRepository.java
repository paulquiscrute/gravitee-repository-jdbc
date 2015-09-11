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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import io.gravitee.repository.api.TeamMembershipRepository;
import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.jpa.converter.TeamJpaConverter;
import io.gravitee.repository.jpa.converter.TeamMemberJpaConverter;
import io.gravitee.repository.jpa.internal.InternalJpaTeamMemberRepository;
import io.gravitee.repository.jpa.internal.InternalJpaTeamRepository;
import io.gravitee.repository.jpa.internal.InternalJpaUserRepository;
import io.gravitee.repository.jpa.model.TeamJpa;
import io.gravitee.repository.jpa.model.TeamMemberJpa;
import io.gravitee.repository.model.Member;
import io.gravitee.repository.model.Team;
import io.gravitee.repository.model.TeamRole;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Repository
public class JpaTeamMembershipRepository implements TeamMembershipRepository {

    @Inject
    private InternalJpaUserRepository internalJpaUserRepository;
    @Inject
    private InternalJpaTeamRepository internalJpaTeamRepository;
    @Inject
    private InternalJpaTeamMemberRepository internalJpaTeamMemberRepository;

    @Inject
    private TeamJpaConverter teamJpaConverter;
    @Inject
    private TeamMemberJpaConverter teamMemberJpaConverter;

    public void addMember(String teamName, Member member) throws TechnicalException {
        final TeamMemberJpa teamMemberJpa = teamMemberJpaConverter.convertFrom(member);
        final TeamJpa team = internalJpaTeamRepository.findOne(teamName);
        teamMemberJpa.setTeam(team);

        team.addMember(teamMemberJpa);
        internalJpaTeamRepository.save(team);
    }

    public void updateMember(String teamName, Member member) throws TechnicalException {
        final TeamJpa teamJpa = internalJpaTeamRepository.findOne(teamName);

        final List<TeamMemberJpa> membersJpa = teamJpa.getMembers();

        if (membersJpa != null) {
            membersJpa.forEach(teamMemberJpa -> {
                if (member.getUsername().equals(teamMemberJpa.getMember().getName())) {
                    teamMemberJpa.setRole(String.valueOf(member.getRole()));
                    teamMemberJpa.setUpdatedAt(member.getUpdatedAt());
                }
            });
            internalJpaTeamRepository.save(teamJpa);
        }
    }

    public void deleteMember(String teamName, String username) throws TechnicalException {
        final TeamJpa teamJpa = internalJpaTeamRepository.findOne(teamName);

        final List<TeamMemberJpa> membersJpa = teamJpa.getMembers();

        if(membersJpa != null){
            membersJpa.forEach(teamMemberJpa -> {
                if(username.equals(teamMemberJpa.getMember().getName())){
                    teamJpa.getMembers().remove(teamMemberJpa);
                }
            });
            internalJpaTeamRepository.save(teamJpa);
        }
    }

    public Set<Member> listMembers(String teamName) throws TechnicalException {
        final TeamJpa teamJpa = internalJpaTeamRepository.findOne(teamName);

        final List<TeamMemberJpa> membersJpa = teamJpa.getMembers();

        final Set<Member> members = new HashSet<>();
        membersJpa.stream().forEach(teamMemberJpa -> {
            final Member member = new Member();
            member.setUsername(teamMemberJpa.getMember().getName());
            member.setRole(TeamRole.valueOf(teamMemberJpa.getRole()));
            members.add(member);
        });
        return members;
    }

    public Set<Team> findByUser(String username) throws TechnicalException {
        return teamJpaConverter.convertAllTo(internalJpaTeamRepository.findByMembersMemberName(username));
    }

    public Member getMember(String teamName, String memberName) throws TechnicalException {
        final TeamJpa team = internalJpaTeamRepository.findByNameAndMembersMemberName(teamName, memberName);

        final Optional<TeamMemberJpa> optional = team.getMembers().stream().filter(
            member -> memberName.equalsIgnoreCase(member.getMember().getName())
        ).findFirst();

        if (optional.isPresent()){
            return teamMemberJpaConverter.convertTo(optional.get());
        }
        return null;
    }
}
