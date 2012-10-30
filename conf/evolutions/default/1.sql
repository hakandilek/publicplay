# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  name                      varchar(255) not null,
  constraint pk_category primary key (name))
;

create table comment (
  key                       bigint not null,
  content                   varchar(255),
  created_on                timestamp,
  updated_on                timestamp,
  postKey                   bigint,
  created_by                varchar(255),
  updated_by                varchar(255),
  constraint pk_comment primary key (key))
;

create table post (
  key                       bigint not null,
  title                     varchar(255),
  content                   varchar(255),
  rating                    integer,
  created_on                timestamp,
  updated_on                timestamp,
  image_id                  varchar(40),
  created_by                varchar(255),
  category                  varchar(255),
  updated_by                varchar(255),
  constraint pk_post primary key (key))
;

create table post_rating (
  user_key                  varchar(255),
  post_key                  bigint,
  value                     integer,
  created_on                timestamp,
  updated_on                timestamp,
  constraint pk_post_rating primary key (user_key, post_key))
;

create table s3file (
  id                        varchar(40) not null,
  bucket                    varchar(255),
  parent                    varchar(255),
  name                      varchar(255),
  constraint pk_s3file primary key (id))
;

create table security_role (
  key                       bigint not null,
  name                      varchar(50),
  constraint uq_security_role_name unique (name),
  constraint pk_security_role primary key (key))
;

create table user (
  key                       varchar(255) not null,
  original_key              varchar(255),
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
  provider                  varchar(255),
  status                    varchar(1),
  revision                  integer not null,
  constraint ck_user_status check (status in ('N','S','A')),
  constraint pk_user primary key (key))
;


create table user_security_role (
  user_key                       varchar(255) not null,
  security_role_key              bigint not null,
  constraint pk_user_security_role primary key (user_key, security_role_key))
;
create sequence category_seq;

create sequence comment_seq;

create sequence post_seq;

create sequence post_rating_seq;

create sequence security_role_seq;

create sequence user_seq;

alter table comment add constraint fk_comment_post_1 foreign key (postKey) references post (key) on delete restrict on update restrict;
create index ix_comment_post_1 on comment (postKey);
alter table comment add constraint fk_comment_createdBy_2 foreign key (created_by) references user (key) on delete restrict on update restrict;
create index ix_comment_createdBy_2 on comment (created_by);
alter table comment add constraint fk_comment_updatedBy_3 foreign key (updated_by) references user (key) on delete restrict on update restrict;
create index ix_comment_updatedBy_3 on comment (updated_by);
alter table post add constraint fk_post_image_4 foreign key (image_id) references s3file (id) on delete restrict on update restrict;
create index ix_post_image_4 on post (image_id);
alter table post add constraint fk_post_createdBy_5 foreign key (created_by) references user (key) on delete restrict on update restrict;
create index ix_post_createdBy_5 on post (created_by);
alter table post add constraint fk_post_category_6 foreign key (category) references category (name) on delete restrict on update restrict;
create index ix_post_category_6 on post (category);
alter table post add constraint fk_post_updatedBy_7 foreign key (updated_by) references user (key) on delete restrict on update restrict;
create index ix_post_updatedBy_7 on post (updated_by);



alter table user_security_role add constraint fk_user_security_role_user_01 foreign key (user_key) references user (key) on delete restrict on update restrict;

alter table user_security_role add constraint fk_user_security_role_securit_02 foreign key (security_role_key) references security_role (key) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists category;

drop table if exists comment;

drop table if exists post;

drop table if exists post_rating;

drop table if exists s3file;

drop table if exists security_role;

drop table if exists user;

drop table if exists user_security_role;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists category_seq;

drop sequence if exists comment_seq;

drop sequence if exists post_seq;

drop sequence if exists post_rating_seq;

drop sequence if exists security_role_seq;

drop sequence if exists user_seq;

