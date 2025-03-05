CREATE TABLE experience (
    id UUID PRIMARY KEY,
    date DATE NOT NULL,
    restaurant_name TEXT NOT NULL,
    restaurant_id UUID NOT NULL,
    reviews json NOT NULL,
    rating INT CHECK (rating BETWEEN 1 AND 5)
);