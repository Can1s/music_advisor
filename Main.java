package advisor;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean auth = false;
        boolean tr = true;
        final String CLIENT_ID = "ad99062a3c0342efae88cb10e210d1bc";
        final String CLIENT_SECRET = "d0ce2d34015645a0b505a5c890b81a63";
        final String[] query = new String[1];
        String accessCode = null;
        String accessServer = "https://accounts.spotify.com";
        String responseJson;
        for (int i = 0; i < args.length; i++) {
            if ("-access".equals(args[i])) {
                accessServer = args[i + 1];
            }
        }
        while (tr) {
            String action = scanner.nextLine();
            if (action.equals("exit")) {
                System.out.println("---GOODBYE!---");
                tr = false;
            } else if (action.equals("auth") && (!auth)) {
                auth = true;
                try {
                    HttpServer server = HttpServer.create();
                    server.bind(new InetSocketAddress(8080), 0);
                    server.createContext("/",
                            new HttpHandler() {
                                @Override
                                public void handle(HttpExchange exchange) throws IOException{
                                    query[0] = exchange.getRequestURI().getQuery();
                                    String answer;
                                    if (query[0] != null && query[0].startsWith("code=")) {
                                        answer = "Got the code. Return back to your program.";
                                    }
                                    else {
                                        answer = "Not found authorization code. Try again.";
                                    }
                                    exchange.sendResponseHeaders(200, answer.length());
                                    exchange.getResponseBody().write(answer.getBytes());
                                    exchange.getResponseBody().close();
                                    exchange.close();
                                }
                            });
                    server.setExecutor(null);
                    server.start();
                    System.out.println("use this link to request the access code:");
                    System.out.println("https://accounts.spotify.com/authorize?client_id=" +
                            CLIENT_ID +
                            "&redirect_uri=http://localhost:8080&response_type=code");
                    System.out.println("waiting for code...");

                    while (true) {
                        if(query[0] != null && query[0].startsWith("code=")) {
                            accessCode = query[0];
                            break;
                        } else if (query[0] != null) {
                            break;
                        } else {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                System.out.println("InterruptedException");
                            }
                        }
                    }
                    server.stop(1);
                    if (accessCode != null) {
                        System.out.println("code received");
                        System.out.println("Making http request for access_token...");
                        HttpClient client = HttpClient.newBuilder().build();

                        HttpRequest request = HttpRequest.newBuilder()
                                .header("Content-Type", "application/x-www-form-urlencoded")
                                .uri(URI.create(accessServer + "/api/token"))
                                .POST(HttpRequest.BodyPublishers.ofString("client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&grant_type=authorization_code&" + accessCode + "&redirect_uri=http://localhost:8080"))
                                .build();
                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        responseJson = response.body();
                        System.out.println(responseJson);
                    }
                } catch (IOException e) {
                    System.out.println("IOException");
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException");
                }
                System.out.println("---SUCCESS---");
            } else if (auth && !(action.equals("auth"))) {
                switch (action) {
                    case "new":
                        System.out.println("---NEW RELEASES---\n" +
                                "Mountains [Sia, Diplo, Labrinth]\n" +
                                "Runaway [Lil Peep]\n" +
                                "The Greatest Show [Panic! At The Disco]\n" +
                                "All Out Life [Slipknot]");
                        break;
                    case "featured":
                        System.out.println("---FEATURED---\n" +
                                "Mellow Morning\n" +
                                "Wake Up and Smell the Coffee\n" +
                                "Monday Motivation\n" +
                                "Songs to Sing in the Shower");
                        break;
                    case "categories":
                        System.out.println("---CATEGORIES---\n" +
                                "Top Lists\n" +
                                "Pop\n" +
                                "Mood\n" +
                                "Latin");
                        break;
                    case "playlists Mood":
                        System.out.println("---MOOD PLAYLISTS---\n" +
                                "Walk Like A Badass  \n" +
                                "Rage Beats  \n" +
                                "Arab Mood Booster  \n" +
                                "Sunday Stroll");
                        break;
                }
            } else {
                System.out.println("Please, provide access for application.");
            }
        }
    }
}
