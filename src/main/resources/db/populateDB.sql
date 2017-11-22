DELETE FROM meals;

DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (meal_id,user_id, datetime, description, calories) VALUES
  (100000 ,100000, '2015-05-31 08:02:01.00000', 'плов', 550 ),
  (100001 ,100000, '2015-05-31 12:00:00.000000', 'плов', 550 ),
  (100002,100000, '2015-05-31 14:00:00.000000', 'мясо', 1550),
  (100003,100001,' 2015-05-31 09:00:00.000000', 'плов', 550),
  (100004, 100001,'2015-05-31 12:00:00.000000', 'мясо', 1550),
  (100005, 100001,'2015-05-31 15:00:00.000000', 'плов', 550);


-- DELETE FROM meals  WHERE meal_id=1 AND user_id =100000;
-- INSERT INTO meals (meal_id,user_id, datetime, description, calories) VALUES
--   (1 ,100000, '2015-05-31 08:02:01.00000', 'плов', 550 );
-- SELECT * FROM  meals WHERE meal_id=1 AND user_id=100000;