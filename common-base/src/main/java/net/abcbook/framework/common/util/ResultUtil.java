package net.abcbook.framework.common.util;


import net.abcbook.framework.common.enums.ResultEnum;
import net.abcbook.framework.common.result.Result;
import net.abcbook.framework.common.result.ValidatedError;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * @author summer
 * @date 2017/8/14 上午8:29
 * 统一返回值处理的封装工具类
 */
@SuppressWarnings("unchecked")
public class ResultUtil {

    /**
     * 返回成功的方法(有需要返回的数据)
     * @param data 返回数据
     * @return com.tinypace.framework.common.result.Result
     */
    public static Result success(Object data){
        return getResult(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(),data);
    }

    /**
     * 返回成功的方法,不需要返回数据
     * @return com.tinypace.framework.common.result.Result
     */
    public static Result success(){
        return success(null);
    }

//    public static Result error(){
//        return error(ResultEnum.ERROR);
//    }
    /**
     * 返回错误的方法,不需要返回值
     * @param code 返回编码
     * @param message 返回信息
     * @return
     */
    public static Result error(Integer code, String message){
        return getResult(code, message, null);
    }

    /**
     * @author summer
     * @date 2018/1/2 上午10:19
     * @param resultEnum 返回数据枚举
     * @param data 返回数据
     * @return com.tinypace.framework.common.result.Result
     * @description 返回错误信息
     */
    public static Result error(ResultEnum resultEnum, Object data){
        return getResult(resultEnum.getCode(), resultEnum.getMessage(), data);
    }

    /**
     * 错误返回值的封装方法
     * @param resultEnum 返回数据枚举
     * @return com.tinypace.framework.common.result.Result
     */
    public static Result error(ResultEnum resultEnum){
        return error(resultEnum.getCode(),resultEnum.getMessage());
    }
    /**
     * @author summer
     * @date 2018/1/2 上午10:28
     * @param fieldError 验证错误信息
     * @return com.tinypace.framework.common.result.Result
     * @description 根据验证的错误信息, 封装返回值
     */
    public static Result validateError(FieldError fieldError){
        ValidatedError validatedResult = new ValidatedError(fieldError);
        return ResultUtil.error(ResultEnum.VALIDATE_ERROR, validatedResult);
    }

    /**
     * @author summer
     * @date 2018/1/2 上午10:41
     * @param fieldErrorList 验证错误信息集合
     * @return com.tinypace.framework.common.result.Result
     * @description 根据验证的错误信息集合, 封装返回值
     */
    public static Result validateError(List<FieldError> fieldErrorList){
        List<ValidatedError> validatedResultList = new ArrayList<>();
        for(FieldError fieldError : fieldErrorList){
            ValidatedError validatedResult = new ValidatedError(fieldError);
            validatedResultList.add(validatedResult);
        }
        return ResultUtil.error(ResultEnum.VALIDATE_ERROR_LIST, validatedResultList);
    }

    /**
     * 用于获取统一返回值对象的封装方法
     * @param code 返回编码
     * @param message 返回信息
     * @param data 返回数据
     * @return com.tinypace.framework.common.result.Result
     */
    private static Result getResult(Integer code, String message, Object data){
        return new Result(code, message, data);
    }
}
