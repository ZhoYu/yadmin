/**
 * <p>
 * 文件名称:    VisitsServiceImpl
 * </p>
 */
package com.zhou.yadmin.system.monitor.service.impl;

import javax.servlet.http.HttpServletRequest;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhou.yadmin.common.plugin.AbstractBaseComponent;
import com.zhou.yadmin.logging.service.LoggingService;
import com.zhou.yadmin.system.monitor.domain.Visits;
import com.zhou.yadmin.system.monitor.repository.VisitsRepository;
import com.zhou.yadmin.system.monitor.service.VisitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 21:27
 */
@Service
public class VisitsServiceImpl extends AbstractBaseComponent implements VisitsService
{
    @Autowired
    private VisitsRepository visitsRepository;

    @Autowired
    private LoggingService loggingService;

    /**
     * 提供给定时任务，每天0点执行
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void save()
    {
        LocalDate now = LocalDate.now();
        Visits visits = visitsRepository.findByDate(now.toString());
        if (visits == null)
        {
            visits = new Visits();
            visits.setWeekDay(DayOfWeek.from(now).getDisplayName(TextStyle.SHORT, Locale.CHINA));
            visits.setPvCounts(1L);
            visits.setIpCounts(1L);
            visits.setDate(now.toString());
            visitsRepository.save(visits);
        }
    }

    /**
     * 新增记录
     *
     * @param request
     */
    @Async
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void count(HttpServletRequest request)
    {
        LocalDate now = LocalDate.now();
        Visits visits = visitsRepository.findByDate(now.toString());
        if (visits != null)
        {
            visits.setPvCounts(visits.getPvCounts() + 1);
            Long ipCounts = loggingService.countRequestIpByDate(now, now.plusDays(1));
            visits.setIpCounts(ipCounts);
            visitsRepository.save(visits);
        }
        else
        {
            save();
        }
    }

    /**
     * 获取数据
     *
     * @return
     */
    @Override
    public Map<String, Object> get()
    {
        LocalDate now = LocalDate.now();
        Visits visits = visitsRepository.findByDate(now.toString());
        Map<String, Object> data = list(now);
        data.put("newVisits", visits.getPvCounts());
        data.put("newIp", visits.getIpCounts());
        return data;
    }

    /**
     * 根据时间获取一段时间的数据
     *
     * @param localDate
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> list(LocalDate localDate)
    {
        Map<String, Object> data = Maps.newHashMap();
        if (localDate != null)
        {
            List<Visits> visitsList = visitsRepository.findAllVisits(localDate.minusDays(6).toString(), localDate.plusDays(1).toString());
            if (!CollectionUtils.isEmpty(visitsList))
            {
                long recentVisits = 0L, recentIp = 0L;
                for (Visits visits : visitsList)
                {
                    recentVisits += visits.getPvCounts();
                    recentIp += visits.getIpCounts();
                }
                data.put("recentVisits", recentVisits);
                data.put("recentIp", recentIp);
                return data;
            }
        }
        return data;
    }

    /**
     * getChartData
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getChartData()
    {
        LocalDate now = LocalDate.now();
        Map<String, Object> data = Maps.newHashMap();
        List<Visits> visitsList = visitsRepository.findAllVisits(now.minusDays(6).toString(), now.plusDays(1).toString());
        List<String> weekDayList = Lists.newArrayList();
        List<Long> pvCountsList = Lists.newArrayList();
        List<Long> ipCountsList = Lists.newArrayList();
        for (Visits visits : visitsList)
        {
            weekDayList.add(visits.getWeekDay());
            pvCountsList.add(visits.getPvCounts());
            ipCountsList.add(visits.getIpCounts());
        }
        data.put("weekDays", weekDayList);
        data.put("visitsData", pvCountsList);
        data.put("ipData", ipCountsList);
        return data;
    }
}
