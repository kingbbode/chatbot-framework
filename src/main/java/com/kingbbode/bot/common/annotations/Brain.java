package com.kingbbode.bot.common.annotations;

import com.kingbbode.bot.common.enums.BrainRequestType;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface Brain {
    BrainRequestType request() default BrainRequestType.COMMON;
}
