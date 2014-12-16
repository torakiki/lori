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
package test.lori.endpoint;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import test.lori.service.MapsService;

/**
 * @author Andrea Vacondio
 *
 */
public class MapsEndpointTest {

    private MapsService service;
    private MapsEndpoint victim;

    @Before
    public void setUp() {
        service = mock(MapsService.class);
        victim = new MapsEndpoint(service);
    }

    @Test
    public void maps() {
        victim.maps();
        verify(service).maps();
    }

    @Test
    public void map() {
        victim.getMap("key");
        verify(service).map("key");
    }
}
