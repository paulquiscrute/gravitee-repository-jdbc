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
package io.gravitee.repository.jdbc;

import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.jdbc.config.AbstractJdbcRepositoryTest;
import io.gravitee.repository.management.api.UserRepository;
import io.gravitee.repository.management.model.User;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
public class JdbcUserRepositoryTest extends AbstractJdbcRepositoryTest {

    @Inject
    private UserRepository userRepository;

    protected String getXmlDataSetResourceName() {
        return "/data/users.xml";
    }

    @Test
    public void createUserTest() throws TechnicalException {
        String username = "newUser";

        User user = new User();
        user.setUsername(username);
        user.setEmail(String.format("%s@gravitee.io", username));
        user.setRoles(Arrays.asList("ADMIN"));
        User userCreated = userRepository.create(user);

        Assert.assertNotNull("User created is null", userCreated);

        Optional<User> optional = userRepository.findByUsername(username);

        Assert.assertTrue("Unable to find saved user", optional.isPresent());
        User userFound = optional.get();

        Assert.assertEquals("Invalid saved user name.", user.getUsername(), userFound.getUsername());
        Assert.assertEquals("Invalid saved user mail.", user.getEmail(), userFound.getEmail());
        Assert.assertEquals("Invalid saved user roles.", user.getRoles(), userFound.getRoles());
    }

    @Test
    public void findAllTest() throws TechnicalException {
        Set<User> users = userRepository.findAll();

        Assert.assertNotNull(users);
        Assert.assertEquals("Invalid user numbers in find all", 2, users.size());
    }

    @Test
    public void findByEmailTest() throws TechnicalException {
        Optional<User> user = userRepository.findByEmail("user@gravitee.io");

        Assert.assertNotNull("Optional is null", user);
        Assert.assertTrue("Impossible to find user by email", user.isPresent());
    }
}
