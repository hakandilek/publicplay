# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table comment (
  key                       bigint not null,
  postKey                   bigint,
  content                   varchar(255),
  created_on                timestamp,
  updated_on                timestamp,
  revision                  integer not null,
  constraint pk_comment primary key (key))
;

create table post (
  key                       bigint not null,
  title                     varchar(255),
  content                   varchar(255),
  created_on                timestamp,
  updated_on                timestamp,
  revision                  integer not null,
  constraint pk_post primary key (key))
;

create sequence comment_seq;

create sequence post_seq;

alter table comment add constraint fk_comment_post_1 foreign key (postKey) references post (key) on delete restrict on update restrict;
create index ix_comment_post_1 on comment (postKey);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists comment;

drop table if exists post;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists comment_seq;

drop sequence if exists post_seq;

