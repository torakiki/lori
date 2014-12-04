/* 
 * This file is part of the Lori source code
 * Created on 29/nov/2014
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
package test.lori.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Andrea Vacondio
 *
 */
@Controller
public class HomeController {
    @RequestMapping({ "/", "/home", "/index" })
    public ModelAndView home() {
        ModelAndView maw = new ModelAndView("index");
        maw.getModel().put("title", "Lori Arena");
        return maw;
    }

    @RequestMapping("/game")
    public String game() {
        return "game";
    }
}
