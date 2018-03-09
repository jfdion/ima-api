package ca.ulaval.gif3101.ima.api.http.json.message;

import javax.servlet.http.HttpServletResponse;

public class NoContentMessage extends AbstractJsonMessage {

    public NoContentMessage(String message) {
        this.message = message;
        this.httpCode = HttpServletResponse.SC_NO_CONTENT;
    }
}
