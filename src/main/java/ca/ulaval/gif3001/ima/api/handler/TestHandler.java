package ca.ulaval.gif3001.ima.api.handler;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;

public class TestHandler implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return new ArrayList<>();
    }
}
