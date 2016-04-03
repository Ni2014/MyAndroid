package com.sdk.lib;

/**
 * Coolspan on 2016/1/26 11:57
 *
 * @author 乔晓松 coolspan@sina.cn
 */
public class FixBugException extends Exception {

    /**
     * 异常类名
     */
    public String message;

    /**
     * 异常的堆栈信息
     */
    public Throwable throwable;

    /**
     * 处理只有字符串的异常
     *
     * @param message
     */
    public FixBugException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * 处理只有Trowable的异常
     *
     * @param throwable
     */
    public FixBugException(Throwable throwable) {
        super(throwable);
        this.throwable = throwable;
    }

    /**
     * 处理有message和Trowable同时存在的异常
     *
     * @param message
     * @param throwable
     */
    public FixBugException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
        this.throwable = throwable;
    }
}
