package net.abcbook.framework.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

/**
 * @author summer
 * @date 2018/1/2 上午10:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidatedError {
    private String field;
    private String code;
    private String message;

    public ValidatedError(FieldError fieldError){
        this.field = fieldError.getField();
        this.code = fieldError.getCode();
        this.message = fieldError.getDefaultMessage();
    }
}
