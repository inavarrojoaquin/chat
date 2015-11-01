/****************************************************
				TABLE PROFILE
****************************************************/
if OBJECT_ID('proc_InsertProfile ') is not null
	drop procedure proc_InsertProfile
go

CREATE PROCEDURE proc_InsertProfile 
    @id int OUTPUT,
    @login varchar(100),
	@password varchar(100)
AS
BEGIN

	IF EXISTS(SELECT * FROM Profile WHERE login = @login)
		BEGIN
			RAISERROR('Error, el usuario ya existe en la db', 18, 1)
			RETURN
		END
	ELSE
		BEGIN
			insert into Profile(login,password)
			values (@login, @password)

			SELECT TOP 1 @id = p.id
			FROM Profile p
			ORDER BY p.id DESC
		END
END
GO

/*
DECLARE @id_out int;
EXECUTE proc_InsertProfile @id_out OUTPUT, 'nico', 'nico'
select @id_out as id
*/

if OBJECT_ID('proc_SelectProfiles ')is not null
	drop procedure proc_SelectProfiles
go

CREATE PROCEDURE proc_SelectProfiles
AS
BEGIN
  
	SELECT * FROM Profile
END
GO

if OBJECT_ID('proc_SelectProfileById ')is not null
	drop procedure proc_SelectProfileById
go

CREATE PROCEDURE proc_SelectProfileById
 @id int
AS
BEGIN
  
	SELECT * FROM Profile p
	where p.id = @id
END
GO

if OBJECT_ID('proc_SelectProfileByLogin ')is not null
	drop procedure proc_SelectProfileByLogin
go

CREATE PROCEDURE proc_SelectProfileByLogin
 @login varchar(100)
AS
BEGIN
  
	SELECT * FROM Profile p
	where p.login = @login
	
END
GO


/****************************************************
				TABLE User_login
****************************************************/

if OBJECT_ID('proc_InsertUserLogin ')is not null
	drop procedure proc_InsertUserLogin
go

CREATE PROCEDURE proc_InsertUserLogin
 @id		int OUTPUT,
 @profile	int,
 @datetimeOfAccessStart	datetime,
 @datetimeOfAccessEnd	datetime
AS
BEGIN
  
	insert into User_login(profile, date_time_of_access_start, date_time_of_access_end)
	values (@profile, @datetimeOfAccessStart, @datetimeOfAccessEnd)

	SELECT TOP 1 @id = ul.id
	FROM User_login ul
	ORDER BY ul.id DESC

END
GO

/*
DECLARE @id_out int;
EXECUTE proc_InsertUserLogin @id_out OUTPUT, 1, '2000-10-26 10:37:31.723', null
select @id_out as id
*/

if OBJECT_ID('proc_SelectUserLoginById ')is not null
	drop procedure proc_SelectUserLoginById
go

CREATE PROCEDURE proc_SelectUserLoginById
 @id int
AS
BEGIN
  
	select * from User_login
	where id = @id
END
GO

if OBJECT_ID('proc_SelectUserLoginByProfile ')is not null
	drop procedure proc_SelectUserLoginByProfile
go

CREATE PROCEDURE proc_SelectUserLoginByProfile
 @profile int
AS
BEGIN
  
	select * from User_login
	where profile = @profile
END
GO

if OBJECT_ID('proc_SelectLastUserLogin ')is not null
	drop procedure proc_SelectLastUserLogin
go

CREATE PROCEDURE proc_SelectLastUserLogin
 @profile int
AS
BEGIN
	select * from User_login
	where profile = @profile
	and date_time_of_access_start = (select max(date_time_of_access_start) 
									from User_login 
									where profile = @profile)

END
GO

if OBJECT_ID('proc_SelectUsersLogin ')is not null
	drop procedure proc_SelectUsersLogin
go

CREATE PROCEDURE proc_SelectUsersLogin
AS
BEGIN
		SELECT * FROM User_login
END
GO

if OBJECT_ID('proc_UpdateUserLogin ')is not null
	drop procedure proc_UpdateUserLogin
go

CREATE PROCEDURE proc_UpdateUserLogin
 @id		int,
 @profile	int,
 @datetimeOfAccessStart	datetime,
 @datetimeOfAccessEnd	datetime
AS
BEGIN
  
	UPDATE User_login
	set date_time_of_access_end = @datetimeOfAccessEnd
	where id = @id
END
GO

--exec proc_UpdateUserLogin 8,15, '2015-10-26 14:23:30.140', '2015-10-26 16:23:30.140'

/****************************************************
				TABLE Room
****************************************************/

if OBJECT_ID('proc_InsertRoom ')is not null
	drop procedure proc_InsertRoom
go

CREATE PROCEDURE proc_InsertRoom
 @id	int OUTPUT,
 @name	varchar(100),
 @type	varchar(100),
 @owner	int
AS
BEGIN
  
	insert into Room(name, type, owner)
	values (@name, @type, @owner)
		
	SELECT TOP 1 @id = id
	FROM Room
	ORDER BY id DESC

END
GO

/*
declare @id_out int
exec proc_InsertRoom @id_out OUTPUT,'ROOM1','TIPO1', null
select @id_out id
*/

