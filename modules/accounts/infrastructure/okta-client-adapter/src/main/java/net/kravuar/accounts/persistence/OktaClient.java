package net.kravuar.accounts.persistence;

import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Clients;
import com.okta.sdk.resource.api.UserApi;
import com.okta.sdk.resource.client.ApiException;
import com.okta.sdk.resource.model.User;
import com.okta.sdk.resource.user.UserBuilder;
import net.kravuar.accounts.domain.Account;
import net.kravuar.accounts.domain.exceptions.AccountException;
import net.kravuar.accounts.ports.out.AccountPersistencePort;
import net.kravuar.context.AppComponent;

@AppComponent
class OktaClient implements AccountPersistencePort {
    private final UserApi client;

    OktaClient() {
        String url = System.getenv().get("OKTA_CLIENT_ORGURL");
        String token = System.getenv().get("OKTA_CLIENT_TOKEN");
        this.client = new UserApi(Clients.builder()
                .setOrgUrl(url)
                .setClientCredentials(new TokenClientCredentials(token))
                .build());
    }

    @Override
    public Account save(Account account) {
        // TODO: mapstruct
        try {
            User user = UserBuilder.instance()
                    .setEmail(account.getEmail())
                    .setFirstName(account.getFirstName())
                    .setLastName(account.getLastName())
                    .setNickName(account.getUsername())
                    .setPassword(account.getPassword().toCharArray())
                    .buildAndCreate(client);

            return Account.builder()
                    .username(account.getUsername())
                    .firstName(account.getFirstName())
                    .lastName(account.getLastName())
                    .email(account.getEmail())
                    .sub(user.getId())
                    .password(account.getPassword())
                    .build();
        } catch (ApiException exception) {
            // TODO: somehow extract whether it was username/email already taken error and map to existing exceptions
            // ApiExceptionHelper.getError(exception).

            throw new AccountException("Could not create account");
        }
    }
}
