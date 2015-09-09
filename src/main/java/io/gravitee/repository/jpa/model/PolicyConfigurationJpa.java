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
@Table(name = "GRAVITEE_POLICY_CONFIGURATION")
@IdClass(PolicyConfigurationId.class)
public class PolicyConfigurationJpa {

    @Id
    @ManyToOne
    @JoinColumn(name = "policy")
    private PolicyJpa policy;

    @Id
    @ManyToOne
    @JoinColumn(name = "api")
    private ApiJpa api;

    private String configuration;

    private Date createdAt;

    public PolicyJpa getPolicy() {
        return policy;
    }

    public void setPolicy(PolicyJpa policy) {
        this.policy = policy;
    }

    public ApiJpa getApi() {
        return api;
    }

    public void setApi(ApiJpa api) {
        this.api = api;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PolicyConfigurationJpa)) {
            return false;
        }
        PolicyConfigurationJpa that = (PolicyConfigurationJpa) o;
        return Objects.equals(policy, that.policy) &&
            Objects.equals(api, that.api);
    }

    public int hashCode() {
        return Objects.hash(policy, api);
    }

    public String toString() {
        return "PolicyConfigurationJpa{" +
            "policy='" + policy + '\'' +
            ", api='" + api + '\'' +
            ", configuration='" + configuration + '\'' +
            ", createdAt=" + createdAt +
            '}';
    }
}
