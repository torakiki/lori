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

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.support.ServletContextResource;

import test.lori.map.JsonMap;

/**
 * @author Andrea Vacondio
 *
 */
public class DescriptorConsumerTest {
    @Test
    public void accept() throws IOException {
        ServletContextResource resource = mock(ServletContextResource.class);
        ClassPathResource cpResource = new ClassPathResource("/descriptor.properties");
        when(resource.getInputStream()).thenReturn(cpResource.getInputStream());
        JsonMap map = mock(JsonMap.class);
        new DescriptorConsumer().accept(map, resource);
        verify(map).name("Map name");
        verify(map).description("Map description");
    }

    @Test
    public void emptyDescriptor() throws IOException {
        ServletContextResource resource = mock(ServletContextResource.class);
        ClassPathResource cpResource = new ClassPathResource("/emptydescriptor.properties");
        when(resource.getInputStream()).thenReturn(cpResource.getInputStream());
        JsonMap map = new JsonMap("key");
        new DescriptorConsumer().accept(map, resource);
        assertEquals(EMPTY, map.name());
        assertEquals(EMPTY, map.description());
    }
}
