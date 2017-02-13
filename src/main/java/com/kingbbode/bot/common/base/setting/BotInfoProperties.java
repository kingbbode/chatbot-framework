package com.kingbbode.bot.common.base.setting;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by YG on 2016-10-13.
 */
@Configuration
@ConfigurationProperties(locations = {"file:/data/etc/bot/bot.yml", "classpath:/properties/bot.yml"}, prefix = "bot")
public class BotInfoProperties {
    Api api = new Api();
    
    Event event = new Event();

    Oauth oauth = new Oauth();

    TeamUp teamup = new TeamUp();
    
    Test test = new Test();

    public Event getEvent() {
        return event;
    }

    public Oauth getOauth() {
        return oauth;
    }

    public TeamUp getTeamup() {
        return teamup;
    }

    public Api getApi() {
        return api;
    }

    public Test getTest() {
        return test;
    }

    public String getReadUrl() {
        return event.message.read;
    }

    public String getSendUrl() {
        return event.message.send;
    }

    public String getFeedWriteUrl() {
        return event.feed.write;
    }

    public String getEventUrl() {
        return event.rtm;
    }
    
    public String getTokenUrl() {
        return event.token;
    }

    public String getClientId() {
        return oauth.client.id;
    }

    public String getClientSecret() {
        return oauth.client.secret;
    }

    public String getName() {
        return teamup.id;
    }

    public String getPassword() {
        return teamup.pw;
    }
    
    public String getApiKey(){
        return api.key;
    }
    
    public String getTestRoom() {
        return test.room;
    }
    
    public String getTestFeed() {
        return test.feed;
    }

    private class Event {
        private Feed feed = new Feed();
        private Message message = new Message();
        private String rtm;
        private String token;

        public Feed getFeed() {
            return feed;
        }

        public Message getMessage() {
            return message;
        }

        public void setRtm(String rtm) {
            this.rtm = rtm;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    private class Feed {
        private String write;

        public void setWrite(String write) {
            this.write = write;
        }
    }

    private class Message {
        private String read;
        private String send;

        public void setRead(String read) {
            this.read = read;
        }

        public void setSend(String send) {
            this.send = send;
        }
    }

    private class Oauth {
        private Client client = new Client();

        public Client getClient() {
            return client;
        }
    }

    private class Client {
        private String id;
        private String secret;

        public void setId(String id) {
            this.id = id;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }

    private class TeamUp {
        private String id;
        private String pw;

        public void setId(String id) {
            this.id = id;
        }

        public void setPw(String pw) {
            this.pw = pw;
        }
    }

    private class Api {
        private String key;

        public void setKey(String key) {
            this.key = key;
        }
    }

    private class Test {
        private String room;
        private String feed;

        public void setRoom(String room) {
            this.room = room;
        }

        public void setFeed(String feed) {
            this.feed = feed;
        }
    }
}
