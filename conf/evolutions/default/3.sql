# --- !Ups
ALTER TABLE beekeepers ADD COLUMN registration_number varchar(255);
ALTER TABLE colony ADD COLUMN hive_number varchar(255);

# --- !Downs
ALTER TABLE beekeepers DROP COLUMN registration_number;
ALTER TABLE colony DROP COLUMN hive_number;