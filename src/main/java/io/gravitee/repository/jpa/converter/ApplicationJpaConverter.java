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

import io.gravitee.repository.jpa.internal.InternalJpaUserRepository;
import io.gravitee.repository.jpa.model.ApplicationJpa;
import io.gravitee.repository.jpa.model.UserJpa;
import io.gravitee.repository.model.Application;
import io.gravitee.repository.model.OwnerType;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Component
public class ApplicationJpaConverter extends AbstractConverter<ApplicationJpa, Application> {

    @Inject
    private InternalJpaUserRepository internalJpaUserRepository;

    public Application convertTo(final ApplicationJpa applicationJpa) {
        if (applicationJpa == null) {
            return null;
        }
        final Application application = new Application();
        copyProperties(applicationJpa, application);

        if(applicationJpa.getOwner() != null){
            application.setOwner(applicationJpa.getOwner().getName());
            if(applicationJpa.getOwner() instanceof UserJpa){
                application.setOwnerType(OwnerType.USER);
            }else{
                application.setOwnerType(OwnerType.TEAM);
            }
        }
        if(applicationJpa.getCreator()!= null){
            application.setCreator(applicationJpa.getCreator().getName());
        }
        
        return application;
    }

    public ApplicationJpa convertFrom(final Application application) {
        if (application == null) {
            return null;
        }
        final ApplicationJpa applicationJpa = new ApplicationJpa();
        copyProperties(application, applicationJpa);
        applicationJpa.setCreator(internalJpaUserRepository.findOne(application.getCreator()));
        applicationJpa.setOwner(internalJpaUserRepository.findOne(application.getOwner()));
        return applicationJpa;
    }
}
