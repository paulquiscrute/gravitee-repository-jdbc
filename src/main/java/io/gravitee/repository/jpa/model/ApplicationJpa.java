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

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Entity
@Table(name = "GRAVITEE_APPLICATION")
public class ApplicationJpa {

	@Id
    private String name;

    private String description;

    private String type;

    private Date createdAt;

    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "owner")
    private AbstractUserJpa owner;

    @ManyToOne
    private UserJpa creator;

    @OneToMany(mappedBy="application")
    private Set<ApiApplicationJpa> apis;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public AbstractUserJpa getOwner() {
        return owner;
    }

    public void setOwner(AbstractUserJpa owner) {
        this.owner = owner;
    }

    public UserJpa getCreator() {
        return creator;
    }

    public void setCreator(UserJpa creator) {
        this.creator = creator;
    }

    public Set<ApiApplicationJpa> getApis() {
        return apis;
    }

    public void setApis(Set<ApiApplicationJpa> apis) {
        this.apis = apis;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationJpa)) {
            return false;
        }
        ApplicationJpa that = (ApplicationJpa) o;
        return Objects.equals(name, that.name);
    }

    public int hashCode() {
        return Objects.hash(name);
    }

    public String toString() {
        return "ApplicationJpa{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", type='" + type + '\'' +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            ", owner=" + owner +
            ", creator=" + creator +
            '}';
    }
}
