package net.kravuar.business.checking;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface AccountEmailViewRepository extends org.springframework.data.repository.Repository<AccountEmailView, Long> {
    Optional<AccountEmailView> findOneById(long id);
}
