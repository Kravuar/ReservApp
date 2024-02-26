package net.kravuar.staff.check;

import net.kravuar.context.AppComponent;
import net.kravuar.staff.ports.out.AccountExistenceCheckPort;

@AppComponent
class FakeAccountExistenceCheckAdapter implements AccountExistenceCheckPort {

    @Override
    public boolean exists(String sub) {
        return true;
    }
}