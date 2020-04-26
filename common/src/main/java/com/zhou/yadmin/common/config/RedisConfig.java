/**
 * <p>
 * 文件名称:    RedisConfig
 * </p>
 */
package com.zhou.yadmin.common.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.zhou.yadmin.common.constants.CommonConstant;
import com.zhou.yadmin.common.redis.serializer.StringRedisSerializer;
import com.zhou.yadmin.common.utils.JSONUtils;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 23:11
 */
@Configuration
@EnableCaching
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig extends CachingConfigurerSupport
{
    @Autowired
    private RedisProperties redisProperties;

    @Value("${spring.application.name}")
    private String appName;
    /**
     * 缓存默认失效时间 单位 ms
     */
    @Value("${spring.redis.ttl:60000}")
    private long timeToLeave;

    /**
     * 自定义缓存key的生成策略。默认的生成策略是看不懂的(乱码内容) 通过Spring 的依赖注入特性进行自定义的配置注入并且此类是一个配置类可以更多程度的自定义配置
     *
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator()
    {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params)
            {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    /**
     * 缓存配置管理器
     */
    @Bean
    public CacheManager cacheManager(@Qualifier("lettuceConnectionFactoryUvPv") RedisConnectionFactory factory,
      GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer)
    {
        //以锁写入的方式创建RedisCacheWriter对象
        RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(factory);
        // 设置CacheManager的Value序列化方式为JdkSerializationRedisSerializer,
        // 但其实RedisCacheConfiguration默认就是使用
        // StringRedisSerializer序列化key，
        // JdkSerializationRedisSerializer序列化value,
        // 所以以下注释代码就是默认实现，没必要写，直接注释掉
        // RedisSerializationContext.SerializationPair pair = RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer(redisProperties.getClassLoader()));
        //创建默认缓存配置对象
        // RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        RedisSerializationContext.SerializationPair<Object> pair =
          RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer);
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
        config.entryTtl(Duration.ofMillis(timeToLeave));
        return new RedisCacheManager(writer, config);
    }

    /**
     * 获取缓存操作助手对象
     * 配置自定义redisTemplate
     *
     * @param factory redis连接工厂实现
     *
     * @return 返回一个可以使用的RedisTemplate实例
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(@Qualifier("lettuceConnectionFactoryUvPv") RedisConnectionFactory factory,
      GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer)
    {
        //创建Redis缓存操作助手RedisTemplate对象
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // 以下代码为将RedisTemplate的Value序列化方式由JdkSerializationRedisSerializer更换为Jackson2JsonRedisSerializer
        // 此种序列化方式结果清晰、容易阅读、存储字节少、速度快，所以推荐更换
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 为RedisTemplate配置Redis连接工厂实现
     * LettuceConnectionFactory实现了RedisConnectionFactory接口
     * UVPV用Redis
     * <p>
     * 这里要注意的是，在构建LettuceConnectionFactory 时，如果不使用内置的destroyMethod，可能会导致Redis连接早于其它Bean被销毁
     *
     * @return 返回LettuceConnectionFactory
     */
    @Bean(destroyMethod = "destroy")
    public LettuceConnectionFactory lettuceConnectionFactoryUvPv(RedisConfiguration redisConfiguration)
    {
        return new LettuceConnectionFactory(redisConfiguration, getLettuceClientConfiguration());
    }

    /**
     * 根据配置文件匹配
     *
     * @return
     */
    @Bean
    public RedisConfiguration redisConfiguration()
    {
        if (redisProperties.getCluster() != null && !CollectionUtils.isEmpty(redisProperties.getCluster().getNodes()))
        {
            RedisClusterConfiguration configuration = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
            if (redisProperties.getCluster().getMaxRedirects() != null)
            {
                configuration.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());
            }
            if (redisProperties.getPassword() != null)
            {
                configuration.setPassword(RedisPassword.of(redisProperties.getPassword())); //集群的密码认证
            }
            // configuration.setTimeout(timeout);
            return configuration;
        }
        else if (redisProperties.getSentinel() != null && !CollectionUtils.isEmpty(redisProperties.getSentinel().getNodes()))
        {
            RedisSentinelConfiguration configuration = new RedisSentinelConfiguration();
            List<String> clusterNodes = redisProperties.getSentinel().getNodes();
            configuration.setSentinels(clusterNodes.stream().map(this::readHostAndPortFromString).collect(Collectors.toSet()));
            configuration.master(redisProperties.getSentinel().getMaster());
            if (redisProperties.getPassword() != null)
            {
                configuration.setPassword(RedisPassword.of(redisProperties.getPassword())); //集群的密码认证
            }
            configuration.setDatabase(redisProperties.getDatabase());
            return configuration;
        }
        else
        {
            // RedisStandaloneConfiguration这个配置类是Spring Data Redis2.0后才有的~~~
            RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
            // 2.0后的写法
            if (StringUtils.hasText(redisProperties.getUrl()))
            {
                ConnectionInfo connectionInfo = parseUrl(redisProperties.getUrl());
                configuration.setHostName(connectionInfo.getHostName());
                configuration.setPort(connectionInfo.getPort());
                configuration.setPassword(RedisPassword.of(connectionInfo.getPassword()));
            }
            else
            {
                configuration.setHostName(redisProperties.getHost());
                configuration.setPort(redisProperties.getPort());
                configuration.setPassword(RedisPassword.of(redisProperties.getPassword()));
            }
            configuration.setDatabase(redisProperties.getDatabase());
            return configuration;
        }
    }

    /**
     * 配置LettuceClientConfiguration 包括线程池配置和安全项配置
     *
     * @return lettuceClientConfiguration
     */
    private LettuceClientConfiguration getLettuceClientConfiguration()
    {
        // ClusterTopologyRefreshOptions配置用于开启自适应刷新和定时刷新。如自适应刷新不开启，Redis集群变更时将会导致连接异常！
        ClusterTopologyRefreshOptions topologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
          //开启自适应刷新
          // .enableAdaptiveRefreshTrigger(RefreshTrigger.MOVED_REDIRECT, RefreshTrigger.PERSISTENT_RECONNECTS)
          //开启所有自适应刷新，MOVED，ASK，PERSISTENT都会触发
          .enableAllAdaptiveRefreshTriggers()
          // 自适应刷新超时时间(默认30秒)
          .adaptiveRefreshTriggersTimeout(Duration.ofSeconds(25)) //默认关闭开启后时间为30秒
          // 开周期刷新
          .enablePeriodicRefresh(Duration.ofSeconds(20))
          // 默认关闭开启后时间为60秒 ClusterTopologyRefreshOptions.DEFAULT_REFRESH_PERIOD 60  .enablePeriodicRefresh(Duration.ofSeconds(2)) = .enablePeriodicRefresh().refreshPeriod(Duration.ofSeconds(2))
          .build();
        LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
        builder.poolConfig(genericPoolConfig()).clientOptions(ClusterClientOptions.builder().topologyRefreshOptions(topologyRefreshOptions).build());
        //将appID传入连接，方便Redis监控中查看
        builder.clientName(appName + "_lettuce");
        Duration shutdownTimeout = redisProperties.getLettuce().getShutdownTimeout();
        if (shutdownTimeout != null)
        {
            builder.shutdownTimeout(shutdownTimeout);
        }
        Duration timeout = redisProperties.getTimeout();
        if (timeout != null)
        {
            builder.commandTimeout(timeout);
        }
        return builder.build();
    }

    /**
     * 初始化自定义的 Jackson2JsonRedisSerializer
     *
     * @return
     */
    @Bean
    public GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer()
    {
        return new GenericJackson2JsonRedisSerializer(getObjectMapper());
    }

    private GenericObjectPoolConfig<Object> genericPoolConfig()
    {
        // common-pool2线程池
        GenericObjectPoolConfig<Object> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
        poolConfig.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
        poolConfig.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
        poolConfig.setMaxWaitMillis(redisProperties.getLettuce().getPool().getMaxWait().toMillis());
        return poolConfig;
    }

    private static ObjectMapper getObjectMapper()
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 注册 jdk8 的支持
        mapper.registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module()).registerModule(JSONUtils.getJavaTimeModule());
        // 支持 jaxb 注解
        mapper.registerModule(new JaxbAnnotationModule());
        //反序列化时候遇到不匹配的属性并不抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //序列化时候遇到空对象不抛出异常
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //反序列化的时候如果是无效子类型,不抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        //使用JSR310提供的序列化类,里面包含了大量的JDK8时间序列化类
        // mapper.registerModule(new JavaTimeModule());
        //启用反序列化所需的类型信息,在属性中添加@class
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        //配置null值的序列化器
        GenericJackson2JsonRedisSerializer.registerNullValueSerializer(mapper, null);
        return mapper;
    }

    private RedisNode readHostAndPortFromString(String hostAndPort)
    {
        String[] values = StringUtils.split(hostAndPort, CommonConstant.COLON_DELIMITER);
        Assert.notNull(values, "HostAndPort need to be seperated by  ':'.");
        Assert.isTrue(values.length == 2, "Host and Port String needs to specified as host:port");
        return new RedisNode(values[0], NumberUtils.toInt(values[1]));
    }

    protected ConnectionInfo parseUrl(String url)
    {
        try
        {
            URI uri = new URI(url);
            boolean useSsl = (url.startsWith("rediss://"));
            String password = null;
            if (uri.getUserInfo() != null)
            {
                password = uri.getUserInfo();
                int index = password.indexOf(':');
                if (index >= 0)
                {
                    password = password.substring(index + 1);
                }
            }
            return new ConnectionInfo(uri, useSsl, password);
        }
        catch (URISyntaxException ex)
        {
            throw new IllegalArgumentException("Malformed url '" + url + "'", ex);
        }
    }

    static class ConnectionInfo
    {
        private final URI uri;

        private final boolean useSsl;

        private final String password;

        ConnectionInfo(URI uri, boolean useSsl, String password)
        {
            this.uri = uri;
            this.useSsl = useSsl;
            this.password = password;
        }

        boolean isUseSsl()
        {
            return this.useSsl;
        }

        String getHostName()
        {
            return this.uri.getHost();
        }

        int getPort()
        {
            return this.uri.getPort();
        }

        String getPassword()
        {
            return this.password;
        }

    }
}
