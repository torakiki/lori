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
package test.lori.map;

import static test.lori.util.RequireUtils.require;
import test.lori.geometry.Point;

/**
 * @author Andrea Vacondio
 *
 */
public class RectangularMapEntity {
    private int width;
    private int height;
    private Point position = null;
    private Point bottomRight = null;

    public RectangularMapEntity(int width, int height) {
        require(width >= 0 && height >= 0);
        this.width = width;
        this.height = height;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public void dropAtPosition(Point position) {
        // can be dropped only once to the map
        require(this.position == null);
        require(position.x() >= 0 && position.y() >= 0);
        this.position = position.copy();
        this.bottomRight = position().copy();
        this.bottomRight.move(width, height);
    }

    /**
     * @return the top left point coordinates on the map for this entity
     */
    public Point position() {
        return position;
    }

    /**
     * @return the bottom right point coordinates on the map for this entity
     */
    public Point bottomRightPosition() {
        return bottomRight;
    }

    public void move(int xAmount, int yAmount) {
        position.move(xAmount, yAmount);
        bottomRight.move(xAmount, yAmount);
    }

    public void alignRightTile(int tilesSizeInPixel) {
        int column = bottomRight.x() / tilesSizeInPixel;
        int rightEdgeX = (column * tilesSizeInPixel) + tilesSizeInPixel - 1;
        move(rightEdgeX - bottomRight.x(), 0);
    }

    public void alignLeftTile(int tilesSizeInPixel) {
        int column = position.x() / tilesSizeInPixel;
        int leftEdgeX = column * tilesSizeInPixel;
        move(-position.x() + leftEdgeX, 0);
    }

    public void alignTopTile(int tilesSizeInPixel) {
        int row = position.y() / tilesSizeInPixel;
        int topEdgeX = row * tilesSizeInPixel;
        move(0, -position.y() + topEdgeX);
    }

    public void alignBottomTile(int tilesSizeInPixel) {
        int row = position.y() / tilesSizeInPixel;
        int bottomEdgeX = (row * tilesSizeInPixel) + tilesSizeInPixel - 1;
        move(0, bottomEdgeX - bottomRight.y());
    }
}
