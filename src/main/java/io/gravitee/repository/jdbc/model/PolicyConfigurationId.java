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
public class PolicyConfigurationId implements Serializable {

    private static final long serialVersionUID = -8462047966967852865L;

    private String policy;

    private String api;

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
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
        if (!(o instanceof PolicyConfigurationId)) {
            return false;
        }
        PolicyConfigurationId that = (PolicyConfigurationId) o;
        return Objects.equals(policy, that.policy) &&
            Objects.equals(api, that.api);
    }

    public int hashCode() {
        return Objects.hash(policy, api);
    }

    public String toString() {
        return "PolicyConfigurationId{" +
            "policy='" + policy + '\'' +
            ", api='" + api + '\'' +
            '}';
    }
}
