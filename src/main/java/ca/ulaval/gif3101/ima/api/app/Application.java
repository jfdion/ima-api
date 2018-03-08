package ca.ulaval.gif3101.ima.api.app;

import ca.ulaval.gif3101.ima.api.app.context.Context;
import spark.Filter;
import spark.Service;

import java.util.ArrayList;
import java.util.List;

import static spark.debug.DebugScreen.enableDebugScreen;

public class Application {

    private static final Integer DEFAULT_PORT = 8080;
    private static final boolean DEBUG_MODE = false;

    private Service spark;

    private boolean isDebugMode = DEBUG_MODE;
    private int portNumber = DEFAULT_PORT;

    private List<Filter> beforeFilters = new ArrayList<>();
    private List<Filter> afterFilters = new ArrayList<>();

    private List<Context> contexts = new ArrayList<>();

    public Application() {
    }

    public Application(Integer port) {
        portNumber = port;
    }

    public void enableDebug() {
        isDebugMode = true;
    }

    public void addContext(Context context) {
        if (!contexts.contains(context)) {
            contexts.add(context);
        }
    }

    public void addBeforeFilter(Filter filter) {
        if (!beforeFilters.contains(filter)) {
            beforeFilters.add(filter);
        }
    }

    public void addAfterFitler(Filter filter) {
        if (!afterFilters.contains(filter)) {
            afterFilters.add(filter);
        }
    }

    public void start() {
        spark = Service.ignite().port(portNumber);
        //spark.staticFiles.location("/web");
        //spark.staticFiles.expireTime(600L);
        if (isDebugMode) {
            enableDebugScreen();
        }

        for (Filter beforeFilter : beforeFilters) {
            spark.before("*", beforeFilter);
        }

        for (Context context : contexts) {
            context.run(spark);
        }

        for (Filter afterFilter : afterFilters) {
            spark.before("*", afterFilter);
        }

    }
}
