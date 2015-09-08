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
public class ApiApplicationId implements Serializable {

    private static final long serialVersionUID = 777652855877004330L;

    private String application;

    private String api;

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiApplicationId)) {
            return false;
        }
        ApiApplicationId that = (ApiApplicationId) o;
        return Objects.equals(application, that.application) &&
            Objects.equals(api, that.api);
    }

    public int hashCode() {
        return Objects.hash(application, api);
    }

    public String toString() {
        return "ApiApplicationId{" +
            "application='" + application + '\'' +
            ", api='" + api + '\'' +
            '}';
    }
}
