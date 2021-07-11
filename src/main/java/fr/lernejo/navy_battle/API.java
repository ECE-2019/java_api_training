package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.entities.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.Executors;

public class API extends Server {
    private final BaseEntity<ApiEntity> api = new BaseEntity<>();

    public void start(int port, String url) throws IOException {
        api.set(new ApiEntity(
            UUID.randomUUID().toString(),
            "http://localhost:" + port,
            "OK"
        ));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.createContext("/ping", this::handlePing);
        server.start();
    }

    private void handlePing(HttpExchange exchange) throws IOException {
        String body = "OK";
        exchange.sendResponseHeaders(200, body.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }


}
