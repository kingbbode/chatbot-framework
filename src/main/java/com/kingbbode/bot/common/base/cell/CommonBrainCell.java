package com.kingbbode.bot.common.base.cell;

import com.kingbbode.bot.common.request.BrainRequest;
import com.kingbbode.bot.common.result.BrainCellResult;
import com.kingbbode.bot.common.result.BrainResult;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by YG on 2017-01-23.
 */
public class CommonBrainCell extends AbstractBrainCell{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private String name;
    private Method active;
    private Object object;
    private BeanFactory beanFactory;
    private com.kingbbode.bot.common.annotations.BrainCell brain;

    public CommonBrainCell(com.kingbbode.bot.common.annotations.BrainCell brain, Class<?> clazz, Method active, BeanFactory beanFactory) {
        this.name = WordUtils.uncapitalize(clazz.getSimpleName());
        this.brain = brain;
        this.active = active;
        this.beanFactory = beanFactory;
    }

    @Override
    public String explain() {
        return brain.explain();
    }

    @Override
    public BrainResult execute(BrainRequest brainRequest) throws InvocationTargetException, IllegalAccessException {
        if (!inject()) {
            return BrainResult.Builder.FAILED.room(brainRequest.getRoom()).build();
        }
        Object result = active.invoke(object, brainRequest);
        
        if(result instanceof BrainCellResult){
            return new BrainResult.Builder()
                    .result((BrainCellResult) result)
                    .type(brain.type())
                    .build();
        }        
        
        return new BrainResult.Builder()
                .message((String) result)
                .room(brainRequest.getRoom())
                .type(brain.type())
                .build();
    }

    private boolean inject() {
        if (object != null) {
            return true;
        }
        if (beanFactory.containsBean(name)) {
            object = beanFactory.getBean(name);
        }

        return object != null;
    }
}
