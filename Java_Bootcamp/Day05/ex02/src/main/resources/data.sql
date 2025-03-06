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
('Room4', 4),
('Room5', 5);

INSERT INTO messages (author_id, chatroom_id, text) VALUES
(1, 1, 'Hello everyone!'),
(2, 1, 'Hi user1!'),
(3, 2, 'Welcome to Room2!'),
(4, 3, 'How are you all?'),
(5, 4, 'Let’s discuss the project.');