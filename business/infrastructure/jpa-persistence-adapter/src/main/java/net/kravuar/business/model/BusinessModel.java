package net.kravuar.business.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "business")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class BusinessModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id private final Long id;

    @Column private String name;
    @Column private String email;
    @Column private boolean emailVerified;
}
