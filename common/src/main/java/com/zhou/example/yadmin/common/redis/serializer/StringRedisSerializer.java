/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.common.redis.serializer.StringRedisSerializer
 * </p>
 */
package com.zhou.example.yadmin.common.redis.serializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.zhou.example.yadmin.common.utils.JSONUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

/**
 * <p>
 * 重写序列化器
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 22:05
 */
public class StringRedisSerializer implements RedisSerializer<Object>
{
    private final Charset charset;

    private final String target = "\"";

    private final String replacement = "";

    public StringRedisSerializer()
    {
        this(StandardCharsets.UTF_8);
    }

    public StringRedisSerializer(Charset charset)
    {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    @Override
    public String deserialize(byte[] bytes)
    {
        return (bytes == null ? null : new String(bytes, charset));
    }

    @Override
    public byte[] serialize(Object object)
    {
        String string = JSONUtils.object2Json(object);
        if (string == null)
        {
            return null;
        }
        string = string.replace(target, replacement);
        return string.getBytes(charset);
    }
}
