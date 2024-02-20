package net.kravuar.services.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "service")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ServiceModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BusinessModel business;
    @Column(unique = true)
    private String name;
    @Column(nullable = false)
    boolean active;
}
