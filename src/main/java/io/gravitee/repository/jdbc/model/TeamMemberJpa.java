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
package io.gravitee.repository.jdbc.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Entity
@Table(name = "GRAVITEE_TEAM_MEMBER")
@IdClass(TeamMemberId.class)
public class TeamMemberJpa {

	@Id
	@ManyToOne
	@JoinColumn(name = "team")
	private TeamJpa team;

	@Id
	@ManyToOne
	@JoinColumn(name = "member")
	private UserJpa member;

    private String role;
    
    private Date createdAt;
    
    private Date updatedAt;

	public TeamJpa getTeam() {
		return team;
	}

	public void setTeam(TeamJpa team) {
		this.team = team;
	}

	public UserJpa getMember() {
		return member;
	}

	public void setMember(UserJpa member) {
		this.member = member;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof TeamMemberJpa)) {
			return false;
		}
		TeamMemberJpa that = (TeamMemberJpa) o;
		return Objects.equals(team, that.team) &&
			Objects.equals(member, that.member);
	}

	public int hashCode() {
		return Objects.hash(team, member);
	}

	public String toString() {
		return "TeamMemberJpa{" +
			"team='" + team + '\'' +
			", member='" + member + '\'' +
			", role='" + role + '\'' +
			", createdAt=" + createdAt +
			", updatedAt=" + updatedAt +
			'}';
	}
}
