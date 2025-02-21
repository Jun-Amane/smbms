create table smbms_provider
(
    id           bigint auto_increment comment '主键ID'
        primary key,
    proCode      varchar(20) null comment '供应商编码',
    proName      varchar(20) null comment '供应商名称',
    proDesc      varchar(50) null comment '供应商详细描述',
    proContact   varchar(20) null comment '供应商联系人',
    proPhone     varchar(20) null comment '联系电话',
    proAddress   varchar(50) null comment '地址',
    proFax       varchar(20) null comment '传真',
    createdBy    bigint      null comment '创建者（userId）',
    creationDate datetime    null comment '创建时间',
    modifyDate   datetime    null comment '更新时间',
    modifyBy     bigint      null comment '更新者（userId）'
)
    collate = utf8mb3_unicode_ci;

INSERT INTO smbmsDB.smbms_provider (id, proCode, proName, proDesc, proContact, proPhone, proAddress, proFax, createdBy, creationDate, modifyDate, modifyBy) VALUES (1, 'BJ_GYS001', '北京三木堂商贸有限公司', '长期合作伙伴，主营产品:茅台、五粮液、郎酒、酒鬼酒、泸州老窖、赖茅酒、法国红酒等', '张国强', '13566667777', '北京市丰台区育芳园北路', '010-58858787', 1, '2013-03-21 16:52:07', null, null);
INSERT INTO smbmsDB.smbms_provider (id, proCode, proName, proDesc, proContact, proPhone, proAddress, proFax, createdBy, creationDate, modifyDate, modifyBy) VALUES (2, 'HB_GYS001', '石家庄帅益食品贸易有限公司', '长期合作伙伴，主营产品:饮料、水饮料、植物蛋白饮料、休闲食品、果汁饮料、功能饮料等', '王军', '13309094212', '河北省石家庄新华区', '0311-67738876', 1, '2016-04-13 04:20:40', null, null);
INSERT INTO smbmsDB.smbms_provider (id, proCode, proName, proDesc, proContact, proPhone, proAddress, proFax, createdBy, creationDate, modifyDate, modifyBy) VALUES (3, 'GZ_GYS001', '深圳市泰香米业有限公司', '初次合作伙伴，主营产品：良记金轮米,龙轮香米等', '郑程瀚', '13402013312', '广东省深圳市福田区深南大道6006华丰大厦', '0755-67776212', 1, '2014-03-21 16:56:07', null, null);
INSERT INTO smbmsDB.smbms_provider (id, proCode, proName, proDesc, proContact, proPhone, proAddress, proFax, createdBy, creationDate, modifyDate, modifyBy) VALUES (4, 'GZ_GYS002', '深圳市喜来客商贸有限公司', '长期合作伙伴，主营产品：坚果炒货.果脯蜜饯.天然花茶.营养豆豆.特色美食.进口食品.海味零食.肉脯肉', '林妮', '18599897645', '广东省深圳市福龙工业区B2栋3楼西', '0755-67772341', 1, '2013-03-22 16:52:07', null, null);
INSERT INTO smbmsDB.smbms_provider (id, proCode, proName, proDesc, proContact, proPhone, proAddress, proFax, createdBy, creationDate, modifyDate, modifyBy) VALUES (5, 'JS_GYS001', '兴化佳美调味品厂', '长期合作伙伴，主营产品：天然香辛料、鸡精、复合调味料', '徐国洋', '13754444221', '江苏省兴化市林湖工业区', '0523-21299098', 1, '2015-11-22 16:52:07', null, null);
INSERT INTO smbmsDB.smbms_provider (id, proCode, proName, proDesc, proContact, proPhone, proAddress, proFax, createdBy, creationDate, modifyDate, modifyBy) VALUES (6, 'BJ_GYS002', '北京纳福尔食用油有限公司', '长期合作伙伴，主营产品：山茶油、大豆油、花生油、橄榄油等', '马莺', '13422235678', '北京市朝阳区珠江帝景1号楼', '010-588634233', 1, '2012-03-21 17:52:07', null, null);
INSERT INTO smbmsDB.smbms_provider (id, proCode, proName, proDesc, proContact, proPhone, proAddress, proFax, createdBy, creationDate, modifyDate, modifyBy) VALUES (7, 'BJ_GYS003', '北京国粮食用油有限公司', '初次合作伙伴，主营产品：花生油、大豆油、小磨油等', '王驰', '13344441135', '北京大兴青云店开发区', '010-588134111', 1, '2016-04-13 00:00:00', null, null);
INSERT INTO smbmsDB.smbms_provider (id, proCode, proName, proDesc, proContact, proPhone, proAddress, proFax, createdBy, creationDate, modifyDate, modifyBy) VALUES (8, 'ZJ_GYS001', '慈溪市广和绿色食品厂', '长期合作伙伴，主营产品：豆瓣酱、黄豆酱、甜面酱，辣椒，大蒜等农产品', '薛圣丹', '18099953223', '浙江省宁波市慈溪周巷小安村', '0574-34449090', 1, '2013-11-21 06:02:07', null, null);
INSERT INTO smbmsDB.smbms_provider (id, proCode, proName, proDesc, proContact, proPhone, proAddress, proFax, createdBy, creationDate, modifyDate, modifyBy) VALUES (9, 'GX_GYS001', '优百商贸有限公司', '长期合作伙伴，主营产品：日化产品', '李立国', '13323566543', '广西南宁市秀厢大道42-1号', '0771-98861134', 1, '2013-03-21 19:52:07', null, null);
INSERT INTO smbmsDB.smbms_provider (id, proCode, proName, proDesc, proContact, proPhone, proAddress, proFax, createdBy, creationDate, modifyDate, modifyBy) VALUES (10, 'JS_GYS002', '南京火头军信息技术有限公司', '长期合作伙伴，主营产品：不锈钢厨具等', '陈女士', '13098992113', '江苏省南京市浦口区浦口大道1号新城总部大厦A座903室', '025-86223345', 1, '2013-03-25 16:52:07', null, null);
INSERT INTO smbmsDB.smbms_provider (id, proCode, proName, proDesc, proContact, proPhone, proAddress, proFax, createdBy, creationDate, modifyDate, modifyBy) VALUES (11, 'GZ_GYS003', '广州市白云区美星五金制品厂', '长期合作伙伴，主营产品：海绵床垫、坐垫、靠垫、海绵枕头、头枕等', '梁天', '13562276775', '广州市白云区钟落潭镇福龙路20号', '020-85542231', 1, '2016-12-21 06:12:17', null, null);
INSERT INTO smbmsDB.smbms_provider (id, proCode, proName, proDesc, proContact, proPhone, proAddress, proFax, createdBy, creationDate, modifyDate, modifyBy) VALUES (12, 'BJ_GYS004', '北京隆盛日化科技', '长期合作伙伴，主营产品：日化环保清洗剂，家居洗涤专卖、洗涤用品网、墙体除霉剂、墙面霉菌清除剂等', '孙欣', '13689865678', '北京市大兴区旧宫', '010-35576786', 1, '2014-11-21 12:51:11', null, null);
INSERT INTO smbmsDB.smbms_provider (id, proCode, proName, proDesc, proContact, proPhone, proAddress, proFax, createdBy, creationDate, modifyDate, modifyBy) VALUES (13, 'SD_GYS001', '山东豪克华光联合发展有限公司', '长期合作伙伴，主营产品：洗衣皂、洗衣粉、洗衣液、洗洁精、消杀类、香皂等', '吴洪转', '13245468787', '山东济阳济北工业区仁和街21号', '0531-53362445', 1, '2015-01-28 10:52:07', null, null);
INSERT INTO smbmsDB.smbms_provider (id, proCode, proName, proDesc, proContact, proPhone, proAddress, proFax, createdBy, creationDate, modifyDate, modifyBy) VALUES (14, 'JS_GYS003', '无锡喜源坤商行', '长期合作伙伴，主营产品：日化品批销', '周一清', '18567674532', '江苏无锡盛岸西路', '0510-32274422', 1, '2016-04-23 11:11:11', null, null);
INSERT INTO smbmsDB.smbms_provider (id, proCode, proName, proDesc, proContact, proPhone, proAddress, proFax, createdBy, creationDate, modifyDate, modifyBy) VALUES (15, 'ZJ_GYS002', '乐摆日用品厂', '长期合作伙伴，主营产品：各种中、高档塑料杯，塑料乐扣水杯（密封杯）、保鲜杯（保鲜盒）、广告杯、礼品杯', '王世杰', '13212331567', '浙江省金华市义乌市义东路', '0579-34452321', 1, '2016-08-22 10:01:30', null, null);

