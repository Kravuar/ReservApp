package net.kravuar.business.checking;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "account")
@Immutable
@Getter
class AccountEmailView {
    @Id
    private Long id;

    @Column private boolean emailVerified;
}