if OBJECT_ID('proc_SelectRoomById ')is not null
	drop procedure proc_SelectRoomById
go

CREATE PROCEDURE proc_SelectRoomById
 @id int
AS
BEGIN
  
	select * from Room
	where id = @id
END
GO

if OBJECT_ID('proc_SelectRoomByName ')is not null
	drop procedure proc_SelectRoomByName
go

CREATE PROCEDURE proc_SelectRoomByName
 @name varchar(100)
AS
BEGIN
  
	select * from Room
	where name = @name
END
GO

if OBJECT_ID('proc_SelectRoomByOwner ')is not null
	drop procedure proc_SelectRoomByOwner
go

CREATE PROCEDURE proc_SelectRoomByOwner
 @owner int
AS
BEGIN
  
	select * from Room
	where owner = @owner
END
GO

if OBJECT_ID('proc_SelectRooms ')is not null
	drop procedure proc_SelectRooms
go

CREATE PROCEDURE proc_SelectRooms
AS
BEGIN
  
	select * from Room
END
GO

/****************************************************
				TABLE User_access
****************************************************/

if OBJECT_ID('proc_InsertUserAccess ')is not null
	drop procedure proc_InsertUserAccess
go

CREATE PROCEDURE proc_InsertUserAccess
 @id		int OUTPUT,
 @room		int,
 @profile	int,
 @datetimeOfAccessStart datetime,
 @datetimeOfAccessEnd datetime
AS
BEGIN
  
	insert into User_access(room, profile, datetime_of_access_start, datetime_of_access_end)
	values	(@room, @profile, @datetimeOfAccessStart, @datetimeOfAccessEnd)
		
	SELECT TOP 1 @id = id
	FROM User_access
	ORDER BY id DESC

END
GO

/*
declare @id_out int
exec proc_InsertUserAccess @id_out OUTPUT, 3, 1, '2015-10-26 00:00:00.000', null
select @id_out id
*/

if OBJECT_ID('proc_UpdateUserAccess')is not null
	drop procedure proc_UpdateUserAccess
go

CREATE PROCEDURE proc_UpdateUserAccess
 @id		int,
 @room		int,
 @profile	int,
 @datetimeOfAccessStart datetime,
 @datetimeOfAccessEnd datetime
AS
BEGIN
  
	update User_access
	set room = @room,
		profile = @profile,
		datetime_of_access_start = @datetimeOfAccessStart,
		datetime_of_access_end = @datetimeOfAccessEnd
	where id = @id
END
GO

if OBJECT_ID('proc_SelectUserAccessById ')is not null
	drop procedure proc_SelectUserAccessById
go

CREATE PROCEDURE proc_SelectUserAccessById
 @id int
AS
BEGIN
  
	select * from User_access
	where id = @id
END
GO

if OBJECT_ID('proc_SelectUserAccessByRoom ')is not null
	drop procedure proc_SelectUserAccessByRoom
go

CREATE PROCEDURE proc_SelectUserAccessByRoom
 @room int
AS
BEGIN
  
	select * from User_access
	where room = @room
END
GO

if OBJECT_ID('proc_SelectUsersAccess ')is not null
	drop procedure proc_SelectUsersAccess
go

CREATE PROCEDURE proc_SelectUsersAccess
AS
BEGIN
  
	select * from User_access
END
GO

/****************************************************
				TABLE Room_access_policy
****************************************************/

if OBJECT_ID('proc_InsertRoomAccessPolicy ')is not null
	drop procedure proc_InsertRoomAccessPolicy
go

CREATE PROCEDURE proc_InsertRoomAccessPolicy
 @id		int OUTPUT,
 @room		int,
 @profile	int,
 @policy	varchar(100)
AS
BEGIN
  
	insert into Room_access_policy(room, profile, policy)
	values	(@room, @profile, @policy)
		
	SELECT TOP 1 @id = id
	FROM Room_access_policy
	ORDER BY id DESC

END
GO

/*
declare @id_out int
exec proc_InsertRoomAccessPolicy @id_out OUTPUT, 1, 1, 'POLICY1'
select @id_out id
*/

if OBJECT_ID('proc_UpdateRoomAccessPolicy')is not null
	drop procedure proc_UpdateRoomAccessPolicy
go

CREATE PROCEDURE proc_UpdateRoomAccessPolicy
 @id		int,
 @room		int,
 @profile	int,
 @policy	varchar(100)
AS
BEGIN
  
	update Room_access_policy
	set room = @room,
		profile = @profile,
		policy = @policy
	where id = @id
END
GO

if OBJECT_ID('proc_SelectRoomAccessPolicyById ')is not null
	drop procedure proc_SelectRoomAccessPolicyById
go

CREATE PROCEDURE proc_SelectRoomAccessPolicyById
 @id int
AS
BEGIN
  
	select * from Room_access_policy
	where id = @id
END
GO

if OBJECT_ID('proc_SelectRoomAccessPolicyByRoom ')is not null
	drop procedure proc_SelectRoomAccessPolicyByRoom
go

