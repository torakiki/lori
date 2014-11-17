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

/**
 * @author Andrea Vacondio
 *
 */
public class Response {
    private long id;
    private String message;

    private Response(long id, String message) {
        this.id = id;
        this.message = message;
    }

    public long id() {
        return id;
    }

    @Override
    public String toString() {
        return message;
    }

    public static Response forRequest(Request req) {
        return new Response(req.id(), req.toString());
    }
}
