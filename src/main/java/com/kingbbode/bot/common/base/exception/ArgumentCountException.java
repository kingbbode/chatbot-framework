package com.kingbbode.bot.common.base.exception;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * Created by YG on 2017-02-08.
 */
public class ArgumentCountException extends UndeclaredThrowableException {

    public ArgumentCountException(Throwable undeclaredThrowable) {
        super(undeclaredThrowable);
    }

    public ArgumentCountException(Throwable undeclaredThrowable, String s) {
        super(undeclaredThrowable, s);
    }
}
