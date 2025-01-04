###修改数据
UPDATE smbms_address SET userid=5 WHERE userid=3;
###添加外键约束
ALTER TABLE smbms_address ADD CONSTRAINT fk_smbms_address_userid FOREIGN KEY (userid) REFERENCES 
smbms_user(id);

ALTER TABLE smbms_user ADD CONSTRAINT fk_smbms_user_userRole FOREIGN KEY (userrole) REFERENCES 
smbms_role(id);

ALTER TABLE smbms_bill ADD CONSTRAINT fk_smbms_bill_providerid FOREIGN KEY (providerid) REFERENCES
smbms_provider(id);


##ALTER TABLE smbms_user ADD userPicName varchar(200);