INSERT INTO smbmsDB.smbms_provider (id, proCode, proName, proDesc, proContact, proPhone, proAddress, proFax, createdBy, creationDate, modifyDate, modifyBy) VALUES 
(16, 'SH_GYS001', '上海润和食品有限公司', '初次合作伙伴，主营产品：罐头食品和速冻食品', '李亚楠', '13912345678', '上海市静安区南京西路', '021-55567890', 1, '2017-02-01 09:00:00', null, null),
(17, 'CQ_GYS001', '重庆特产有限公司', '长期合作伙伴，主营产品：重庆火锅底料、麻辣拌料', '杨浩', '13711112222', '重庆市渝中区解放碑', '023-87654321', 1, '2015-05-12 13:45:00', null, null),
(18, 'HN_GYS001', '海南水果贸易公司', '长期合作伙伴，主营产品：进口热带水果', '赵敏', '13622223333', '海南省三亚市河东路', '0898-98765432', 1, '2018-07-23 11:30:00', null, null),
(19, 'SC_GYS001', '四川辣椒加工厂', '初次合作伙伴，主营产品：辣椒酱、香辣酱、泡椒', '刘芸', '13833334444', '四川省成都市锦江区草堂路', '028-76543210', 1, '2019-11-15 16:20:00', null, null),
(20, 'HLJ_GYS001', '黑龙江乳品有限公司', '长期合作伙伴，主营产品：奶粉、液体乳制品', '张伟', '13944445555', '黑龙江省哈尔滨市南岗区', '0451-65432109', 1, '2020-03-19 14:00:00', null, null);
