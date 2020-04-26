/**
 * <p>
 * 文件名称:    MenuController
 * </p>
 */
package com.zhou.yadmin.system.authorization.web.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import com.zhou.yadmin.common.exception.BadRequestException;
import com.zhou.yadmin.common.utils.WebUtils;
import com.zhou.yadmin.logging.aop.annotation.Log;
import com.zhou.yadmin.system.authorization.domain.Menu;
import com.zhou.yadmin.system.authorization.dto.MenuDto;
import com.zhou.yadmin.system.authorization.dto.UserDto;
import com.zhou.yadmin.system.authorization.mapper.RoleMapper;
import com.zhou.yadmin.system.authorization.service.MenuService;
import com.zhou.yadmin.system.authorization.service.UserService;
import com.zhou.yadmin.system.core.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/29 17:49
 */
@RestController
@RequestMapping("api/menus")
public class MenuController
{
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMapper roleMapper;

    private static final String ENTITY_NAME = "menu";

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_SELECT')")
    public ResponseEntity getMenus(@PathVariable Long id)
    {
        return new ResponseEntity(menuService.findById(id), HttpStatus.OK);
    }

    /**
     * 构建前端路由所需要的菜单
     *
     * @return
     */
    @GetMapping(value = "/build")
    public ResponseEntity buildMenus(HttpServletRequest request)
    {
        UserDetails userDetails = WebUtils.getUserDetails();
        UserDto user = userService.findByName(userDetails.getUsername());
        List<MenuDto> menuDTOList = menuService.findByRoles(roleMapper.toEntity(user.getRoles()));
        return new ResponseEntity(menuService.buildMenus((List<MenuDto>) menuService.buildTree(menuDTOList).get("content")), HttpStatus.OK);
    }

    /**
     * 返回全部的菜单
     *
     * @return
     */
    @GetMapping(value = "/tree")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_SELECT')")
    public ResponseEntity getMenuTree()
    {
        return new ResponseEntity(menuService.getMenuTree(menuService.findByPid(0L)), HttpStatus.OK);
    }

    @Log("查询菜单")
    @GetMapping(value = "")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_SELECT')")
    public ResponseEntity getMenus(@RequestParam(required = false) String name)
    {
        List<MenuDto> menuDTOList = menuService.queryAll(name);
        return new ResponseEntity(menuService.buildTree(menuDTOList), HttpStatus.OK);
    }

    @Log("新增菜单")
    @PostMapping(value = "")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Menu resources)
    {
        if (resources.getId() != null)
        {
            throw BadRequestException.newExceptionBySystem("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity(menuService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改菜单")
    @PutMapping(value = "")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_EDIT')")
    public ResponseEntity update(@Validated @RequestBody Menu resources)
    {
        if (resources.getId() == null)
        {
            throw BadRequestException.newExceptionBySystem(ENTITY_NAME + " ID Can not be empty");
        }
        menuService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除菜单")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_DELETE')")
    public ResponseEntity delete(@PathVariable Long id)
    {
        menuService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
