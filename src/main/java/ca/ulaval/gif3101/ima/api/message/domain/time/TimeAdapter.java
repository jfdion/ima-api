package ca.ulaval.gif3101.ima.api.message.domain.time;

public interface TimeAdapter {

    static final String MIDNIGHT_TIME_START = "00:00";
    static final String MIDNIGHT_TIME_END = "23:59:59";

    Integer hour();
    Integer minute();

    boolean before(TimeAdapter time);
    boolean after(TimeAdapter time);
    boolean between(TimeAdapter start, TimeAdapter end);

    TimeAdapter startOfDay();
    TimeAdapter endOfDay();

    boolean equals(TimeAdapter timeAdapter);

}
