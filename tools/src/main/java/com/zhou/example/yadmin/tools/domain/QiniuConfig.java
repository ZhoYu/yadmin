/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.tools.domain.QiniuConfig
 * </p>
 */
package com.zhou.example.yadmin.tools.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.zhou.example.yadmin.common.domain.BaseEntity;

/**
 * <p>
 * 七牛云对象存储配置类
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/19 19:22
 */
@Entity
@Table(name = "t_qiniu_config")
public class QiniuConfig extends BaseEntity<Long>
{
    private static final long serialVersionUID = -1414511219915186533L;

    /**
     * 一个账号最多拥有两对密钥(Access/Secret Key)
     */
    @NotBlank
    @Column(name = "access_key", columnDefinition = "text")
    private String accessKey;

    /**
     * 一个账号最多拥有两对密钥(Access/Secret Key)
     */
    @NotBlank
    @Column(name = "secret_key", columnDefinition = "text")
    private String secretKey;

    /**
     * 存储空间名称作为唯一的 Bucket 识别符
     */
    @NotBlank
    private String bucket;

    /**
     * Zone表示与机房的对应关系
     * 华东	Zone.zone0()
     * 华北	Zone.zone1()
     * 华南	Zone.zone2()
     * 北美	Zone.zoneNa0()
     * 东南亚	Zone.zoneAs0()
     */
    @NotBlank
    private String zone;

    /**
     * 外链域名，可自定义，需在七牛云绑定
     */
    @NotBlank
    private String host;

    /**
     * 空间类型：公开/私有
     */
    private String type = "公开";

    /**
     * Gets the value of accessKey
     *
     * @return the value of accessKey
     */
    public String getAccessKey()
    {
        return accessKey;
    }

    /**
     * Sets the accessKey
     * <p>You can use getAccessKey() to get the value of accessKey</p>
     *
     * @param accessKey accessKey
     */
    public void setAccessKey(String accessKey)
    {
        this.accessKey = accessKey;
    }

    /**
     * Gets the value of secretKey
     *
     * @return the value of secretKey
     */
    public String getSecretKey()
    {
        return secretKey;
    }

    /**
     * Sets the secretKey
     * <p>You can use getSecretKey() to get the value of secretKey</p>
     *
     * @param secretKey secretKey
     */
    public void setSecretKey(String secretKey)
    {
        this.secretKey = secretKey;
    }

    /**
     * Gets the value of bucket
     *
     * @return the value of bucket
     */
    public String getBucket()
    {
        return bucket;
    }

    /**
     * Sets the bucket
     * <p>You can use getBucket() to get the value of bucket</p>
     *
     * @param bucket bucket
     */
    public void setBucket(String bucket)
    {
        this.bucket = bucket;
    }

    /**
     * Gets the value of zone
     *
     * @return the value of zone
     */
    public String getZone()
    {
        return zone;
    }

    /**
     * Sets the zone
     * <p>You can use getZone() to get the value of zone</p>
     *
     * @param zone zone
     */
    public void setZone(String zone)
    {
        this.zone = zone;
    }

    /**
     * Gets the value of host
     *
     * @return the value of host
     */
    public String getHost()
    {
        return host;
    }

    /**
     * Sets the host
     * <p>You can use getHost() to get the value of host</p>
     *
     * @param host host
     */
    public void setHost(String host)
    {
        this.host = host;
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
}
