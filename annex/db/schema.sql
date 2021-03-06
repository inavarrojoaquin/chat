USE [chat]
GO

IF OBJECT_ID ('Invitation') IS NOT NULL
DROP TABLE [dbo].[Invitation]
GO
IF OBJECT_ID ('Message') IS NOT NULL
DROP TABLE [dbo].[Message]
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


/****** Object:  Table [dbo].[Invitation]    Script Date: 07/09/2015 11:27:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Invitation](
	[id] [int] identity(1,1) NOT NULL,
	[room] [int] NOT NULL,
	[sender] [int] NOT NULL,
	[receiver] [int] NOT NULL,
	[state] [varchar](100) NOT NULL,
 CONSTRAINT [PK_Invitation] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Message]    Script Date: 07/09/2015 11:27:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Message](
	[id] [int] identity(1,1) NOT NULL,
	[room] [int] NOT NULL,
	[owner] [int] NOT NULL,
	[datetime_of_creation] [datetime] NOT NULL,
	[body] [varchar](100) NOT NULL,
	[state] [varchar](100) NOT NULL,
 CONSTRAINT [PK_Message] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Profile]    Script Date: 07/09/2015 11:27:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Profile](
	[id] [int] identity(1,1) NOT NULL,
	[login] [varchar](100) NOT NULL,
	[password] [varchar](100) NOT NULL,
	[type] [varchar](100) NOT NULL default 'user'
 CONSTRAINT [PK_Profile] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Room]    Script Date: 07/09/2015 11:27:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Room](
	[id] [int] identity(1,1) NOT NULL,
	[name] [varchar](100) NOT NULL,
	[type] [varchar](100) NOT NULL,
	[owner] [int] NULL,
 CONSTRAINT [PK_Room] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Room_access_policy]    Script Date: 07/09/2015 11:27:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Room_access_policy](
	[id] [int] identity(1,1) NOT NULL,
	[room] [int] NOT NULL,
	[profile] [int] NOT NULL,
	[policy] [varchar](100) NOT NULL,
 CONSTRAINT [PK_Room_access_policy] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[User_access]    Script Date: 07/09/2015 11:27:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User_access](
	[id] [int] identity(1,1) NOT NULL,
	[room] [int] NOT NULL,
	[profile] [int] NOT NULL,
	[datetime_of_access_start] [datetime] NOT NULL,
	[datetime_of_access_end] [datetime] NULL,
 CONSTRAINT [PK_User_access] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[User_login]    Script Date: 07/09/2015 11:27:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User_login](
	[id] [int] identity(1,1) NOT NULL,
	[profile] [int] NOT NULL,
	[date_time_of_access_start] [datetime] NOT NULL,
	[date_time_of_access_end] [datetime] NULL,
 CONSTRAINT [PK_User_login] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
ALTER TABLE [dbo].[Invitation]  WITH CHECK ADD  CONSTRAINT [FK_Invitation_Profile] FOREIGN KEY([sender])
REFERENCES [dbo].[Profile] ([id])
GO
ALTER TABLE [dbo].[Invitation] CHECK CONSTRAINT [FK_Invitation_Profile]
GO
ALTER TABLE [dbo].[Invitation]  WITH CHECK ADD  CONSTRAINT [FK_Invitation_Profile1] FOREIGN KEY([receiver])
REFERENCES [dbo].[Profile] ([id])
GO
ALTER TABLE [dbo].[Invitation] CHECK CONSTRAINT [FK_Invitation_Profile1]
GO
ALTER TABLE [dbo].[Invitation]  WITH CHECK ADD  CONSTRAINT [FK_Invitation_Room] FOREIGN KEY([room])
REFERENCES [dbo].[Room] ([id]) ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Invitation] CHECK CONSTRAINT [FK_Invitation_Room]
GO
ALTER TABLE [dbo].[Message]  WITH CHECK ADD  CONSTRAINT [FK_Message_Profile] FOREIGN KEY([owner])
REFERENCES [dbo].[Profile] ([id])
GO
ALTER TABLE [dbo].[Message] CHECK CONSTRAINT [FK_Message_Profile]
GO

ALTER TABLE [dbo].[Message]  WITH CHECK ADD  CONSTRAINT [FK_Message_Room] FOREIGN KEY([room])
REFERENCES [dbo].[Room] ([id]) ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Message] CHECK CONSTRAINT [FK_Message_Room]
GO
ALTER TABLE [dbo].[Room]  WITH CHECK ADD  CONSTRAINT [FK_Room_Profile] FOREIGN KEY([owner])
REFERENCES [dbo].[Profile] ([id])
GO
ALTER TABLE [dbo].[Room] CHECK CONSTRAINT [FK_Room_Profile]
GO
ALTER TABLE [dbo].[Room_access_policy]  WITH CHECK ADD  CONSTRAINT [FK_Room_access_policy_Profile] FOREIGN KEY([profile])
REFERENCES [dbo].[Profile] ([id])
GO
ALTER TABLE [dbo].[Room_access_policy] CHECK CONSTRAINT [FK_Room_access_policy_Profile]
GO
ALTER TABLE [dbo].[Room_access_policy]  WITH CHECK ADD  CONSTRAINT [FK_Room_access_policy_Room] FOREIGN KEY([room])
REFERENCES [dbo].[Room] ([id]) ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Room_access_policy] CHECK CONSTRAINT [FK_Room_access_policy_Room]
GO
ALTER TABLE [dbo].[User_access]  WITH CHECK ADD  CONSTRAINT [FK_User_access_Profile] FOREIGN KEY([profile])
REFERENCES [dbo].[Profile] ([id])
GO
ALTER TABLE [dbo].[User_access] CHECK CONSTRAINT [FK_User_access_Profile]
GO
ALTER TABLE [dbo].[User_access]  WITH CHECK ADD  CONSTRAINT [FK_User_access_Room] FOREIGN KEY([room])
REFERENCES [dbo].[Room] ([id]) ON DELETE CASCADE
GO
ALTER TABLE [dbo].[User_access] CHECK CONSTRAINT [FK_User_access_Room]
GO
ALTER TABLE [dbo].[User_login]  WITH CHECK ADD  CONSTRAINT [FK_User_login_Profile] FOREIGN KEY([profile])
REFERENCES [dbo].[Profile] ([id])
GO
ALTER TABLE [dbo].[User_login] CHECK CONSTRAINT [FK_User_login_Profile]
GO

ALTER TABLE [dbo].[Profile] WITH CHECK ADD CONSTRAINT [CHECK_profile_type_END] CHECK (type IN ('admin','user'))
GO
ALTER TABLE [dbo].[Profile] CHECK CONSTRAINT [CHECK_profile_type_END]
GO
