/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.monitor.service.impl.LoggingServiceImpl
 * </p>
 */
package com.zhou.example.yadmin.logging.service.impl;

import javax.persistence.criteria.Predicate;
import java.util.List;

import com.google.common.collect.Lists;
import com.zhou.example.yadmin.logging.domain.Logging;
import com.zhou.example.yadmin.logging.repository.LoggingRepository;
import com.zhou.example.yadmin.logging.service.LoggingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
 * @since 2020/2/27 22:59
 */
@Service
public class LoggingServiceImpl implements LoggingService
{

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private LoggingRepository loggingRepository;

    /**
     * 新增日志
     *
     * @param logging
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void save(Logging logging)
    {
        loggingRepository.save(logging);
    }

    /**
     * 分页查询所有的日志对象
     *
     * @param logging
     * @param pageable
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Logging> queryAll(Logging logging, Pageable pageable)
    {
        return loggingRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> list = Lists.newArrayList();

            if (StringUtils.isNotBlank(logging.getUsername()))
            {
                list.add(criteriaBuilder.like(root.get("username").as(String.class), "%" + logging.getUsername() + "%"));
            }

            if (StringUtils.isNotBlank(logging.getLogType()))
            {
                list.add(criteriaBuilder.equal(root.get("logType").as(String.class), logging.getLogType()));
            }

            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        }, pageable);
    }
}
