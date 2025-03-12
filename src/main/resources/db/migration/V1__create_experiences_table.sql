CREATE TABLE experience (
    id UUID PRIMARY KEY,
    date DATE NOT NULL,
    restaurant_id UUID NOT NULL,
    reviews json NOT NULL,
    created_ts TIMESTAMP NOT NULL,
    updated_ts TIMESTAMP NOT NULL
);