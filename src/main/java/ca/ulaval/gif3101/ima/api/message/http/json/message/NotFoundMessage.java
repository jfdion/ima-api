package ca.ulaval.gif3101.ima.api.message.http.json.message;

import javax.servlet.http.HttpServletResponse;

public class NotFoundMessage extends AbstractJsonMessage {

    public NotFoundMessage(String message) {
        this.message = message;
        this.httpCode = HttpServletResponse.SC_NOT_FOUND;
    }
}
