# --- !Ups
ALTER TABLE hive_record ADD COLUMN weight varchar(255);

# --- !Downs
ALTER TABLE hive_record DROP COLUMN weight;
