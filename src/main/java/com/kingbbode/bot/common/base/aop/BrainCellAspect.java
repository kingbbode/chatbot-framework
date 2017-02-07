package com.kingbbode.bot.common.base.aop;

import com.kingbbode.bot.common.annotations.BrainCell;
import com.kingbbode.bot.common.request.BrainRequest;
import com.kingbbode.bot.common.result.BrainCellResult;
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

    @Around("@annotation(com.zum.front.bot.lib.common.annotations.BrainCell)")
    public Object checkArgLength(ProceedingJoinPoint joinPoint) throws Throwable {
        BrainCell brainCell = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(BrainCell.class);
        BrainRequest request = (BrainRequest) joinPoint.getArgs()[0];
        if(request.getContent().split(" ").length < brainCell.min()){
            return brainCell.example();
        }
        Object object =  joinPoint.proceed();

        if(!(object instanceof String) && !(object instanceof BrainCellResult) ){
            object = "서버 에러";
            logger.error("BrainCell must be return String or BrainCellResult");
        }
        
        return object;
    }
}
