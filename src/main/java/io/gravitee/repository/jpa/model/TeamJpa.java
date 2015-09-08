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
package io.gravitee.repository.jpa.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Entity
@Table(name = "TEAM")
public class TeamJpa extends AbstractUserJpa {

    /**
     * The team can only be visible for members.
     */
    private boolean privateTeam;

	@OneToMany(mappedBy="team")
	private List<TeamMemberJpa> members;

	public List<TeamMemberJpa> getMembers() {
		return members;
	}

	public void setMembers(List<TeamMemberJpa> members) {
		this.members = members;
	}

	public boolean isPrivateTeam() {
		return privateTeam;
	}

	public void setPrivateTeam(boolean privateTeam) {
		this.privateTeam = privateTeam;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof UserJpa)) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		UserJpa userJpa = (UserJpa) o;
		return Objects.equals(name, userJpa.name);
	}

	public int hashCode() {
		return Objects.hash(super.hashCode(), name);
	}
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Team{");
		sb.append(" email='").append(email).append(",\'");
		sb.append(" teamname='").append(name).append("\'");
		sb.append('}');
		return sb.toString();
	}
}
