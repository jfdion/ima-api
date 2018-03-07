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
        Integer dbPort = initEnvVars.getDbPort();
        String dbName = initEnvVars.getDbName();
        port(portNumber);

        get("/", (req, res) -> "Project dashboard api");
        get("/status", (req, res) -> String.format("mongodb://%s:%s@%s:%d/%s", dbUser, dbPassword, dbHost, dbPort, dbName));
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
        private String env;
        private Integer portNumber;
        private String dbUser;
        private String dbPassword;
        private String dbHost;
        private Integer dbPort;
        private String dbName;

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

        public Integer getDbPort() {
            return dbPort;
        }

        public String getDbName() {
            return dbName;
        }

        public InitEnvVars invoke() {
            env= Try.of(() -> stringValueOf(System.getenv("ENV"))).orElseGet((t) -> {
                System.err.println("There was an error retrieving ENV env var using the default one (dev)");
                return "dev";
            });

            portNumber = Try.of(() -> Integer.valueOf(System.getenv("PORT"))).orElseGet((t) -> {
                System.err.println("There was an error retrieving PORT env var using the default one (8080)");
                return 8080;
            });
            if (!env.equals("dev")) {
                dbPort = Try.of(() -> Integer.valueOf(System.getenv("DB_PORT"))).orElseGet((t) -> {
                    System.err.println("There was an error retrieving DB_PORT env var using the default one (59778)");
                    return 59778;
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

                dbName = Try.of(() -> stringValueOf(System.getenv("DB_NAME"))).orElseGet((t) -> {
                    System.err.println("There was an error retrieving DB_NAME env var using the default one (database)");
                    return "database";
                });
            }

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
