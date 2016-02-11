use chat
go

DELETE [dbo].[Invitation]
GO
DBCC CHECKIDENT ('[dbo].[Invitation]', RESEED, 0);
GO
DELETE [dbo].[Message]
GO
DBCC CHECKIDENT ('[dbo].[Message]', RESEED, 0);
GO
DELETE [dbo].[User_access]
GO
DBCC CHECKIDENT ('[dbo].[User_access]', RESEED, 0);
GO
DELETE [dbo].[Room_access_policy]
GO
DBCC CHECKIDENT ('[dbo].[Room_access_policy]', RESEED, 0);
GO
DELETE [dbo].[Room]
GO
DBCC CHECKIDENT ('[dbo].[Room]', RESEED, 0);
GO
DELETE [dbo].[User_login]
GO
DBCC CHECKIDENT ('[dbo].[User_login]', RESEED, 0);
GO
DELETE [dbo].[Profile]
GO
DBCC CHECKIDENT ('[dbo].[Profile]', RESEED, 0);
GO

/**Add admin profile
insert into Profile
values('admin@admin.com','admin','ADMIN')
*/

/**Insert some rooms
insert into Room(name, type, owner)
values ('Human Sexuality', 'public', 1)
insert into Room(name, type, owner)
values ('Reproductive System', 'public', 1)
insert into Room(name, type, owner)
values ('Sexual Orientation', 'public', 1)
insert into Room(name, type, owner)
values ('Sexual Relations', 'public', 1)
insert into Room(name, type, owner)
values ('Contraceptives', 'public', 1)
insert into Room(name, type, owner)
values ('Sexually Transmitted Sisease', 'public', 1)
*/