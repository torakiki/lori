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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.lori.geometry.Point;

/**
 * @author Andrea Vacondio
 *
 */
public class BlockingTileLayer {
    private static final Logger LOG = LoggerFactory.getLogger(BlockingTileLayer.class);

    // a list of rows of tiles
    private List<List<Boolean>> tiles = new ArrayList<>(new ArrayList<>());
    private int tilesSizeInPixel;

    public BlockingTileLayer(int tilesSizeInPixel, int width, int height) {
        require(tilesSizeInPixel > 0);
        require(width > 0 && height > 0);
        tiles = Collections.nCopies(height, Collections.nCopies(width, Boolean.FALSE));
        this.tilesSizeInPixel = tilesSizeInPixel;
    }

    /**
     * @return if the entity can be dropped at the given point without colliding with anything
     */
    public boolean canDrop(RectangularMapEntity entity, Point point) {
        return !isCollisionRow(tilesRowIndexFor(point.y()), tilesColumnIndexFor(point.x()),
                tilesColumnIndexFor(point.x() + entity.width()))
                && !isCollisionRow(tilesRowIndexFor(point.y() + entity.height()), tilesColumnIndexFor(point.x()),
                        tilesColumnIndexFor(point.x() + entity.width()))
                && !isCollisionColumn(tilesColumnIndexFor(point.x()), tilesRowIndexFor(point.y()),
                        tilesRowIndexFor(point.y() + entity.height()))
                && !isCollisionColumn(tilesColumnIndexFor(point.x() + entity.width()), tilesRowIndexFor(point.y()),
                        tilesRowIndexFor(point.y() + entity.height()));

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

    /**
     * @return true if the given colNum collides with the given entity
     */
    private boolean entityCollideWithColumn(RectangularMapEntity entity, int colNum) {
        return isCollisionColumn(colNum, tilesRowIndexFor(entity.position().y()), tilesRowIndexFor(entity
                .bottomRightPosition().y()));
    }

    /**
     * @return true if at least one of the tiles in colNum, starting from startRow to endRow, is a blocking tile.
     */
    private boolean isCollisionColumn(int colNum, int startRow, int endRow) {
        try {
            for (int i = startRow; i <= endRow; i++) {
                if (tiles.get(i).get(colNum)) {
                    return true;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            LOG.warn("Invalid tiles coordinates: [col=" + colNum + ", startRow=" + startRow + ", endRow=" + endRow
                    + "]", e);
        }
        return false;
    }

    /**
     * @return true if the given rowNum collides with the given entity
     */
    private boolean entityCollideWithRow(RectangularMapEntity entity, int rowNum) {
        return isCollisionRow(rowNum, tilesColumnIndexFor(entity.position().x()), tilesColumnIndexFor(entity
                .bottomRightPosition().x()));
    }

    /**
     * @return true if at least one of the tiles in rowNum, starting from startColumn to endColumn, is a blocking tile.
     */
    private boolean isCollisionRow(int rowNum, int startColumn, int endColumn) {
        try {
            List<Boolean> row = tiles.get(rowNum);
            for (int i = startColumn; i <= endColumn; i++) {
                if (row.get(i)) {
                    return true;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            LOG.warn("Invalid tiles coordinates: [row=" + rowNum + ", startCol" + startColumn + ", endCol" + endColumn
                    + "]", e);
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
