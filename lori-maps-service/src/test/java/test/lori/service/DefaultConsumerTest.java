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

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.servlet.ServletContext;

import org.junit.Test;
import org.springframework.web.context.support.ServletContextResource;

import test.lori.map.JsonMap;

/**
 * @author Andrea Vacondio
 *
 */
public class DefaultConsumerTest {

    @Test
    public void accept() {
        ServletContextResource resource = new ServletContextResource(mock(ServletContext.class),
                "/some/path/and/the/key/");
        JsonMap map = mock(JsonMap.class);
        new DefaultConsumer("/some").accept(map, resource);
        verify(map).addResource(eq("/path/and/the/key/"));
    }
}
