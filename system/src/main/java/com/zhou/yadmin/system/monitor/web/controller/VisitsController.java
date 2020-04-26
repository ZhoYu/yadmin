/**
 * <p>
 * 文件名称:    VisitsController
 * </p>
 */
package com.zhou.yadmin.system.monitor.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.zhou.yadmin.system.monitor.service.VisitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 20:27
 */
@RestController
@RequestMapping("api/visits")
public class VisitsController
{
    @Autowired
    private VisitsService visitsService;

    @PostMapping(value = "")
    public ResponseEntity create(HttpServletRequest request)
    {
        visitsService.count(request);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value = "")
    public ResponseEntity get()
    {
        return new ResponseEntity(visitsService.get(), HttpStatus.OK);
    }

    @GetMapping(value = "chartData")
    public ResponseEntity getChartData()
    {
        return new ResponseEntity(visitsService.getChartData(), HttpStatus.OK);
    }
}
