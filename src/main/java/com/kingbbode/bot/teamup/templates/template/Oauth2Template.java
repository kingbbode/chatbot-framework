package com.kingbbode.bot.teamup.templates.template;

import com.kingbbode.bot.common.base.setting.BotInfoProperties;
import com.kingbbode.bot.common.enums.GrantType;
import com.kingbbode.bot.teamup.oauth2.OAuth2Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

/**
 * Created by YG on 2016-10-14.
 */
@Component
public class Oauth2Template  {
    @Autowired
    @Qualifier(value = "messageRestOperations")
    private RestOperations restOperations;

    @Autowired
    private BotInfoProperties environmentProperties;
    
    public OAuth2Token token(OAuth2Token accessToken){
        if (accessToken == null) {
            return post(accessToken, GrantType.PASSWORD);
        }else{
            if (accessToken.isExpired()) {
                return refresh(accessToken);
            }
        }
        return accessToken;
    }

    private OAuth2Token refresh(OAuth2Token accessToken) {
        return post(accessToken, GrantType.REFRESH);
    }

    private OAuth2Token post(OAuth2Token accessToken, GrantType grantType) {
        ResponseEntity<OAuth2Token> response = restOperations.postForEntity(environmentProperties.getTokenUrl(), getEntity(accessToken, grantType),
                OAuth2Token.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            accessToken = response.getBody();
        }

        return accessToken;
    }
    

    private HttpEntity<Object> getEntity(OAuth2Token oAuth2AccessToken, GrantType grantType) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> data = new LinkedMultiValueMap<>();
        data.add("grant_type", grantType.getKey());
        if (GrantType.PASSWORD.equals(grantType)) {
            data.add("client_id", environmentProperties.getClientId());
            data.add("client_secret", environmentProperties.getClientSecret());
            data.add("username", environmentProperties.getName());
            data.add("password", environmentProperties.getPassword());
        } else if (GrantType.REFRESH.equals(grantType)) {
            data.add("refresh_token", oAuth2AccessToken.getRefreshToken());
        }
        return new HttpEntity<>(data, header);
    }
}
