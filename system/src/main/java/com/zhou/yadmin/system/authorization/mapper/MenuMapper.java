/**
 * <p>
 * 文件名称:    MenuMapper
 * </p>
 */
package com.zhou.yadmin.system.authorization.mapper;

import com.zhou.yadmin.common.mapper.EntityMapper;
import com.zhou.yadmin.system.authorization.domain.Menu;
import com.zhou.yadmin.system.authorization.dto.MenuDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:57
 */
@Mapper(componentModel = "spring", uses = {RoleMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends EntityMapper<MenuDto, Menu>
{
}
