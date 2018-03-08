package ca.ulaval.gif3101.ima.api.http.json.message;

import javax.servlet.http.HttpServletResponse;

public class OkMessage extends AbstractJsonMessage {

    public OkMessage(String message) {
        this.message = message;
        this.httpCode = HttpServletResponse.SC_OK;
    }
}
