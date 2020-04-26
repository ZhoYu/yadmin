/**
 * <p>
 * 文件名称:    TradeDto
 * </p>
 */
package com.zhou.yadmin.tools.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 交易详情，按需应该存入数据库，这里存入数据库，仅供临时测试
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/19 20:01
 */
public class TradeDto implements Serializable
{
    private static final long serialVersionUID = 8221309427777793129L;

    /**
     * （必填）商品描述
     */
    @NotBlank
    private String body;

    /**
     * （必填）商品名称
     */
    @NotBlank
    private String subject;

    /**
     * （必填）商户订单号，应该由后台生成
     */
    @ApiModelProperty(hidden = true)
    private String outTradeNo;

    /**
     * （必填）第三方订单号
     */
    @ApiModelProperty(hidden = true)
    private String tradeNo;

    /**
     * （必填）价格
     */
    @NotBlank
    private String totalAmount;

    /**
     * 订单状态,已支付，未支付，作废
     */
    @ApiModelProperty(hidden = true)
    private String state;

    /**
     * 创建时间，存入数据库时需要
     */
    @ApiModelProperty(hidden = true)
    private LocalDateTime createTime;

    /**
     * 作废时间，存入数据库时需要
     */
    @ApiModelProperty(hidden = true)
    private Date cancelTime;

    /**
     * Gets the value of body
     *
     * @return the value of body
     */
    public String getBody()
    {
        return body;
    }

    /**
     * Sets the body
     * <p>You can use getBody() to get the value of body</p>
     *
     * @param body body
     */
    public void setBody(String body)
    {
        this.body = body;
    }

    /**
     * Gets the value of subject
     *
     * @return the value of subject
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     * Sets the subject
     * <p>You can use getSubject() to get the value of subject</p>
     *
     * @param subject subject
     */
    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    /**
     * Gets the value of outTradeNo
     *
     * @return the value of outTradeNo
     */
    public String getOutTradeNo()
    {
        return outTradeNo;
    }

    /**
     * Sets the outTradeNo
     * <p>You can use getOutTradeNo() to get the value of outTradeNo</p>
     *
     * @param outTradeNo outTradeNo
     */
    public void setOutTradeNo(String outTradeNo)
    {
        this.outTradeNo = outTradeNo;
    }

    /**
     * Gets the value of tradeNo
     *
     * @return the value of tradeNo
     */
    public String getTradeNo()
    {
        return tradeNo;
    }

    /**
     * Sets the tradeNo
     * <p>You can use getTradeNo() to get the value of tradeNo</p>
     *
     * @param tradeNo tradeNo
     */
    public void setTradeNo(String tradeNo)
    {
        this.tradeNo = tradeNo;
    }

    /**
     * Gets the value of totalAmount
     *
     * @return the value of totalAmount
     */
    public String getTotalAmount()
    {
        return totalAmount;
    }

    /**
     * Sets the totalAmount
     * <p>You can use getTotalAmount() to get the value of totalAmount</p>
     *
     * @param totalAmount totalAmount
     */
    public void setTotalAmount(String totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    /**
     * Gets the value of state
     *
     * @return the value of state
     */
    public String getState()
    {
        return state;
    }

    /**
     * Sets the state
     * <p>You can use getState() to get the value of state</p>
     *
     * @param state state
     */
    public void setState(String state)
    {
        this.state = state;
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

    /**
     * Gets the value of cancelTime
     *
     * @return the value of cancelTime
     */
    public Date getCancelTime()
    {
        return cancelTime;
    }

    /**
     * Sets the cancelTime
     * <p>You can use getCancelTime() to get the value of cancelTime</p>
     *
     * @param cancelTime cancelTime
     */
    public void setCancelTime(Date cancelTime)
    {
        this.cancelTime = cancelTime;
    }
}
