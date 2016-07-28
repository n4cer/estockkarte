# --- !Ups

create table stand (
  id                        bigserial not null,
  name                      varchar(255),
  first_name                varchar(255),
  last_name                 varchar(255),
  street                    varchar(255),
  zip_code                  varchar(255),
  city                      varchar(255),
  user_id                   bigint,
  constraint pk_stand primary key (id))
;

alter table stand add constraint fk_stand_user_1 foreign key (user_id) references beekeepers (id);
create index ix_stand_user_1 on stand (user_id);

ALTER TABLE colony ADD COLUMN stand_id bigint;

alter table colony add constraint fk_colony_stand_1 foreign key (stand_id) references stand (id);
create index ix_colony_stand_1 on colony (stand_id);

# --- !Downs

drop table if exists stand cascade;

ALTER TABLE colony DROP COLUMN stand_id;