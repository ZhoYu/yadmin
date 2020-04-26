/**
 * <p>
 * 文件名称:    SchedulerLogRepository
 * </p>
 */
package com.zhou.yadmin.system.scheduling.repository;

import com.zhou.yadmin.system.scheduling.domain.SchedulerLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/27 21:09
 */
@Repository
public interface SchedulerLogRepository extends JpaRepository<SchedulerLog, Long>, JpaSpecificationExecutor<SchedulerLog>
{
}
