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

import static org.apache.commons.lang3.StringUtils.removeEnd;
import static org.apache.commons.lang3.StringUtils.removeStart;

import org.springframework.web.context.support.ServletContextResource;

/**
 * Utilities related to Map resources
 * 
 * @author Andrea Vacondio
 *
 */
final class MapResourceUtils {
    private MapResourceUtils() {
        // nothing
    }

    /**
     * @param resource
     * @return the key for the map extracted from the given resource. Ex. "/static/maps/myMap" will produce "myMap"
     */
    public static String extractMapKey(ServletContextResource resource) {
        String path = removeEnd(resource.getPath(), "/");
        return path.substring(path.lastIndexOf('/') + 1);
    }

    /**
     * @param resource
     * @param removablePrefix
     * @return the external path to the given static resource. It can be used to load the resource form the browser.
     */
    public static String toExternalPath(ServletContextResource resource, String removablePrefix) {
        return removeStart(resource.getPath(), removablePrefix);
    }
}
