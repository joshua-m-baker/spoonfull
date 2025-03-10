CREATE TABLE restaurant(
    id UUID PRIMARY KEY,
    name TEXT NOT NULL
);

ALTER TABLE experience ADD FOREIGN KEY (restaurant_id) REFERENCES restaurant(id);
