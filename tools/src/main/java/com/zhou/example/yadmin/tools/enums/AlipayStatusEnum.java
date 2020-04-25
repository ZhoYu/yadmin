/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.tools.enums.AlipayStatusEnum
 * </p>
 */
package com.zhou.example.yadmin.tools.enums;

/**
 * <p>
 * 支付状态
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/19 21:52
 */
public enum AlipayStatusEnum
{
    /**
     * 交易成功
     */
    FINISHED("交易成功", "TRADE_FINISHED"),
    /**
     * 支付成功
     */
    SUCCESS("支付成功", "TRADE_SUCCESS"),
    /**
     * 交易创建
     */
    BUYER_PAY("交易创建", "WAIT_BUYER_PAY"),

    /**
     * 交易关闭
     */
    CLOSED("交易关闭", "TRADE_CLOSED");

    private String name;
    private String value;

    AlipayStatusEnum(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

    /**
     * Gets the value of name
     *
     * @return the value of name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the value of value
     *
     * @return the value of value
     */
    public String getValue()
    {
        return value;
    }
}
