/**
 * <p>
 * 文件名称:    RoleController
 * </p>
 */
package com.zhou.yadmin.system.authorization.web.controller;

import com.zhou.yadmin.common.exception.BadRequestException;
import com.zhou.yadmin.logging.aop.annotation.Log;
import com.zhou.yadmin.system.authorization.domain.Role;
import com.zhou.yadmin.system.authorization.dto.RoleDto;
import com.zhou.yadmin.system.authorization.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/29 21:05
 */
@RestController
@RequestMapping("api")
public class RoleController
{
    @Autowired
    private RoleService roleService;

    private static final String ENTITY_NAME = "role";

    @GetMapping(value = "/roles/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ROLE_ALL','ROLE_SELECT')")
    public ResponseEntity getRoles(@PathVariable Long id)
    {
        return new ResponseEntity(roleService.findById(id), HttpStatus.OK);
    }

    /**
     * 返回全部的角色，新增用户时下拉选择
     *
     * @return
     */
    @GetMapping(value = "/roles/tree")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_SELECT','ROLE_ALL','USER_ALL','USER_SELECT','USER_EDIT')")
    public ResponseEntity getRoleTree()
    {
        return new ResponseEntity(roleService.getRoleTree(), HttpStatus.OK);
    }

    @Log("查询角色")
    @GetMapping(value = "/roles")
    @PreAuthorize("hasAnyRole('ADMIN','ROLE_ALL','ROLE_SELECT')")
    public ResponseEntity getRoles(RoleDto resources, Pageable pageable)
    {
        return new ResponseEntity(roleService.queryAll(resources, pageable), HttpStatus.OK);
    }

    @Log("新增角色")
    @PostMapping(value = "/roles")
    @PreAuthorize("hasAnyRole('ADMIN','ROLE_ALL','ROLE_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Role resources)
    {
        if (resources.getId() != null)
        {
            throw BadRequestException.newExceptionBySystem("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity(roleService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改角色")
    @PutMapping(value = "/roles")
    @PreAuthorize("hasAnyRole('ADMIN','ROLE_ALL','ROLE_EDIT')")
    public ResponseEntity update(@Validated @RequestBody Role resources)
    {
        if (resources.getId() == null)
        {
            throw BadRequestException.newExceptionBySystem(ENTITY_NAME + " ID Can not be empty");
        }
        roleService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除角色")
    @DeleteMapping(value = "/roles/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ROLE_ALL','ROLE_DELETE')")
    public ResponseEntity delete(@PathVariable Long id)
    {
        roleService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
