DROP DATABASE minigur;
CREATE DATABASE IF NOT EXISTS minigur;
USE minigur;

CREATE TABLE IF NOT EXISTS UserCredentials (
  username VARCHAR(32),
  password VARCHAR(255),
  PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS User (
  id INT(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(32),
  is_admin BOOLEAN,
  PRIMARY KEY (id),
  FOREIGN KEY (username) REFERENCES UserCredentials (username) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Image (
  id INT(11) NOT NULL AUTO_INCREMENT,
  filename VARCHAR(256),
  title VARCHAR (256),
  upload_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  owner_user INT(11),
  PRIMARY KEY (id),
  FOREIGN KEY (owner_user) REFERENCES User (id)
);

CREATE TABLE IF NOT EXISTS Comment (
  id INT(11) NOT NULL AUTO_INCREMENT,
  user_id INT(11),
  image_id INT(11),
  text TEXT,
  post_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES User (id),
  FOREIGN KEY (image_id) REFERENCES Image (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Tag (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(32) NOT NULL UNIQUE,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS TagRelations (
  id INT(11) NOT NULL AUTO_INCREMENT,
  image_id INT(11),
  tag_id INT(11),
  PRIMARY KEY (id),
  FOREIGN KEY (image_id) REFERENCES Image (id) ON DELETE CASCADE,
  FOREIGN KEY (tag_id) REFERENCES Tag (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Rating (
  user_id INT(11),
  image_id INT(11),
  is_upvote BOOLEAN,
  PRIMARY KEY (user_id, image_id),
  FOREIGN KEY (user_id) REFERENCES User (id),
  FOREIGN KEY (image_id) REFERENCES Image (id) ON DELETE CASCADE
);

# Adding 5 User Credentials
INSERT INTO UserCredentials (password, username)
VALUES ('b123', 'bob123');
INSERT INTO UserCredentials (password, username)
VALUES ('69forlife', 'mary27');
INSERT INTO UserCredentials (password, username)
VALUES ('howudoin', 'joe2');
INSERT INTO UserCredentials (password, username)
VALUES ('chicken', 'jane');
INSERT INTO UserCredentials (password, username)
VALUES ('wut27', 'rolf');

# Adding 5 Users
INSERT INTO User (username, is_admin)
VALUES ('bob123', false);
INSERT INTO User (username, is_admin)
VALUES ('mary27', false);
INSERT INTO User (username, is_admin)
VALUES ('joe2', true);
INSERT INTO User (username, is_admin)
VALUES ('jane', false);
INSERT INTO User (username, is_admin)
VALUES ('rolf', true);

# Adding 5 Images
INSERT INTO Image (filename, owner_user)
VALUES ('https://s3-us-west-2.amazonaws.com/minigur/0000001.jpg', 1);
INSERT INTO Image (filename, owner_user)
VALUES ('https://s3-us-west-2.amazonaws.com/minigur/0000002.jpg', 2);
INSERT INTO Image (filename, owner_user)
VALUES ('https://s3-us-west-2.amazonaws.com/minigur/0000003.jpg', 3);
INSERT INTO Image (filename, owner_user)
VALUES ('https://s3-us-west-2.amazonaws.com/minigur/0000004.jpg', 3);
INSERT INTO Image (filename, owner_user)
VALUES ('https://s3-us-west-2.amazonaws.com/minigur/0000005.jpg', 1);

# Adding 5 Comments
INSERT INTO Comment (user_id, image_id, text)
VALUES (1, 1, 'this image is awful');
INSERT INTO Comment (user_id, image_id, text)
VALUES (2, 1, 'yeah like wtf');
INSERT INTO Comment (user_id, image_id, text)
VALUES (3, 2, 'death');
INSERT INTO Comment (user_id, image_id, text)
VALUES (1, 2, 'what??');
INSERT INTO Comment (user_id, image_id, text)
VALUES (4, 4, 'hello');

# Adding 5 Tags
INSERT INTO Tag (name)
VALUES ('flower');
INSERT INTO Tag (name)
VALUES ('dog');
INSERT INTO Tag (name)
VALUES ('cat');
INSERT INTO Tag (name)
VALUES ('tree');
INSERT INTO Tag (name)
VALUES ('death');

# Adding 5 Tag relations
INSERT INTO TagRelations (image_id, tag_id)
VALUES (1, 1);
INSERT INTO TagRelations (image_id, tag_id)
VALUES (1, 2);
INSERT INTO TagRelations (image_id, tag_id)
VALUES (5, 3);
INSERT INTO TagRelations (image_id, tag_id)
VALUES (2, 4);
INSERT INTO TagRelations (image_id, tag_id)
VALUES (3, 5);

# Adding 5 Ratings
INSERT INTO Rating (user_id, image_id, is_upvote)
VALUES (1, 1, true);
INSERT INTO Rating (user_id, image_id, is_upvote)
VALUES (2, 1, false);
INSERT INTO Rating (user_id, image_id, is_upvote)
VALUES (3, 5, false);
INSERT INTO Rating (user_id, image_id, is_upvote)
VALUES (4, 4, true);
INSERT INTO Rating (user_id, image_id, is_upvote)
VALUES (5, 3, false);