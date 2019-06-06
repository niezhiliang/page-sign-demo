package cc.huluwa.page.sign.demo.server.utils;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class RedisListTools<T>  {

    @Resource
    private ListOperations<String,T> listOperations;

    /**
     * 移除左边第一个元素
     * @param key
     * @return
     */
    public T rightPop(String key) {
        return listOperations.rightPop("list_"+key);
    }

    /**
     * list末尾加上一个元素
     * @param key
     * @param value
     */
    public void leftPush(String key,T value) {
        listOperations.leftPush("list_"+key,value);
    }

    /**
     * list末尾加上一系列元素
     * @param key
     * @param value
     */
    public void leftPushList(String key, List<T> value) {
        listOperations.leftPushAll("list_"+key,value);
    }

    /**
     * 删除list集合
     * @param key
     */
    public void delete(String key) {
        //保留集合中索引0，0之间的值，其余全部删除  所以list只有有一个值存在
        listOperations.trim("list_"+key,0,0);
        //将list中的剩余的一个值也删除
        listOperations.leftPop("list_"+key);
    }



}
