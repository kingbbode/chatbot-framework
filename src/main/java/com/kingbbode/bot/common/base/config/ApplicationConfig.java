package com.kingbbode.bot.common.base.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.kingbbode.bot.common.base.setting.BotProperties;
import com.kingbbode.bot.common.response.EventResponse;
import com.kingbbode.bot.sensor.EventQueue;
import com.kingbbode.bot.util.RestTemplateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestOperations;

/**
 * Created by YG 2016-03-28
 */

@Configuration
public class ApplicationConfig {

    @Autowired
    Environment environment;
    
    @Bean(name = "messageRestOperations")
    @Primary
    public RestOperations messageRestOperations() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(1000);
        factory.setReadTimeout(1000);
        return RestTemplateFactory.getRestOperations(factory);
    }

    @Bean(name = "eventRestOperations")
    public RestOperations eventRestOperations() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(30000);
        factory.setReadTimeout(30000);
        return RestTemplateFactory.getRestOperations(factory);
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new JodaModule());
        return objectMapper;
    }

    @Bean
    @Primary
    public ThreadPoolTaskExecutor threadPoolTaskExecutorDefault() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(1000);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
    
    @Bean
    EventQueue<EventResponse.Event> eventQueue(){
        return new EventQueue<>();
    }
    
    @Bean
    @Profile({"bot"})
    public BotProperties realBotConfig(){
        return new BotProperties("#", false);
    }
    
    @Bean
    @Profile({"dev"})
    public BotProperties devBotConfig(){
        return new BotProperties("#@", true);
    }
    
    
}
