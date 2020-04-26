/**
 * <p>
 * 文件名称:    RedisServiceImpl
 * </p>
 */
package com.zhou.yadmin.system.monitor.service.impl;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.zhou.yadmin.common.constants.CommonConstant;
import com.zhou.yadmin.common.plugin.AbstractBaseComponent;
import com.zhou.yadmin.common.utils.JSONUtils;
import com.zhou.yadmin.common.utils.PageUtils;
import com.zhou.yadmin.system.monitor.dto.RedisDto;
import com.zhou.yadmin.system.monitor.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 20:00
 */
@Service
public class RedisServiceImpl extends AbstractBaseComponent implements RedisService
{
    // @Autowired
    // private StatefulRedisConnection<String, String> redisConnection;
    // @Autowired
    // private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Page<RedisDto> findByKey(String key, Pageable pageable)
    {
        // try
        // {
        //     RedisCommands<String, String> redisCommands = redisConnection.sync();
        //     List<RedisDto> redisVos = Lists.newArrayList();
        //
        //     if (!key.contains(CommonConstant.FIND_WILD_CARD))
        //     {
        //         key = CommonConstant.FIND_WILD_CARD + key + CommonConstant.FIND_WILD_CARD;
        //     }
        //     // 序列化转化成字节时，声明编码RedisCacheConst.SERIALIZE_ENCODE（ISO-8859-1），
        //     // 否则转换很容易出错（编码为UTF-8也会转换错误）
        //     for (String s : redisCommands.keys(key))
        //     {
        //         RedisDto redisDto = new RedisDto(s, new String(redisCommands.get(s).getBytes(CommonConstant.SERIALIZE_ENCODE_ISO)));
        //         redisVos.add(redisDto);
        //     }
        //     return new PageImpl<>(PageUtils.toPage(pageable.getPageNumber(), pageable.getPageSize(), redisVos), pageable, redisVos.size());
        // }
        // catch (Exception e)
        // {
        //     logger.error("获取缓存值异常", e);
        // }
        List<RedisDto> redisVos = Lists.newArrayList();
        if (!key.contains(CommonConstant.FIND_WILD_CARD))
        {
            key = CommonConstant.FIND_WILD_CARD + key + CommonConstant.FIND_WILD_CARD;
        }
        Set<String> keys = redisTemplate.keys(key);
        if (!CollectionUtils.isEmpty(keys))
        {
            for (String s : keys)
            {
                RedisDto redisDto = new RedisDto(s, JSONUtils.object2Json(redisTemplate.opsForValue().get(s)));
                redisVos.add(redisDto);
            }
            return new PageImpl<>(PageUtils.toPage(pageable.getPageNumber(), pageable.getPageSize(), redisVos), pageable, redisVos.size());
        }
        return null;
    }

    @Override
    public void save(RedisDto redisDto)
    {
        logger.debug("-------加入缓存------");
        // RedisCommands<String, String> redisCommands = redisConnection.sync();
        // // byte[] serialize = jackson2JsonRedisSerializer.serialize(value);
        // // redisCommands.set(redisDto.getKey(), new String(serialize, CommonConstant.SERIALIZE_ENCODE_ISO));
        // redisCommands.set(redisDto.getKey(), redisDto.getValue());
        redisTemplate.opsForValue().set(redisDto.getKey(), redisDto.getValue());
    }

    @Override
    public void delete(String key)
    {
        logger.debug("-------删除缓存 key={} ------", key);
        // RedisCommands<String, String> redisCommands = redisConnection.sync();
        // RedisCacheConst.WILDCARD是Redis中键的通配符“*”，用在这里使键值删除也能使用通配方式
        if (key.contains(CommonConstant.FIND_WILD_CARD))
        {
            // List<String> cacheKeys = redisCommands.keys(key);
            Set<String> cacheKeys = redisTemplate.keys(key);
            if (!CollectionUtils.isEmpty(cacheKeys))
            {
                // redisCommands.del(cacheKeys.toArray(new String[0]));
                redisTemplate.delete(cacheKeys);
            }
        }
        else
        {
            // redisCommands.del(key);
            redisTemplate.delete(key);
        }
    }

    @Override
    public void flushDb()
    {
        logger.debug("-------清空缓存------");
        // RedisCommands<String, String> redis = redisConnection.sync();
        // redis.flushdb();
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.flushDb();
            return "ok";
        });
    }
}
