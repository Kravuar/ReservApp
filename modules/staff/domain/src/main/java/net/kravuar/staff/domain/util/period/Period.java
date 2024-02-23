package net.kravuar.staff.domain.util.period;

import java.time.LocalTime;

public interface Period {
    LocalTime getStart();
    LocalTime getEnd();
}
