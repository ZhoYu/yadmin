/**
 * 文件名称:    BaseOracleEntity<br />
 * 项目名称:    springexample<br />
 * 创建时间:    11:02<br />
 *
 * @since 2014/9/22 11:02
 * @author zhou
 */
package com.zhou.example.yadmin.common.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 抽象实体基类，提供统一的ID，和相关的基本功能方法<br />
 * 如果是如mysql这种自动生成主键的，请参考{@link BaseEntity}<br />
 * 子类只需要在类头上加 @SequenceGenerator(name="auto_id_seq", sequenceName="你的sequence名字")<br />
 * <p/>
 * 项目名称: springexample<br />
 * 版本: <br />
 * 作者: zhou<br />
 * 日期: 2014/9/22 11:02<br />
 *
 * @author zhou
 */
@MappedSuperclass
public class BaseOracleEntity<ID extends Serializable> extends AbstractEntity<ID>
{
    private static final long serialVersionUID = -884158559095441457L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auto_id_seq")
    private ID id;

    @Override
    public void setId(ID id)
    {
        this.id = id;
    }

    @Override
    public ID getId()
    {
        return this.id;
    }
}
