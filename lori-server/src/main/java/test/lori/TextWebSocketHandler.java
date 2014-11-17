/* 
 * This file is part of the Lori source code
 * Created on 15/nov/2014
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
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Vacondio
 *
 */
public class TextWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static final Logger LOG = LoggerFactory.getLogger(TextWebSocketHandler.class);

    private final ChannelGroup group;
    private final Game game;
    private final AtomicLong ids = new AtomicLong();

    public TextWebSocketHandler(ChannelGroup group, Game game) {
        this.group = group;
        this.game = game;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            LOG.debug("Client {} connected, adding to group {}", ctx.channel(), group);
            group.add(ctx.channel());
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        game.addCommand(new Request(ids.incrementAndGet(), msg.text()));
    }
}
