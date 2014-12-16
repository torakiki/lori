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

import static test.lori.service.MapResourceUtils.toExternalPath;

import java.util.function.BiConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletContextResource;

import test.lori.map.JsonMap;

/**
 * Default consumer for a map resource adding the resource to the {@link JsonMap}
 * 
 * @author Andrea Vacondio
 *
 */
@Component
class DefaultConsumer implements BiConsumer<JsonMap, ServletContextResource> {

    private String removablePrefix;

    @Autowired
    public DefaultConsumer(@Qualifier("removablePrefix") String removablePrefix) {
        this.removablePrefix = removablePrefix;
    }

    @Override
    public void accept(JsonMap m, ServletContextResource r) {
        m.addResource(toExternalPath(r, removablePrefix));
    }

}
