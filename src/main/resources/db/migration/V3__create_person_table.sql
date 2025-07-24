CREATE TABLE person(
    id UUID PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    created_ts TIMESTAMP NOT NULL,
    updated_ts TIMESTAMP NOT NULL
);
