package com.kingbbode.bot.sensor;

import com.kingbbode.bot.common.response.EventResponse;
import com.kingbbode.bot.dispatcher.DispatcherBrain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * Created by YG on 2016-08-17.
 */
@Service
@EnableScheduling
public class TaskRunner {
    
    @Autowired
    private ThreadPoolTaskExecutor executer;

    @Autowired
    private DispatcherBrain botDispatcher;

    @Autowired
    private EventQueue<EventResponse.Event> eventQueue;

    @Scheduled(fixedDelay = 10)
    private void execute(){
        while(eventQueue.hasNext()){
            executer.execute(new FetcherTask(botDispatcher, eventQueue.poll()));
        }
    }
    
    public static class FetcherTask implements Runnable {
        DispatcherBrain botDispatcher;
        EventResponse.Event event;
        public FetcherTask(DispatcherBrain botDispatcher, EventResponse.Event event) {
            this.botDispatcher = botDispatcher;
            this.event = event;
        }

        @Override
        public void run() {
            botDispatcher.dispatch(event);
        }
    }
}
