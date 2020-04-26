/**
 * <p>
 * 文件名称:    Picture
 * </p>
 */
package com.zhou.yadmin.tools.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

import com.google.common.base.MoreObjects;
import com.zhou.yadmin.common.domain.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;

/**
 * <p>
 * sm.ms图床
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/15 23:22
 */
@Entity
@Table(name = "t_picture")
public class Picture extends BaseEntity<Long>
{
    private static final long serialVersionUID = -8027143725126360425L;

    private String filename;

    private String url;

    private String size;

    private String height;

    private String width;

    /**
     * delete URl
     */
    @Column(name = "delete_url")
    private String delete;

    private String username;

    @CreationTimestamp
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * Gets the value of filename
     *
     * @return the value of filename
     */
    public String getFilename()
    {
        return filename;
    }

    /**
     * Sets the filename
     * <p>You can use getFilename() to get the value of filename</p>
     *
     * @param filename filename
     */
    public void setFilename(String filename)
    {
        this.filename = filename;
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
     * Gets the value of height
     *
     * @return the value of height
     */
    public String getHeight()
    {
        return height;
    }

    /**
     * Sets the height
     * <p>You can use getHeight() to get the value of height</p>
     *
     * @param height height
     */
    public void setHeight(String height)
    {
        this.height = height;
    }

    /**
     * Gets the value of width
     *
     * @return the value of width
     */
    public String getWidth()
    {
        return width;
    }

    /**
     * Sets the width
     * <p>You can use getWidth() to get the value of width</p>
     *
     * @param width width
     */
    public void setWidth(String width)
    {
        this.width = width;
    }

    /**
     * Gets the value of delete
     *
     * @return the value of delete
     */
    public String getDelete()
    {
        return delete;
    }

    /**
     * Sets the delete
     * <p>You can use getDelete() to get the value of delete</p>
     *
     * @param delete delete
     */
    public void setDelete(String delete)
    {
        this.delete = delete;
    }

    /**
     * Gets the value of username
     *
     * @return the value of username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Sets the username
     * <p>You can use getUsername() to get the value of username</p>
     *
     * @param username username
     */
    public void setUsername(String username)
    {
        this.username = username;
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

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this).add("filename", filename).add("url", url).add("size", size).add("height", height).add("width", width)
          .add("delete", delete).add("username", username).add("createTime", createTime).toString();
    }
}
