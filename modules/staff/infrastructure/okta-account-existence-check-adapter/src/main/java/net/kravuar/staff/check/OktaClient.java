package net.kravuar.staff.check;

import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Clients;
import com.okta.sdk.resource.api.UserApi;
import com.okta.sdk.resource.client.ApiException;
import net.kravuar.context.AppComponent;
import net.kravuar.staff.ports.out.AccountExistenceCheckPort;

import java.net.HttpURLConnection;

@AppComponent
class OktaClient implements AccountExistenceCheckPort {
    private final UserApi client = new UserApi(Clients.builder()
            .setOrgUrl(System.getenv().get("OKTA_CLIENT_ORGURL"))
            .setClientCredentials(new TokenClientCredentials(System.getenv().get("OKTA_CLIENT_TOKEN")))
            .build());

    @Override
    public boolean exists(String sub) {
        try {
            client.getUser(sub);
            return true;
        } catch (ApiException exception) {
            if (exception.getCode() == HttpURLConnection.HTTP_NOT_FOUND)
                return false;
            throw exception;
        }
    }
}
