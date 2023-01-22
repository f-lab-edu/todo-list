CREATE TABLE todo (
  id INT NOT NULL,
  user_id INT NOT NULL,
  things VARCHAR(45) NOT NULL,
  is_done BOOLEAN NOT NULL,
  PRIMARY KEY (`id`)
);