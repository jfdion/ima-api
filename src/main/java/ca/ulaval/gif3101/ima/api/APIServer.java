package ca.ulaval.gif3101.ima.api;

import ca.ulaval.gif3101.ima.api.bootstrap.Bootstrap;
import ca.ulaval.gif3101.ima.api.controller.message.MessageController;
import javaslang.control.Try;

import static spark.Spark.*;

public class APIServer {

    public static void main(String[] args) throws Exception {
        InitEnvVars initEnvVars = new InitEnvVars().invoke();
        Bootstrap bootstrap = new Bootstrap(initEnvVars.getEnv());

        port(initEnvVars.getPortNumber());

        get("/", (req, res) -> "Project dashboard api");

        get("/ping", (req, res) -> "pong");

        MessageController messageController = bootstrap.messageController();
        get("/api/messages", (req, res) -> messageController.getAll(req, res));
        get("/api/messages/:message-id", (req, res) -> messageController.getOne(req, res));
        post("/api/messages", (req, res) -> messageController.create(req, res));

        options("*", (request, response) -> "");
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "*");
            response.header("Access-Control-Allow-Headers", "*");
            response.type("application/json");
        });

    }

    private static class InitEnvVars {
        private String env;
        private Integer portNumber;
        private String mongoDb;

        public Integer getPortNumber() {
            return portNumber;
        }

        public String getEnv() {
            return env;
        }

        public String getMongoDb() {
            return mongoDb;
        }

        public InitEnvVars invoke() {
            env = Try.of(() -> stringValueOf(System.getenv("ENV"))).orElseGet((t) -> {
                System.err.println("There was an error retrieving ENV env var using the default one (dev)");
                return "dev";
            });

            portNumber = Try.of(() -> Integer.valueOf(System.getenv("PORT"))).orElseGet((t) -> {
                System.err.println("There was an error retrieving PORT env var using the default one (8080)");
                return 8080;
            });

            env = Try.of(() -> stringValueOf(System.getenv("MONGO_DB"))).orElseGet((t) -> {
                System.err.println("There was an error retrieving MONGO_DB env var using the default one (mongodb://dropmessage-ripoff:zxcvbn%95@ds259778.mlab.com:59778/ima-api)");
                return "mongodb://dropmessage-ripoff:zxcvbn%95@ds259778.mlab.com:59778/ima-api";
            });

            return this;
        }

        private String stringValueOf(String value) throws Exception {
            if (value == null) {
                throw new Exception("Invalid argument, cannot be of null value");
            }
            return String.valueOf(value);
        }
    }
}
