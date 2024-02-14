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
    @Id private Long id;

    @ManyToOne
    private OwnerModel ownerModel;

    @Column private String name;
    @Column boolean active;
}
