package ca.ulaval.gif3101.ima.api;

import ca.ulaval.gif3101.ima.api.app.Application;
import ca.ulaval.gif3101.ima.api.app.config.CorsConfig;
import ca.ulaval.gif3101.ima.api.app.context.Context;
import ca.ulaval.gif3101.ima.api.bootstrap.Bootstrap;
import ca.ulaval.gif3101.ima.api.http.context.RestContext;
import ca.ulaval.gif3101.ima.api.http.endpoint.MessageEndpoint;
import javaslang.control.Try;

public class APIServer {

    public static void main(String[] args) throws Exception {
        InitEnvVars initEnvVars = new InitEnvVars().invoke();
        Bootstrap bootstrap = new Bootstrap(initEnvVars.getEnv());
        Application app = new Application(initEnvVars.getPortNumber());

        Context restContext = new RestContext("/api", initCorsConfig());
        restContext.addEndpoint(new MessageEndpoint(bootstrap.messageController()));

        app.addContext(restContext);
        app.start();
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
            env= Try.of(() -> stringValueOf(System.getenv("ENV"))).orElseGet((t) -> {
                System.err.println("There was an error retrieving ENV env var using the default one (dev)");
                return "dev";
            });

            portNumber = Try.of(() -> Integer.valueOf(System.getenv("PORT"))).orElseGet((t) -> {
                System.err.println("There was an error retrieving PORT env var using the default one (8080)");
                return 8080;
            });

            env= Try.of(() -> stringValueOf(System.getenv("MONGO_DB"))).orElseGet((t) -> {
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

    private static CorsConfig initCorsConfig() {
        CorsConfig corsConfig = new CorsConfig();
        corsConfig.setPath("/api");
        corsConfig.addOrigin("*");
        corsConfig.addMethod("GET");
        corsConfig.addMethod("POST");
        corsConfig.addMethod("PUT");
        corsConfig.addMethod("DELETE");
        corsConfig.addMethod("OPTIONS");
        corsConfig.addHeader("X-Requested-With");
        corsConfig.addHeader("Content-Type");
        return corsConfig;
    }
}
