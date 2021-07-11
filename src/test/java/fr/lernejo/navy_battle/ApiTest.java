package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class ApiTest {
    private final HttpClient client = HttpClient.newHttpClient();
    static final Launcher server = new Launcher();

    @Test
    public void
    pingTest() throws Exception {
        Launcher.main(new String[]{"3001"});

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:3001/ping"))
            .GET()
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals("OK", response.body());
    }

    @Test
    void handleStartTest() throws IOException, InterruptedException {
        Launcher.main(new String[]{"3001"});

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:3001/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .GET()
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(response.statusCode(), 400);
    }

    @Test
    void handleFireTest() throws IOException, InterruptedException {
        Launcher.main(new String[]{"3001"});

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:3001/api/game/fire"))
            .setHeader("Accept", "application/json")
            .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(response.statusCode(), 400);
    }
}
