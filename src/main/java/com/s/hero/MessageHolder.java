package com.s.hero;

import com.s.hero.model.RequestMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by alpa on 11/24/18
 */
@Slf4j
public class MessageHolder {


    private static final int DEFAULT_TIME_OUT = 20;
    private static ArrayBlockingQueue<RequestMessage> requestMessageArrayBlockingQueue = new ArrayBlockingQueue<>(1);
    private static MessageHolder INSTANCE;

    public static MessageHolder getInstance() {
        if (INSTANCE == null) {
            INSTANCE =  new MessageHolder();
            return INSTANCE;
        }
        return INSTANCE;
    }


    public static RequestMessage getRequestMessage() {
        return getRequestMessage(DEFAULT_TIME_OUT);
    }

    public static RequestMessage getRequestMessage(int seconds) {
        try {
            return requestMessageArrayBlockingQueue.poll(seconds, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setRequestMessage(RequestMessage requestMessage) {
        log.info("Set request message {}", requestMessage);
        try {
            this.requestMessageArrayBlockingQueue.put(requestMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
