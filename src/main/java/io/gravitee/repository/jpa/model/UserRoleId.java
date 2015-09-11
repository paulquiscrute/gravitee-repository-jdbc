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

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
public class UserRoleId implements Serializable {

    private static final long serialVersionUID = 428682958725100363L;

    private String user;

    private String role;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRoleId)) {
            return false;
        }
        UserRoleId that = (UserRoleId) o;
        return Objects.equals(user, that.user) &&
            Objects.equals(role, that.role);
    }

    public int hashCode() {
        return Objects.hash(user, role);
    }

    public String toString() {
        return "UserRoleId{" +
            "user='" + user + '\'' +
            ", role='" + role + '\'' +
            '}';
    }
}
