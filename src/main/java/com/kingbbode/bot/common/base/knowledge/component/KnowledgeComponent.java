package com.kingbbode.bot.common.base.knowledge.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingbbode.bot.common.base.setting.BotProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by YG on 2016-04-12.
 */
@Component
public class KnowledgeComponent {

    @Resource(name="redisTemplate")
    HashOperations<String, String, String> hashOperations;

    @Autowired
    ObjectMapper objectMapper;
    
    @Autowired
    BotProperties botProperties;

    private static final String REDIS_KEY_KNOWLEDGE = "/ultron/knowledge";

    private Map<String, List<String>> knowledge;
    
    @PostConstruct
    public void init() throws IOException {
        Map<String, List<String>> map = new ConcurrentHashMap<>();
        Map<String, String> entries = hashOperations.entries(REDIS_KEY_KNOWLEDGE);
        for(Map.Entry<String, String> entry : entries.entrySet()){
            map.put(entry.getKey(), objectMapper.readValue(entry.getValue(), new TypeReference<List<String>>() {
            }));
        }
        knowledge = map;
    }

    public void clear() {
        knowledge.clear();
    }

    public boolean contains(String command){
        return knowledge.containsKey(command);
    }

    public List<String> get(String command) {
        if(!contains(command)){
            return null;
        }
        return knowledge.get(command);
    }

    public String addKnowledge(String command, String content) throws JsonProcessingException {
        if (content.startsWith(botProperties.getPrefix())) {
            return "#로 시작하는 내용은 암기할 수 없습니다";
        }

        if (knowledge.containsKey(command)) {
            List<String> list = knowledge.get(command);
            if (list.size() > 9) {
                return command + " 명령어에 습득할 수 있는 머리가 꽉 차서 못하겠습니다";
            }
            list.add(content);
            hashOperations.put(REDIS_KEY_KNOWLEDGE, command, objectMapper.writeValueAsString(list));
            return command + " 명령어에 지식을 하나 더 습득했습니다";
        } else {
            if (knowledge.size() > 99) {
                return "제 머리로는 더 이상 지식을 습득할 수 없습니다";
            }
            List<String> list = new ArrayList<>();
            list.add(content);
            knowledge.put(command, list);
            hashOperations.put(REDIS_KEY_KNOWLEDGE, command, objectMapper.writeValueAsString(list));
            return "새로운 지식을 습득했습니다. \n 사용법 : " + command;
        }
    }

    public String forgetKnowledge(String command) {
        if (knowledge.containsKey(command)) {
            knowledge.remove(command);
            hashOperations.delete(REDIS_KEY_KNOWLEDGE, command);
            return command + "를 까먹었습니다";
        } else {
            return "그런건 원래 모릅니다";
        }
    }
    
    public Set<Map.Entry<String, List<String>>> getCommands(){
        return knowledge.entrySet();
    }
}
