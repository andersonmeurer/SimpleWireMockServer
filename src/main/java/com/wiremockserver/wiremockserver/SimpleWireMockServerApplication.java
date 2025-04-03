package com.wiremockserver.wiremockserver;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class SimpleWireMockServerApplication {

    private final WireMockServer wireMockServer;

    private final int PORT = 9000;

    public SimpleWireMockServerApplication() throws IOException {
        wireMockServer = new WireMockServer(PORT);
        wireMockServer.start();
        WireMock.configureFor("localhost", PORT);

        wireMockServer.stubFor(get(urlEqualTo("/api/user/1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 1, \"name\": \"John Doe\"}")));
    }

    public void stopServer() {
        if (wireMockServer.isRunning()) {
            wireMockServer.stop();
        }
    }

    public static void main(String[] args) throws IOException {
        SimpleWireMockServerApplication app = new SimpleWireMockServerApplication();
        Runtime.getRuntime().addShutdownHook(new Thread(app::stopServer));
    }
}