CREATE TABLE links (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    trip_id INTEGER,

    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE
);