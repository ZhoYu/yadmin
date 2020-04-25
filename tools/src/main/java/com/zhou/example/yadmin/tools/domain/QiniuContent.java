/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.tools.domain.QiniuContent
 * </p>
 */
package com.zhou.example.yadmin.tools.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

import com.zhou.example.yadmin.common.domain.BaseEntity;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * <p>
 * 上传成功后，存储结果
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/19 19:47
 */
@Entity
@Table(name = "t_qiniu_content")
public class QiniuContent extends BaseEntity<Long>
{
    private static final long serialVersionUID = -7089394680749572995L;

    /**
     * 文件名，如qiniu.jpg
     */
    @Column(name = "name",unique = false)
    private String key;

    /**
     * 空间名
     */
    private String bucket;

    /**
     * 大小
     */
    private String size;

    /**
     * 文件地址
     */
    private String url;

    /**
     * 空间类型：公开/私有
     */
    private String type = "公开";

    /**
     * 更新时间
     */
    @UpdateTimestamp
    @Column(name = "update_time")
    private LocalDateTime updateTime;

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
     * Gets the value of size
     *
     * @return the value of size
     */
    public String getSize()
    {
        return size;
    }

    /**
     * Sets the size
     * <p>You can use getSize() to get the value of size</p>
     *
     * @param size size
     */
    public void setSize(String size)
    {
        this.size = size;
    }

    /**
     * Gets the value of url
     *
     * @return the value of url
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * Sets the url
     * <p>You can use getUrl() to get the value of url</p>
     *
     * @param url url
     */
    public void setUrl(String url)
    {
        this.url = url;
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
     * Gets the value of updateTime
     *
     * @return the value of updateTime
     */
    public LocalDateTime getUpdateTime()
    {
        return updateTime;
    }

    /**
     * Sets the updateTime
     * <p>You can use getUpdateTime() to get the value of updateTime</p>
     *
     * @param updateTime updateTime
     */
    public void setUpdateTime(LocalDateTime updateTime)
    {
        this.updateTime = updateTime;
    }
}
