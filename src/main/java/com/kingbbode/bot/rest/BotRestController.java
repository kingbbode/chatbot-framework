package com.kingbbode.bot.rest;
import com.kingbbode.bot.common.base.setting.BotInfoProperties;
import com.kingbbode.bot.common.result.BrainResult;
import com.kingbbode.bot.teamup.message.MessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by YG on 2016-09-19.
 */
@RestController
public class BotRestController {
    
    @Autowired
    private MessageService messageService;
    
    @Autowired
    private BotInfoProperties botInfoProperties;
    
    @RequestMapping(value = "/api/send", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity send(@RequestBody Map<String, String> data){
        String key = data.get("key");
        String type = data.get("type");
        String room = data.get("room");
        String message = data.get("message");
        if(StringUtils.isEmpty(key) || StringUtils.isEmpty(type) || StringUtils.isEmpty(room) || StringUtils.isEmpty(message) || !StringUtils.isNumeric(room)){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }else if(!botInfoProperties.getApiKey().equals(key)){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }else{
            if("message".equals(type)){
                messageService.sendMessage(
                        new BrainResult.Builder()
                            .message(message)
                            .room(room)
                            .build()
                );
            }else if("feed".equals(type)){
                messageService.writeFeed(
                        new BrainResult.Builder()
                            .message(message)
                            .room(room)
                            .build()
                );
            }else{
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
