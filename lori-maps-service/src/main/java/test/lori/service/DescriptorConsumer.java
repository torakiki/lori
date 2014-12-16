/* 
 * This file is part of the Lori source code
 * Created on 16/dic/2014
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

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletContextResource;

import test.lori.map.JsonMap;

/**
 * Consumer for the descriptor resource extracting properties from the map descriptor and populating the {@link JsonMap}
 * 
 * @author Andrea Vacondio
 *
 */
@Component
class DescriptorConsumer implements MapResourceConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(DescriptorConsumer.class);
    private static final String NAME_KEY = "map.name";
    private static final String DESCRIPTION_KEY = "map.description";

    @Override
    public void accept(JsonMap m, ServletContextResource r) {
        Properties props = new Properties();
        try {
            props.load(r.getInputStream());
            m.name(props.getProperty(NAME_KEY));
            m.description(props.getProperty(DESCRIPTION_KEY));
        } catch (IllegalArgumentException | IOException e) {
            LOG.error("Unable to load map descriptor", e);
        }
    }

    @Override
    public String key() {
        return "descriptor.properties";
    }

}
