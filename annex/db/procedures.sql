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
			RETURN -1
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
EXECUTE proc_InsertProfile @id_out OUTPUT, 'adele', 'adele'
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
				TABLE 
****************************************************/