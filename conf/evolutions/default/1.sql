# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table colony (
  id                        bigserial not null,
  name                      varchar(255),
  number                    varchar(255),
  race_id                   bigint,
  queen                     varchar(255),
  queen_color               integer,
  queen_ident               varchar(255),
  hive_id                   bigint,
  comment                   varchar(255),
  user_id                   bigint,
  visible                   boolean default false,
  shot_url                  varchar(10),
  constraint uq_colony_shot_url unique (shot_url),
  constraint pk_colony primary key (id))
;

create table hive (
  id                        bigserial not null,
  name                      varchar(255),
  constraint pk_hive primary key (id))
;

create table hive_record (
  id                        bigserial not null,
  colony_id                 bigint,
  date                      timestamp,
  gentleness                integer,
  swarming                  boolean,
  strength                  integer,
  queen                     boolean,
  eggs                      boolean,
  maggots                   boolean,
  capped_brood              boolean,
  queen_cells               boolean,
  feeding                   varchar(255),
  varroa_check              varchar(255),
  varroa                    varchar(255),
  comment                   varchar(255),
  user_id                   bigint,
  constraint pk_hive_record primary key (id))
;

create table race (
  id                        bigserial not null,
  name                      varchar(255),
  constraint pk_race primary key (id))
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
  visible                   boolean default false,
  active                    boolean default true,
  constraint uq_beekeepers_name unique (name),
  constraint uq_beekeepers_email unique (email),
  constraint pk_beekeepers primary key (id))
;

alter table colony add constraint fk_colony_race_1 foreign key (race_id) references race (id);
create index ix_colony_race_1 on colony (race_id);
alter table colony add constraint fk_colony_hive_2 foreign key (hive_id) references hive (id);
create index ix_colony_hive_2 on colony (hive_id);
alter table colony add constraint fk_colony_user_3 foreign key (user_id) references beekeepers (id);
create index ix_colony_user_3 on colony (user_id);
alter table hive_record add constraint fk_hive_record_colony_4 foreign key (colony_id) references colony (id);
create index ix_hive_record_colony_4 on hive_record (colony_id);
alter table hive_record add constraint fk_hive_record_user_5 foreign key (user_id) references beekeepers (id);
create index ix_hive_record_user_5 on hive_record (user_id);



# --- !Downs

drop table if exists colony cascade;

drop table if exists hive cascade;

drop table if exists hive_record cascade;

drop table if exists race cascade;

drop table if exists beekeepers cascade;

