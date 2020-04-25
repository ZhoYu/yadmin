/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.tools.domain.VerificationCode
 * </p>
 */
package com.zhou.example.yadmin.tools.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import com.zhou.example.yadmin.common.domain.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/12 22:06
 */
@Entity
@Table(name = "t_verification_code")
public class VerificationCode extends BaseEntity<Long>
{
    private static final long serialVersionUID = -6862520362485959355L;

    private String code;

    /**
     * 使用场景，自己定义
     */
    private String scenes;

    /**
     * true 为有效，false 为无效
     */
    private Boolean status = true;

    /**
     * 类型 ：phone 和 email
     */
    @NotBlank
    private String type;

    /**
     * 具体的phone与email
     */
    @NotBlank
    private String value;

    /**
     * 创建日期
     */
    @CreationTimestamp
    @Column(name = "create_time")
    private LocalDateTime createTime;

    public VerificationCode()
    {
    }

    public VerificationCode(String code, String scenes, @NotBlank String type, @NotBlank String value)
    {
        this.code = code;
        this.scenes = scenes;
        this.type = type;
        this.value = value;
    }

    public VerificationCode(Long id, String code, String scenes, @NotBlank String type, @NotBlank String value, Boolean status, LocalDateTime createTime)
    {
        this.setId(id);
        this.code = code;
        this.scenes = scenes;
        this.type = type;
        this.value = value;
        this.status = status;
        this.createTime = createTime;
    }

    /**
     * Gets the value of code
     *
     * @return the value of code
     */
    public String getCode()
    {
        return code;
    }

    /**
     * Sets the code
     * <p>You can use getCode() to get the value of code</p>
     *
     * @param code code
     */
    public void setCode(String code)
    {
        this.code = code;
    }

    /**
     * Gets the value of scenes
     *
     * @return the value of scenes
     */
    public String getScenes()
    {
        return scenes;
    }

    /**
     * Sets the scenes
     * <p>You can use getScenes() to get the value of scenes</p>
     *
     * @param scenes scenes
     */
    public void setScenes(String scenes)
    {
        this.scenes = scenes;
    }

    /**
     * Gets the value of status
     *
     * @return the value of status
     */
    public Boolean getStatus()
    {
        return status;
    }

    /**
     * Sets the status
     * <p>You can use getStatus() to get the value of status</p>
     *
     * @param status status
     */
    public void setStatus(Boolean status)
    {
        this.status = status;
    }

    /**
     * Gets the value of type
     *
     * @return the value of type
     */
    public String getType()
    {
        return type;
    }

    /**
     * Sets the type
     * <p>You can use getType() to get the value of type</p>
     *
     * @param type type
     */
    public void setType(String type)
    {
        this.type = type;
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

    /**
     * Gets the value of createTime
     *
     * @return the value of createTime
     */
    public LocalDateTime getCreateTime()
    {
        return createTime;
    }

    /**
     * Sets the createTime
     * <p>You can use getCreateTime() to get the value of createTime</p>
     *
     * @param createTime createTime
     */
    public void setCreateTime(LocalDateTime createTime)
    {
        this.createTime = createTime;
    }
}
