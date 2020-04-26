/**
 * <p>
 * 文件名称:    SchedulerJobController
 * </p>
 */
package com.zhou.yadmin.system.scheduling.web.controller;

import com.zhou.yadmin.common.exception.BadRequestException;
import com.zhou.yadmin.logging.aop.annotation.Log;
import com.zhou.yadmin.system.scheduling.domain.SchedulerJob;
import com.zhou.yadmin.system.scheduling.domain.SchedulerLog;
import com.zhou.yadmin.system.scheduling.service.SchedulerJobService;
import com.zhou.yadmin.system.scheduling.service.SchedulerLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/31 19:16
 */
@RestController
@RequestMapping("api/job")
public class SchedulerJobController
{
    private static final String ENTITY_NAME = "SchedulerJob";

    @Autowired
    private SchedulerJobService schedulerJobService;

    @Autowired
    private SchedulerLogService schedulerLogService;

    @Log("查询定时任务")
    @GetMapping(value = "/all")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_SELECT')")
    public ResponseEntity getJobs(SchedulerJob resources, Pageable pageable)
    {
        return new ResponseEntity(schedulerJobService.queryAll(resources, pageable), HttpStatus.OK);
    }

    /**
     * 查询定时任务日志
     *
     * @param resources
     * @param pageable
     *
     * @return
     */
    @GetMapping(value = "logs")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_SELECT')")
    public ResponseEntity getJobLogs(SchedulerLog resources, Pageable pageable)
    {
        return new ResponseEntity(schedulerLogService.queryAll(resources, pageable), HttpStatus.OK);
    }

    @Log("新增定时任务")
    @PostMapping(value = "/")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_CREATE')")
    public ResponseEntity create(@Validated @RequestBody SchedulerJob resources)
    {
        if (resources.getId() != null)
        {
            throw BadRequestException.newExceptionBySystem("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity(schedulerJobService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改定时任务")
    @PutMapping(value = "/")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_EDIT')")
    public ResponseEntity update(@Validated @RequestBody SchedulerJob resources)
    {
        if (resources.getId() == null)
        {
            throw BadRequestException.newExceptionBySystem(ENTITY_NAME + " ID Can not be empty");
        }
        schedulerJobService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("更改定时任务状态")
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_EDIT')")
    public ResponseEntity updatePaused(@PathVariable Long id)
    {
        schedulerJobService.updatePaused(schedulerJobService.findById(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("执行定时任务")
    @PutMapping(value = "/exec/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_EDIT')")
    public ResponseEntity execution(@PathVariable Long id)
    {
        schedulerJobService.execution(schedulerJobService.findById(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除定时任务")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_DELETE')")
    public ResponseEntity delete(@PathVariable Long id)
    {
        schedulerJobService.delete(schedulerJobService.findById(id));
        return new ResponseEntity(HttpStatus.OK);
    }
}
