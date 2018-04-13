package ca.ulaval.gif3101.ima.api.message.domain.VisibilityPeriod;

import ca.ulaval.gif3101.ima.api.message.domain.time.TimeAdapter;
import org.joda.time.LocalTime;

public class VisibilityPeriod {

    protected TimeAdapter start;
    protected TimeAdapter end;

    public VisibilityPeriod(TimeAdapter start, TimeAdapter end) {
        this.start = start;
        this.end = end;
    }

    public TimeAdapter start() {
        return start;
    }

    public TimeAdapter end() {
        return end;
    }

    public boolean isVisible(TimeAdapter time) {
        if (start.before(end)) {
            return start.before(time) && end.after(time);
        } else {
            return time.between(start, time.endOfDay()) || time.between(time.startOfDay(), end);
        }
    }
}
