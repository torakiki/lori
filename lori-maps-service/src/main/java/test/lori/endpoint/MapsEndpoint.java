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
package test.lori.endpoint;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import test.lori.map.JsonMap;
import test.lori.service.MapsService;

/**
 * Endpoint exposing Maps related services
 * 
 * @author Andrea Vacondio
 *
 */
@RestController
public class MapsEndpoint {

    private MapsService service;

    @Autowired
    public MapsEndpoint(MapsService service) {
        this.service = service;
    }

    @RequestMapping(value = "/maps", method = RequestMethod.GET)
    public Set<JsonMap> maps() {
        return service.maps();
    }

    @RequestMapping(value = "/map/{mapKey}", method = RequestMethod.GET)
    public JsonMap getMap(@PathVariable String mapKey) {
        return service.map(mapKey);
    }
}
