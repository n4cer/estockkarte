# --- !Ups
ALTER TABLE hive_record ALTER COLUMN weight TYPE real USING weight::numeric;

# --- !Downs
ALTER TABLE hive_record ALTER COLUMN weight TYPE varchar(255);
