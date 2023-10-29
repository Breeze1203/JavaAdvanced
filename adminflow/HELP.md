# 创建user表
CREATE TABLE user (
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(255) NOT NULL,
phone BIGINT NOT NULL,
address VARCHAR(255) NOT NULL,
username VARCHAR(50) NOT NULL,
password VARCHAR(50) NOT NULL
);
# 创建role表
CREATE TABLE role (
id INT NOT NULL AUTO_INCREMENT,
role_name VARCHAR(30) NOT NULL,
nameZh VARCHAR(30) NOT NULL,
PRIMARY KEY (id)
);
#创建菜单角色关联表
CREATE TABLE menu_role (
id INT NOT NULL AUTO_INCREMENT,
menu_id INT NOT NULL,
role_id INT NOT NULL,
PRIMARY KEY (id)
);

# 创建menu表
create TABLE menu(
id INT NOT NULL AUTO_INCREMENT,
url VARCHAR(30) NOT NULL,
path VARCHAR(30) NOT NULL,
component VARCHAR(30) NOT NULL,
name VARCHAR(30) NOT NULL,
parentId INT NOT NULL,
PRIMARY KEY (id)
);
# 创建用户角色关联表
create TABLE user_role(
id INT NOT NULL AUTO_INCREMENT,
user_id INT NOT NULL,
role_id INT NOT NULL,
PRIMARY KEY (id)
);
# 角色操作权限关联表
将不同角色可以访问的权限添加到这个表里，例如，角色id为1的admin
可以操作的权限为log:edit,log:delete
# 菜单架构
系统管理
    权限设置
    部门设置
    职位设置
人事管理
    员工基本资料
    员工高级资料
日志管理
    登录日志
    操作日志
    访问日志
消息通知
    入职通知
    公告信息
    聊天管理

### 基本权限设计思路
当用户登录成功将用户的信息存入到redis数据库中，
同时在mysql数据库中根据当前用户id查询出role角色id，
在redis数据库中存入键 roleId(角色id)
根据角色id查询角色权限表查询出可以访问的角色
    
    




