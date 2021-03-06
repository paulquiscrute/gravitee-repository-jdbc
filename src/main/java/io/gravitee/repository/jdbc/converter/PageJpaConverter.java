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

import io.gravitee.repository.jdbc.model.PageJpa;
import io.gravitee.repository.management.model.Page;
import io.gravitee.repository.management.model.PageType;
import org.springframework.stereotype.Component;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Component
public class PageJpaConverter extends AbstractConverter<PageJpa, Page> {

    public Page convertTo(final PageJpa pageJpa) {
        if (pageJpa == null) {
            return null;
        }
        final Page page = new Page();
        copyProperties(pageJpa, page);
        page.setType(PageType.valueOf(pageJpa.getType()));
        return page;
    }

    public PageJpa convertFrom(final Page page) {
        if (page == null) {
            return null;
        }
        final PageJpa pageJpa = new PageJpa();
        copyProperties(page, pageJpa);
        final PageType pageType = page.getType();
        pageJpa.setType(pageType.name());
        return pageJpa;
    }
}
