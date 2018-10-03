package net.abcbook.framework.common.handler;

import lombok.extern.slf4j.Slf4j;
import net.abcbook.framework.common.enums.ResultEnum;
import net.abcbook.framework.common.exception.AuthorizeException;
import net.abcbook.framework.common.exception.CommonException;
import net.abcbook.framework.common.exception.LoginException;
import net.abcbook.framework.common.result.Result;
import net.abcbook.framework.common.util.ResultUtil;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.IOException;
import java.util.Set;

/**
 * @author summer
 * @date 2017/8/14 上午10:47
 * 公共异常类的过滤处理器
 */
@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    /**
     * 异常统一拦截的过滤器
     * @param e
     * @return
     */
    /*
     * 异常监听把手,value用于定义异常的监听类型
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionHandle(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        log.error("[异常拦截] message: {}", e);
        /**
         * 处理基础异常
         */
        if (e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            return ResultUtil.error(commonException.getCode(), commonException.getMessage());
        /**
         * 处理登录异常
         */
        }else if (e instanceof LoginException) {
            LoginException loginException = (LoginException) e;
            return ResultUtil.error(loginException.getCode(), loginException.getMessage());
        /**
         * 处理 shiro 的权限异常连接
         */
        }else if (e instanceof UnauthorizedException) {
            UnauthorizedException unauthorizedException = (UnauthorizedException) e;
            return ResultUtil.error(ResultEnum.USER_PERMISSION_DENIED.getCode(), unauthorizedException.getMessage());
        /**
         * 处理权限异常
         */
        }else if (e instanceof AuthorizeException) {
            AuthorizeException authorizeException = (AuthorizeException) e;
            return ResultUtil.error(authorizeException.getCode(), authorizeException.getMessage());
        /**
         * 处理数据校验异常
         */
        } else if (e instanceof BindException) {
            BindingResult bindingResult = ((BindException) e).getBindingResult();
            return ResultUtil.validateError(bindingResult.getFieldError());

        /**
         * 数据校验异常
         */
        } else if (e instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            return ResultUtil.validateError(bindingResult.getFieldError());
        /**
         * Hibernate 数据校验异常
         */
        } else if (e instanceof ConstraintViolationException){
            Set<ConstraintViolation<?>> constraintViolationSet = ((ConstraintViolationException) e).getConstraintViolations();
            for (ConstraintViolation constraintViolation : constraintViolationSet){
                return ResultUtil.error(ResultEnum.PARAM_ERROR.getCode(), constraintViolation.getMessage());
            }
            return ResultUtil.error(ResultEnum.PARAM_ERROR.getCode(), e.getMessage());
        /**
         * 处理路径未找到的问题
         */
        } else if (e instanceof NoHandlerFoundException) {
            return ResultUtil.error(ResultEnum.PATH_PAGE_NOT_EXIST);
        /**
         * 处理其他未知异常
         */
        }else {
            return ResultUtil.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage());
        }
    }
}
