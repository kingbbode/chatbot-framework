package com.kingbbode.bot.common.base.aop;

import com.kingbbode.bot.common.annotations.BrainCell;
import com.kingbbode.bot.common.base.exception.ArgumentCountException;
import com.kingbbode.bot.common.base.exception.InvalidReturnTypeException;
import com.kingbbode.bot.common.request.BrainRequest;
import com.kingbbode.bot.common.result.BrainCellResult;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by YG-MAC on 2017. 1. 26..
 */
@Component
@Aspect
public class BrainCellAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(com.kingbbode.bot.common.annotations.BrainCell)")
    public Object checkArgLength(ProceedingJoinPoint joinPoint) throws Throwable {
        BrainCell brainCell = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(BrainCell.class);
        BrainRequest request = (BrainRequest) joinPoint.getArgs()[0];
        String[] test = request.getContent().split(" ");
        if(brainCell.min() > 0 && ((test.length == 1 && StringUtils.isEmpty(test[0])) || test.length < brainCell.min())){
            throw new ArgumentCountException(new Throwable());
        }
        Object object =  joinPoint.proceed();

        if(!(object instanceof String) && !(object instanceof BrainCellResult) ){
            throw new InvalidReturnTypeException(new Throwable());
        }
        
        return object;
    }
}
