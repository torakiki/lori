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

import java.util.Set;

import test.lori.map.JsonMap;

/**
 * Maps related services
 * 
 * @author Andrea Vacondio
 *
 */
public interface MapsService {
    /**
     * @return a view of the available maps
     */
    Set<JsonMap> maps();

    /**
     * @param key
     * @return the map with the given key or null if no map with the given key is found
     */
    JsonMap map(String key);

}
