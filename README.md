# yadmin

yadmin是一个前后端分离的权限管理系统，权限控制采用 RBAC 思想，支持 动态路由、项目1.0版本提供一个纯净的后台管理，第三方工具将在后面的版本中添加，

#### 后端技术栈

- 基础框架：Spring Boot 2.2.x.RELEASE
- 持久层框架：Spring boot Jpa
- 安全框架：Spring Security
- 缓存框架：Redis
- 日志打印：logback+log4jdbc
- 接口文档 swagger2
- 其他：jackson，aop，MapStruct等。

#### 前端技术栈

- Vue
- vue-router
- axios
- element ui

#### 系统功能模块

- 用户管理 提供用户的相关配置
- 角色管理 角色菜单分配权限
- 权限管理 权限细化到接口
- 菜单管理 已实现动态路由，后端可配置化
- 系统日志 记录用户访问监控异常信息
- 系统缓存管理 将redis的操作可视化，提供对redis的基本操作
- Sql监控 采用 druid 监控数据库访问性能

#### 系统预览


#### 反馈交流
