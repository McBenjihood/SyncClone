CREATE TABLE file_index (
    path TEXT PRIMARY KEY,
    size INTEGER NOT NULL,
    modified_time INTEGER NOT NULL,
    hash TEXT NOT NULL
);