/**
 * <p>
 * 文件名称:    AlipayServiceImpl
 * </p>
 */
package com.zhou.yadmin.tools.service.impl;

import java.util.Optional;

import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayRequest;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.zhou.yadmin.common.exception.BadRequestException;
import com.zhou.yadmin.tools.domain.AlipayConfig;
import com.zhou.yadmin.tools.dto.TradeDto;
import com.zhou.yadmin.tools.repository.AlipayRepository;
import com.zhou.yadmin.tools.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/19 21:49
 */
@Service
@CacheConfig(cacheNames = "alipay")
public class AlipayServiceImpl implements AlipayService
{
    @Autowired
    private AlipayRepository alipayRepository;

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
    @Override
    public String toPayAsPC(AlipayConfig alipay, TradeDto trade) throws Exception
    {
        // 处理交易请求 -- 电脑网页版
        return toPay(alipay, trade, new AlipayTradePagePayRequest());
    }

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
    @Override
    public String toPayAsWeb(AlipayConfig alipay, TradeDto trade) throws Exception
    {
        // 处理交易请求 -- 手机网页版
        return toPay(alipay, trade, new AlipayTradeWapPayRequest());
    }

    /**
     * 查询配置
     *
     * @return
     */
    @Override
    @Cacheable(key = "'1'")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public AlipayConfig find()
    {
        Optional<AlipayConfig> alipayConfig = alipayRepository.findById(1L);
        return alipayConfig.orElseGet(AlipayConfig::new);
    }

    /**
     * 更新配置
     *
     * @param alipayConfig
     *
     * @return
     */
    @Override
    @CachePut(key = "'1'")
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public AlipayConfig update(AlipayConfig alipayConfig)
    {
        return alipayRepository.saveAndFlush(alipayConfig);
    }

    /**
     * 处理交易请求
     *
     * @param alipay
     * @param trade
     * @param request
     *
     * @return
     */
    private String toPay(AlipayConfig alipay, TradeDto trade, AlipayRequest<? extends AlipayResponse> request) throws Exception
    {
        checkConfig(alipay);
        checkMoney(trade);

        AlipayClient alipayClient =
          new DefaultAlipayClient(alipay.getGatewayUrl(), alipay.getAppID(), alipay.getPrivateKey(), alipay.getFormat(), alipay.getCharset(),
            alipay.getPublicKey(), alipay.getSignType());

        // 订单完成后返回的页面和异步通知地址
        request.setReturnUrl(alipay.getReturnUrl());
        request.setNotifyUrl(alipay.getNotifyUrl());
        // 填充订单参数
        if (request instanceof AlipayTradePagePayRequest)
        {
            ((AlipayTradePagePayRequest) request).setBizContent(builderBizContent(alipay, trade));//填充业务参数
        }
        else if (request instanceof AlipayTradeWapPayRequest)
        {
            ((AlipayTradeWapPayRequest) request).setBizContent(builderBizContent(alipay, trade)); //填充业务参数
        }
        // 调用SDK生成表单 通过GET方式，口可以获取url
        return alipayClient.pageExecute(request, HttpMethod.GET.name()).getBody();
    }

    private void checkMoney(TradeDto trade)
    {
        double money = Double.parseDouble(trade.getTotalAmount());
        if (money <= 0 || money >= 5000)
        {
            throw BadRequestException.newExceptionByTools("测试金额过大");
        }
    }

    private void checkConfig(AlipayConfig alipay)
    {
        if (alipay.getId() == null)
        {
            throw BadRequestException.newExceptionByTools("请先添加相应配置，再操作");
        }
    }

    private String builderBizContent(AlipayConfig alipay, TradeDto trade)
    {
        StringBuilder bizContent = new StringBuilder();
        bizContent.append('{');
        bizContent.append("    \"out_trade_no\":\"").append(trade.getOutTradeNo()).append("\",");
        bizContent.append("    \"product_code\":\"FAST_INSTANT_TRADE_PAY\",");
        bizContent.append("    \"total_amount\":").append(trade.getTotalAmount()).append(',');
        bizContent.append("    \"subject\":\"").append(trade.getSubject()).append("\",");
        bizContent.append("    \"body\":\"").append(trade.getBody()).append("\",");
        bizContent.append("    \"extend_params\":{");
        bizContent.append("    \"sys_service_provider_id\":\"").append(alipay.getSysServiceProviderId()).append("\"");
        bizContent.append("     } ");
        bizContent.append("}");
        return bizContent.toString();
    }
}
