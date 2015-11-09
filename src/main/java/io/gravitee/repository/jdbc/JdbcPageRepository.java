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
import io.gravitee.repository.jdbc.converter.PageJpaConverter;
import io.gravitee.repository.jdbc.internal.InternalJpaPageRepository;
import io.gravitee.repository.management.api.PageRepository;
import io.gravitee.repository.management.model.Page;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Repository
public class JdbcPageRepository implements PageRepository {

    @Inject
    private InternalJpaPageRepository internalJpaPageRepository;

    @Inject
    private PageJpaConverter pageJpaConverter;

    public Set<Page> findByApiName(String apiName) throws TechnicalException {
        return pageJpaConverter.convertAllTo(internalJpaPageRepository.findByApiName(apiName));
    }

    public Optional<Page> findByName(String name) throws TechnicalException {
        return Optional.ofNullable(pageJpaConverter.convertTo(internalJpaPageRepository.findOne(name)));
    }

    public Page create(Page page) throws TechnicalException {
        return save(page, true);
    }

    public Page update(Page page) throws TechnicalException {
        return save(page, false);
    }

    private Page save(Page page, boolean creationMode) {
        final boolean exists = internalJpaPageRepository.exists(page.getName());
        if (creationMode && exists) {
            throw new IllegalStateException(format("The page '%s' can not be created cause already exists", page.getName()));
        } else if (!creationMode && !exists) {
            throw new IllegalStateException(format("The page '%s' can not be updated cause does not exist", page.getName()));
        }
        return pageJpaConverter.convertTo(
            internalJpaPageRepository.save(pageJpaConverter.convertFrom(page))
        );
    }

    public void delete(String name) throws TechnicalException {
        internalJpaPageRepository.delete(name);
    }

    public Integer findMaxPageOrderByApiName(String apiName) throws TechnicalException {
        return internalJpaPageRepository.findMaxOrderByApiName(apiName);
    }
}
