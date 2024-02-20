package net.kravuar.services.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "business")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class BusinessModel {
    @Id
    private Long id;

    @Column(nullable = false)
    boolean active;

    @Column(updatable = false, nullable = false)
    String ownerSub;
}
