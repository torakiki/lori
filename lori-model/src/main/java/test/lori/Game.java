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

import static test.lori.util.RequireUtils.require;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.lori.geometry.Point;
import test.lori.map.TileMap;

/**
 * @author Andrea Vacondio
 *
 */
public class Game {
    private static final Logger LOG = LoggerFactory.getLogger(Game.class);
    private static final int PHYSICS_TICK = 15;
    private static final int OUT_TICK = 45;

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private List<Request> inputCommands = new ArrayList<>();
    private List<Response> outputCommands = new ArrayList<>();
    private boolean started = false;
    private ChannelGroup channelGroup;

    public Game(ChannelGroup channelGroup) {
        require(channelGroup != null);
        this.channelGroup = channelGroup;
    }

    private Set<Player> players = new HashSet<>();
    private TileMap map = new TileMap();

    public void addPlayer(Player player, Point point) {
        require(!started);
        if (map.canDrop(player, point)) {
            player.dropAtPosition(point);
            players.add(player);
        }
    }

    public Future<Boolean> addCommand(Request command) {
        LOG.debug("Adding command {}", command.id());
        return executor.submit(() -> inputCommands.add(command));
    }

    public void start() {
        this.started = true;
        executor.scheduleWithFixedDelay(() -> {
            inputCommands.stream().map(Response::forRequest).forEachOrdered(outputCommands::add);
            inputCommands.clear();
        }, PHYSICS_TICK, PHYSICS_TICK, TimeUnit.MILLISECONDS);
        executor.scheduleWithFixedDelay(() -> {
            outputCommands.forEach(r -> {
                channelGroup.write(new TextWebSocketFrame(r.toString()).retain());
                LOG.debug("Response {} written to the group", r);
            });
            if (!outputCommands.isEmpty()) {
                channelGroup.flush();
            }
            outputCommands.clear();
        }, OUT_TICK, OUT_TICK, TimeUnit.MILLISECONDS);
    }

    public boolean ready() {
        return players.size() > 3;
    }
}
