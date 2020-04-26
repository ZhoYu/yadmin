/**
 * 文件名称:    BaseEntity<br />
 * 项目名称:    springexample<br />
 * 创建时间:    10:56<br />
 *
 * @since 2014/9/22 10:56
 * @author zhou
 */
package com.zhou.yadmin.common.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

/**
 * 抽象实体基类，提供统一的ID，和相关的基本功能方法, 这里的id是长度为32的uuid字符串<br />
 * <p/>
 * 项目名称: springexample<br />
 * 版本: <br />
 * 作者: zhou<br />
 * 日期: 2014/9/22 10:56<br />
 *
 * @author zhou
 */
@MappedSuperclass
public abstract class BaseUUIDEntity extends AbstractEntity<String>
{
    private static final long serialVersionUID = 3414176427476611181L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "assigned")
    @Column(length = 35, nullable = false, unique = true)
    private String id;

    @Override
    public void setId(String id)
    {
        this.id = id;
    }

    @Override
    public String getId()
    {
        return id;
    }

    @Override
    public boolean isNew()
    {
        return StringUtils.isBlank(id);
    }
}
