CREATE TABLE Users
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(150)        NOT NULL
);

DROP TABLE users;

CREATE TABLE Locations
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(30) NOT NULL,
    user_Id    BIGINT NOT NULL,
    Latitude  DECIMAL(10, 8) NOT NULL,
    Longitude DECIMAL(11, 8) NOT NULL,
    FOREIGN KEY (user_Id) REFERENCES Users (id) ON DELETE CASCADE
);

DROP TABLE locations;

DROP TABLE sessions;

CREATE TABLE Sessions
(
    id        VARCHAR(36) PRIMARY KEY,
    user_Id   BIGINT    NOT NULL,
    expiresAt TIMESTAMP NOT NULL,
    FOREIGN KEY (user_Id) REFERENCES Users (id) ON DELETE CASCADE
);

