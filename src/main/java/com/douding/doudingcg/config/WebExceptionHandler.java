package com.douding.doudingcg.config;

import com.douding.doudingcg.base.controller.BaseResponseVO;
import com.douding.doudingcg.constant.enums.ResponseCodeEnum;
import com.douding.doudingcg.exception.PramsException;
import com.douding.doudingcg.exception.ServiceException;
import com.douding.doudingcg.validator.ValidatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@Order(Integer.MAX_VALUE)
public class WebExceptionHandler {

    //TODO:需要重新定义CodeEnums的值

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    public BaseResponseVO<String> illegalArgumentException(IllegalArgumentException e){
        log.error("illegalArgumentException exception:{}", e.getMessage());
        return BaseResponseVO.fail(ResponseCodeEnum.ITEM_NOT_FOUND);
    }

    @ExceptionHandler(PramsException.class)
    @ResponseBody
    public BaseResponseVO<String> exception(PramsException e) {
        log.error("参数校验异常 exception:{}", e.getMessage());
        return new BaseResponseVO<String>(ResponseCodeEnum.ERR_MISSING_REQUEST_PARAM);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public BaseResponseVO<Object> exception(HttpMessageNotReadableException e) {
        log.error("请求参数无法识别异常 HttpMessageNotReadableException:{}", e.getMessage());
        return BaseResponseVO.fail(ResponseCodeEnum.SYS_REQ_MESSAGE_NOT_READABLE);
    }

    @ExceptionHandler({BadSqlGrammarException.class})
    @ResponseBody
    public BaseResponseVO<Object> exception(BadSqlGrammarException e) {
        log.error("数据sql传入参数问题 BadSqlGrammarException:", e);
        return BaseResponseVO.fail(ResponseCodeEnum.SYS_DATA_SQL_NOT_READABLE);
    }

    @ExceptionHandler({MyBatisSystemException.class})
    @ResponseBody
    public BaseResponseVO<Object> exception(MyBatisSystemException e) {
        log.error("MyBatis系统问题或Mapper层数据sql传入参数问题 MyBatisSystemException:", e);
        return BaseResponseVO.fail(ResponseCodeEnum.SYS_DATA_SQL_NOT_READABLE, "MyBatis系统问题或Mapper层数据sql传入参数问题");
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public BaseResponseVO<Object> exception(MissingServletRequestParameterException e) {
        log.error("请求参数丢失异常 MissingServletRequestParameterException:{}", e.getMessage());
        return BaseResponseVO.fail(ResponseCodeEnum.SYS_MISSING_REQ_PARAMS, new Object[]{e.getParameterType(), "'" + e.getParameterName() + "'"});
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseBody
    public BaseResponseVO<Object> exception(DataIntegrityViolationException e) {
        if (((String)Objects.requireNonNull(e.getMessage())).contains("Data truncation")) {
            log.error(" 数据过长异常 exception:{}", e.getMessage());
            return new BaseResponseVO<Object>(ResponseCodeEnum.SYSTEM_ERROR, "数据参数超出范围，请确认");
        } else if (((String)Objects.requireNonNull(e.getMessage())).contains("Duplicate entry")) {
            log.error(" 数据重复异常 exception:{}", e.getMessage());
            return new BaseResponseVO<Object>(ResponseCodeEnum.SYSTEM_ERROR, "数据已存在，请确认");
        } else {
            log.error("数据库非空异常 exception:{}", e.getMessage());
            return new BaseResponseVO<Object>(ResponseCodeEnum.ITEM_NOT_FOUND, "请确认你的参数非空");
        }
    }

    @ExceptionHandler({ServiceException.class})
    @ResponseBody
    public BaseResponseVO<Object> exception(ServiceException e) {
        log.error("common-ServiceException exception:{}", e.getMessage());
        return new BaseResponseVO<Object>(e.getResponseCodeEnum());
    }


    /**
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常，详情继续往下看代码
     * @param e
     * @return
     */
    @ExceptionHandler({BindException.class})
    @ResponseBody
    public BaseResponseVO<Object> bindExceptionHandler(BindException e) {
        String message = e.getBindingResult().getAllErrors().stream().map((DefaultMessageSourceResolvable resolvable) ->
                resolvable instanceof FieldError ?
                        ((FieldError) resolvable).getField() + ValidatorUtils.message("参数格式错误") : resolvable.getDefaultMessage()).limit(1).collect(Collectors.joining());
        return new BaseResponseVO<Object>(ResponseCodeEnum.ERR_REQUEST_PARAM_FORMAT,message);
    }

    /**
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @Validated
    @ResponseBody
    public BaseResponseVO<Object> constraintViolationExceptionHandler(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));
        return new BaseResponseVO<Object>(ResponseCodeEnum.ERR_REQUEST_PARAM_FORMAT,message);
    }

    /**
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public BaseResponseVO<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));
        return new BaseResponseVO<Object>(ResponseCodeEnum.ERR_REQUEST_PARAM_FORMAT,message);
    }

    /**
     * 处理请求参数格式错误 @RequestParam上参数类型转换失败后抛出的异常是MethodArgumentTypeMismatchException异常。
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public BaseResponseVO<Object> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        return new BaseResponseVO<Object>(ResponseCodeEnum.ERR_REQUEST_PARAM_FORMAT,e.getName() + ValidatorUtils.message("数据类型错误"));
    }

    /**
     * 处理请求没有发现对应的@RequestMapping抛出的异常是NoHandlerFoundException异常。
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public BaseResponseVO<Object> noHandlerFoundException(NoHandlerFoundException e) {
        return new BaseResponseVO<Object>(ResponseCodeEnum.ERR_REQUEST_NOT_FOUND,"无法找到该接口");
    }

    /**
     * 处理请求方法不支持的问题 如： Request method 'GET' not supported。
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public BaseResponseVO<Object> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
    	return new BaseResponseVO<Object>(ResponseCodeEnum.ERR_METHOD_NOT_SUPPORTED,e.getMessage());
    }


    /**
     * Service层统一返回的一个异常用于接口响应。
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResponseVO<Object> exception(Exception e) {
        log.error("handle exception", e);
        return new BaseResponseVO<Object>(ResponseCodeEnum.SYSTEM_ERROR,"系统异常");
    }

}
