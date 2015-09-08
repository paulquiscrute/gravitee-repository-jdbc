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
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Entity
@Table(name = "POLICY")
public class PolicyJpa {

    @Id
    private String name;

    private String description;

    private String version;

    private String configuration;

    @OneToMany(mappedBy="policy")
    private List<PolicyConfigurationJpa> apis;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public List<PolicyConfigurationJpa> getApis() {
        return apis;
    }

    public void setApis(List<PolicyConfigurationJpa> apis) {
        this.apis = apis;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PolicyJpa)) {
            return false;
        }
        PolicyJpa policyJpa = (PolicyJpa) o;
        return Objects.equals(name, policyJpa.name);
    }

    public int hashCode() {
        return Objects.hash(name);
    }

    public String toString() {
        return "PolicyJpa{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", version='" + version + '\'' +
            ", configuration='" + configuration + '\'' +
            ", apis=" + apis +
            '}';
    }
}
