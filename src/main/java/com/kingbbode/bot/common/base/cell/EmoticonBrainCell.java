package com.kingbbode.bot.common.base.cell;
import com.kingbbode.bot.common.enums.BrainResponseType;
import com.kingbbode.bot.common.request.BrainRequest;
import com.kingbbode.bot.common.result.BrainResult;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by YG on 2017-01-26.
 */
public class EmoticonBrainCell extends AbstractBrainCell{
    
    private BrainResult.Builder builder;

    public EmoticonBrainCell(String emoticon) {
        this.builder = new BrainResult.Builder()
                .message(emoticon);
    }

    @Override
    public BrainResult execute(BrainRequest brainRequest) throws InvocationTargetException, IllegalAccessException {
        return builder
                .room(brainRequest.getRoom())
                .type(BrainResponseType.EMOTICON)
                .build();
    }
}
