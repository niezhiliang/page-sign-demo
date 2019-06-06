package cc.huluwa.page.sign.demo.server.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/24 下午7:23
 */
@Component
public class RedisTools {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 永久存储数据
     * @param key
     * @param value
     */
    public void save(String key, String value) {
        stringRedisTemplate.opsForValue().set(key,value);
    }

    /**
     * 给定过期时间(分钟)存储数据
     * @param key
     * @param value
     * @param times
     */
    public void save(String key, String value, int times) {
        stringRedisTemplate.opsForValue().set(key,value,times,TimeUnit.MINUTES);
    }

    /**
     * 给定过期时间存储数据
     * @param key
     * @param value
     * @param times
     * @param timeUnit
     */
    public void save(String key, String value, int times,TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key,value,times,timeUnit);
    }

    /**
     * 通过key获取值
     * @param key
     * @return
     */
    public String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 根据指定key删除
     * @param key
     */
    public boolean delete(String key) {
        return stringRedisTemplate.delete(key);
    }

    /**
     * 判断是否包含key
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }
}