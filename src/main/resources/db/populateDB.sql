DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories) VALUES
  (100001, '2015-05-31 12:02:01.00000',  dinner, 550 ),
  (100001, '2015-05-31 12:00:00.000000', "dinner", 550 ),
  (100001,'2015-05-31 12:00:00.000000', "dinner", 550),
  (100001,' 2015-05-31 12:00:00.000000', "dinner", 550),
  (100001,'2015-05-31 12:00:00.000000', "dinner", 550);

