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
import io.gravitee.repository.jdbc.converter.NodeJpaConverter;
import io.gravitee.repository.jdbc.internal.InternalJpaNodeRepository;
import io.gravitee.repository.jdbc.model.NodeJpa;
import io.gravitee.repository.management.api.NodeRepository;
import io.gravitee.repository.management.model.Node;
import io.gravitee.repository.management.model.NodeState;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.Date;
import java.util.Set;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Repository
public class JdbcNodeRepository implements NodeRepository {

	@Inject
	private InternalJpaNodeRepository internalJpaNodeRepository;

	@Inject
	private NodeJpaConverter nodeJpaConverter;

	public void register(Node node) throws TechnicalException {
		final NodeJpa nodeJpa = nodeJpaConverter.convertFrom(node);

		nodeJpa.setLastStartupTime(new Date());
		nodeJpa.setState(NodeState.REGISTERED.name());

		internalJpaNodeRepository.save(nodeJpa);
	}

	public void unregister(String nodename) throws TechnicalException {
		final NodeJpa nodeJpa = internalJpaNodeRepository.findOne(nodename);

		nodeJpa.setLastStopTime(new Date());
		nodeJpa.setState(NodeState.UNREGISTERED.name());

		internalJpaNodeRepository.save(nodeJpa);
	}

	public Set<Node> findAll() throws TechnicalException {
		return nodeJpaConverter.convertAllTo(internalJpaNodeRepository.findAll());
	}
}
