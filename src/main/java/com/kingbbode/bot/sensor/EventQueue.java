package com.kingbbode.bot.sensor;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by YG on 2016-11-03.
 */
public class EventQueue<T> {
    private Queue<T> queue = new ConcurrentLinkedQueue<>();
    
    public boolean hasNext(){
        return queue.size()>0;
    }
    
    public void offer(T e) {
        if (e == null) {
            return;
        }
        queue.offer(e);
    }

    public T poll() {
        return queue.poll();
    }
}
