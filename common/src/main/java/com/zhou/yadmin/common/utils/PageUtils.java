/**
 * <p>
 * 文件名称:    PageUtils
 * </p>
 */
package com.zhou.yadmin.common.utils;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.data.domain.Page;

/**
 * <p>
 * 分页工具
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 21:19
 */
public final class PageUtils
{
    private PageUtils()
    {
    }

    /**
     * List 分页
     *
     * @param page 当前页面
     * @param size 每页数量
     * @param list 待分页的数据
     *
     * @return
     */
    public static <E> List<E> toPage(int page, int size, List<E> list)
    {
        int fromIndex = page * size;
        int toIndex = page * size + size;

        if (fromIndex > list.size())
        {
            return Lists.newArrayList();
        }
        else if (toIndex >= list.size())
        {
            return list.subList(fromIndex, list.size());
        }
        else
        {
            return list.subList(fromIndex, toIndex);
        }
    }

    /**
     * Page 数据处理，预防redis反序列化报错
     *
     * @param page
     *
     * @return
     */
    public static <E> Map<String, Object> toPage(Page<E> page)
    {
        Map<String, Object> map = Maps.newHashMap();
        map.put("content", page.getContent());
        map.put("totalElements", page.getTotalElements());
        return map;
    }

    /**
     * @param object
     * @param totalElements
     *
     * @return
     */
    public static Map<String, Object> toPage(Object object, Object totalElements)
    {
        Map<String, Object> map = Maps.newHashMap();
        map.put("content", object);
        map.put("totalElements", totalElements);
        return map;
    }
}
