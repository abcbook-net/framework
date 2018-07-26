package net.abcbook.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author summer
 * @date 2017/8/14 上午8:34
 * 统一返回值,返回码的枚举类
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResultEnum {

    /**
     * 成功
     */
    /**  */
    SUCCESS(200,"成功"),

    /**
     * 3XX 错误, 权限错误
     */
    USER_LOGIN_ERROR(300, "登录错误"),
    /** 用户名或密码不正确 */
    USERNAME_OR_PASSWORD_NOT_MATCHING(301,"用户名或密码不正确"),

    /** 用户名不存 */
    USER_UNKNOWN_ACCOUNT(310, "用户名不存"),
    /** 密码不正确 */
    USER_INCORRECT_CREDENTIALS(311, "密码不正确"),
    /** 验证码错误 */
    USER_KAPTCHA_VALIDATE_FAILED(312, "验证码错误"),

    /** 没有权限 */
    USER_PERMISSION_DENIED(350,"没有权限"),
    /** cookie已过期或被禁用 */
    USER_COOKIE_EXPIRED(351,"cookie已过期或被禁用"),
    /** 登录已过期请重新登录 */
    USER_REDIS_EXPIRED(352,"登录已过期"),

    /**
     * 4XX 错误, 路径错误
     */
    /** 请求地址或页面不存在 */
    PATH_PAGE_NOT_EXIST(404,"请求地址或页面不存在"),

    /**
     * 5XX 错误, 参数错误
     */
    /** 系统错误 */
    ERROR(500,"系统错误"),

    /** 参数不正确 */
    PARAM_ERROR(501,"参数错误"),
    /** 必要参数为空 */
    PARAM_IS_EMPTY(502,"必要参数为空"),

    /** 关联数据为空 */
    PARAM_FOREIGN_DATA_EMPTY(503,"关联数据为空"),
    /** 关联资源不正确 */
    PARAM_FOREIGN_DATA_ERROR(504,"关联数据错误"),

    /** 相关数据已存在 */
    PARAM_DATA_EXIST(506,"数据已存在"),
    /** 数据被关联或被使用 */
    PARAM_DATA_USED(507,"数据被关联或被使用"),

    /** 数据校验未通过 */
    VALIDATE_ERROR(550, "数据校验未通过"),
    /** 数据校验未通过集合 */
    VALIDATE_ERROR_LIST(560, "数据校验未通过集合")
    ;

    private Integer code;
    private String message;
}
