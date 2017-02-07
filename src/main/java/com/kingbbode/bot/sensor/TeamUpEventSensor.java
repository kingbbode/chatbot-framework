package com.kingbbode.bot.sensor;

import com.kingbbode.bot.common.response.EventResponse;
import com.kingbbode.bot.teamup.templates.template.EventTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;

/**
 * Created by YG on 2016-03-28.
 */
@Component
@EnableScheduling
public class TeamUpEventSensor {

    private static final Logger logger = LoggerFactory.getLogger( TeamUpEventSensor.class );
        
    @Autowired
    private EventTemplate eventTemplate;
    
    @Autowired
    private EventQueue<EventResponse.Event> eventQueue;

    private boolean ready;
    
    public void setReady(boolean ready){
        this.ready = ready;
    }

    @Scheduled(fixedDelay = 10)
    public void sensingEvent(){
        if(ready) {
            EventResponse eventResponse = null;
            try {
                eventResponse = eventTemplate.getEvent();
            } catch (Exception e) {
                logger.error("TeamUpEventSensor - sensingEvent : {}", e);
            }
            if (!ObjectUtils.isEmpty(eventResponse)) {
                ArrayList<EventResponse.Event> events = eventResponse.getEvents();
                if (events != null && !events.isEmpty()) {
                    events.stream().forEach(event -> this.eventQueue.offer(event));
                }
            }
        }
    }
}
