CREATE DATABASE `tp-java`
CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

USE `tp-java`;

CREATE TABLE users (
id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50) NOT NULL UNIQUE,
password_hash VARCHAR(255) NOT NULL,
created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB;

CREATE TABLE contacts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150),
    telephone VARCHAR(20),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_contacts_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

INSERT INTO contacts (user_id, nom, prenom, email, telephone)
VALUES
(1, 'Dupont', 'Marie', 'marie@example.com', '0612345678'),
(1, 'Martin', 'Paul', 'paul@example.com', '0623456789'),
(1, 'Durand', 'Sophie', 'sophie@example.com', '0634567890');