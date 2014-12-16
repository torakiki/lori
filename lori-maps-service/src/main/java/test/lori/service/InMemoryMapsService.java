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
package test.lori.service;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.removeEnd;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.ServletContextResource;

import test.lori.map.JsonMap;

/**
 * Default implementation of the map service loading all the map information at startup and holing an in memory data store.
 * 
 * @author Andrea Vacondio
 *
 */
@Service
public class InMemoryMapsService implements MapsService {

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryMapsService.class);

    private Map<String, JsonMap> dataStore = new HashMap<>();
    private String mapsLocation;
    private ResourcePatternResolver resolver;

    private Map<String, BiConsumer<JsonMap, ServletContextResource>> consumersByKey = new HashMap<>();
    private final DefaultConsumer defaultConsumer;

    @Autowired
    public InMemoryMapsService(ResourcePatternResolver resolver, @Qualifier("mapsLocation") String mapsLocation,
            DefaultConsumer defaultConsumer, List<MapResourceConsumer> consumers) throws IOException {
        this.mapsLocation = mapsLocation;
        this.resolver = resolver;
        this.defaultConsumer = defaultConsumer;
        consumers.stream().forEach(c -> consumersByKey.put(c.key(), c));
        initDataStore();
    }

    private void initDataStore() throws IOException {
        Resource[] resources = listResources(mapsLocation);
        LOG.debug("Found {} json map resources.", resources.length);
        for (Resource current : resources) {
            LOG.debug("Processing {}", current);
            JsonMap map = fromResource((ServletContextResource) current);
            if (map.isValid()) {
                dataStore.put(map.key(), map);
                LOG.info("Processed map at {}", current);
            } else {
                LOG.warn("Invalid map found at {}", current);
            }
        }
    }

    private Resource[] listResources(String location) throws IOException {
        return resolver.getResources(format("%s/*", removeEnd(location, "/")));
    }

    private JsonMap fromResource(ServletContextResource resource) throws IOException {
        Resource[] innerResources = listResources(resource.getPath());
        String key = MapResourceUtils.extractMapKey(resource);
        JsonMap map = new JsonMap(key);
        Arrays.stream(innerResources).forEach(
                r -> {
                    consumersByKey.getOrDefault(r.getFilename().toLowerCase(), defaultConsumer).accept(map,
                            (ServletContextResource) r);
                    LOG.debug("Processed map resource {}", r);
                });
        return map;
    }

    @Override
    public Set<JsonMap> maps() {
        return new HashSet<>(dataStore.values());
    }

    @Override
    public JsonMap map(String key) {
        return dataStore.get(key);
    }
}
