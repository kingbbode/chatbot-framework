package com.kingbbode.bot.common.request;

import com.kingbbode.bot.common.response.EventResponse;
import com.kingbbode.bot.common.response.MessageResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by YG on 2017-01-23.
 */
public class BrainRequest {
    private String user;
    private String room;
    private String content;
    private String command;
    
    public BrainRequest(EventResponse.Event.Chat chat, MessageResponse.Message message) {
        this.user = String.valueOf(message.getUser());
        this.room = chat.getRoom();
        this.command = message.getContent().split(" ")[0];
        this.content = message.getContent().substring(command.length(), message.getContent().length()).trim();
    }

    public boolean isValid(){
        return !StringUtils.isEmpty(command);
    }

    public String getCommand() {
        return command;
    }

    public String getRoom() {
        return room;
    }

    public String getContent() {
        return content;
    }

    public String getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "BrainRequest{" +
                "user='" + user + '\'' +
                ", room='" + room + '\'' +
                '}';
    }
}
