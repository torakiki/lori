/* 
 * This file is part of the Lori source code
 * Created on 14/nov/2014
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

import io.netty.util.AttributeKey;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;

import test.lori.map.RectangularMapEntity;

/**
 * @author Andrea Vacondio
 *
 */
public class Player extends RectangularMapEntity {
    public static final AttributeKey<String> ID_ATTRIBUTE = AttributeKey.valueOf("player");

    private String id = UUID.randomUUID().toString();

    public Player(int width, int height) {
        super(width, height);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(id).toString();
    }
}
