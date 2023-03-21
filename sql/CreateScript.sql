USE Letbillet;

DROP TABLE IF EXISTS [Ticket];
DROP TABLE IF EXISTS [Event];
DROP TABLE IF EXISTS [User];
DROP TABLE IF EXISTS [Role];
DROP TABLE IF EXISTS [Customer];

CREATE TABLE [Role] (
    [Id] INT PRIMARY KEY IDENTITY(1, 1) NOT NULL,
    [Name] NVARCHAR(64) NOT NULL,
);

CREATE TABLE [User] (
    [Id] INT PRIMARY KEY IDENTITY(1, 1) NOT NULL,
    [Username] VARCHAR(30) NOT NULL UNIQUE,
    [PasswordHash] VARCHAR(MAX),
    [Role] INT FOREIGN KEY REFERENCES [Role](Id),
);

CREATE TABLE [Customer] (
    [Id] INT PRIMARY KEY IDENTITY(1, 1) NOT NULL,
    [Name] VARCHAR(256) NOT NULL,
    [Email] VARCHAR(256) NOT NULL UNIQUE,
);

CREATE TABLE [Event] (
    [Id] INT PRIMARY KEY IDENTITY(1, 1) NOT NULL,
    [Name] NVARCHAR(256) NOT NULL,
    [Location] NVARCHAR(256) NOT NULL,
    [Notes] NVARCHAR(MAX),
    [StartTime] DATETIME NOT NULL,
    [EndTime] DATETIME,
    [Price] INT NOT NULL DEFAULT 0,
    [CreatedBy] INT FOREIGN KEY REFERENCES [User](Id)
    -- location guidence
);

CREATE TABLE [Ticket] (
    [Id] INT PRIMARY KEY IDENTITY(1, 1) NOT NULL,
    [Guid] UNIQUEIDENTIFIER DEFAULT NEWID(),
    [IssuedAt] DATETIME DEFAULT GETUTCDATE(),
    [CustomerId] INT FOREIGN KEY REFERENCES [Customer](Id),
    [EventId] INT FOREIGN KEY REFERENCES [Event](Id),
);

-- Insert default data

INSERT INTO [Role] ([Name]) VALUES ('Admin'), ('Event Coordinator');

INSERT INTO [User] ([Username], [PasswordHash], [Role]) VALUES 
    ('admin', 'password', 1),
    ('ec', 'password', 2),
    ('ec3', 'password', 2);

INSERT INTO [Customer] ([Name], [Email]) VALUES 
    ('ANONYMOUS', 'ANONYMOUS'),
    ('Test User 2', 'test2@user.com');

INSERT INTO [Event] ([Name], [Location], [StartTime], [EndTime], [Notes], [CreatedBy]) VALUES ('AU Hack', 'AU', CONVERT(datetime, '2023-03-24 17:00:00'), CONVERT(datetime, '2023-04-26 15:30:00'), 'Mega sejt event!', 2);
INSERT INTO [Event] ([Name], [Location], [StartTime], [Price], [CreatedBy]) VALUES ('Fredagsbar Koncert', 'EASV', CONVERT(datetime, '2023-03-17 13:30:00'), 50, 2);

INSERT INTO [Ticket] ([EventId], [CustomerId]) VALUES
    (1, 1),
    (1, 2),
    (2, 1);