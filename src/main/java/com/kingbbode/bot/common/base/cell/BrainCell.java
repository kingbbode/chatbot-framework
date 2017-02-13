package com.kingbbode.bot.common.base.cell;

import com.kingbbode.bot.common.result.BrainResult;
import com.kingbbode.bot.common.request.BrainRequest;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by YG on 2017-01-26.
 */
public interface BrainCell {
    String explain();
    BrainResult execute(BrainRequest brainRequest) throws InvocationTargetException, IllegalAccessException;
}
