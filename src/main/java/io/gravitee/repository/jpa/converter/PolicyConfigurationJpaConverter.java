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
package io.gravitee.repository.jpa.converter;

import static org.springframework.beans.BeanUtils.copyProperties;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import io.gravitee.repository.jpa.internal.InternalJpaPolicyRepository;
import io.gravitee.repository.jpa.model.PolicyConfigurationJpa;
import io.gravitee.repository.model.management.PolicyConfiguration;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Component
public class PolicyConfigurationJpaConverter extends AbstractConverter<PolicyConfigurationJpa, PolicyConfiguration> {

    @Inject
    private InternalJpaPolicyRepository internalJpaPolicyRepository;

    public PolicyConfiguration convertTo(final PolicyConfigurationJpa policyConfigurationJpa) {
        if (policyConfigurationJpa == null) {
            return null;
        }
        final PolicyConfiguration policyConfiguration = new PolicyConfiguration();
        copyProperties(policyConfigurationJpa, policyConfiguration);
        policyConfiguration.setPolicy(policyConfigurationJpa.getPolicy().getName());
        return policyConfiguration;
    }

    public PolicyConfigurationJpa convertFrom(final PolicyConfiguration policyConfiguration) {
        if (policyConfiguration == null) {
            return null;
        }
        final PolicyConfigurationJpa policyConfigurationJpa = new PolicyConfigurationJpa();
        copyProperties(policyConfiguration, policyConfigurationJpa);
        policyConfigurationJpa.setPolicy(internalJpaPolicyRepository.findOne(policyConfiguration.getPolicy()));
        return policyConfigurationJpa;
    }
}
