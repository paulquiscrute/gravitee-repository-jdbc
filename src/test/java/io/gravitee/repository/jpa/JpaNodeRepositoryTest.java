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
package io.gravitee.repository.jpa;

import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.jpa.config.AbstractJpaRepositoryTest;
import io.gravitee.repository.management.api.NodeRepository;
import io.gravitee.repository.management.model.Node;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Date;
import java.util.Set;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
public class JpaNodeRepositoryTest extends AbstractJpaRepositoryTest {

    @Inject
    private NodeRepository nodeRepository;

    protected String getXmlDataSetResourceName() {
        return "/data/nodes.xml";
    }

    @Test
    public void findAll() throws TechnicalException {
        final Set<Node> nodes = nodeRepository.findAll();
        Assert.assertEquals(1, nodes.size());
    }

    @Test
    public void register() throws TechnicalException {
        Node node = new Node();
        node.setCluster("clusterA");
        node.setHost("localhost");
        node.setName("nodeA-1");
        node.setLastStartupTime(new Date());

        nodeRepository.register(node);

        Assert.assertEquals(2, nodeRepository.findAll().size());
    }

    @Test
    public void unregister() throws TechnicalException {
        nodeRepository.unregister("Node");
    }
}
