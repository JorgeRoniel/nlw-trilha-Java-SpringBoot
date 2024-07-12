CREATE TABLE participants (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    is_confirmed BOOLEAN NOT NULL,
    trip_id INTEGER,
    FOREIGN KEY (trip_id) REFERENCES trips(id)
);