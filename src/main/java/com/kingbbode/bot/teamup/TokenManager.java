package com.kingbbode.bot.teamup;

import com.kingbbode.bot.sensor.TeamUpEventSensor;
import com.kingbbode.bot.teamup.oauth2.OAuth2Token;
import com.kingbbode.bot.teamup.templates.template.Oauth2Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by YG on 2016-05-11.
 */
@Component
public class TokenManager {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private TeamUpEventSensor teamUpEventSensor;

    private OAuth2Token accessToken;
    
    @Autowired
    private Oauth2Template oauth2Template;
    
    @PostConstruct
    void init(){
        accessToken = oauth2Template.token(accessToken);
        if(!accessToken.equals("null") && accessToken!=null) {
            teamUpEventSensor.setReady(true);
        }else{
            logger.error("Authentication Failed");
            System.exit(0);
        }
    }

    public String getAccessToken() {
        accessToken = oauth2Template.token(accessToken);
        return accessToken.getAccessToken();
    }
}
