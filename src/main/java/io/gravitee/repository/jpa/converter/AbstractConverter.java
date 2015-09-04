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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Defines an abstraction to convert from a source to a target type and vice versa.
 *
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
public abstract class AbstractConverter<S, T> {

    public abstract S convertFrom(T object);

    public abstract T convertTo(S object);

    public Set<S> convertAllFrom(final Collection<? extends T> objects) {
        if (objects == null) {
            return null;
        }
        final Set<S> results = new HashSet<>(objects.size());
        objects.forEach(object -> results.add(convertFrom(object)));
        return results;
    }

    public Set<T> convertAllTo(final Collection<? extends S> objects) {
        if (objects == null) {
            return null;
        }
        final Set<T> results = new HashSet<T>(objects.size());
        objects.forEach(object -> results.add(convertTo(object)));
        return results;
    }
}