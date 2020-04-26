/**
 * <p>
 * 文件名称:    RedisController
 * </p>
 */
package com.zhou.yadmin.system.monitor.web.controller;

import com.zhou.yadmin.logging.aop.annotation.Log;
import com.zhou.yadmin.system.monitor.dto.RedisDto;
import com.zhou.yadmin.system.monitor.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 20:34
 */
@RestController
@RequestMapping("api/redis")
public class RedisController
{
    @Autowired
    private RedisService redisService;

    @Log("查询Redis缓存")
    @GetMapping(value = "/")
    @PreAuthorize("hasAnyRole('ADMIN','REDIS_ALL','REDIS_SELECT')")
    public ResponseEntity getRedis(String key, Pageable pageable)
    {
        return new ResponseEntity(redisService.findByKey(key, pageable), HttpStatus.OK);
    }

    @Log("新增Redis缓存")
    @PostMapping(value = "/")
    @PreAuthorize("hasAnyRole('ADMIN','REDIS_ALL','REDIS_CREATE')")
    public ResponseEntity create(@Validated @RequestBody RedisDto resources)
    {
        redisService.save(resources);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Log("修改Redis缓存")
    @PutMapping(value = "/")
    @PreAuthorize("hasAnyRole('ADMIN','REDIS_ALL','REDIS_EDIT')")
    public ResponseEntity update(@Validated @RequestBody RedisDto resources)
    {
        redisService.save(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除Redis缓存")
    @DeleteMapping(value = "/")
    @PreAuthorize("hasAnyRole('ADMIN','REDIS_ALL','REDIS_DELETE')")
    public ResponseEntity delete(@RequestParam("key") String key)
    {
        redisService.delete(key);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("清空Redis缓存")
    @DeleteMapping(value = "all")
    @PreAuthorize("hasAnyRole('ADMIN','REDIS_ALL','REDIS_DELETE')")
    public ResponseEntity deleteAll()
    {
        redisService.flushDb();
        return new ResponseEntity(HttpStatus.OK);
    }
}
