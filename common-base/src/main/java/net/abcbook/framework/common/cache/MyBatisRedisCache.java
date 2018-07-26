package net.abcbook.framework.common.cache;//package com.tinypace.framework.common.cache;
//
//import com.tinypace.framework.common.util.ApplicationContextProvider;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.cache.Cache;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.ReadWriteLock;
//import java.util.concurrent.locks.ReentrantReadWriteLock;
//
///**
// * @author summer
// * @date 2018/1/13 下午11:14
// */
//@Slf4j
//public class MyBatisRedisCache implements Cache {
//
//    /** 前缀 */
//    private static final String MY_BATISE_CACHE_PREFIX = "my_batis_cache:";
//    /** 读写锁 */
//    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
//
//    /** cache instance id */
//    private final String id;
//    private RedisTemplate<String, Object> redisTemplate;
//
//    /** redis过期时间 */
//    private static final long EXPIRE_TIME_IN_MINUTES = 30;
//
//    private RedisTemplate<String, Object> getRedisTemplate() {
//        if (redisTemplate == null) {
//            redisTemplate = ApplicationContextProvider.getBean("redisTemplate");
//        }
//        return redisTemplate;
//    }
//
//    public MyBatisRedisCache(String id) {
//        if (id == null) {
//            throw new IllegalArgumentException("Cache instances require an ID");
//        }
//        this.id = id;
//    }
//
//    @Override
//    public String getId() {
//        return this.id;
//    }
//
//    /**
//     * Put query result to redis
//     *
//     * @param key
//     * @param value
//     */
//    @Override
//    @SuppressWarnings("unchecked")
//    public void putObject(Object key, Object value) {
//        if(key == null){
//            return;
//        }
//        RedisTemplate redisTemplate = getRedisTemplate();
//        ValueOperations opsForValue = redisTemplate.opsForValue();
//
//        String strKey = MY_BATISE_CACHE_PREFIX + key.toString();
//
//        log.error(strKey);
//        log.error("put");
//        opsForValue.set(strKey, value, EXPIRE_TIME_IN_MINUTES, TimeUnit.MINUTES);
//    }
//
//    /**
//     * Get cached query result from redis
//     *
//     * @param key
//     * @return
//     */
//    @Override
//    public Object getObject(Object key) {
//        if(key == null){
//            return null;
//        }
//        RedisTemplate redisTemplate = getRedisTemplate();
//        ValueOperations opsForValue = redisTemplate.opsForValue();
//
//        String strKey = MY_BATISE_CACHE_PREFIX + key.toString();
//
//        log.error("get");
//        log.error(strKey);
//        return opsForValue.get(strKey);
//    }
//
//    /**
//     * Remove cached query result from redis
//     *
//     * @param key
//     * @return
//     */
//    @Override
//    @SuppressWarnings("unchecked")
//    public Object removeObject(Object key) {
//        RedisTemplate redisTemplate = getRedisTemplate();
//
//        String strKey = MY_BATISE_CACHE_PREFIX + key.toString();
//
//        log.error("remove");
//        log.error(strKey);
//        redisTemplate.delete(strKey);
//        return null;
//    }
//
//    /**
//     * Clears this cache instance
//     */
//    @Override
//    public void clear() {
//        RedisTemplate redisTemplate = getRedisTemplate();
//        String prefix = MY_BATISE_CACHE_PREFIX + "**";
//        Set<String> keySet = redisTemplate.keys(prefix);
//
//        log.error("clear");
//
//        redisTemplate.delete(keySet);
//
////        redisTemplate.execute((RedisCallback) connection -> {
////            connection.flushDb();
////            return null;
////        });
//    }
//
//    @Override
//    public int getSize() {
////        Long size = (Long) redisTemplate.execute(new RedisCallback<Long>() {
////            @Override
////            public Long doInRedis(RedisConnection connection) throws DataAccessException {
////                return connection.dbSize();
////            }
////        });
////        return size.intValue();
//        return 0;
//    }
//
//    @Override
//    public ReadWriteLock getReadWriteLock() {
//        return this.readWriteLock;
//    }
//
//}
