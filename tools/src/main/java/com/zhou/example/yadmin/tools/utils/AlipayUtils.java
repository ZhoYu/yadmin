/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.tools.utils.AlipayUtils
 * </p>
 */
package com.zhou.example.yadmin.tools.utils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

import com.alipay.api.internal.util.AlipaySignature;
import com.google.common.collect.Maps;
import com.zhou.example.yadmin.common.constants.CommonConstant;
import com.zhou.example.yadmin.common.constants.DateConstant;
import com.zhou.example.yadmin.tools.domain.AlipayConfig;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 支付宝工具类
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/19 21:55
 */
public final class AlipayUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AlipayUtils.class);

    private AlipayUtils()
    {
    }

    /**
     * 生成订单号
     *
     * @return
     */
    public static String getOrderCode()
    {
        return LocalDateTime.now().format(DateConstant.FORMATTER_DATE_TIME_THIN) + RandomStringUtils.randomNumeric(4);
    }

    /**
     * 校验签名
     *
     * @param request
     *
     * @return
     */
    public static boolean rsaCheck(HttpServletRequest request, AlipayConfig alipay)
    {
        /**
         *  获取支付宝POST过来反馈信息
         */
        Map<String, String> params = Maps.newHashMap();
        Map<String, String[]> requestParams = request.getParameterMap();
        requestParams.forEach((name, values) -> params.put(name, StringUtils.join(values, CommonConstant.COMMA_DELIMITER)));

        try
        {
            return AlipaySignature.rsaCheckV1(params, alipay.getPublicKey(), alipay.getCharset(), alipay.getSignType());
        }
        catch (Exception e)
        {
            LOGGER.error("签名验证失败", e);
            return false;
        }
    }
}
