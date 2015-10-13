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
import io.gravitee.repository.management.api.TeamRepository;
import io.gravitee.repository.management.model.Team;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
public class JpaTeamRepositoryTest extends AbstractJpaRepositoryTest {

    @Inject
    private TeamRepository teamRepository;

    protected String getXmlDataSetResourceName() {
        return "/data/teams.xml";
    }

    @Test
    public void createTeamTest() throws TechnicalException {
        String teamname = "team-created";

        final Team team = new Team();
        team.setName(teamname);
        team.setEmail(String.format("%s@gravitee.io", teamname));
        team.setDescription("Sample description");
        team.setPrivateTeam(true);
        team.setCreatedAt(new Date());
        team.setUpdatedAt(new Date());

        Team userCreated = teamRepository.create(team);

        Assert.assertNotNull("Team created is null", userCreated);

        Optional<Team> optional = teamRepository.findByName(teamname);

        Assert.assertTrue("Unable to find saved team", optional.isPresent());
        Team teamFound = optional.get();

        Assert.assertEquals("Invalid saved team name.", team.getName(), teamFound.getName());
        Assert.assertEquals("Invalid saved team mail.", team.getEmail(), teamFound.getEmail());
        Assert.assertEquals("Invalid saved team description.", team.getDescription(), teamFound.getDescription());
        Assert.assertEquals("Invalid saved team visibility.", team.isPrivateTeam(), teamFound.isPrivateTeam());
        Assert.assertEquals("Invalid saved team creationDate.", team.getCreatedAt(), teamFound.getCreatedAt());
        Assert.assertEquals("Invalid saved team updateDate.", team.getUpdatedAt(), teamFound.getUpdatedAt());
    }

    @Test
    public void findByNameTest() throws TechnicalException {
        String teamname = "Gravitee";
        Optional<Team> optional = teamRepository.findByName(teamname);

        Assert.assertTrue("Unable to find team", optional.isPresent());
        Team teamFound = optional.get();

        Assert.assertEquals("Invalid saved team name.", teamname, teamFound.getName());
        Assert.assertEquals("Invalid saved team mail.", "team@gravitee.io", teamFound.getEmail());
        Assert.assertEquals("Invalid saved team description.", "Sample Gravitee description", teamFound.getDescription());
        Assert.assertEquals("Invalid saved team visibility.", true, teamFound.isPrivateTeam());
        Assert.assertEquals("Invalid saved team creationDate.", toDate("2016-02-11 08:00:00"), teamFound.getCreatedAt());
        Assert.assertEquals("Invalid saved team updateDate.", toDate("2016-02-11 09:00:01"), teamFound.getUpdatedAt());
    }

    @Test
    public void updateTest() throws TechnicalException {
        String teamname = "Gravitee";

        String newDescription = "updated-team1 description";
        String newEmail = "updated-team1@gravitee.io";
        boolean newVisibility = false;
        Date udpatedAt = new Date();

        Team team = new Team();
        team.setName(teamname);
        team.setEmail(newEmail);
        team.setDescription(newDescription);
        team.setPrivateTeam(newVisibility);
        team.setUpdatedAt(udpatedAt);

        teamRepository.update(team);

        Optional<Team> optional = teamRepository.findByName(teamname);
        Team updatedTeam = optional.get();

        Assert.assertTrue("Team modified not found", optional.isPresent());

        Assert.assertEquals("Invalid updated team description.", newDescription, updatedTeam.getDescription());
        Assert.assertEquals("Invalid updated team email.", newEmail, updatedTeam.getEmail());
        Assert.assertEquals("Invalid updated team visibility.", newVisibility, updatedTeam.isPrivateTeam());
        Assert.assertEquals("Invalid updated team updatedAt date.", udpatedAt, updatedTeam.getUpdatedAt());
    }


    @Test
    public void deleteTest() throws TechnicalException {
        String teamname = "Gravitee";

        int nbTeamBefore = teamRepository.findAll(false).size();
        teamRepository.delete(teamname);

        Optional<Team> optional = teamRepository.findByName(teamname);
        int nbTeamAfter = teamRepository.findAll(false).size();

        Assert.assertFalse("Deleted team always present", optional.isPresent());
        Assert.assertEquals("Invalid number of team after deletion", nbTeamBefore - 1, nbTeamAfter);
    }


    @Test
    public void findAllTest() throws TechnicalException {
        Set<Team> teams = teamRepository.findAll(false);

        Assert.assertNotNull(teams);
        Assert.assertEquals("Invalid team numbers in find all", 2, teams.size());
    }

    @Test
    public void findAllPublicTest() throws TechnicalException {
        Set<Team> teams = teamRepository.findAll(true);

        Assert.assertNotNull(teams);
        Assert.assertEquals("Invalid team numbers in find all", 1, teams.size());
    }
}
