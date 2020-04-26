/**
 * 文件名称:    AbstractEntity<br />
 * 项目名称:    springexample<br />
 * 创建时间:    11:01<br />
 *
 * @since 2014/9/22 11:01
 * @author zhou
 */
package com.zhou.yadmin.common.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.domain.Persistable;

/**
 * 抽象实体基类，如果主键是数据库端自动生成 请使用{@link BaseEntity}，如果是Oracle 请使用{@link BaseOracleEntity}<br />
 * <p/>
 * 项目名称: springexample<br />
 * 版本: <br />
 * 作者: zhou<br />
 * 日期: 2014/9/22 11:01<br />
 *
 * @author zhou
 */
public abstract class AbstractEntity<ID extends Serializable> implements Persistable<ID>, Serializable
{

    /**
     * Sets the id of the entity.
     *
     * @param id the id to set
     */
    public abstract void setId(final ID id);

    @Override
    public abstract ID getId();

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.data.domain.Persistable#isNew()

     */
    @JsonIgnore
    @Override
    public boolean isNew()
    {
        return null == getId();
    }

    @Override
    public int hashCode()
    {
        int hashCode = 0;
        hashCode = ((null == getId()) ? 0 : getId().hashCode() * 31);
        return hashCode;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (null == obj)
        {
            return false;
        }
        if (!(obj instanceof AbstractEntity))
        {
            return false;
        }

        AbstractEntity that = (AbstractEntity) obj;

        if (this.getId() != null ? !this.getId().equals(that.getId()) : that.getId() != null)
        {
            return false;
        }

        return true;
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.toString(this);
    }
}