CREATE PROCEDURE proc_SelectRoomAccessPolicyByRoom
 @room int
AS
BEGIN
  
	select * from Room_access_policy
	where room = @room
END
GO

if OBJECT_ID('proc_SelectRoomsAccessPolicy ')is not null
	drop procedure proc_SelectRoomsAccessPolicy
go

CREATE PROCEDURE proc_SelectRoomsAccessPolicy
AS
BEGIN
  
	select * from Room_access_policy
END
GO

/****************************************************
				TABLE Invitation
****************************************************/

if OBJECT_ID('proc_InsertInvitation ')is not null
	drop procedure proc_InsertInvitation
go

CREATE PROCEDURE proc_InsertInvitation
 @id		int OUTPUT,
 @room		int,
 @sender	int,
 @receiver	int,
 @state		varchar(100)
AS
BEGIN
  
	insert into Invitation(room, sender, receiver, state)
	values	(@room, @sender, @receiver, @state)
		
	SELECT TOP 1 @id = id
	FROM Invitation
	ORDER BY id DESC

END
GO

/*
declare @id_out int
exec proc_InsertInvitation @id_out OUTPUT, 1, 1, 2,'pending'
select @id_out id
*/

if OBJECT_ID('proc_UpdateInvitation')is not null
	drop procedure proc_UpdateInvitation
go

CREATE PROCEDURE proc_UpdateInvitation
 @id		int,
 @room		int,
 @sender	int,
 @receiver	int,
 @state		varchar(100)
AS
BEGIN
  
	update Invitation
	set room = @room,
		sender = @sender,
		receiver = @receiver,
		state = @state
	where id = @id
END
GO

if OBJECT_ID('proc_SelectInvitationById ')is not null
	drop procedure proc_SelectInvitationById
go

CREATE PROCEDURE proc_SelectInvitationById
 @id int
AS
BEGIN
  
	select * from Invitation
	where id = @id
END
GO

if OBJECT_ID('proc_SelectInvitationBySender ')is not null
	drop procedure proc_SelectInvitationBySender
go

CREATE PROCEDURE proc_SelectInvitationBySender
 @sender int
AS
BEGIN
  
	select * from Invitation
	where sender = @sender
END
GO

if OBJECT_ID('proc_SelectInvitationByReceiver ')is not null
	drop procedure proc_SelectInvitationByReceiver
go

CREATE PROCEDURE proc_SelectInvitationByReceiver
 @receiver int
AS
BEGIN
  
	select * from Invitation
	where receiver = @receiver
END
GO

if OBJECT_ID('proc_SelectInvitations ')is not null
	drop procedure proc_SelectInvitations
go

CREATE PROCEDURE proc_SelectInvitations
AS
BEGIN
  
	select * from Invitation
END
GO

/****************************************************
				TABLE Message
****************************************************/

if OBJECT_ID('proc_InsertMessage ')is not null
	drop procedure proc_InsertMessage
go

CREATE PROCEDURE proc_InsertMessage
 @id		int OUTPUT,
 @room		int,
 @owner		int,
 @datetimeOfCreation datetime,
 @body		varchar(100),
 @state		varchar(100)
AS
BEGIN
  
	insert into Message(room, owner, datetime_of_creation, body, state)
	values	(@room, @owner, @datetimeOfCreation, @body, @state)
		
	SELECT TOP 1 @id = id
	FROM Message
	ORDER BY id DESC

END
GO

/*
declare @id_out int
exec proc_InsertMessage @id_out OUTPUT, 1, 1, '2015-10-26 00:00:00.000', 'First message', 'pending'
select @id_out id
*/

if OBJECT_ID('proc_UpdateMessage')is not null
	drop procedure proc_UpdateMessage
go

CREATE PROCEDURE proc_UpdateMessage
 @id		int,
 @room		int,
 @owner		int,
 @datetimeOfCreation datetime,
 @body		varchar(100),
 @state		varchar(100)
AS
BEGIN
  
	update Message
	set room = @room,
		owner = @owner,
		datetime_of_creation = @datetimeOfCreation,
		body = @body,
		state = @state
	where id = @id
END
GO

if OBJECT_ID('proc_SelectMessageById ')is not null
	drop procedure proc_SelectMessageById
go

CREATE PROCEDURE proc_SelectMessageById
 @id int
AS
BEGIN
  
	select * from Message
	where id = @id
END
GO

if OBJECT_ID('proc_SelectMessageByRoom ')is not null
	drop procedure proc_SelectMessageByRoom
go

CREATE PROCEDURE proc_SelectMessageByRoom
 @room int
AS
BEGIN
  
	select * from Message
	where room = @room
END
GO

if OBJECT_ID('proc_SelectMessageByOwner ')is not null
	drop procedure proc_SelectMessageByOwner
go

CREATE PROCEDURE proc_SelectMessageByOwner
 @owner int
AS
BEGIN
  
	select * from Message
	where owner = @owner
END
GO

if OBJECT_ID('proc_SelectMessages ')is not null
	drop procedure proc_SelectMessages
go

CREATE PROCEDURE proc_SelectMessages
AS
BEGIN
  
	select * from Message
END
GO