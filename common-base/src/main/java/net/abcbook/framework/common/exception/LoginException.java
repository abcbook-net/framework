package net.abcbook.framework.common.exception;

import lombok.Getter;
import net.abcbook.framework.common.enums.ResultEnum;

/**
 * @author summer
 * @date 2017/12/6 下午4:40
 * 登录状态的异常
 */
@Getter
public class LoginException extends RuntimeException {
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 通过返回值枚举, 封装的异常构造方法
     * @param resultEnum
     */
    public LoginException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    /**
     * 通过错误码和提示信息封装的异常构造方法
     * @param code
     * @param message
     */
    public LoginException(Integer code, String message){
        super(message);
        this.code = code;
    }
}
