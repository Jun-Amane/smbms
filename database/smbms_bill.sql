create table smbms_bill
(
    id           bigint auto_increment comment '主键ID'
        primary key,
    billCode     varchar(20)    null comment '账单编码',
    productName  varchar(20)    null comment '商品名称',
    productDesc  varchar(50)    null comment '商品描述',
    productUnit  varchar(10)    null comment '商品单位',
    productCount decimal(20, 2) null comment '商品数量',
    totalPrice   decimal(20, 2) null comment '商品总额',
    isPayment    int(10)        null comment '是否支付（1：未支付 2：已支付）',
    createdBy    bigint         null comment '创建者（userId）',
    creationDate datetime       null comment '创建时间',
    modifyBy     bigint         null comment '更新者（userId）',
    modifyDate   datetime       null comment '更新时间',
    providerId   bigint         null comment '供应商ID',
    constraint fk_smbms_bill_providerid
        foreign key (providerId) references smbms_provider (id)
)
    collate = utf8mb3_unicode_ci;

INSERT INTO smbmsDB.smbms_bill (id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, modifyBy, modifyDate, providerId) VALUES (1, 'BILL2016_001', '洗发水、护发素', '日用品-洗发、护发', '瓶', 500.00, 25000.00, 2, 1, '2014-12-14 13:02:03', null, null, 13);
INSERT INTO smbmsDB.smbms_bill (id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, modifyBy, modifyDate, providerId) VALUES (2, 'BILL2016_002', '香皂、肥皂、药皂', '日用品-皂类', '块', 1000.00, 10000.00, 2, 1, '2016-03-23 04:20:40', null, null, 13);
INSERT INTO smbmsDB.smbms_bill (id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, modifyBy, modifyDate, providerId) VALUES (3, 'BILL2016_003', '大豆油', '食品-食用油', '斤', 300.00, 5890.00, 2, 1, '2014-12-14 13:02:03', null, null, 6);
INSERT INTO smbmsDB.smbms_bill (id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, modifyBy, modifyDate, providerId) VALUES (4, 'BILL2016_004', '橄榄油', '食品-进口食用油', '斤', 200.00, 9800.00, 2, 1, '2013-10-10 03:12:13', null, null, 7);
INSERT INTO smbmsDB.smbms_bill (id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, modifyBy, modifyDate, providerId) VALUES (5, 'BILL2016_005', '洗洁精', '日用品-厨房清洁', '瓶', 500.00, 7000.00, 2, 1, '2014-12-14 13:02:03', null, null, 9);
INSERT INTO smbmsDB.smbms_bill (id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, modifyBy, modifyDate, providerId) VALUES (6, 'BILL2016_006', '美国大杏仁', '食品-坚果', '袋', 300.00, 5000.00, 2, 1, '2016-04-14 06:08:09', null, null, 4);
INSERT INTO smbmsDB.smbms_bill (id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, modifyBy, modifyDate, providerId) VALUES (7, 'BILL2016_007', '沐浴液、精油', '日用品-沐浴类', '瓶', 500.00, 23000.00, 1, 1, '2016-07-22 10:10:22', null, null, 14);
INSERT INTO smbmsDB.smbms_bill (id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, modifyBy, modifyDate, providerId) VALUES (8, 'BILL2016_008', '不锈钢盘碗', '日用品-厨房用具', '个', 600.00, 6000.00, 2, 1, '2016-04-14 05:12:13', null, null, 14);
INSERT INTO smbmsDB.smbms_bill (id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, modifyBy, modifyDate, providerId) VALUES (9, 'BILL2016_009', '塑料杯', '日用品-杯子', '个', 350.00, 1750.00, 2, 1, '2016-02-04 11:40:20', null, null, 14);
INSERT INTO smbmsDB.smbms_bill (id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, modifyBy, modifyDate, providerId) VALUES (10, 'BILL2016_010', '豆瓣酱', '食品-调料', '瓶', 200.00, 2000.00, 2, 1, '2013-10-29 05:07:03', null, null, 8);
INSERT INTO smbmsDB.smbms_bill (id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, modifyBy, modifyDate, providerId) VALUES (11, 'BILL2016_011', '海之蓝', '饮料-国酒', '瓶', 50.00, 10000.00, 1, 1, '2016-04-14 16:16:00', null, null, 1);
INSERT INTO smbmsDB.smbms_bill (id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, modifyBy, modifyDate, providerId) VALUES (12, 'BILL2016_012', '芝华士', '饮料-洋酒', '瓶', 20.00, 6000.00, 1, 1, '2016-09-09 17:00:00', null, null, 1);
INSERT INTO smbmsDB.smbms_bill (id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, modifyBy, modifyDate, providerId) VALUES (13, 'BILL2016_013', '长城红葡萄酒', '饮料-红酒', '瓶', 60.00, 800.00, 2, 1, '2016-11-14 15:23:00', null, null, 1);
INSERT INTO smbmsDB.smbms_bill (id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, modifyBy, modifyDate, providerId) VALUES (14, 'BILL2016_014', '泰国香米', '食品-大米', '斤', 400.00, 5000.00, 2, 1, '2016-10-09 15:20:00', null, null, 3);
INSERT INTO smbmsDB.smbms_bill (id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, modifyBy, modifyDate, providerId) VALUES (15, 'BILL2016_015', '东北大米', '食品-大米', '斤', 600.00, 4000.00, 2, 1, '2016-11-14 14:00:00', null, null, 3);
INSERT INTO smbmsDB.smbms_bill (id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, modifyBy, modifyDate, providerId) VALUES (16, 'BILL2016_016', '可口可乐', '饮料', '瓶', 2000.00, 6000.00, 2, 1, '2012-03-27 13:03:01', null, null, 2);
INSERT INTO smbmsDB.smbms_bill (id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, modifyBy, modifyDate, providerId) VALUES (17, 'BILL2016_017', '脉动', '饮料', '瓶', 1500.00, 4500.00, 2, 1, '2016-05-10 12:00:00', null, null, 2);
INSERT INTO smbmsDB.smbms_bill (id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, modifyBy, modifyDate, providerId) VALUES (18, 'BILL2016_018', '哇哈哈', '饮料', '瓶', 2000.00, 4000.00, 2, 1, '2015-11-24 15:12:03', null, null, 2);

