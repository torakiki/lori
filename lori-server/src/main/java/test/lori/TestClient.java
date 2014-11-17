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

import java.net.URI;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

/**
 * @author Andrea Vacondio
 *
 */
public class TestClient {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String destUri = "ws://localhost:9999";
        if (args.length > 0) {
            destUri = args[0];
        }
        WebSocketClient client = new WebSocketClient();
        SimpleEchoSocket socket = new SimpleEchoSocket();
        try {
            client.start();
            URI echoUri = new URI(destUri);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket, echoUri, request);
            System.out.printf("Connecting to : %s%n", echoUri);
            socket.awaitClose(60, TimeUnit.SECONDS);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @WebSocket(maxTextMessageSize = 64 * 1024)
    public static class SimpleEchoSocket {

        private final CountDownLatch closeLatch;
        private UUID uuid = UUID.randomUUID();
        private Session session;

        public SimpleEchoSocket() {
            this.closeLatch = new CountDownLatch(1);
        }

        public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
            return this.closeLatch.await(duration, unit);
        }

        @OnWebSocketClose
        public void onClose(int statusCode, String reason) {
            System.out.printf("Connection closed: %d - %s%n", statusCode, reason);
            this.session = null;
            this.closeLatch.countDown();
        }

        @OnWebSocketConnect
        public void onConnect(Session session) {
            System.out.printf("Got connect: %s%n", session);
            this.session = session;
            try {
                Future<Void> fut;
                fut = session.getRemote().sendStringByFuture("Hello from " + uuid.toString());
                fut.get(2, TimeUnit.SECONDS);
                fut = session.getRemote().sendStringByFuture(
                        "Thanks for the conversation, " + uuid.toString() + " out!");
                fut.get(2, TimeUnit.SECONDS);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        @OnWebSocketMessage
        public void onMessage(String msg) {
            System.out.printf("Got msg: %s%n", msg);
        }
    }
}
