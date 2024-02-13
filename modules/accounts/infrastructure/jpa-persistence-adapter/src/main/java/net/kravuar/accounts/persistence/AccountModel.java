package net.kravuar.accounts.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class AccountModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id private Long id;

    @Column private String username;
    @Column private String email;
    @Column private boolean emailVerified;
    @Column private String passwordEncrypted;
}
