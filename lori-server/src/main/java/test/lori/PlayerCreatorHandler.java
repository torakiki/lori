/* 
 * This file is part of the Lori source code
 * Created on 16/nov/2014
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

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.lori.geometry.Point;

/**
 * @author Andrea Vacondio
 *
 */
public class PlayerCreatorHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static final Logger LOG = LoggerFactory.getLogger(PlayerCreatorHandler.class);
    private final Game game;

    public PlayerCreatorHandler(Game game) {
        this.game = game;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            Player player = new Player(40, 40);
            game.addPlayer(player, new Point(50, 50));
            LOG.debug("Player {} created", player);
            if (game.ready()) {
                game.start();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        //
    }
}