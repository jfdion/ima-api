package ca.ulaval.gif3001.ima.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import javaslang.control.Try;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class APIServer {

    private static ObjectMapper jsonObjectMapper = new ObjectMapper();

    public static void main(String[] args) {
        Integer portNumber = Try.of(() -> Integer.valueOf(System.getenv("PORT"))).orElseGet((t) -> {
            System.err.println("There was an error retrieving PORT env var using the default one (8080)");
            return 8080;
        });

        String dbUser = Try.of(() -> String.valueOf(System.getenv("DB_USER"))).orElseGet((t) -> {
            System.err.println("There was an error retrieving DB_USER env var using the default one (robert)");
            return "robert";
        });

        port(portNumber);

        get("/", (req, res) -> "Project dashboard api");
        get("/status", (req, res) -> String.format("Running on port %s, database user %s", portNumber, dbUser));
        get("/ping", (req, res) -> "pong bang crash");

        options("*", (request, response) -> "");
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "*");
            response.header("Access-Control-Allow-Headers", "*");
            response.type("application/json");
        });
    }
}
