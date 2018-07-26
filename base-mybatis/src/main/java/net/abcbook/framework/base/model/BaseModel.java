package net.abcbook.framework.base.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @author summer
 * @date 2017/12/7 上午10:41
 * 基础实体类
 */
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode
@Accessors(chain = true)
@Component
public class BaseModel implements Serializable {

    private static final long serialVersionUID = -8149178435557670884L;

    /** 删除标识 -- 已删除 */
    public static final Boolean DELETED_TRUE = true;
    /** 删除标识 -- 未删除 */
    public static final Boolean DELETED_FALSE = false;

    /** 页码(没有配置时的默认值) */
    public static final Integer PAGE_NUM_DEFAULT = 1;
    /** 每页显示的数据条数(没有配置时的默认值) */
    public static final Integer PAGE_SIZE_DEFAULT = 10;

    /** id 主键[Long] */
    @Id
    private Long id;

    /** 数据创建时间(添加时自动填充系统时间)[Date] */
    private Date createTime;

    /** 数据更新时间(添加时自动填充系统时间,更新时自动更新)[Date] */
    private Date updateTime;

    /** 伪删除标记字段(false(0): 未删除, true(1): 已删除)[Boolean] */
    private Boolean isDeleted = DELETED_FALSE;


    @Transient
    private Integer pageNum = PAGE_NUM_DEFAULT;

    @Transient
    private Integer pageSize = PAGE_SIZE_DEFAULT;
}
