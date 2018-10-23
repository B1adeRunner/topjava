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

INSERT INTO meals (datetime, user_id, description, calories) VALUES
  (to_timestamp('2015-06-30 10:00','YYYY-MM-DD HH24:MI'), 100000, 'Завтрак', 1000),
  (to_timestamp('2015-06-30 13:00','YYYY-MM-DD HH24:MI'), 100000, 'Обед', 500),
  (to_timestamp('2015-06-30 20:00','YYYY-MM-DD HH24:MI'), 100000, 'Ужин', 1000),
  (to_timestamp('2015-06-31 10:00','YYYY-MM-DD HH24:MI'), 100001, 'Завтрак', 1000),
  (to_timestamp('2015-06-31 13:00','YYYY-MM-DD HH24:MI'), 100001, 'Обед', 500),
  (to_timestamp('2015-06-31 20:00','YYYY-MM-DD HH24:MI'), 100001, 'Ужин', 510);
