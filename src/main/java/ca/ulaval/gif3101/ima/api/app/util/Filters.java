package ca.ulaval.gif3101.ima.api.app.util;

import spark.*;

import static ca.ulaval.gif3101.ima.api.app.util.RequestUtil.*;

public class Filters {

    public static Filter addTrailingSlashes = (Request request, Response response) -> {
        if (!request.pathInfo().endsWith("/")) {
            response.redirect(request.pathInfo() + "/");
        }
    };

    public static Filter handleLocaleChange = (Request request, Response response) -> {
        if (getQueryLocale(request) != null) {
            request.session().attribute("locale", getQueryLocale(request));
            response.redirect(request.pathInfo());
        }
    };

    public static Filter addGzipHeader = (Request request, Response response) -> {
        response.header("Content-Encoding", "gzip");
    };
}
