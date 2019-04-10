package com.patrick.rxstudy07;

import io.reactivex.functions.Consumer;

public class ErrorHandler implements Consumer<Throwable> {
    private static final ErrorHandler INSTANCE = new ErrorHandler();

    private ErrorHandler() { }

    public static ErrorHandler get() {
        return INSTANCE;
    }

    @Override
    public void accept(Throwable throwable) {
        LogUtil.log(throwable);
    }
}
