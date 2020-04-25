/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.tools.domain.EmailConfig
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
 * 邮件配置类，数据存覆盖式存入数据存
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/15 23:18
 */
@Entity
@Table(name = "t_email_config")
public class EmailConfig extends BaseEntity<Long>
{
    private static final long serialVersionUID = -7968441784951920983L;

    /**
     * 邮件服务器 SMTP 地址
     */
    @NotBlank
    private String host;

    /**
     * 邮件服务器 SMTP 端口
     */
    @NotBlank
    private String port;

    /**
     * 发件者用户名，默认为发件人邮箱前缀
     */
    @NotBlank
    private String user;

    @NotBlank
    private String pass;

    /**
     * 发件人
     */
    @NotBlank
    @Column(name = "from_user")
    private String fromUser;

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
     * Gets the value of port
     *
     * @return the value of port
     */
    public String getPort()
    {
        return port;
    }

    /**
     * Sets the port
     * <p>You can use getPort() to get the value of port</p>
     *
     * @param port port
     */
    public void setPort(String port)
    {
        this.port = port;
    }

    /**
     * Gets the value of user
     *
     * @return the value of user
     */
    public String getUser()
    {
        return user;
    }

    /**
     * Sets the user
     * <p>You can use getUser() to get the value of user</p>
     *
     * @param user user
     */
    public void setUser(String user)
    {
        this.user = user;
    }

    /**
     * Gets the value of pass
     *
     * @return the value of pass
     */
    public String getPass()
    {
        return pass;
    }

    /**
     * Sets the pass
     * <p>You can use getPass() to get the value of pass</p>
     *
     * @param pass pass
     */
    public void setPass(String pass)
    {
        this.pass = pass;
    }

    /**
     * Gets the value of fromUser
     *
     * @return the value of fromUser
     */
    public String getFromUser()
    {
        return fromUser;
    }

    /**
     * Sets the fromUser
     * <p>You can use getFromUser() to get the value of fromUser</p>
     *
     * @param fromUser fromUser
     */
    public void setFromUser(String fromUser)
    {
        this.fromUser = fromUser;
    }
}
