/**
 * <p>
 * 文件名称:    RedisDto
 * </p>
 */
package com.zhou.yadmin.system.monitor.dto;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 23:20
 */
public class RedisDto
{
    @NotBlank
    private String key;

    @NotBlank
    private String value;

    public RedisDto()
    {
    }

    public RedisDto(@NotBlank String key, @NotBlank String value)
    {
        this.key = key;
        this.value = value;
    }

    /**
     * Gets the value of key
     *
     * @return the value of key
     */
    public String getKey()
    {
        return key;
    }

    /**
     * Sets the key
     * <p>You can use getKey() to get the value of key</p>
     *
     * @param key key
     */
    public void setKey(String key)
    {
        this.key = key;
    }

    /**
     * Gets the value of value
     *
     * @return the value of value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * Sets the value
     * <p>You can use getValue() to get the value of value</p>
     *
     * @param value value
     */
    public void setValue(String value)
    {
        this.value = value;
    }
}
