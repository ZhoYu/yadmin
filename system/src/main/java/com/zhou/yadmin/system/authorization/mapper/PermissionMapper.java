/**
 * <p>
 * 文件名称:    PermissionMapper
 * </p>
 */
package com.zhou.yadmin.system.authorization.mapper;

import com.zhou.yadmin.common.mapper.EntityMapper;
import com.zhou.yadmin.system.authorization.domain.Permission;
import com.zhou.yadmin.system.authorization.dto.PermissionDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:53
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper extends EntityMapper<PermissionDto, Permission>
{
}
