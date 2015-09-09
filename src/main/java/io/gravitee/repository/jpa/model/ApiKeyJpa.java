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
package io.gravitee.repository.jpa.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Azize Elamrani (azize dot elamrani at gmail dot com)
 */
@Entity
@Table(name = "GRAVITEE_API_KEY")
public class ApiKeyJpa {

	@Id
	private String key;

	private boolean revoked;
	
	private Date expiration;

	private Date createdAt;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isRevoked() {
		return revoked;
	}

	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ApiKeyJpa)) {
			return false;
		}
		ApiKeyJpa apiKeyJpa = (ApiKeyJpa) o;
		return Objects.equals(key, apiKeyJpa.key);
	}

	public int hashCode() {
		return Objects.hash(key);
	}

	public String toString() {
		return "ApiKeyJpa{" +
			"key='" + key + '\'' +
			", revoked=" + revoked +
			", expiration=" + expiration +
			", createdAt=" + createdAt +
			'}';
	}
}
