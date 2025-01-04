create table smbms_address
(
    id           bigint auto_increment comment '主键ID'
        primary key,
    contact      varchar(15) null comment '联系人姓名',
    addressDesc  varchar(50) null comment '收货地址明细',
    postCode     varchar(15) null comment '邮编',
    tel          varchar(20) null comment '联系人电话',
    createdBy    bigint      null comment '创建者',
    creationDate datetime    null comment '创建时间',
    modifyBy     bigint      null comment '修改者',
    modifyDate   datetime    null comment '修改时间',
    userId       bigint      null comment '用户ID',
    constraint fk_smbms_address_userid
        foreign key (userId) references smbms_user (id)
)
    collate = utf8mb3_unicode_ci;

INSERT INTO smbmsDB.smbms_address (id, contact, addressDesc, postCode, tel, createdBy, creationDate, modifyBy, modifyDate, userId) VALUES (1, '王丽', '北京市东城区东交民巷44号', '100010', '13678789999', 1, '2016-04-13 00:00:00', null, null, 1);
INSERT INTO smbmsDB.smbms_address (id, contact, addressDesc, postCode, tel, createdBy, creationDate, modifyBy, modifyDate, userId) VALUES (2, '张红丽', '北京市海淀区丹棱街3号', '100000', '18567672312', 1, '2016-04-13 00:00:00', null, null, 1);
INSERT INTO smbmsDB.smbms_address (id, contact, addressDesc, postCode, tel, createdBy, creationDate, modifyBy, modifyDate, userId) VALUES (3, '任志强', '北京市东城区美术馆后街23号', '100021', '13387906742', 1, '2016-04-13 00:00:00', null, null, 1);
INSERT INTO smbmsDB.smbms_address (id, contact, addressDesc, postCode, tel, createdBy, creationDate, modifyBy, modifyDate, userId) VALUES (4, '曹颖', '北京市朝阳区朝阳门南大街14号', '100053', '13568902323', 1, '2016-04-13 00:00:00', null, null, 2);
INSERT INTO smbmsDB.smbms_address (id, contact, addressDesc, postCode, tel, createdBy, creationDate, modifyBy, modifyDate, userId) VALUES (5, '李慧', '北京市西城区三里河路南三巷3号', '100032', '18032356666', 1, '2016-04-13 00:00:00', null, null, 5);
INSERT INTO smbmsDB.smbms_address (id, contact, addressDesc, postCode, tel, createdBy, creationDate, modifyBy, modifyDate, userId) VALUES (6, '王国强', '北京市顺义区高丽营镇金马工业区18号', '100061', '13787882222', 1, '2016-04-13 00:00:00', null, null, 5);
