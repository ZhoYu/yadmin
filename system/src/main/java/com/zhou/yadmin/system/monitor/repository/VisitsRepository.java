/**
 * <p>
 * 文件名称:    VisitsRepository
 * </p>
 */
package com.zhou.yadmin.system.monitor.repository;

import java.util.List;

import com.zhou.yadmin.system.monitor.domain.Visits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 23:22
 */
@Repository
public interface VisitsRepository extends JpaRepository<Visits, Long>
{
    /**
     * findByDate
     *
     * @param date
     *
     * @return
     */
    Visits findByDate(String date);

    /**
     * 获得一个时间段的记录
     *
     * @param date1
     * @param date2
     *
     * @return
     */
    @Query(value = "SELECT * FROM T_VISITS WHERE CREATE_TIME BETWEEN ?1 AND ?2", nativeQuery = true)
    List<Visits> findAllVisits(String date1, String date2);
}
