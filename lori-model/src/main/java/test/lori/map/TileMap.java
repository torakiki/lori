/* 
 * This file is part of the Lori source code
 * Created on 11/nov/2014
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

import test.lori.geometry.Point;

/**
 * Rectangular shape map
 * 
 * @author Andrea Vacondio
 *
 */
public class TileMap {

    private int tilesSizeInPixel = 32;
    // TODO some way to get json file
    // private String tiledJson;
    private BlockingTileLayer blockingLayer = new BlockingTileLayer(tilesSizeInPixel, 20, 20);

    /**
     * @see test.lori.map.BlockingTileLayer#canDrop(test.lori.map.RectangularMapEntity, test.lori.geometry.Point)
     */
    public boolean canDrop(RectangularMapEntity entity, Point point) {
        return blockingLayer.canDrop(entity, point);
    }

    public Point moveRight(RectangularMapEntity entity, int amount) {
        if (blockingLayer.canMoveRight(entity, amount)) {
            entity.move(amount, 0);
        } else {
            entity.alignRightTile(tilesSizeInPixel);
        }
        return entity.position();
    }

    public Point moveLeft(RectangularMapEntity entity, int amount) {
        if (blockingLayer.canMoveLeft(entity, amount)) {
            entity.move(-amount, 0);
        } else {
            entity.alignLeftTile(tilesSizeInPixel);
        }
        return entity.position();
    }

    public Point moveDown(RectangularMapEntity entity, int amount) {
        if (blockingLayer.canMoveDown(entity, amount)) {
            entity.move(0, amount);
        } else {
            entity.alignBottomTile(tilesSizeInPixel);
        }
        return entity.position();
    }

    public Point moveUp(RectangularMapEntity entity, int amount) {
        if (blockingLayer.canMoveUp(entity, amount)) {
            entity.move(0, -amount);
        } else {
            entity.alignTopTile(tilesSizeInPixel);
        }
        return entity.position();
    }
}
