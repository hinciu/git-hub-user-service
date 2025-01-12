INSERT INTO app_user (id,username, password_hash, enabled)
VALUES (1,'admin', '$2a$10$oWd2/ec/cEEDeJZdCZvFgO8LorzNOQgz8T5WVDve1rVpGEy83YHh6', true)
    ON CONFLICT (username) DO NOTHING;