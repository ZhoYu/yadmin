/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.tools.domain.AlipayConfig
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
 * 支付宝配置类
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/19 19:10
 */
@Entity
@Table(name = "t_alipay_config")
public class AlipayConfig extends BaseEntity<Long>
{
    private static final long serialVersionUID = 5504637957217351194L;

    /**
     * 应用ID,APPID，收款账号既是APPID对应支付宝账号
     */
    @NotBlank
    @Column(name = "app_id")
    private String appID;

    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
     */
    @NotBlank
    @Column(name = "private_key", columnDefinition = "text")
    private String privateKey;

    /**
     * 支付宝公钥
     */
    @NotBlank
    @Column(name = "public_key", columnDefinition = "text")
    private String publicKey;

    /**
     * 签名方式，固定格式
     */
    @Column(name = "sign_type")
    private String signType="RSA2";

    /**
     * 支付宝开放安全地址，一般不会变
     */
    @Column(name = "gateway_url")
    private String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    /**
     * 编码，固定格式
     */
    private String charset= "utf-8";

    /**
     * 异步通知地址
     */
    @NotBlank
    @Column(name = "notify_url")
    private String notifyUrl;

    /**
     * 订单完成后返回的页面
     */
    @NotBlank
    @Column(name = "return_url")
    private String returnUrl;

    /**
     * 类型，固定格式
     */
    private String format="JSON";

    /**
     * 商户号
     */
    @NotBlank
    @Column(name = "sys_service_provider_id")
    private String sysServiceProviderId;

    /**
     * Gets the value of appID
     *
     * @return the value of appID
     */
    public String getAppID()
    {
        return appID;
    }

    /**
     * Sets the appID
     * <p>You can use getAppID() to get the value of appID</p>
     *
     * @param appID appID
     */
    public void setAppID(String appID)
    {
        this.appID = appID;
    }

    /**
     * Gets the value of privateKey
     *
     * @return the value of privateKey
     */
    public String getPrivateKey()
    {
        return privateKey;
    }

    /**
     * Sets the privateKey
     * <p>You can use getPrivateKey() to get the value of privateKey</p>
     *
     * @param privateKey privateKey
     */
    public void setPrivateKey(String privateKey)
    {
        this.privateKey = privateKey;
    }

    /**
     * Gets the value of publicKey
     *
     * @return the value of publicKey
     */
    public String getPublicKey()
    {
        return publicKey;
    }

    /**
     * Sets the publicKey
     * <p>You can use getPublicKey() to get the value of publicKey</p>
     *
     * @param publicKey publicKey
     */
    public void setPublicKey(String publicKey)
    {
        this.publicKey = publicKey;
    }

    /**
     * Gets the value of signType
     *
     * @return the value of signType
     */
    public String getSignType()
    {
        return signType;
    }

    /**
     * Sets the signType
     * <p>You can use getSignType() to get the value of signType</p>
     *
     * @param signType signType
     */
    public void setSignType(String signType)
    {
        this.signType = signType;
    }

    /**
     * Gets the value of gatewayUrl
     *
     * @return the value of gatewayUrl
     */
    public String getGatewayUrl()
    {
        return gatewayUrl;
    }

    /**
     * Sets the gatewayUrl
     * <p>You can use getGatewayUrl() to get the value of gatewayUrl</p>
     *
     * @param gatewayUrl gatewayUrl
     */
    public void setGatewayUrl(String gatewayUrl)
    {
        this.gatewayUrl = gatewayUrl;
    }

    /**
     * Gets the value of charset
     *
     * @return the value of charset
     */
    public String getCharset()
    {
        return charset;
    }

    /**
     * Sets the charset
     * <p>You can use getCharset() to get the value of charset</p>
     *
     * @param charset charset
     */
    public void setCharset(String charset)
    {
        this.charset = charset;
    }

    /**
     * Gets the value of notifyUrl
     *
     * @return the value of notifyUrl
     */
    public String getNotifyUrl()
    {
        return notifyUrl;
    }

    /**
     * Sets the notifyUrl
     * <p>You can use getNotifyUrl() to get the value of notifyUrl</p>
     *
     * @param notifyUrl notifyUrl
     */
    public void setNotifyUrl(String notifyUrl)
    {
        this.notifyUrl = notifyUrl;
    }

    /**
     * Gets the value of returnUrl
     *
     * @return the value of returnUrl
     */
    public String getReturnUrl()
    {
        return returnUrl;
    }

    /**
     * Sets the returnUrl
     * <p>You can use getReturnUrl() to get the value of returnUrl</p>
     *
     * @param returnUrl returnUrl
     */
    public void setReturnUrl(String returnUrl)
    {
        this.returnUrl = returnUrl;
    }

    /**
     * Gets the value of format
     *
     * @return the value of format
     */
    public String getFormat()
    {
        return format;
    }

    /**
     * Sets the format
     * <p>You can use getFormat() to get the value of format</p>
     *
     * @param format format
     */
    public void setFormat(String format)
    {
        this.format = format;
    }

    /**
     * Gets the value of sysServiceProviderId
     *
     * @return the value of sysServiceProviderId
     */
    public String getSysServiceProviderId()
    {
        return sysServiceProviderId;
    }

    /**
     * Sets the sysServiceProviderId
     * <p>You can use getSysServiceProviderId() to get the value of sysServiceProviderId</p>
     *
     * @param sysServiceProviderId sysServiceProviderId
     */
    public void setSysServiceProviderId(String sysServiceProviderId)
    {
        this.sysServiceProviderId = sysServiceProviderId;
    }
}
