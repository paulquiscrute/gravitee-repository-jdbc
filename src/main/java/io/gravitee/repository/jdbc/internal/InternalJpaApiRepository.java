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
package io.gravitee.repository.jdbc.internal;

import io.gravitee.repository.jdbc.model.ApiJpa;
import io.gravitee.repository.management.model.Api;
import io.gravitee.repository.management.model.OwnerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
public interface InternalJpaApiRepository extends JpaRepository<ApiJpa, String> {

    List<ApiJpa> findByOwnerAndOwnerType(String owner, OwnerType ownerType);

    List<ApiJpa> findByOwnerAndOwnerTypeAndPrivateApi(String owner, OwnerType ownerType, boolean privateApi);

    int countByOwnerAndOwnerType(String owner, OwnerType ownerType);

    int countByOwnerAndOwnerTypeAndPrivateApi(String owner, OwnerType ownerType, boolean privateApi);

    Set<Api> findByCreator(String userName);
}