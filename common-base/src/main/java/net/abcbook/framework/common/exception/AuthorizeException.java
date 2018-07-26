package net.abcbook.framework.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.abcbook.framework.common.enums.ResultEnum;

/**
 * @author summer
 * @date 2017/12/6 下午4:40
 * 授权异常, 没有权限或授权过程中抛出的异常
 */
@NoArgsConstructor
@Getter
public class AuthorizeException extends RuntimeException {
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 通过返回值枚举, 封装的异常构造方法
     * @param resultEnum 异常的枚举构造
     */
    public AuthorizeException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    /**
     * 通过错误码和提示信息封装的异常构造方法
     * @param code
     * @param message
     */
    public AuthorizeException(Integer code,String message){
        super(message);
        this.code = code;
    }
}
