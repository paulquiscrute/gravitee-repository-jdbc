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
package io.gravitee.repository.jdbc.converter;

import io.gravitee.repository.jdbc.internal.InternalJpaRoleRepository;
import io.gravitee.repository.jdbc.model.RoleJpa;
import io.gravitee.repository.jdbc.model.UserJpa;
import io.gravitee.repository.management.model.User;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Component
public class UserJpaConverter extends AbstractConverter<UserJpa, User> {

    @Inject
    private InternalJpaRoleRepository internalJpaRoleRepository;

    public User convertTo(final UserJpa userJpa) {
        if (userJpa == null) {
            return null;
        }
        final User user = new User();
        copyProperties(userJpa, user);
        user.setUsername(userJpa.getName());
        final List<RoleJpa> roles = userJpa.getRoles();
        if (roles != null && !roles.isEmpty()) {
            user.setRoles(roles.stream().map(role -> role.getName()).collect(Collectors.toList()));
        }
        return user;
    }

    public UserJpa convertFrom(final User user) {
        if (user == null) {
            return null;
        }
        final UserJpa userJpa = new UserJpa();
        copyProperties(user, userJpa);
        userJpa.setName(user.getUsername());

        final List<String> roles = user.getRoles();
        if (roles != null && !roles.isEmpty()) {
            userJpa.setRoles(internalJpaRoleRepository.findAllByName(roles));
        }

        return userJpa;
    }
}
