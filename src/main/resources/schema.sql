CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS items (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(512),
    owner_id BIGINT REFERENCES users(id),
    available BOOLEAN NOT NULL,
    request_id BIGINT REFERENCES requests(id)
);

CREATE TABLE IF NOT EXISTS bookings (
    id BIGSERIAL PRIMARY KEY,
    item_id BIGINT REFERENCES items(id),
    booker_id BIGINT REFERENCES users(id),
    start_date TIMESTAMP WITHOUT TIME ZONE,
    end_date TIMESTAMP WITHOUT TIME ZONE,
    status VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS comments (
    id BIGSERIAL PRIMARY KEY,
    item_id BIGINT REFERENCES items(id),
    user_id BIGINT REFERENCES users(id),
    comment_text VARCHAR(512),
    created TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS requests (
    id BIGSERIAL PRIMARY KEY,
    creator_id REFERENCES users(id),
    description VARCHAR(512),
    created TIMESTAMP WITHOUT TIME ZONE
)