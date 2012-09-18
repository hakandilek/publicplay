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

create table post_rating (
  value                     bigint,
  created_on                timestamp,
  updated_on                timestamp)
;

create table user (
  key                       varchar(255) not null,
  created_on                timestamp,
  updated_on                timestamp,
  login_count               integer,
  last_login                timestamp,
  first_name                varchar(255),
  last_name                 varchar(255),
  birthdate                 timestamp,
  email                     varchar(255),
  country                   varchar(255),
  gender                    varchar(255),
  location                  varchar(255),
  profile_image_url         varchar(255),
  provider_id               varchar(255),
  revision                  integer not null,
  constraint pk_user primary key (key))
;

create sequence comment_seq;

create sequence post_seq;

create sequence user_seq;

alter table comment add constraint fk_comment_post_1 foreign key (postKey) references post (key) on delete restrict on update restrict;
create index ix_comment_post_1 on comment (postKey);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists comment;

drop table if exists post;

drop table if exists post_rating;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists comment_seq;

drop sequence if exists post_seq;

drop sequence if exists user_seq;

