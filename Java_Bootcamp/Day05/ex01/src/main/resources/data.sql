INSERT INTO users (login, password) VALUES
('user1', 'pass1'),
('user2', 'pass2'),
('user3', 'pass3'),
('user4', 'pass4'),
('user5', 'pass5');

INSERT INTO chatrooms (name, owner_id) VALUES
('Room1', 1),
('Room2', 2),
('Room3', 3),
('Room4', 3),
('Room5', 5);

INSERT INTO messages (author_id, chatroom_id, text) VALUES
(1, 1, 'Hello everyone!'),
(2, 1, 'Hi user1!'),
(3, 2, 'Welcome to Room2!'),
(4, 3, 'How are you all?'),
(5, 4, 'Let’s discuss the project.');

INSERT INTO chatroom_users (user_id, chatroom_id) VALUES
(1, 1),
(1, 2),
(2, 1),
(2, 3),
(3, 2),
(3, 4),
(4, 3),
(4, 5),
(5, 4),
(5, 1);