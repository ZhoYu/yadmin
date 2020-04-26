/**
 * 文件名称:    BaseEntity<br />
 * 项目名称:    springexample<br />
 * 创建时间:    10:56<br />
 *
 * @author zhou
 * @since 2014/9/22 10:56
 */
package com.zhou.yadmin.common.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

import org.hibernate.annotations.GenericGenerator;

/**
 * 抽象实体基类，提供统一的ID，和相关的基本功能方法, 如果是oracle请参考{@link BaseOracleEntity}<br />
 * <p/>
 * 项目名称: springexample<br />
 * 版本: <br />
 * 作者: zhou<br />
 * 日期: 2014/9/22 10:56<br />
 *
 * @author zhou
 */
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> extends AbstractEntity<ID>
{
    private static final long serialVersionUID = -3283471008174179814L;

    private static final String GENERATOR_NAME_NATIVE = "native";

    /**
     * mysql 使用 auto 时由于不支持 sequence hibernate5 会自动使用 TABLE 而不是 IDENTITY
     * 因此使用mysql数据库时要想使用数据库的自动递增来当主键需要指明 IDENTITY
     * 如果想要兼容其他数据库, 那么需要按以下的方式来设定 strategy
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = GENERATOR_NAME_NATIVE)
    @GenericGenerator(name = GENERATOR_NAME_NATIVE, strategy = GENERATOR_NAME_NATIVE)
    private ID id;

    @Override
    public void setId(ID id)
    {
        this.id = id;
    }

    @Override
    public ID getId()
    {
        return id;
    }
}
