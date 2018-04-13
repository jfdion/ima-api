package ca.ulaval.gif3101.ima.api.message.http.json.message;

import javax.servlet.http.HttpServletResponse;

public class CreatedMessage extends AbstractJsonMessage {

    public CreatedMessage(String message) {
        this.message = message;
        this.httpCode = HttpServletResponse.SC_CREATED;
    }
}
