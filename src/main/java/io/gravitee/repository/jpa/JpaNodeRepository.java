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

import java.util.Date;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import io.gravitee.repository.api.NodeRepository;
import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.jpa.converter.NodeJpaConverter;
import io.gravitee.repository.jpa.internal.InternalJpaNodeRepository;
import io.gravitee.repository.jpa.model.NodeJpa;
import io.gravitee.repository.model.Node;
import io.gravitee.repository.model.NodeState;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Repository
public class JpaNodeRepository implements NodeRepository {

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
