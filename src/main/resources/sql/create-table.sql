CREATE TABLE IF NOT EXISTS blog (
blogid int(20) NOT NULL AUTO_INCREMENT,
blogtitle varchar(50) DEFAULT NULL,
article varchar(10000) DEFAULT NULL,
time varchar(50) DEFAULT NULL,
PRIMARY KEY (blogid)
)comment='博客表';

CREATE TABLE IF NOT EXISTS diary(
diaryid int(20) NOT NULL AUTO_INCREMENT,
diary varchar(10000) DEFAULT NULL,
time varchar(20) DEFAULT NULL,
PRIMARY KEY (diaryid)
)comment='日记表';