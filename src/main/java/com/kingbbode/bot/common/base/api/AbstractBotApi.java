package com.kingbbode.bot.common.base.api;

import com.kingbbode.bot.common.request.BrainRequest;
import com.kingbbode.bot.common.result.BrainResult;

/**
 * Created by YG on 2017-01-26.
 */
public abstract class AbstractBotApi<T extends ApiData> implements BotApi<T> {
    
    public BrainResult execute(BrainRequest brainRequest) {
        T api = getRequest(brainRequest.getCommand());

        return new BrainResult.Builder()
                .room(brainRequest.getRoom())
                .result(get(api))
                .type(api.response())
                .build();
    }
}
