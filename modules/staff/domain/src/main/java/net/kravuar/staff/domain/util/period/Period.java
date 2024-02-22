package net.kravuar.staff.domain.util.period;

import java.time.LocalDateTime;

public interface Period {
    LocalDateTime getStart();
    LocalDateTime getEnd();
}
