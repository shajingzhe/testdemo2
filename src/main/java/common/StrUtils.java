package common;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class StrUtils {
    /**
     * 获取雪花ID
     * 分布式系统中，有一些需要使用全局唯一ID的场景，有些时候我们希望能使用一种简单一些的ID，并且希望ID能够按照时间有序生成。Twitter的Snowflake 算法就是这种生成器。
     */
    public static String getSnowflakeId() {
        //注意 IdUtil.createSnowflake每次调用会创建一个新的Snowflake对象，不同的Snowflake对象创建的ID可能会有重复，因此请自行维护此对象为单例，或者使用IdUtil.getSnowflake使用全局单例对象。
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        long id = snowflake.nextId();
        return Long.toString(id);
    }
}
