package net.abcbook.framework.common.container;

import lombok.*;

import java.io.Serializable;
import java.util.Map;

/**
 * @author summer
 * @date 2018/2/7 下午2:20
 * 简单邮件信息的封装对象,用于项目间封装后一次性传值
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class TempleEmailContainer implements Serializable {

    /** 收件人 */
    private String receiver;
    /** 邮件模板的识别码 */
    private String emailTemplateCode;
    /** 动态信息 */
    private Map<String, Object> dynamicContent;

}
