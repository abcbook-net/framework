package net.abcbook.framework.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.abcbook.framework.common.enums.ResultEnum;

/**
 * @author summer
 * @date 2017/12/7 上午8:59
 * 公共的异常类
 */
@NoArgsConstructor
@Getter
public class CommonException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 通过返回值枚举, 封装的异常构造方法
     * @param resultEnum
     */
    public CommonException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    /**
     * 通过错误码和提示信息封装的异常构造方法
     * @param code
     * @param message
     */
    public CommonException(Integer code,String message){
        super(message);
        this.code = code;
    }
}
