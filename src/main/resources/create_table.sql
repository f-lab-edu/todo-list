CREATE TABLE todo (
  id INT NOT NULL,
  user_id INT NOT NULL,
  things VARCHAR(45) NOT NULL,
  is_done BOOLEAN NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE member (
    id BIGINT AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    emailToken VARCHAR(255),
    joinedAt TIMESTAMP,
    emailTokenGeneratedAt TIMESTAMP,
    isValid BOOLEAN,
    PRIMARY KEY (`id`)
);