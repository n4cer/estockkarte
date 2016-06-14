# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table colony (
  id                        bigserial not null,
  name                      varchar(255),
  number                    varchar(255),
  race                      varchar(255),
  queen                     varchar(255),
  queen_color               varchar(255),
  queen_ident               varchar(255),
  hive                      varchar(255),
  comment                   varchar(255),
  user_id                   bigint,
  constraint uq_colony_name unique (name),
  constraint pk_colony primary key (id))
;

create table hive_records (
  id                        bigserial not null,
  gentleness                integer,
  swarming                  integer,
  strength                  integer,
  queen                     boolean,
  maggots                   boolean,
  capped_brood              boolean,
  queen_cells               boolean,
  feeding                   varchar(255),
  varroa_check              varchar(255),
  varroa                    varchar(255),
  comment                   varchar(255),
  user_id                   bigint,
  constraint pk_hive_records primary key (id))
;

create table beekeepers (
  id                        bigserial not null,
  name                      varchar(255),
  first_name                varchar(255),
  last_name                 varchar(255),
  street                    varchar(255),
  zip_code                  varchar(255),
  city                      varchar(255),
  email                     varchar(255),
  telephone                 varchar(255),
  password_hash             varchar(255),
  constraint uq_beekeepers_name unique (name),
  constraint pk_beekeepers primary key (id))
;

alter table colony add constraint fk_colony_user_1 foreign key (user_id) references beekeepers (id);
create index ix_colony_user_1 on colony (user_id);
alter table hive_records add constraint fk_hive_records_user_2 foreign key (user_id) references beekeepers (id);
create index ix_hive_records_user_2 on hive_records (user_id);



# --- !Downs

drop table if exists colony cascade;

drop table if exists hive_records cascade;

drop table if exists beekeepers cascade;

