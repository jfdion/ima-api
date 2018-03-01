package ca.ulaval.glo3001.ima.api;

import ca.ulaval.glo3001.ima.api.handler.TestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import javaslang.control.Try;

import static spark.Spark.*;

public class APIServer {

    private static ObjectMapper jsonObjectMapper = new ObjectMapper();

    public static void main(String[] args) {

        Integer portNumber = Try.of(() -> Integer.valueOf(System.getenv("PORT"))).orElseGet((t) -> {
            System.err.println("There was an error retrieving PORT env var using the default one (8080)");
            return 8080;
        });

        port(portNumber);

        get("/api/test", new TestHandler(), jsonObjectMapper::writeValueAsString);

        options("*", (request, response) -> "");
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "*");
            response.header("Access-Control-Allow-Headers", "*");
            response.type("application/json");
        });
    }
}
