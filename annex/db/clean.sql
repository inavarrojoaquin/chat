use chat
go

IF OBJECT_ID ('Invitation') IS NOT NULL
DROP TABLE [dbo].[Invitation]
GO
IF OBJECT_ID ('Massage') IS NOT NULL
DROP TABLE [dbo].[Massage]
GO
IF OBJECT_ID ('User_access') IS NOT NULL
DROP TABLE [dbo].[User_access]
GO
IF OBJECT_ID ('Room_access_policy') IS NOT NULL
DROP TABLE [dbo].[Room_access_policy]
GO
IF OBJECT_ID ('Room') IS NOT NULL
DROP TABLE [dbo].[Room]
GO
IF OBJECT_ID ('User_login') IS NOT NULL
DROP TABLE [dbo].[User_login]
GO
IF OBJECT_ID ('Profile') IS NOT NULL
DROP TABLE [dbo].[Profile]
GO

