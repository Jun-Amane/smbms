###查看用户信息
###select * from mysql.user;
###收回用户的权限
###revoke all privileges on smbmsDB.* from smbmsUser@localhost;
###删除用户
###drop USER smbmsUser@localhost;工作中谨慎，不做此操作
###创建用户
CREATE USER smbmsUser@localhost IDENTIFIED BY '123456';
###删除数据库
###drop database smbmsDB;工作中谨慎，不做此操作
###创建数据库
CREATE DATABASE smbmsDB;
###给用户授权
GRANT ALL ON smbmsDB.* TO smbmsUser@localhost;
###切换数据库
USE smbmsDB;
###导入数据表
