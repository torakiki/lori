/* 
 * This file is part of the Lori source code
 * Created on 13/nov/2014
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

import java.util.List;

/**
 * @author Andrea Vacondio
 *
 */
public class BlockingTileLayer {
    private List<List<Boolean>> tiles;
    private int tilesSizeInPixel;

    public BlockingTileLayer(int tilesSizeInPixel) {
        require(tilesSizeInPixel > 0);
        this.tilesSizeInPixel = tilesSizeInPixel;
    }

    public boolean canMoveRight(RectangularMapEntity entity, int amount) {
        return !entityCollideWithColumn(entity, tilesColumnIndexFor(entity.bottomRightPosition().x() + amount));
    }

    public boolean canMoveLeft(RectangularMapEntity entity, int amount) {
        return !entityCollideWithColumn(entity, tilesColumnIndexFor(entity.position().x() - amount));
    }

    public boolean canMoveUp(RectangularMapEntity entity, int amount) {
        return !entityCollideWithRow(entity, tilesRowIndexFor(entity.position().y() - amount));
    }

    public boolean canMoveDown(RectangularMapEntity entity, int amount) {
        return !entityCollideWithRow(entity, tilesRowIndexFor(entity.position().y() + amount));
    }

    private boolean entityCollideWithColumn(RectangularMapEntity entity, int tilesColumn) {
        for (int i = tilesRowIndexFor(entity.position().y()); i <= tilesRowIndexFor(entity.bottomRightPosition().y()); i++) {
            if (tiles.get(i).get(tilesColumn)) {
                return true;
            }
        }
        return false;
    }

    private boolean entityCollideWithRow(RectangularMapEntity entity, int tilesRow) {
        List<Boolean> row = tiles.get(tilesRow);
        for (int i = tilesColumnIndexFor(entity.position().x()); i <= tilesColumnIndexFor(entity.bottomRightPosition()
                .x()); i++) {
            if (row.get(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param yCoordinate
     * @return in a rectangular map with squared tiles, the index of the tiles row for the given y coordinate
     */
    private int tilesRowIndexFor(int yCoordinate) {
        return yCoordinate / tilesSizeInPixel;
    }

    /**
     * @param xCoordinate
     * @return in a rectangular map with squared tiles, the index of the tiles column for the given x coordinate
     */
    private int tilesColumnIndexFor(int xCoordinate) {
        return xCoordinate / tilesSizeInPixel;
    }
}
