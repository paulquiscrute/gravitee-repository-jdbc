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

import io.gravitee.repository.jdbc.model.NodeJpa;
import io.gravitee.repository.management.model.Node;
import org.springframework.stereotype.Component;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Component
public class NodeJpaConverter extends AbstractConverter<NodeJpa, Node> {

    public Node convertTo(final NodeJpa nodeJpa) {
        if (nodeJpa == null) {
            return null;
        }
        final Node node = new Node();
        copyProperties(nodeJpa, node);
        return node;
    }

    public NodeJpa convertFrom(final Node node) {
        if (node == null) {
            return null;
        }
        final NodeJpa nodeJpa = new NodeJpa();
        copyProperties(node, nodeJpa);
        return nodeJpa;
    }
}
