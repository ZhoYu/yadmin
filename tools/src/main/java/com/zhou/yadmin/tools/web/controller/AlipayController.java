/**
 * <p>
 * 文件名称:    AlipayController
 * </p>
 */
package com.zhou.yadmin.tools.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.zhou.yadmin.common.plugin.AbstractBaseComponent;
import com.zhou.yadmin.logging.aop.annotation.Log;
import com.zhou.yadmin.tools.domain.AlipayConfig;
import com.zhou.yadmin.tools.dto.TradeDto;
import com.zhou.yadmin.tools.enums.AlipayStatusEnum;
import com.zhou.yadmin.tools.service.AlipayService;
import com.zhou.yadmin.tools.utils.AlipayUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/19 22:29
 */
@RestController
@RequestMapping("api/alipay")
public class AlipayController extends AbstractBaseComponent
{
    @Autowired
    private AlipayService alipayService;

    @GetMapping(value = "")
    public ResponseEntity get()
    {
        return new ResponseEntity(alipayService.find(), HttpStatus.OK);
    }

    @Log("配置支付宝")
    @PutMapping(value = "")
    public ResponseEntity payConfig(@Validated @RequestBody AlipayConfig alipayConfig)
    {
        alipayConfig.setId(1L);
        alipayService.update(alipayConfig);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("支付宝PC网页支付")
    @ApiOperation(value = "PC网页支付")
    @PostMapping(value = "/toPayAsPC")
    public ResponseEntity toPayAsPC(@Validated @RequestBody TradeDto trade) throws Exception
    {
        logger.debug("REST request to toPayAsPC Trade : {}", trade);
        AlipayConfig alipay = alipayService.find();
        trade.setOutTradeNo(AlipayUtils.getOrderCode());
        String payUrl = alipayService.toPayAsPC(alipay, trade);
        return ResponseEntity.ok(payUrl);
    }

    @Log("支付宝手机网页支付")
    @ApiOperation(value = "手机网页支付")
    @PostMapping(value = "/toPayAsWeb")
    public ResponseEntity toPayAsWeb(@Validated @RequestBody TradeDto trade) throws Exception
    {
        logger.debug("REST request to toPayAsWeb Trade : {}", trade);
        AlipayConfig alipay = alipayService.find();
        trade.setOutTradeNo(AlipayUtils.getOrderCode());
        String payUrl = alipayService.toPayAsWeb(alipay, trade);
        return ResponseEntity.ok(payUrl);
    }

    @ApiIgnore
    @GetMapping("/return")
    @ApiOperation(value = "支付之后跳转的链接")
    public ResponseEntity returnPage(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        AlipayConfig alipay = alipayService.find();
        response.setContentType("text/html;charset=" + alipay.getCharset());
        //内容验签，防止黑客篡改参数
        if (AlipayUtils.rsaCheck(request, alipay))
        {
            //商户订单号
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            logger.debug("商户订单号{}  第三方交易号{}", outTradeNo, tradeNo);
            // 根据业务需要返回数据，这里统一返回OK
            return new ResponseEntity("payment successful", HttpStatus.OK);
        }
        else
        {
            // 根据业务需要返回数据
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiIgnore
    @RequestMapping("/notify")
    @ApiOperation(value = "支付异步通知(要公网访问)，接收异步通知，检查通知内容app_id、out_trade_no、total_amount是否与请求中的一致，根据trade_status进行后续业务处理")
    public ResponseEntity notify(HttpServletRequest request) throws Exception
    {
        AlipayConfig alipay = alipayService.find();
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder notifyBuild = new StringBuilder("/****************************** pay notify ******************************/\n");
        parameterMap.forEach((key, value) -> notifyBuild.append(key).append('=').append(value[0]).append('\n'));
        //内容验签，防止黑客篡改参数
        if (AlipayUtils.rsaCheck(request, alipay))
        {
            //交易状态
            String tradeStatus = new String(request.getParameter("trade_status").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            // 商户订单号
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //付款金额
            String totalAmount = new String(request.getParameter("total_amount").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //验证
            if (tradeStatus.equals(AlipayStatusEnum.SUCCESS.getValue()) || tradeStatus.equals(AlipayStatusEnum.FINISHED.getValue()))
            {
                /**
                 * 验证通过后应该根据业务需要处理订单
                 */
            }
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
