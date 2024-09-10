CREATE TABLE Users
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100)        NOT NULL
);

DROP TABLE Users;

CREATE TABLE Locations
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(100)   NOT NULL,
    userId    BIGINT            NOT NULL,
    latitude  DECIMAL(10, 5) NOT NULL,
    longitude DECIMAL(10, 5) NOT NULL,
    FOREIGN KEY (userId) REFERENCES Users (id)
);

DROP TABLE Locations;

DROP TABLE Sessions;

CREATE TABLE Sessions
(
    id        VARCHAR(36) PRIMARY KEY,
    user_Id    BIGINT NOT NULL UNIQUE ,
    expiresAt TIMESTAMP NOT NULL,
    FOREIGN KEY (user_Id) REFERENCES Users (id)
);

