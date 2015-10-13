if OBJECT_ID('proc_InsertProfile ')is not null
	drop procedure proc_InsertProfile
go

CREATE PROCEDURE proc_InsertProfile 
    @id int OUTPUT,
    @login varchar(100),
	@password varchar(100),
	@type varchar(100)
AS
BEGIN
    insert into Profile(login,password,type)
	values (@login, @password, @type)

	SELECT TOP 1 @id = p.id
	FROM Profile p
	ORDER BY p.id DESC
END
GO

/*
DECLARE @id_out int;
EXECUTE proc_InsertProfile @id_out OUTPUT, 'febo', 'febo', 'admin'
select @id_out as id
*/

if OBJECT_ID('proc_ProfileList ')is not null
	drop procedure proc_ProfileList
go

CREATE PROCEDURE proc_ProfileList
AS
BEGIN
  
	SELECT * FROM Profile
END
GO