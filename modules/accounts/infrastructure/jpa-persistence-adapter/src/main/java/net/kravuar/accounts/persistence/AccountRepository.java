package net.kravuar.accounts.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface AccountRepository extends JpaRepository<AccountModel, Long> {
}
