/**
 * <p>
 * 文件名称:    com.zhou.yadmin.system.monitor.web.controller.LoggingController
 * </p>
 */
package com.zhou.yadmin.logging.web.controller;

import com.zhou.yadmin.logging.domain.Logging;
import com.zhou.yadmin.logging.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 20:32
 */
@RestController
@RequestMapping("api/logs")
public class LoggingController
{
    @Autowired
    private LoggingService loggingService;

    @GetMapping(value = "")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity getLogs(Logging logging, Pageable pageable)
    {
        logging.setLogType("INFO");
        return new ResponseEntity(loggingService.queryAll(logging, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "error")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity getErrorLogs(Logging logging, Pageable pageable)
    {
        logging.setLogType("ERROR");
        return new ResponseEntity(loggingService.queryAll(logging, pageable), HttpStatus.OK);
    }
}
