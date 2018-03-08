package ca.ulaval.gif3101.ima.api.http.json.message;

import javax.servlet.http.HttpServletResponse;

public class UnexpectedErrorMessage extends AbstractJsonMessage {

    public UnexpectedErrorMessage(String message) {
        this.message = message;
        this.httpCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

}
