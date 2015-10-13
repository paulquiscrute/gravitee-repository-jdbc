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
import io.gravitee.repository.jpa.config.AbstractJpaRepositoryTest;
import io.gravitee.repository.management.api.TeamMembershipRepository;
import io.gravitee.repository.management.model.Member;
import io.gravitee.repository.management.model.Team;
import io.gravitee.repository.management.model.TeamRole;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Date;
import java.util.Set;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
public class JpaTeamMemberRepositoryTest extends AbstractJpaRepositoryTest {

    @Inject
    private TeamMembershipRepository teamMembershipRepository;

    protected String getXmlDataSetResourceName() {
        return "/data/teamMembers.xml";
    }

    @Test
    public void getTeamMembersTest() throws TechnicalException {
        Set<Member> members = teamMembershipRepository.listMembers("Gravitee");

        Assert.assertNotNull("Team members is null", members);
        Assert.assertEquals("Invalid number of member user name.", 2, members.size());
    }

    @Test
    public void addTeamMemberTest() throws TechnicalException {
        String teamName = "Public";
        String memberName = "user";

        Member member = new Member();
        member.setUsername(memberName);
        member.setRole(TeamRole.MEMBER);
        member.setUpdatedAt(new Date());

        teamMembershipRepository.addMember(teamName, member);

        Set<Member> membersUpdated = teamMembershipRepository.listMembers(teamName);

        Assert.assertNotNull("Team members is null", membersUpdated);
        Assert.assertEquals("Invalid number of member user name after adding one.", 1, membersUpdated.size());

        long validMembers = membersUpdated.stream().filter(
            memberUpdated -> memberUpdated.getUsername().equals(memberName) && TeamRole.MEMBER.equals(memberUpdated.getRole())
        ).count();
        Assert.assertEquals("Invalid added member count found", 1, validMembers);

    }

    @Test
    public void updateTeamMemberTest() throws TechnicalException {
        String teamName = "Gravitee";
        String memberName = "user";

        Member member = new Member();
        member.setUsername(memberName);
        member.setRole(TeamRole.ADMIN);
        member.setUpdatedAt(new Date());

        teamMembershipRepository.updateMember(teamName, member);

        Set<Member> membersUpdated = teamMembershipRepository.listMembers(teamName);

        Assert.assertNotNull("Team members is null", teamName);
        Assert.assertEquals("Invalid number of member user name after updating one.", 2, membersUpdated.size());

        long validMembers = membersUpdated.stream().filter(
            memberUpdated -> memberUpdated.getUsername().equals(memberName) && TeamRole.ADMIN.equals(memberUpdated.getRole())
        ).count();
        Assert.assertEquals("Invalid added member count found", 1, validMembers);
    }

    @Test
    public void removeTeamMemberTest() throws TechnicalException {
        String teamName = "Gravitee";
        String memberName = "user";

        teamMembershipRepository.deleteMember(teamName, memberName);

        Set<Member> membersRemaining = teamMembershipRepository.listMembers(teamName);

        Assert.assertNotNull("Team members is null", teamName);
        Assert.assertEquals("Invalid number of member user name after removing one.", 1, membersRemaining.size());

        long validMembers = membersRemaining.stream().filter(
            memberRemaining -> memberRemaining.getUsername().equals(memberName)
        ).count();
        Assert.assertEquals("Invalid added member count found", 0, validMembers);
    }

    @Test
    public void findTeamByUserTest() throws TechnicalException {
        Set<Team> teams = teamMembershipRepository.findByUser("user");
        Assert.assertNotNull("No teams found for the given user", teams);
        Assert.assertEquals("Invalid number of teams found for user", 1, teams.size());
    }


    @Test
    public void getMemberTest() throws TechnicalException {
        Member member = teamMembershipRepository.getMember("Gravitee", "user");
        Assert.assertNotNull("No team member found for the given user and team", member);
    }
}
