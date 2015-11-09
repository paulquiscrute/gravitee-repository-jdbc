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

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
public class TeamMemberId implements Serializable {

    private static final long serialVersionUID = -3433378730576783218L;

    private String team;

    private String member;

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamMemberId)) {
            return false;
        }
        TeamMemberId that = (TeamMemberId) o;
        return Objects.equals(team, that.team) &&
            Objects.equals(member, that.member);
    }

    public int hashCode() {
        return Objects.hash(team, member);
    }

    public String toString() {
        return "TeamMemberId{" +
            "team='" + team + '\'' +
            ", member='" + member + '\'' +
            '}';
    }
}
