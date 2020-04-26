/**
 * <p>
 * 文件名称:    SchedulerLogServiceImpl
 * </p>
 */
package com.zhou.yadmin.system.scheduling.service.impl;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.zhou.yadmin.common.constants.CommonConstant;
import com.zhou.yadmin.common.utils.PageUtils;
import com.zhou.yadmin.system.scheduling.domain.SchedulerLog;
import com.zhou.yadmin.system.scheduling.repository.SchedulerLogRepository;
import com.zhou.yadmin.system.scheduling.service.SchedulerLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/30 19:58
 */
@Service
public class SchedulerLogServiceImpl implements SchedulerLogService
{
    @Autowired
    private SchedulerLogRepository schedulerLogRepository;

    /**
     * 模糊分页查询
     *
     * @param schedulerLog
     * @param pageable
     *
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Map<String, Object> queryAll(SchedulerLog schedulerLog, Pageable pageable)
    {
        return PageUtils.toPage(schedulerLogRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = Lists.newArrayListWithCapacity(2);
            if (StringUtils.isNotBlank(schedulerLog.getJobName()))
            {
                predicateList.add(criteriaBuilder.like(root.get("jobName").as(String.class),
                  CommonConstant.PERCENT_SIGN_DELIMITER + schedulerLog.getJobName() + CommonConstant.PERCENT_SIGN_DELIMITER));
            }
            if (schedulerLog.getSuccess() != null)
            {
                predicateList.add(criteriaBuilder.equal(root.get("success").as(Boolean.class), schedulerLog.getSuccess()));
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        }, pageable));
    }
}
