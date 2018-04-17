package ca.ulaval.gif3101.ima.api.message.domain.date;

public interface DateAdapter {
    String toString();

    boolean before(DateAdapter dateAdapter);

    boolean after(DateAdapter dateAdapter);

    java.util.Date toDate();
}
