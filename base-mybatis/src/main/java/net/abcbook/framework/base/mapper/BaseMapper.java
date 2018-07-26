package net.abcbook.framework.base.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.base.BaseSelectMapper;

/**
 * @author summer
 * @date 2017/12/29 上午9:44
 * 公共的 Dao 接口
 */
public interface BaseMapper<T>  extends
        Mapper<T>,
        MySqlMapper<T>,
        BaseSelectMapper<T> {

}
