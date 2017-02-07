package com.kingbbode.bot.teamup.templates.template;

import com.kingbbode.bot.common.base.setting.BotInfoProperties;
import com.kingbbode.bot.common.base.setting.BotProperties;
import com.kingbbode.bot.common.request.MessageRequest;
import com.kingbbode.bot.common.response.MessageResponse;
import com.kingbbode.bot.teamup.templates.BaseTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import javax.annotation.PostConstruct;

/**
 * Created by YG on 2016-10-13.
 */
@Component
public class EdgeTemplate extends BaseTemplate {
    @Autowired
    private BotInfoProperties environmentProperties;
    
    @Autowired
    private BotProperties botProperties;

    private static final String TEST_FEED_GROUP = "13572";
    private static final String TEST_MESSAGE_GROUP = "70367";

    @Autowired
    @Qualifier(value = "messageRestOperations")
    RestOperations restOperations;
    @PostConstruct
    void init(){
        super.setRestOperations(restOperations);
    }
    
    @Async
    public MessageResponse readMessage(String message, String room) {
        ParameterizedTypeReference<MessageResponse> p = new ParameterizedTypeReference<MessageResponse>() {
        };
        return get(environmentProperties.getReadUrl() + room + "/1/0/" + message, p);
        
    }

    @Async
    public void sendMessage(String message, String room) {
        if(!room.equals("999999999999") && !StringUtils.isEmpty(message)) {
            ParameterizedTypeReference<MessageResponse> p = new ParameterizedTypeReference<MessageResponse>() {
            };
            post(environmentProperties.getSendUrl() + (botProperties.isTestMode()?TEST_MESSAGE_GROUP:room), new MessageRequest(message), p);
        }
    }

    @Async
    public void writeFeed(String message, String room) {
        ParameterizedTypeReference<MessageResponse> p = new ParameterizedTypeReference<MessageResponse>() {
        };
        post(environmentProperties.getFeedWriteUrl() + (botProperties.isTestMode()?TEST_FEED_GROUP:room),new MessageRequest(message), p);
    }

    @Async
    public void sendEmoticon(String fileId, String room) {
        if(!room.equals("999999999999") && !StringUtils.isEmpty(fileId)) {
            ParameterizedTypeReference<MessageResponse> p = new ParameterizedTypeReference<MessageResponse>() {
            };
            post(environmentProperties.getSendUrl() + (botProperties.isTestMode()?TEST_MESSAGE_GROUP:room) +"/2", new MessageRequest(fileId), p);
        }
    }
}
