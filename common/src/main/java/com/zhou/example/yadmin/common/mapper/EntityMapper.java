/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.common.mapper.EntityMapper
 * </p>
 */
package com.zhou.example.yadmin.common.mapper;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 22:00
 */
public interface EntityMapper<D, E>
{
    /**
     * DTO转Entity
     *
     * @param dto
     *
     * @return
     */
    E toEntity(D dto);

    /**
     * Entity转DTO
     *
     * @param entity
     *
     * @return
     */
    D toDto(E entity);

    /**
     * DTO集合转Entity集合
     *
     * @param dtoList
     *
     * @return
     */
    List<E> toEntity(List<D> dtoList);

    /**
     * DTO集合转Entity集合
     *
     * @param dtoSet
     *
     * @return
     */
    Set<E> toEntity(Set<D> dtoSet);

    /**
     * Entity集合转DTO集合
     *
     * @param entityList
     *
     * @return
     */
    List<D> toDto(List<E> entityList);
}
