package ca.ulaval.gif3001.ima.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import javaslang.control.Try;

import static spark.Spark.*;

public class APIServer {

    private static ObjectMapper jsonObjectMapper = new ObjectMapper();

    public static void main(String[] args) {

        InitEnvVars initEnvVars = new InitEnvVars().invoke();
        Integer portNumber = initEnvVars.getPortNumber();
        String dbUser = initEnvVars.getDbUser();
        String dbPassword = initEnvVars.getDbPassword();
        String dbHost = initEnvVars.getDbHost();

        port(portNumber);

        get("/", (req, res) -> "Project dashboard api");
        get("/status", (req, res) -> String.format("Running on port %s, database user %s, database password %s, database host %s", portNumber, dbUser, dbPassword, dbHost));
        get("/ping", (req, res) -> "pong bang crash");

        options("*", (request, response) -> "");
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "*");
            response.header("Access-Control-Allow-Headers", "*");
            response.type("application/json");
        });
    }

    private static class InitEnvVars {
        private Integer portNumber;
        private String dbUser;
        private String dbPassword;
        private String dbHost;

        public Integer getPortNumber() {
            return portNumber;
        }

        public String getDbUser() {
            return dbUser;
        }

        public String getDbPassword() {
            return dbPassword;
        }

        public String getDbHost() {
            return dbHost;
        }

        public InitEnvVars invoke() {
            portNumber = Try.of(() -> Integer.valueOf(System.getenv("PORT"))).orElseGet((t) -> {
                System.err.println("There was an error retrieving PORT env var using the default one (8080)");
                return 8080;
            });


            dbUser = Try.of(() -> stringValueOf(System.getenv("DB_USER"))).orElseGet((t) -> {
                System.err.println("There was an error retrieving DB_USER env var using the default one (root)");
                return "robert";
            });

            dbPassword = Try.of(() -> stringValueOf(System.getenv("DB_PASSWORD"))).orElseGet((t) -> {
                System.err.println("There was an error retrieving DB_PASSWORD env var using the default one (\"\")");
                return "";
            });

            dbHost = Try.of(() -> stringValueOf(System.getenv("DB_HOST"))).orElseGet((t) -> {
                System.err.println("There was an error retrieving DB_PASSWORD env var using the default one (localhost)");
                return "localhost";
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
