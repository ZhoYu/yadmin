/**
 * <p>
 * 文件名称:    WebSocketConfig
 * </p>
 */
package com.zhou.yadmin.system.monitor.config;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;

import com.zhou.yadmin.common.exception.handler.GlobalExceptionHandler;
import com.zhou.yadmin.common.plugin.AbstractBaseComponent;
import com.zhou.yadmin.system.monitor.dto.LogMessage;
import com.zhou.yadmin.system.monitor.log.queue.LoggerQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * <p>
 * 配置WebSocket消息代理端点，即stomp服务端
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/10 19:53
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractBaseComponent implements WebSocketMessageBrokerConfigurer
{
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    @Qualifier("customExecutor")
    private Executor executor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry)
    {
        registry.addEndpoint("/websocket").setAllowedOrigins("*").withSockJS();
    }

    /**
     * 推送日志到/topic/pullLogger
     */
    @PostConstruct
    public void pushLogger()
    {
        executor.execute(() -> {
            while (true)
            {
                try
                {
                    LogMessage log = LoggerQueue.getInstance().poll();
                    if (log != null)
                    {
                        // 格式化异常堆栈信息
                        if ("ERROR".equals(log.getLevel()) && GlobalExceptionHandler.class.getName().equals(log.getClassName()))
                        {
                            log.setBody("<pre>" + log.getBody() + "</pre>");
                        }
                        else if (log.getClassName().equals("jdbc.resultsettable"))
                        {
                            log.setBody("<br><pre>" + log.getBody() + "</pre>");
                        }
                        if (messagingTemplate != null)
                        {
                            messagingTemplate.convertAndSend("/topic/logMsg", log);
                        }
                    }
                }
                catch (Exception e)
                {
                    logger.error("处理消息异常", e);
                }
            }
        });
    }
}
