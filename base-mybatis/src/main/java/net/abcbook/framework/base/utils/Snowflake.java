package net.abcbook.framework.base.utils;

/**
 * @author summer
 * @date 2017/12/29 下午1:09
 *
 * 基于Twitter的Snowflake算法实现分布式高效有序ID生产黑科技(sequence)
 * <br>
 * SnowFlake的结构如下(每部分用-分开):<br>
 * <br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * <br>
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。
 * 41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * <br>
 * 10位的数据机器位，可以部署在1024个节点，包括5位 datacenterId 和5位 workerId <br>
 * <br>
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 * <br>
 * <br>
 * 加起来刚好64位，为一个Long型。<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 */
public class Snowflake {

    /** 开始时间戳 2015-01-01 */
    private long twepoch = 1420041600000L;
    /** 机器 id 所占的位数 */
    private final long workerIdBits = 5L;
    /** 数据中心 id 所占的位数 */
    private final long datacenterIdBits = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /** 支持的最大数据标识id，结果是31 */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /** 序列在id中占的位数 */
    private final long sequenceBits = 12L;

    /** 机器ID向左移12位 */
    private final long workerIdShift = sequenceBits;
    /** 数据标识id向左移17位(12+5) */
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    /** 时间截向左移22位(5+5+12) */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 工作机器ID(0~31) */
    private long workerId;
    /** 数据中心ID(0~31) */
    private long datacenterId;
    /** 毫秒内序列(0~4095) */
    private long sequence = 0L;
    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;

    /** 机器 id */
    private static final long DEFAULT_WORKER_ID = 0L;
    /** 数据中心 id */
    private static final long DEFAULT_DATACENTER_ID = 0L;

    /**
     * @author summer
     * @date 2017/12/29 下午1:20
     * @param workerId 工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     * @description
     */
    private Snowflake(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 定义 snowflake 对象, 用于单例返回
     */
    private static Snowflake instance = null;

    /**
     * @author summer
     * @date 2018/9/7 下午4:19
     * @description 饿汉模式, 修改生成 Snowflake 对象
     * @return net.abcbook.framework.base.utils.Snowflake
     * @version V1.0.0-RELEASE
     */
    public static Snowflake getInstance(){
        if (instance == null){
            synchronized (Snowflake.class){
                if (instance == null) {
                    instance = new Snowflake(DEFAULT_DATACENTER_ID, DEFAULT_DATACENTER_ID);
                }
            }
        }
        return instance;
    }

    /**
     * @author summer
     * @date 2017/12/29 下午2:19
     * @return long
     * @description 生成全局唯一的有序 id
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        // 如果上次生成时间和当前时间相同,在同一毫秒内
        if (lastTimestamp == timestamp) {
            //sequence自增，因为sequence只有12bit，所以和sequenceMask相与一下，去掉高位
            sequence = (sequence + 1) & sequenceMask;
            //判断是否溢出,也就是每毫秒内超过4095，当为4095时，与sequenceMask相与，sequence就等于0
            if (sequence == 0) {
                //自旋等待到下一毫秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }

    /**
     * @author summer
     * @date 2017/12/29 下午1:12
     * @param lastTimestamp 上次使用的时间戳
     * @return long
     * @description 防止产生的时间比之前的时间还要小（由于NTP回拨等问题）,保持增量的趋势.
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * @author summer
     * @date 2017/12/29 下午1:12
     * @return long
     * @description 获取当前的时间戳
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

//    public static void main(String[] args) {
//        Snowflake snowflake = new Snowflake(0, 0);
//
////        Date date = new Date(1514526547434L);
//        for(int i = 1; i < 10000; i++) {
//            System.out.println(snowflake.nextId());
//        }
//    }
}
