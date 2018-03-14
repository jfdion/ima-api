package ca.ulaval.gif3101.ima.api.domain.date;

public interface Date {
    String toString();

    boolean before(DateJodaTimeAdapter date);
}
