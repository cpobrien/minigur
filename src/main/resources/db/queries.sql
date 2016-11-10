
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
INSERT INTO TagRelations (image_id, tag_id, usr_id)
VALUES (1, 1, 8);
INSERT INTO TagRelations (image_id, tag_id, usr_id)
VALUES (1, 2, 8);
INSERT INTO TagRelations (image_id, tag_id, usr_id)
VALUES (5, 3, 8);
INSERT INTO TagRelations (image_id, tag_id, usr_id)
VALUES (2, 4, 8);
INSERT INTO TagRelations (image_id, tag_id, usr_id)
VALUES (3, 5, 8);

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