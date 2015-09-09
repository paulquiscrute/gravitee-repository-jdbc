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

import javax.persistence.CascadeType;
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
@Table(name = "GRAVITEE_API_APPLICATION")
@IdClass(ApiApplicationId.class)
public class ApiApplicationJpa {

    @Id
    @ManyToOne
    @JoinColumn(name = "application")
    private ApplicationJpa application;

    @Id
    @ManyToOne
    @JoinColumn(name = "api")
    private ApiJpa api;

    private String configuration;

    private Date createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "key")
    private ApiKeyJpa key;

    public ApplicationJpa getApplication() {
        return application;
    }

    public void setApplication(ApplicationJpa application) {
        this.application = application;
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

    public ApiKeyJpa getKey() {
        return key;
    }

    public void setKey(ApiKeyJpa key) {
        this.key = key;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiApplicationJpa)) {
            return false;
        }
        ApiApplicationJpa that = (ApiApplicationJpa) o;
        return Objects.equals(application, that.application) &&
            Objects.equals(api, that.api);
    }

    public int hashCode() {
        return Objects.hash(application, api);
    }

    public String toString() {
        return "ApiApplicationJpa{" +
            "application='" + application + '\'' +
            ", api='" + api + '\'' +
            ", configuration='" + configuration + '\'' +
            ", createdAt=" + createdAt +
            ", key=" + key +
            '}';
    }
}
