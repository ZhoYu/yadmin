/**
 * <p>
 * 文件名称:    RedisService
 * </p>
 */
package com.zhou.yadmin.system.monitor.service;

import com.zhou.yadmin.system.monitor.dto.RedisDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 23:36
 */
public interface RedisService
{
    /**
     * findById
     *
     * @param key
     *
     * @return
     */
    Page<RedisDto> findByKey(String key, Pageable pageable);

    /**
     * create
     *
     * @param redisDto
     */
    void save(RedisDto redisDto);

    /**
     * delete
     *
     * @param key
     */
    void delete(String key);

    /**
     * 清空所有缓存
     */
    void flushDb();
}
