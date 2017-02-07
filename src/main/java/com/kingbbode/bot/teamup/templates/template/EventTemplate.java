package com.kingbbode.bot.teamup.templates.template;

import com.kingbbode.bot.common.base.setting.BotInfoProperties;
import com.kingbbode.bot.common.response.EventResponse;
import com.kingbbode.bot.teamup.templates.BaseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import javax.annotation.PostConstruct;

/**
 * Created by YG on 2016-10-13.
 */
@Component
public class EventTemplate extends BaseTemplate {
    @Autowired
    BotInfoProperties environmentProperties;


    @Autowired
    @Qualifier(value = "eventRestOperations")
    RestOperations restOperations;
    
    @PostConstruct
    void init(){
        super.setRestOperations(restOperations);
    }

    public EventResponse getEvent() {
        ParameterizedTypeReference<EventResponse> p = new ParameterizedTypeReference<EventResponse>() {
        };
        return get(environmentProperties.getEventUrl(),  p);
    }
}
