package com.kingbbode.bot.teamup.message;

import com.kingbbode.bot.common.response.EventResponse;
import com.kingbbode.bot.common.response.MessageResponse;
import com.kingbbode.bot.common.result.BrainResult;
import com.kingbbode.bot.teamup.templates.template.EdgeTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * Created by YG on 2016-03-28.
 */
@Service
public class MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    
    @Autowired
    private EdgeTemplate edgeTemplate;

    /*public String excuteMessageForChat(String content, String command) {
        return getMessageResult("999999999999", "8170", content, command);
    }*/

    public MessageResponse.Message readMessage(EventResponse.Event.Chat chat) {
        MessageResponse readResponse = edgeTemplate.readMessage(chat.getMsg(), chat.getRoom());
        if (!ObjectUtils.isEmpty(readResponse) && readResponse.getMsgs().size() > 0) {
            return  readResponse.getMsgs().get(0);
        }
        return null;
    }

    public void sendMessage(BrainResult result) {
        edgeTemplate.sendMessage(result.getMessage(), result.getRoom());
    }

    public void sendEmoticon(BrainResult result) {
        edgeTemplate.sendEmoticon(result.getMessage(), result.getRoom());
    }

    public void writeFeed(BrainResult result) {
        edgeTemplate.writeFeed(result.getMessage(), result.getRoom());
    }

}
