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
package test.lori;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Application exposing Maps related endpoints
 * 
 * @author Andrea Vacondio
 *
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class MApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MApp.class);
        app.setWebEnvironment(true);
        app.run(args);
    }

    @Bean(name = "mapsLocation")
    public String mapsLocation() {
        return "/static";
    }

    /**
     * @return the prefix to be removed to get the resource external path
     */
    @Bean(name = "removablePrefix")
    public String removablePrefix() {
        return "/static";
    }
}