package net.abcbook.framework.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author summer
 * @date 2018/1/6 上午12:03
 */
@Component
@ConfigurationProperties(prefix = "summer.common.redis-cache")
@Getter
@Setter
public class RedisCacheProperties {

    /** 默认 redis 缓存的有效时长 */
    private Long expiration;
}

