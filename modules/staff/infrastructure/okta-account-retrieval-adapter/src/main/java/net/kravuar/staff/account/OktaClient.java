package net.kravuar.staff.account;

import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Clients;
import com.okta.sdk.resource.api.UserApi;
import com.okta.sdk.resource.client.ApiException;
import com.okta.sdk.resource.model.User;
import com.okta.sdk.resource.model.UserProfile;
import net.kravuar.context.AppComponent;
import net.kravuar.staff.domain.AccountDetails;
import net.kravuar.staff.domain.exceptions.StaffNotFoundException;
import net.kravuar.staff.ports.out.AccountRetrievalPort;

import java.net.HttpURLConnection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AppComponent
class OktaClient implements AccountRetrievalPort {
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

    @Override
    public AccountDetails getBySub(String sub) {
        try {
            User user = client.getUser(sub);
            UserProfile userProfile = user.getProfile();
            String fullName = "";
//        URI picture = null;
            if (userProfile != null) {
                fullName = Stream.of(userProfile.getFirstName(), userProfile.getLastName())
                        .filter(Objects::nonNull)
                        .filter(str -> !str.isBlank())
                        .collect(Collectors.joining(" "));
//            picture = URI.create(String.valueOf(userProfile.getAdditionalProperties().get("picture")));
            }
            return new AccountDetails(
                    fullName
//                picture
            );
        } catch (ApiException exception) {
            throw new StaffNotFoundException();
        }
    }
}
