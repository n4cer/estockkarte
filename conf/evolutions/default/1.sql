# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table beekeepers (
  id                        bigserial not null,
  name                      varchar(255),
  email                     varchar(255),
  password_hash             varchar(255),
  constraint uq_beekeepers_name unique (name),
  constraint pk_beekeepers primary key (id))
;




# --- !Downs

drop table if exists beekeepers cascade;

