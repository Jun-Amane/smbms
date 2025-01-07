create table smbms_role
(
    id           bigint auto_increment comment '主键ID'
        primary key,
    roleCode     varchar(16) null comment '角色编码',
    roleName     varchar(15) null comment '角色名称',
    createdBy    bigint      null comment '创建者',
    creationDate datetime    null comment '创建时间',
    modifyBy     bigint      null comment '修改者',
    modifyDate   datetime    null comment '修改时间'
)
    collate = utf8mb3_unicode_ci;

INSERT INTO smbmsDB.smbms_role (id, roleCode, roleName, createdBy, creationDate, modifyBy, modifyDate) VALUES (1, 'SMBMS_ADMIN', '系统管理员', 1, '2016-04-13 00:00:00', null, null);
INSERT INTO smbmsDB.smbms_role (id, roleCode, roleName, createdBy, creationDate, modifyBy, modifyDate) VALUES (2, 'SMBMS_MANAGER', '经理', 1, '2016-04-13 00:00:00', null, null);
INSERT INTO smbmsDB.smbms_role (id, roleCode, roleName, createdBy, creationDate, modifyBy, modifyDate) VALUES (3, 'SMBMS_EMPLOYEE', '普通员工', 1, '2016-04-13 00:00:00', null, null);
