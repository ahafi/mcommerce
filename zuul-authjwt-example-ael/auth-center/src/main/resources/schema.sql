--------------- MYSQL ---------------
SET FOREIGN_KEY_CHECKS=0;

drop table if exists user;
CREATE TABLE user
     (
     id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
     username VARCHAR(20) NOT NULL,
      enabled TINYINT  DEFAULT 1 ,
    password VARCHAR(1000)
     ) ;

drop table if exists user_roles;
CREATE TABLE user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,user_id),
  KEY fk_username_idx (user_id),
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user (id));
  
  SET FOREIGN_KEY_CHECKS=1;
  