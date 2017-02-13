package com.kingbbode.bot.dispatcher;

import com.kingbbode.bot.common.result.BrainResult;
import com.kingbbode.bot.common.base.factory.BrainFactory;
import com.kingbbode.bot.common.request.BrainRequest;
import com.kingbbode.bot.common.response.EventResponse;
import com.kingbbode.bot.common.response.MessageResponse;
import com.kingbbode.bot.teamup.message.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by YG on 2017-01-23.
 */
@Component
public class DispatcherBrain {
    private static final Logger logger = LoggerFactory.getLogger(BrainFactory.class);

    private static final String EVENT_MESSAGE = "chat.message";
    private static final String EVENT_JOIN = "chat.join";
    private static final String ULTRON_USER_ID = "10849";
    private static final int MESSAGE_TYPE = 1;
    private static final int FILE_TYPE = 2;
    private static final String FILE_PATTERN_REGEX = "^(yg_)(.*)\\.(bmp|gif|jpg|jpeg|png)$";

    @Autowired
    private BrainFactory brainFactory;

    @Autowired
    private MessageService messageService;

    public void dispatch(EventResponse.Event event) {
        if (EVENT_MESSAGE.equals(event.getType())) {
            if (!event.getChat().getUser().equals(ULTRON_USER_ID)) {
                classification(event);
            }
        } else if (EVENT_JOIN.equals(event.getType())) {
            send(BrainResult.Builder.GREETING.room( event.getChat().getRoom()).build());
        }
    }

    private void classification(EventResponse.Event event) {
        MessageResponse.Message message = messageService.readMessage(event.getChat());
        switch (message.getType()) {
            case MESSAGE_TYPE:
                BrainRequest brainRequest = new BrainRequest(event.getChat(), message);
                if (!brainRequest.isValid()) {
                    break;
                }
                execute(brainRequest);
                break;
            case FILE_TYPE:
                MessageResponse.File file = message.getFile();
                String fileName = file.getName();
                if (!fileName.matches(FILE_PATTERN_REGEX)) {
                    break;
                }
                brainFactory.putEmoticon("@" + fileName.substring(3, fileName.length() - 4), file.getId());
                break;
        }
    }

    private void execute(BrainRequest brainRequest) {
        try {
            send(brainFactory.get(brainRequest.getCommand()).execute(brainRequest));
        } catch (Exception e) {
            logger.warn("execute error -{}", e);
        }
    }

    private void send(BrainResult result) {
        if(result!=null) {
            switch (result.type()) {
                case MESSAGE:
                    messageService.sendMessage(result);
                    break;
                case FEED:
                    messageService.writeFeed(result);
                    break;
                case EMOTICON:
                    messageService.sendEmoticon(result);
                    break;
            }
        }
    }
}
