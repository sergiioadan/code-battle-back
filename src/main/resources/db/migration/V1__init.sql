

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE points (
    id BIGSERIAL PRIMARY KEY,
    player VARCHAR(100) NOT NULL,
    level VARCHAR(50) NOT NULL,
    points INTEGER NOT NULL,
    CONSTRAINT unique_player_level UNIQUE (player, level)
);
