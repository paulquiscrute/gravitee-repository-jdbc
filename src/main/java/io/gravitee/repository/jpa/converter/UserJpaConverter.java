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

import org.springframework.stereotype.Component;

import io.gravitee.repository.jpa.model.UserJpa;
import io.gravitee.repository.model.User;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Component
public class UserJpaConverter extends AbstractConverter<UserJpa, User> {

    public User convertTo(final UserJpa userJpa) {
        if (userJpa == null) {
            return null;
        }
        final User user = new User();
        copyProperties(userJpa, user);
        user.setUsername(userJpa.getName());
        return user;
    }

    public UserJpa convertFrom(final User user) {
        if (user == null) {
            return null;
        }
        final UserJpa userJpa = new UserJpa();
        copyProperties(user, userJpa);
        userJpa.setName(user.getUsername());
        return userJpa;
    }
}