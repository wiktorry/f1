CREATE TABLE teams (
	team_id int PRIMARY KEY AUTO_INCREMENT,
    technical_director varchar(255),
    name varchar(255)
);
CREATE TABLE drivers (
	id int PRIMARY KEY AUTO_INCREMENT,
    first_name varchar(255),
    last_name varchar(255),
    nationality varchar(255),
    team_id int,
    FOREIGN KEY (team_id) REFERENCES teams(team_id)
);