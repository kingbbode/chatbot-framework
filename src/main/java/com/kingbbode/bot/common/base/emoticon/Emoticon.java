package com.kingbbode.bot.common.base.emoticon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingbbode.bot.common.base.setting.BotProperties;
import com.kingbbode.bot.common.request.BrainRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by YG on 2016-11-01.
 */
@Component
public class Emoticon {

    @Resource(name="redisTemplate")
    HashOperations<String, String, String> hashOperations;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BotProperties botProperties;

    private static final String REDIS_KEY_EMOTICON = "/ultron/emoticon";
    
    private Map<String, String> emoticons;

    @PostConstruct
    public void init() throws IOException {
        Map<String, String> map = new ConcurrentHashMap<>();
        Map<String, String> entries = hashOperations.entries(REDIS_KEY_EMOTICON);
        for(Map.Entry<String, String> entry : entries.entrySet()){
            map.put(entry.getKey(), entry.getValue());
        }
        emoticons = map;
    }
    
    public String execute(BrainRequest brainRequest){
        return emoticons.get(brainRequest.getCommand());
    }
    
    public Map<String, String> getEmoticons(){
        return emoticons;
    }
    
    public void put(String key, String value){
        hashOperations.put(REDIS_KEY_EMOTICON, key, value);
        emoticons.put(key, value);
    }
}