INSERT INTO smbmsDB.smbms_bill (id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, modifyBy, modifyDate, providerId) VALUES 
(19, 'BILL2017_001', '可乐口香糖', '食品-糖果', '包', 1000.00, 2000.00, 1, 1, '2017-01-10 10:00:00', null, null, 15),
(20, 'BILL2017_002', '芒果果干', '食品-果脯', '袋', 1500.00, 7500.00, 2, 1, '2016-12-12 09:00:00', null, null, 15),
(21, 'BILL2017_003', '纸巾', '日用品-纸制品', '包', 2000.00, 4000.00, 2, 1, '2015-07-07 11:00:00', null, null, 10),
(22, 'BILL2017_004', '洗衣液', '日用品-清洁剂', '瓶', 1000.00, 10000.00, 1, 1, '2018-03-15 14:00:00', null, null, 10),
(23, 'BILL2017_005', '夏季丝巾', '服装-配饰', '条', 200.00, 3000.00, 2, 1, '2019-06-18 15:30:00', null, null, 12),
(24, 'BILL2017_006', '汽车香水', '日用品-车用', '瓶', 150.00, 2250.00, 1, 1, '2018-05-20 13:30:00', null, null, 12),
(25, 'BILL2017_007', '高粱酒', '饮料-酒水', '瓶', 100.00, 10000.00, 2, 1, '2018-07-07 10:30:00', null, null, 5),
(26, 'BILL2017_008', '红米', '食品-大米', '斤', 800.00, 4800.00, 1, 1, '2019-08-22 11:15:00', null, null, 5),
(27, 'BILL2017_009', '虾干', '食品-海鲜', '公斤', 120.00, 2400.00, 2, 1, '2020-01-15 08:45:00', null, null, 11),
(28, 'BILL2017_010', '干贝', '食品-海鲜', '公斤', 80.00, 4000.00, 1, 1, '2020-03-25 10:05:00', null, null, 11),
(29, 'BILL2017_011', '绿茶', '饮料-茶类', '袋', 300.00, 7500.00, 2, 1, '2021-04-13 17:50:00', null, null, 18),
(30, 'BILL2017_012', '乌龙茶', '饮料-茶类', '袋', 250.00, 6250.00, 1, 1, '2021-05-17 16:25:00', null, null, 18),
(31, 'BILL2017_013', '椰汁', '饮料-植物蛋白', '瓶', 400.00, 4000.00, 2, 1, '2017-11-21 13:45:00', null, null, 19),
(32, 'BILL2017_014', '鲜榨橙汁', '饮料-果汁', '瓶', 500.00, 7500.00, 1, 1, '2018-12-05 12:20:00', null, null, 19),
(33, 'BILL2017_015', '纸杯', '日用品-生活用品', '个', 1000.00, 2000.00, 2, 1, '2019-02-15 14:15:00', null, null, 20),
(34, 'BILL2017_016', '环保袋', '日用品-生活用品', '个', 2000.00, 4000.00, 1, 1, '2019-03-12 15:00:00', null, null, 20);

