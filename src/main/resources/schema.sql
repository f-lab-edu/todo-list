CREATE TABLE IF NOT EXISTS todo (
  id BIGINT AUTO_INCREMENT,
  user_id INT NOT NULL,
  things VARCHAR(45) NOT NULL,
  is_done BOOLEAN NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS member (
    id BIGINT AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL unique,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    emailToken VARCHAR(255),
    joinedAt TIMESTAMP,
    emailTokenGeneratedAt TIMESTAMP,
    tokenExpiration TIMESTAMP,
    isValid BOOLEAN,
    PRIMARY KEY (`id`)
);