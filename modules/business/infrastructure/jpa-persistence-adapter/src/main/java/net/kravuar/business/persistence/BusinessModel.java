package net.kravuar.business.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "business")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class BusinessModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String ownerSub;
    @Column(unique = true)
    private String name;
    @Column(nullable = false)
    boolean active;
}
