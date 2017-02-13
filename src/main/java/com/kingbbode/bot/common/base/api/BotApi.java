package com.kingbbode.bot.common.base.api;

import com.kingbbode.bot.common.request.BrainRequest;
import com.kingbbode.bot.common.result.BrainCellResult;
import com.kingbbode.bot.common.result.BrainResult;

import java.util.Collection;

/**
 * Created by YG on 2017-01-26.
 */
public interface BotApi<T> {
    void update();

    BrainCellResult get(T request);

    T getRequest(String command);

    Collection<String> getCommands();
}
