/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.tools.service.AlipayService
 * </p>
 */
package com.zhou.example.yadmin.tools.service;

import com.zhou.example.yadmin.tools.domain.AlipayConfig;
import com.zhou.example.yadmin.tools.dto.TradeDto;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/19 21:43
 */
public interface AlipayService
{
    /**
     * 处理来自PC的交易请求
     *
     * @param alipay
     * @param trade
     *
     * @return
     *
     * @throws Exception
     */
    String toPayAsPC(AlipayConfig alipay, TradeDto trade) throws Exception;

    /**
     * 处理来自手机网页的交易请求
     *
     * @param alipay
     * @param trade
     *
     * @return
     *
     * @throws Exception
     */
    String toPayAsWeb(AlipayConfig alipay, TradeDto trade) throws Exception;

    /**
     * 查询配置
     *
     * @return
     */
    AlipayConfig find();

    /**
     * 更新配置
     *
     * @param alipayConfig
     *
     * @return
     */
    AlipayConfig update(AlipayConfig alipayConfig);
}
