package com.ding.common.config;

import com.ding.common.BusinessException;
import com.ding.common.JsonObject;
import com.ding.common.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 * @author dingzhencong
 * @date 2019年12月9日
 */
@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public JsonObject handleBindException(BindException ex) {
        //校验 除了 requestbody 注解方式的参数校验 对应的 bindingresult 为 BeanPropertyBindingResult
        FieldError fieldError = ex.getBindingResult().getFieldError();
        log.info("必填校验BindException异常:{}({})", fieldError.getDefaultMessage(),fieldError.getField());
        JsonObject jsonObject = JsonObject.builder().code(StatusEnum.FAIL.getType())
                .message(fieldError.getDefaultMessage()).build();
        return jsonObject;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public JsonObject handleBindException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        log.info("参数校验MethodArgumentNotValidException异常:{}({})", fieldError.getDefaultMessage(),fieldError.getField());
        JsonObject jsonObject = JsonObject.builder().code(StatusEnum.FAIL.getType())
                .message(fieldError.getDefaultMessage()).build();
        return jsonObject;
    }

    /**
     *  拦截Exception类的异常
     * @param ex
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public JsonObject exceptionHandler(BusinessException ex){
        log.info("异常信息::{}", ex.toString());
        JsonObject jsonObject = JsonObject.builder().code(ex.getCode())
                .message(ex.getMessage()).build();
        return jsonObject;
    }
}
