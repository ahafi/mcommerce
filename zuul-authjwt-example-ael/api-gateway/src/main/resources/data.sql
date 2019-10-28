
INSERT INTO USER(id, username, password,enabled) 
VALUES (1, 'admin', 'admin',true);

INSERT INTO USER(id, username, password,enabled) 
VALUES (2, 'ael', 'ael',true);

INSERT INTO USER(id, username, password,enabled) 
VALUES (3, 'toto', 'toto',false);
	
INSERT INTO springbootdb.user_roles (user_role_id,user_id, role)
VALUES (1,1, 'ROLE_USER');
INSERT INTO springbootdb.user_roles (user_role_id, user_id, role)
VALUES (2,1, 'ROLE_ADMIN');
INSERT INTO springbootdb.user_roles (user_role_id, user_id, role)
VALUES (3,2, 'ROLE_USER');

