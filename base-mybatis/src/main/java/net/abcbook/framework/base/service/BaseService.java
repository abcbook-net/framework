package net.abcbook.framework.base.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author summer
 * @date 2017/12/29 上午10:46
 * Service 的基类
 */
public interface BaseService<T> {

    /** 机器的 id 编号 */
    Integer WORKER_ID = 0;
    /** 数据中心的 id 编号 */
    Integer DATACENTER_ID = 0;

    /**
     * 在插入数据之前对数据的处理
     * @param entity 需要保存的数据
     * @return 原对象
     */
    void perInsert(T entity);


    /**
     * 用于保存数据的方法, 如果 id 为空则执行添加操作, 否则执行修改操作
     * @author summer
     * @date 2017/12/29 上午11:54
     * @param entity 需要保存的数据对象
     * @return T
     */
    T insertOrUpdateSelective(T entity);

    /**
     * 用于新建(插入)数据的方法, 实体中有对应的值则直接插入, 没有则忽略
     * @author summer
     * @date 2017/12/29 上午11:54
     * @param entity 需要保存的数据对象
     * @return java.lang.Integer
     */
    T insertAll(T entity);

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     * @author summer
     * @date 2018/1/17 上午9:57
     * @param entity 需要保存的实体
     * @return T
     */
    T insertSelective(T entity);

    /**
     * 根据对象的主键, 删除对应的数据对象
     * @author summer
     * @date 2018/1/17 上午10:30
     * @param id 将要删除的数据的 id
     * @return java.lang.Integer
     */
    Integer realDelete(Long id);

    /**
     * 根据数据的 id 删除(伪删除)数据的方法
     * @author summer
     * @date 2017/12/29 下午3:05
     * @param id 将要删除(伪删除) 的数据的 id
     * @return java.lang.Integer
     */
    Integer delete(Long id);

    /**
     * 根据数据的条件对象删除(伪删除)数据的方法--选择性拼接(如果有值则加入拼接,否则不加入)
     * @author summer
     * @date 2017/12/29 下午3:05
     * @param entity 将要删除(伪删除) 的数据的 条件对象
     * @return java.lang.Integer
     */
    Integer deleteSelective(T entity);

    /**
     * 根据实体条件删除对应的数据
     * @author summer
     * @date 2018/1/17 上午10:33
     * @param entity 筛选条件(使用等号过滤)
     * @return java.lang.Integer
     */
    Integer realDeleteSelective(T entity);

    /**
     * @author summer
     * @date 2017/12/31 下午2:41
     * @param entity 将要执行更新的实体条件对象
     * @return T
     * @description 根据传入的数据, 对数据进行全面的修改
     */
     T updateAll(T entity);

    /**
     * 根据传入的数据, 对数据进行选择性的修改--选择性拼接(如果有值则加入拼接,否则不加入)
     * @author summer
     * @date 2017/12/31 下午2:41
     * @param entity 将要执行更新的实体条件对象
     * @return java.lang.Integer
     */
    T updateSelective(T entity);

    /**
     * 根据 id 查询对应的对象
     * @author summer
     * @date 2017/12/29 下午3:09
     * @param id 数据的 id
     * @return T
     */
    T get(Long id);


    /**
     * 根据传入的实体对象条件, 查询出符合条件的数据
     * 注意: 使用此方法获取唯一对象有风险
     * @author summer
     * @date 2017/12/29 下午3:21
     * @param entity 用于封装查询条件的实体
     * @return T
     */
    T getByParameters(T entity);

    /**
     * 根据传入的实体对象条件, 查询出符合条件的数据的集合
     * @author summer
     * @date 2017/12/29 下午3:17
     * @param entity 用于封装查询条件的实体
     * @return java.util.List<T>
     */
    List<T> list(T entity);

    /**
     * 根据传入的实体条件 / 页码 / 每页显示的数据量
     * 查询出符合条件的分页对象
     * @author summer
     * @date 2017/12/29 下午3:43
     * @param entity 用于封装条件的实体类
     * @return com.github.pagehelper.Page<T>
     */
    Page<T> findPage(T entity);

    /**
     * 根据传入的实体条件 / 页码 / 每页显示的数据量
     * 查询出符合条件的分页对象
     * @author summer
     * @date 2017/12/29 下午3:43
     * @param entity 用于封装条件的实体类
     * @param orderBy 排序方式 例如: "update_time desc"
     * @return com.github.pagehelper.Page<T>
     */
    Page<T> findPage(T entity, String orderBy);

    /**
     * 根据传入的实体条件 / 页码 / 每页显示的数据量
     * 查询出符合条件的分页对象 (包括分页信息的详情)
     * @author summer
     * @date 2017/12/29 下午3:43
     * @param entity 用于封装条件的实体类
     * @return com.github.pagehelper.PageInfo<T>
     */
     PageInfo<T> findPageInfo(T entity);

    /**
     * 根据传入的实体条件 / 页码 / 每页显示的数据量
     * 查询出符合条件的分页对象 (包括分页信息详情)
     * @author summer
     * @date 2017/12/29 下午3:43
     * @param entity 用于封装条件的实体类
     * @return com.github.pagehelper.PageInfo<T>
     */
     PageInfo<T> findPageInfo(T entity, String orderBy);
}