INSERT INTO smbmsDB.smbms_bill (id, billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, modifyBy, modifyDate, providerId) VALUES
(35, 'BILL2017_017', '苏打水', '饮料-汽水', '瓶', 1500.00, 4500.00, 2, 1, '2020-04-18 10:30:00', null, null, 16),
(36, 'BILL2017_018', '曲奇饼干', '食品-早餐', '盒', 1200.00, 7200.00, 1, 1, '2020-05-22 09:45:00', null, null, 16),
(37, 'BILL2017_019', '白葡萄酒', '饮料-酒水', '瓶', 300.00, 9000.00, 2, 1, '2020-06-30 11:20:00', null, null, 17),
(38, 'BILL2017_020', '火锅底料', '食品-调味品', '包', 1000.00, 10000.00, 1, 1, '2020-07-15 14:00:00', null, null, 17),
(39, 'BILL2017_021', '面条', '食品-主食', '斤', 500.00, 2500.00, 2, 1, '2020-08-12 13:30:00', null, null, 20),
(40, 'BILL2017_022', '挂面', '食品-主食', '斤', 400.00, 1600.00, 1, 1, '2020-09-18 12:00:00', null, null, 20),
(41, 'BILL2017_023', '西瓜汁', '饮料-果汁', '瓶', 800.00, 6400.00, 2, 1, '2020-10-10 15:40:00', null, null, 18),
(42, 'BILL2017_024', '橙子', '食品-鲜果', '斤', 1500.00, 7500.00, 1, 1, '2020-11-17 17:25:00', null, null, 18),
(43, 'BILL2017_025', '柠檬', '食品-鲜果', '斤', 600.00, 3000.00, 2, 1, '2020-12-20 16:35:00', null, null, 19),
(44, 'BILL2017_026', '苹果', '食品-鲜果', '斤', 800.00, 3200.00, 1, 1, '2021-01-15 11:50:00', null, null, 19),
(45, 'BILL2017_027', '酸菜鱼调料', '食品-调味品', '包', 900.00, 8100.00, 2, 1, '2021-02-22 09:25:00', null, null, 17),
(46, 'BILL2017_028', '春卷', '食品-速冻', '袋', 500.00, 6000.00, 1, 1, '2021-03-18 10:15:00', null, null, 17),
(47, 'BILL2017_029', '坚果大礼包', '食品-坚果', '袋', 300.00, 9000.00, 2, 1, '2021-04-27 09:10:00', null, null, 16),
(48, 'BILL2017_030', '酱香鸭', '食品-熟食', '只', 200.00, 8000.00, 1, 1, '2021-05-22 10:50:00', null, null, 16);


