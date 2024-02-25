package net.kravuar.staff.persistence.schedule;

import jakarta.persistence.*;
import lombok.*;
import net.kravuar.staff.domain.Service;
import net.kravuar.staff.domain.Staff;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NavigableSet;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ScheduleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Service service;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Staff staff;

    @Column(nullable = false)
    @Enumerated
    private DayOfWeek dayOfWeek;

    @Column(updatable = false, nullable = false)
    private LocalDate validFrom;
    @Column(updatable = false)
    private LocalDateTime disabledAt;

    @ElementCollection
    private NavigableSet<WorkingHoursFragmentEmbeddable> workingHours;
}
