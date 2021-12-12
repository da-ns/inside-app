CREATE DATABASE insideapp CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

CREATE TABLE user (
    id INT NOT NULL AUTO_INCREMENT,
    login VARCHAR(64) NOT NULL,
    password VARCHAR(64) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY login (login)
) ENGINE=InnoDB;

CREATE TABLE message (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    text VARCHAR(512) NOT NULL, -- type TEXT?
    date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB;

insert into user (login, password) values ('panda', 'bamboo');