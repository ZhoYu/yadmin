/**
 * <p>
 * 文件名称:    com.zhou.yadmin.common.aop.LogAspect
 * </p>
 */
package com.zhou.yadmin.logging.aop;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zhou.yadmin.common.constants.FrontConstant;
import com.zhou.yadmin.common.exception.BadRequestException;
import com.zhou.yadmin.common.plugin.AbstractBaseComponent;
import com.zhou.yadmin.common.utils.ExceptionUtils;
import com.zhou.yadmin.common.utils.JSONUtils;
import com.zhou.yadmin.common.utils.WebUtils;
import com.zhou.yadmin.logging.aop.annotation.Log;
import com.zhou.yadmin.logging.domain.Logging;
import com.zhou.yadmin.logging.service.LoggingService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * <p>
 * 日志切面
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 22:39
 */
@Aspect
@Component
public class LogAspect extends AbstractBaseComponent
{
    @Autowired
    private LoggingService loggingService;

    private ThreadLocal<StopWatch> watchThreadLocal = new ThreadLocal<>();

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.zhou.yadmin.logging.aop.annotation.Log)")
    public void pointcut() // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    {
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint)
    {
        Logging logging = buildLoggingInfo(joinPoint, LogLevel.INFO.name());
        logger.debug("{} 调用开始...", logging.getMethod());
        startWatch();
        Object result;
        try
        {
            result = joinPoint.proceed();
        }
        catch (Throwable e)
        {
            throw BadRequestException.newExceptionByLogging(e.getMessage(), e);
        }
        StopWatch watch = stopWatch();
        logging.setTime(watch.getLastTaskTimeMillis());
        logger.debug("{} 调用结束, 总耗时 {} ms", logging.getMethod(), logging.getTime());
        loggingService.save(logging);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e)
    {
        StopWatch watch = stopWatch();
        if (watch != null)
        {
            Logging logging = buildLoggingInfo(joinPoint, LogLevel.ERROR.name());
            logging.setExceptionDetail(ExceptionUtils.getStackTrace(e));
            logging.setTime(watch.getLastTaskTimeMillis());
            logger.debug("{} 调用发生异常, 总耗时 {} ms", logging.getMethod(), logging.getTime(), e);
            loggingService.save(logging);
        }
        else
        {
            logger.error("日志切面获取计时器错误", e);
        }
    }

    private Logging buildLoggingInfo(JoinPoint joinPoint, String logType)
    {
        Logging logging = new Logging(logType, 0L);
        // 获取request
        HttpServletRequest request = WebUtils.getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = method.getAnnotation(Log.class);

        // 描述
        if (log != null)
        {
            logging.setDescription(log.value());
        }

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

        StringBuilder params = new StringBuilder("{");
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        // 用户名
        String username = "";

        if (argValues != null)
        {
            for (int i = 0; i < argValues.length; i++)
            {
                params.append(' ').append(argNames[i]).append(": ").append(argValues[i]);
            }
        }
        params.append(" }");
        // 获取IP地址
        logging.setRequestIp(WebUtils.getIpAddress(request));

        if (!FrontConstant.LOGIN_PATH.equals(signature.getName()))
        {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails)
            {
                UserDetails userDetails = (UserDetails) principal;
                username = userDetails.getUsername();
            }
            else if (principal instanceof String)
            {
                username = (String) principal;
            }
        }
        else
        {
            Map<String, String> userInfoMap = null;
            if (argValues[0] instanceof String)
            {
                userInfoMap = JSONUtils.json2Object((String) argValues[0], new TypeReference<HashMap<String, String>>(){});
                // AuthorizationUser user = JSONUtils.json2Object((String) argValues[0], AuthorizationUser.class);
                // AuthorizationUser user = JSONUtil.toBean(new JSONObject(argValues[0]), AuthorizationUser.class);
                // username = user.getUsername();
            }
            else if (argValues[0] instanceof Object)
            // else if (argValues[0] instanceof AuthorizationUser)
            {
                userInfoMap = JSONUtils.json2Object(JSONUtils.object2Json(argValues[0]), new TypeReference<HashMap<String, String>>(){});
                // AuthorizationUser user = (AuthorizationUser) argValues[0];
                // username = user.getUsername();
            }
            if (userInfoMap != null)
            {
                username = userInfoMap.get("username");
            }
        }
        logging.setMethod(methodName);
        logging.setUsername(username);
        logging.setParams(params.toString());
        return logging;
    }

    private StopWatch startWatch()
    {
        StopWatch watch = watchThreadLocal.get();
        if (watch == null)
        {
            watch = new StopWatch(Thread.currentThread().getName());
            watch.setKeepTaskList(false);
            watchThreadLocal.set(watch);
        }
        watch.start();
        return watch;
    }

    private StopWatch stopWatch()
    {
        StopWatch watch = watchThreadLocal.get();
        if (watch != null)
        {
            watch.stop();
            return watch;
        }
        return watch;
    }
}
