package net.kravuar.staff.persistence.schedule;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
class WorkingHoursFragmentEmbeddable {
    private LocalTime start;
    private LocalTime end;
    private double cost;
}
