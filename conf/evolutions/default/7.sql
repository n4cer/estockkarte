# --- !Ups
insert into hive (name) values ('Mini-Plus');

# --- !Downs
delete from hive where name = 'Mini-Plus';