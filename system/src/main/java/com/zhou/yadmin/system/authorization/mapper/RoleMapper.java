/**
 * <p>
 * 文件名称:    RoleMapper
 * </p>
 */
package com.zhou.yadmin.system.authorization.mapper;

import com.zhou.yadmin.common.mapper.EntityMapper;
import com.zhou.yadmin.system.authorization.domain.Role;
import com.zhou.yadmin.system.authorization.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:51
 */
@Mapper(componentModel = "spring", uses = {PermissionMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends EntityMapper<RoleDto, Role>
{
}
