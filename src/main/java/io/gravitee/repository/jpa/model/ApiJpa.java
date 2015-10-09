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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.gravitee.repository.model.management.LifecycleState;
import io.gravitee.repository.model.management.OwnerType;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Entity
@Table(name = "GRAVITEE_API")
public class ApiJpa {

    @Id
    private String name;

    private String version;

    private String description;

    private String definition;

    private Date createdAt;
    
    private Date updatedAt;

    @Enumerated(EnumType.STRING)
    private OwnerType ownerType;
    
    private String owner;

    private String creator;

    private boolean privateApi;

    @Enumerated(EnumType.STRING)
    private LifecycleState lifecycleState = LifecycleState.STOPPED;

    @OneToMany(mappedBy="api", cascade = CascadeType.ALL)
    private Set<ApiApplicationJpa> applications;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public LifecycleState getLifecycleState() {
        return lifecycleState;
    }

    public void setLifecycleState(LifecycleState lifecycleState) {
        this.lifecycleState = lifecycleState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isPrivate() {
        return privateApi;
    }

    public void setPrivate(boolean privateApi) {
        this.privateApi = privateApi;
    }
    
    public OwnerType getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(OwnerType ownerType) {
		this.ownerType = ownerType;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPrivateApi() {
        return privateApi;
    }

    public void setPrivateApi(boolean privateApi) {
        this.privateApi = privateApi;
    }

    public Set<ApiApplicationJpa> getApplications() {
        return applications;
    }

    public void setApplications(Set<ApiApplicationJpa> applications) {
        this.applications = applications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiJpa api = (ApiJpa) o;
        return Objects.equals(name, api.name) &&
                Objects.equals(version, api.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, version);
    }

    public String toString() {
        return "Api{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", version='" + version + '\'' +
            ", definition=" + definition +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            ", ownerType=" + ownerType +
            ", owner='" + owner + '\'' +
            ", creator='" + creator + '\'' +
            ", privateApi=" + privateApi +
            ", lifecycleState=" + lifecycleState +
            ", definition=" + definition +
            '}';
    }
}
