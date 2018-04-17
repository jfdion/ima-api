package ca.ulaval.gif3101.ima.api.message.domain.author;

public class Author {

    public final static String DEFAULT_USERNAME = "Anonymous";

    protected String username;

    public Author(String username) {
        this.username = username;
    }

    public Author() {
        this.username = DEFAULT_USERNAME;
    }

    public String getUsername() {
        return username;
    }
}
