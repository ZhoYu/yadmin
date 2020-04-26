/**
 * <p>
 * 文件名称:    SchedulerJobRepository
 * </p>
 */
package com.zhou.yadmin.system.scheduling.repository;

import java.util.List;

import com.zhou.yadmin.system.scheduling.domain.SchedulerJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/27 21:04
 */
@Repository
public interface SchedulerJobRepository extends JpaRepository<SchedulerJob, Long>, JpaSpecificationExecutor<SchedulerJob>
{
    /**
     * 更新状态
     *
     * @param id
     */
    @Modifying
    @Query(value = "update t_scheduler_job set paused = true where id = ?1", nativeQuery = true)
    void updatePaused(Long id);

    /**
     * 查询不是启用的任务
     *
     * @return
     */
    List<SchedulerJob> findByPausedIsFalse();
}
