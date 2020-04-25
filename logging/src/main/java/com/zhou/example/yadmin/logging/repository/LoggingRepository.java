/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.monitor.repository.LoggingRepository
 * </p>
 */
package com.zhou.example.yadmin.logging.repository;

import com.zhou.example.yadmin.logging.domain.Logging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 23:28
 */
@Repository
public interface LoggingRepository extends JpaRepository<Logging, Long>, JpaSpecificationExecutor<Logging>
{
    /**
     * 获取一个时间段的IP记录
     *
     * @param date1
     * @param date2
     *
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM (SELECT REQUEST_IP FROM T_LOG WHERE CREATE_TIME BETWEEN ?1 AND ?2 GROUP BY REQUEST_IP) AS S", nativeQuery = true)
    Long findIp(String date1, String date2);
}
