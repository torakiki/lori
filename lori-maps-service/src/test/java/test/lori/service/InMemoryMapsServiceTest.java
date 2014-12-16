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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.context.support.ServletContextResource;

/**
 * @author Andrea Vacondio
 *
 */
public class InMemoryMapsServiceTest {

    private String mapsLocation = "/location";
    private DefaultConsumer defaultConsumer;
    private ResourcePatternResolver resolver;
    private List<MapResourceConsumer> consumers = new ArrayList<>();
    private InMemoryMapsService victim;

    @Before
    public void setUp() {
        resolver = mock(ResourcePatternResolver.class);
        defaultConsumer = new DefaultConsumer("/location");
        consumers.add(new DescriptorConsumer());
        consumers.add(new JsonMapConsumer("/location"));

    }

    @Test
    public void empty() throws IOException {
        when(resolver.getResources(eq("/location/*"))).thenReturn(new ServletContextResource[0]);
        victim = new InMemoryMapsService(resolver, mapsLocation, defaultConsumer, consumers);
        assertTrue(victim.maps().isEmpty());
    }

    @Test
    public void emptyMapLocation() throws IOException {
        when(resolver.getResources(eq("/location/*")))
                .thenReturn(
                        new ServletContextResource[] { new ServletContextResource(mock(ServletContext.class),
                                "/location/map1") });
        when(resolver.getResources(eq("/location/map1/*"))).thenReturn(new ServletContextResource[0]);
        victim = new InMemoryMapsService(resolver, mapsLocation, defaultConsumer, consumers);
        assertTrue(victim.maps().isEmpty());
    }

    @Test
    public void invalidMap() throws IOException {
        when(resolver.getResources(eq("/location/*")))
                .thenReturn(
                        new ServletContextResource[] { new ServletContextResource(mock(ServletContext.class),
                                "/location/map1") });
        when(resolver.getResources(eq("/location/map1/*"))).thenReturn(
                new ServletContextResource[] { new ServletContextResource(mock(ServletContext.class),
                        "/location/map1/map.json") });
        victim = new InMemoryMapsService(resolver, mapsLocation, defaultConsumer, consumers);
        assertTrue(victim.maps().isEmpty());
    }

    @Test
    public void validMap() throws IOException {
        ServletContextResource descriptor = spy(new ServletContextResource(mock(ServletContext.class),
                "/location/map1/descriptor.properties"));
        ClassPathResource cpResource = new ClassPathResource("/descriptor.properties");
        doReturn(cpResource.getInputStream()).when(descriptor).getInputStream();

        when(resolver.getResources(eq("/location/*")))
                .thenReturn(
                        new ServletContextResource[] { new ServletContextResource(mock(ServletContext.class),
                                "/location/map1") });
        when(resolver.getResources(eq("/location/map1/*"))).thenReturn(
                new ServletContextResource[] { descriptor,
                        new ServletContextResource(mock(ServletContext.class), "/location/map1/map.json"),
                        new ServletContextResource(mock(ServletContext.class), "/location/map1/tileset.png") });
        victim = new InMemoryMapsService(resolver, mapsLocation, defaultConsumer, consumers);
        assertFalse(victim.maps().isEmpty());
        assertNotNull(victim.map("map1"));
    }
}
