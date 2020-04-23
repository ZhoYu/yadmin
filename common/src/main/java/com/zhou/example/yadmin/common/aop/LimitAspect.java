package com.zhou.example.yadmin.common.aop;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

import com.google.common.collect.ImmutableList;
import com.zhou.example.yadmin.common.aop.annotation.Limit;
import com.zhou.example.yadmin.common.constants.CommonConstant;
import com.zhou.example.yadmin.common.enums.LimitType;
import com.zhou.example.yadmin.common.exception.BadRequestException;
import com.zhou.example.yadmin.common.plugin.AbstractBaseComponent;
import com.zhou.example.yadmin.common.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.common.aop.LimitAspect
 * </p>
 */
@Aspect
@Component
public class LimitAspect extends AbstractBaseComponent
{
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Pointcut("@annotation(com.zhou.example.yadmin.common.aop.annotation.Limit)")
    public void pointcut()
    {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable
    {
        HttpServletRequest request = WebUtils.getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Limit limit = method.getAnnotation(Limit.class);
        String key = limit.key();
        if (StringUtils.isBlank(key))
        {
            if (limit.limitType() == LimitType.IP)
            {
                key = WebUtils.getIpAddress(request);
            }
            else
            {
                key = method.getName();
            }
        }
        ImmutableList<String> keys = ImmutableList.of(StringUtils
          .join(limit.prefix(), CommonConstant.UNDERSCORE_DELIMITER, key, CommonConstant.UNDERSCORE_DELIMITER,
            request.getRequestURI().replaceAll(CommonConstant.SLASH_DELIMITER, CommonConstant.UNDERSCORE_DELIMITER)));
        String luaScript = buildLuaScript();
        DefaultRedisScript<Number> script = new DefaultRedisScript<>(luaScript, Number.class);
        Number count = redisTemplate.execute(script, keys, limit.count(), limit.period());
        if (count != null && count.intValue() <= limit.count())
        {
            logger.info("第{}次访问key为 {}，描述为 [{}] 的接口", count, keys, limit.name());
            return joinPoint.proceed();
        }
        else
        {
            throw BadRequestException.newExceptionByCommon("访问受限制");
        }
    }

    /**
     * 限流脚本
     */
    private String buildLuaScript()
    {
        return "local c" +
               "\nc = redis.call('get',KEYS[1])" +
               "\nif c and tonumber(c) > tonumber(ARGV[1]) then" +
               "\nreturn c;" +
               "\nend" +
               "\nc = redis.call('incr',KEYS[1])" +
               "\nif tonumber(c) == 1 then" +
               "\nredis.call('expire',KEYS[1],ARGV[2])" +
               "\nend" +
               "\nreturn c;";
    }
}
