package com.ding.common.utils;

import com.ding.common.BusinessException;
import com.ding.common.enums.StatusEnum;
import org.slf4j.Logger;

/**
 * @author dingzhencong
 * @date 2019年11月14日
 */
public class ExceptionUtils<T> {

    private ExceptionUtils() {}

    /**
     * 如果ex为FssBizException则抛出内嵌的异常
     *
     * @param statusEnum
     * @param ex
     * @return
     */
    public static BusinessException throwException(StatusEnum statusEnum, Logger log, Throwable ex) {
        log.error(statusEnum.getMessage(), ex);
        if (ex instanceof BusinessException) {
            return (BusinessException) ex;
        }
        return new BusinessException(statusEnum.getType(), statusEnum.getMessage(), ex);
    }
    /**
     * 抛出业务异常
     *
     * @param statusEnum
     * @return
     */
    public static BusinessException throwException (StatusEnum statusEnum, Logger log) {
        log.error(statusEnum.getMessage());
        return new BusinessException(statusEnum.getType(), statusEnum.getMessage());
    }

    /**
     * 抛出业务异常
     *
     * @param message
     * @return
     */
    public static BusinessException throwException (String message, Logger log) {
        log.error(message);
        return new BusinessException(message);
    }

    /**
     * 抛出业务异常
     *
     * @param message
     * @return
     */
    public static<T> BusinessException throwException(String message, T t, Logger log) {
        log.error(message, t);
        return new BusinessException(message);
    }
}
