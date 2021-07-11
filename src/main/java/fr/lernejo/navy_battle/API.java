package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.entities.*;
import fr.lernejo.navy_battle.enums.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.Executors;

import org.json.JSONObject;

public class API extends Server {
    private final BaseEntity<ApiEntity> api = new BaseEntity<>();
    private final BaseEntity<ApiEntity> client = new BaseEntity<>();
    private final BaseEntity<MapEntity> serverMap = new BaseEntity<>();
    private final BaseEntity<MapEntity> clientMap = new BaseEntity<>();

    public void start(int port, String url) throws IOException {
        api.set(new ApiEntity(
            UUID.randomUUID().toString(),
            "http://localhost:" + port,
            "OK"
        ));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.createContext("/ping", this::ping);
        server.createContext("/api/game/start", s -> gameStart(new RequestHandler(s)));
        server.createContext("/api/game/fire", s -> gameStart(new RequestHandler(s)));
        server.start();
    }

    private void ping(HttpExchange exchange) throws IOException {
        String body = "OK";
        exchange.sendResponseHeaders(200, body.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }

    public void gameStart(RequestHandler handler) throws IOException {
        try {
            client.set(ApiEntity.fromJSON(handler.getJSONObject()));
            serverMap.set(new MapEntity(true));
            clientMap.set(new MapEntity(false));
            System.out.println("Server will fight against the following client: " + client.get().getUrl());
            handler.response(202, api.get().toJSON());
            fire();
        } catch (Exception e) {
            e.printStackTrace();
            handler.sendString(400, e.getMessage());
        }
    }

    public void fire() throws IOException, InterruptedException {
        CoordinatesEntity coordinates = clientMap.get().getNextPlaceToHit();
        var response =
            sendGET(client.get().getUrl() + "/api/game/fire?cell=" + coordinates.toString());
        if (!response.getBoolean("shipLeft")) {
            return;
        }
        var result = ResEnum.fromAPI(response.getString("consequence"));
        if (result == ResEnum.MISS)
            clientMap.get().setCell(coordinates, CellEnum.MISSED_FIRE);
        else
            clientMap.get().setCell(coordinates, CellEnum.SUCCESSFUL_FIRE);
    }
    public void handleFire(RequestHandler handler) throws IOException {
        try {
            String cell = handler.getQueryParameter("cell");
            var position = new CoordinatesEntity(cell);
            var fireResult = serverMap.get().hit(position);
            var response = new JSONObject();
            response.put("consequence", fireResult.toAPI());
            response.put("shipLeft", serverMap.get().hasShipLeft());
            handler.response(200, response);
            if (serverMap.get().hasShipLeft()) {
                fire();
            }
        } catch (Exception e) {
            e.printStackTrace();
            handler.sendString(400, e.getMessage());
        }
    }
}
