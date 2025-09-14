INSERT INTO users (name, email, age) VALUES ('John Doe', 'john@example.com', 30);
INSERT INTO users (name, email, age) VALUES ('Jane Smith', 'jane@example.com', 25);


-- Posts
INSERT INTO posts (title, content, created_at, user_id)
VALUES ('First post', 'This is Alice''s first post', NOW(), 1);

INSERT INTO posts (title, content, created_at, user_id)
VALUES ('Second post', 'Another post by Alice', NOW(), 1);

INSERT INTO posts (title, content, created_at, user_id)
VALUES ('Bob''s post', 'Bob is writing something here', NOW(), 2);