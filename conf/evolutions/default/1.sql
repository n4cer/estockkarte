# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table colony (
  id                        bigserial not null,
  name                      varchar(255),
  race                      varchar(255),
  queen                     varchar(255),
  queen_color               varchar(255),
  queen_ident               varchar(255),
  hive                      varchar(255),
  comment                   varchar(255),
  constraint uq_colony_name unique (name),
  constraint pk_colony primary key (id))
;

create table beekeepers (
  id                        bigserial not null,
  name                      varchar(255),
  email                     varchar(255),
  password_hash             varchar(255),
  constraint uq_beekeepers_name unique (name),
  constraint pk_beekeepers primary key (id))
;




# --- !Downs

drop table if exists colony cascade;

drop table if exists beekeepers cascade;

