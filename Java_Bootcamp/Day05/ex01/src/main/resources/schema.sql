DROP TABLE IF EXISTS users, messages, chatrooms, chatroom_users;

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS chatrooms (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    owner_id BIGINT NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS messages (
    id BIGSERIAL PRIMARY KEY,
    author_id BIGINT NOT NULL,
    chatroom_id BIGINT NOT NULL,
    text TEXT,
    date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (chatroom_id) REFERENCES chatrooms(id)
);

CREATE TABLE IF NOT EXISTS chatroom_users (
    user_id BIGINT NOT NULL,
    chatroom_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, chatroom_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (chatroom_id) REFERENCES chatrooms(id)
);