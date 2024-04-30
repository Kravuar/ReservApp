package net.kravuar.staff.check;

import net.kravuar.context.AppComponent;
import net.kravuar.staff.model.AccountDetails;
import net.kravuar.staff.ports.out.AccountRetrievalPort;

@AppComponent
class FakeAccountRetrievalAdapter implements AccountRetrievalPort {

    @Override
    public boolean exists(String sub) {
        return true;
    }

    @Override
    public AccountDetails getBySub(String sub) {
        return new AccountDetails("aboba");
    }
}