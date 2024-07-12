CREATE TABLE trips (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    destination VARCHAR(255) NOT NULL,
    starts_at TIMESTAMP NOT NULL,
    ends_at TIMESTAMP NOT NULL,
    is_confirmed BOOLEAN NOT NULL,
    onwer_name VARCHAR(255) NOT NULL,
    onwer_email VARCHAR(255) NOT NULL

);