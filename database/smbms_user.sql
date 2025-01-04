create table smbms_user
(
    id           bigint auto_increment comment '主键ID'
        primary key,
    userCode     varchar(15)  null comment '用户编码',
    userName     varchar(15)  null comment '用户名称',
    userPassword varchar(255) null comment '用户密码',
    gender       int(10)      null comment '性别（1:女、 2:男）',
    birthday     date         null comment '出生日期',
    phone        varchar(15)  null comment '手机',
    address      varchar(30)  null comment '地址',
    userRole     bigint       null comment '用户角色（取自角色表-角色id）',
    createdBy    bigint       null comment '创建者（userId）',
    creationDate datetime     null comment '创建时间',
    modifyBy     bigint       null comment '更新者（userId）',
    modifyDate   datetime     null comment '更新时间',
    constraint fk_smbms_user_userRole
        foreign key (userRole) references smbms_role (id)
)
    collate = utf8mb3_unicode_ci;

INSERT INTO smbmsDB.smbms_user (id, userCode, userName, userPassword, gender, birthday, phone, address, userRole, createdBy, creationDate, modifyBy, modifyDate) VALUES (1, 'admin', '系统管理员', '$2a$10$VpX8o7tVzfniPIYz6XqIqe0wpF/EMi2Ao6uYtq0vEpER06MSLQrWG', 1, '1983-10-10', '13688889999', '北京市海淀区成府路207号', 1, 1, '2013-03-21 16:52:07', 1, '2025-01-04 21:15:10');
INSERT INTO smbmsDB.smbms_user (id, userCode, userName, userPassword, gender, birthday, phone, address, userRole, createdBy, creationDate, modifyBy, modifyDate) VALUES (2, 'liming', '李明', '$2a$10$1HRab1MCyNaO49Jvpli1XurZG1CuI3ajpjzM9Ty8rmjqCiKBcnTIa', 2, '1983-12-10', '13688884457', '北京市东城区前门东大街9号', 2, 1, '2014-12-31 19:52:09', 1, '2025-01-04 21:15:10');
INSERT INTO smbmsDB.smbms_user (id, userCode, userName, userPassword, gender, birthday, phone, address, userRole, createdBy, creationDate, modifyBy, modifyDate) VALUES (5, 'hanlubiao', '韩路彪', '$2a$10$l5H3P3.cCm/kywegY08uGu9xAh.RjLD8KlJTqTgtKdDXCsT9hiZRq', 2, '1984-06-05', '18567542321', '北京市朝阳区北辰中心12号', 2, 1, '2014-12-31 19:52:09', 1, '2025-01-04 21:15:10');
INSERT INTO smbmsDB.smbms_user (id, userCode, userName, userPassword, gender, birthday, phone, address, userRole, createdBy, creationDate, modifyBy, modifyDate) VALUES (6, 'zhanghua', '张华', '$2a$10$KsUwpx4L0kgrAAQs5O0mIeMyUoOwPTEW.aStA5Uti41h22Al5Zh3S', 1, '1983-06-15', '13544561111', '北京市海淀区学院路61号', 3, 1, '2013-02-11 10:51:17', 1, '2025-01-04 21:15:10');
INSERT INTO smbmsDB.smbms_user (id, userCode, userName, userPassword, gender, birthday, phone, address, userRole, createdBy, creationDate, modifyBy, modifyDate) VALUES (7, 'wangyang', '王洋', '$2a$10$f.crY9lWK.6SrINm/WV9du3mWBZVZ/vy36PaFf/EwabeFBTTShPfO', 2, '1982-12-31', '13444561124', '北京市海淀区西二旗辉煌国际16层', 3, 1, '2014-06-11 19:09:07', 1, '2025-01-04 21:15:10');
INSERT INTO smbmsDB.smbms_user (id, userCode, userName, userPassword, gender, birthday, phone, address, userRole, createdBy, creationDate, modifyBy, modifyDate) VALUES (8, 'zhaoyan', '赵燕', '$2a$10$EJj0FsmBtEa67Z0qqTgA3OpW3EwcNbhCwfRMI1kwZhbzIAF7KL78W', 1, '1986-03-07', '18098764545', '北京市海淀区回龙观小区10号楼', 3, 1, '2016-04-21 13:54:07', 1, '2025-01-04 21:15:11');
INSERT INTO smbmsDB.smbms_user (id, userCode, userName, userPassword, gender, birthday, phone, address, userRole, createdBy, creationDate, modifyBy, modifyDate) VALUES (10, 'sunlei', '孙磊', '$2a$10$QkK4i4wW4Gt/EtmRhLh6DeB2Sgl/Gj1/a1dM8ucZ2GaiShX5G2isy', 2, '1981-01-04', '13387676765', '北京市朝阳区管庄新月小区12楼', 3, 1, '2015-05-06 10:52:07', 1, '2025-01-04 21:15:11');
INSERT INTO smbmsDB.smbms_user (id, userCode, userName, userPassword, gender, birthday, phone, address, userRole, createdBy, creationDate, modifyBy, modifyDate) VALUES (11, 'sunxing', '孙兴', '$2a$10$CReHwlE3u5sQtgncEkKngetEXyEAw7p/310lfoP1HJNBuTSYAPqoq', 2, '1978-03-12', '13367890900', '北京市朝阳区建国门南大街10号', 3, 1, '2016-11-09 16:51:17', 1, '2025-01-04 21:15:11');
INSERT INTO smbmsDB.smbms_user (id, userCode, userName, userPassword, gender, birthday, phone, address, userRole, createdBy, creationDate, modifyBy, modifyDate) VALUES (12, 'zhangchen', '张晨', '$2a$10$dnno0VRFV419YktMDCQ0UegpSa/TYiCKdqK0oek7DqsW4YwEBA7hO', 1, '1986-03-28', '18098765434', '朝阳区管庄路口北柏林爱乐三期13号楼', 3, 1, '2016-08-09 05:52:37', 1, '2025-01-04 21:15:11');
INSERT INTO smbmsDB.smbms_user (id, userCode, userName, userPassword, gender, birthday, phone, address, userRole, createdBy, creationDate, modifyBy, modifyDate) VALUES (13, 'dengchao', '邓超', '$2a$10$AC/L3byKXGLibqLJEPbKIOSrJ4B4rUzN5UP/GqlGWMN7X.Ps0maXe', 2, '1981-11-04', '13689674534', '北京市海淀区北航家属院10号楼', 3, 1, '2016-07-11 08:02:47', 1, '2025-01-04 21:15:11');
INSERT INTO smbmsDB.smbms_user (id, userCode, userName, userPassword, gender, birthday, phone, address, userRole, createdBy, creationDate, modifyBy, modifyDate) VALUES (14, 'yangguo', '杨过', '$2a$10$.mXTx2Xdim5v1jJziFEACOmHFrH9/PRX/gfiZrRdKNmOKGWnIQ4bi', 2, '1980-01-01', '13388886623', '北京市朝阳区北苑家园茉莉园20号楼', 3, 1, '2015-02-01 03:52:07', 1, '2025-01-04 21:15:11');
INSERT INTO smbmsDB.smbms_user (id, userCode, userName, userPassword, gender, birthday, phone, address, userRole, createdBy, creationDate, modifyBy, modifyDate) VALUES (15, 'zhaomin', '赵敏', '$2a$10$4kHkO6YNkvwl85fvR6ETG.XYmiPAUjh1qaqb8OnZEyUozKPvVf3VK', 1, '1987-12-04', '18099897657', '北京市昌平区天通苑3区12号楼', 2, 1, '2015-09-12 12:02:12', 1, '2025-01-04 21:15:11');
