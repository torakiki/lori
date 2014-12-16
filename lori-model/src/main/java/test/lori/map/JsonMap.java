/* 
 * This file is part of the Lori source code
 * Created on 15/dic/2014
 * Copyright 2013-2014 by Andrea Vacondio (andrea.vacondio@gmail.com).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as 
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package test.lori.map;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static test.lori.util.RequireUtils.requireNotBlank;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Map represented by a set of resources and a json file.
 * 
 * @author Andrea Vacondio
 *
 */
public class JsonMap {
    private String key = EMPTY;
    private String name = EMPTY;
    private String description = EMPTY;
    private String jsonPath = EMPTY;
    private Set<String> resources = new HashSet<>();

    public JsonMap(String key) {
        this.key = requireNotBlank(key);
    }

    @JsonProperty
    public String key() {
        return key;
    }

    @JsonProperty
    public String name() {
        return name;
    }

    public void name(String name) {
        this.name = requireNotBlank(name);
    }

    @JsonProperty
    public String description() {
        return description;
    }

    public void description(String description) {
        this.description = requireNotBlank(description);
    }

    @JsonProperty
    public String jsonPath() {
        return jsonPath;
    }

    public void jsonPath(String json) {
        this.jsonPath = requireNotBlank(json);
    }

    @JsonProperty
    public Set<String> resources() {
        return Collections.unmodifiableSet(resources);
    }

    public boolean addResource(String resource) {
        return resources.add(resource);
    }

    @JsonIgnore
    public boolean isValid() {
        return isNotBlank(key) && isNotBlank(name) && isNotBlank(jsonPath);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(key).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(key).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof JsonMap)) {
            return false;
        }
        JsonMap otherMap = (JsonMap) other;
        return new EqualsBuilder().append(key, otherMap.key()).isEquals();
    }
}
