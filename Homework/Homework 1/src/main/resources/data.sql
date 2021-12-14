USE lab8_spring;

INSERT INTO users(username, full_name, user_type, account_created) VALUES ('User1', 'User Cool 1', 'STUDENT', current_date());
INSERT INTO users(username, full_name, user_type, account_created) VALUES ('User2', 'User Cool 2', 'STUDENT', current_date());
INSERT INTO users(username, full_name, user_type, account_created) VALUES ('User3', 'User Cool 3', 'STUDENT', current_date());
INSERT INTO users(username, full_name, user_type, account_created) VALUES ('User4', 'User Cool 4', 'STUDENT', current_date());

INSERT INTO user_details(cnp, age) VALUES ('1990226335200', 21);
INSERT INTO user_details(cnp, age) VALUES ('1990126335201', 21);
INSERT INTO user_details(cnp, age) VALUES ('1990326335202', 21);
INSERT INTO user_details(cnp, age) VALUES ('1990426335203', 21);

INSERT INTO users_user_details(users, user_details) VALUES (1, 1);
INSERT INTO users_user_details(users, user_details) VALUES (2, 2);
INSERT INTO users_user_details(users, user_details) VALUES (3, 3);
INSERT INTO users_user_details(users, user_details) VALUES (4, 4);