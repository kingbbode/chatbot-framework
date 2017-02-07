package com.kingbbode.bot.common.base.cell;

import com.kingbbode.bot.common.request.BrainRequest;
import com.kingbbode.bot.common.result.BrainResult;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by YG on 2017-01-26.
 */
public abstract class AbstractBrainCell implements BrainCell{
    @Override
    public String explain() {
        return "";
    }

    public static AbstractBrainCell NOT_FOUND_BRAIN_CELL = new AbstractBrainCell() {
        @Override
        public BrainResult execute(BrainRequest brainRequest) throws InvocationTargetException, IllegalAccessException {
            return null;
        }

        @Override
        public String explain() {
            return "존재하지 않는 기능입니다.";
        }
    };
}
