CREATE TABLE IF NOT EXISTS User (
    id BIGINT PRIMARY KEY,
    role VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Task (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    status VARCHAR(50),
    assigneduser BIGINT,
    deadline DATETIME,
    FOREIGN KEY (assigneduser) REFERENCES User(id)
);

CREATE TABLE IF NOT EXISTS Label (
    id BIGINT PRIMARY KEY,
    text VARCHAR(255),
    author BIGINT,
    task BIGINT,
    timestamp DATETIME,
    FOREIGN KEY (author) REFERENCES User(id),
    FOREIGN KEY (task) REFERENCES Task(id)
);

CREATE TABLE IF NOT EXISTS Comment (
    id BIGINT PRIMARY KEY,
    text TEXT,
    author BIGINT,
    timestamp DATETIME,
    FOREIGN KEY (author) REFERENCES User(id)
);
