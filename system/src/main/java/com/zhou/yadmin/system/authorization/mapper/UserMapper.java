/**
 * <p>
 * 文件名称:    UserMapper
 * </p>
 */
package com.zhou.yadmin.system.authorization.mapper;

import com.zhou.yadmin.common.mapper.EntityMapper;
import com.zhou.yadmin.system.authorization.domain.User;
import com.zhou.yadmin.system.authorization.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:52
 */
@Mapper(componentModel = "spring", uses = {RoleMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends EntityMapper<UserDto, User>
{
}
