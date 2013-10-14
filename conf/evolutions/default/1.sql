# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table TBL_ACTION (
  key                       bigint not null,
  action_type               varchar(14),
  created_on                timestamp,
  updated_on                timestamp,
  target_post_key           bigint,
  target_comment_key        bigint,
  target_user_key           varchar(255),
  created_by                varchar(255),
  revision                  integer not null,
  constraint ck_TBL_ACTION_action_type check (action_type in ('CREATE_COMMENT','CREATE_POST','FOLLOW_USER')),
  constraint pk_TBL_ACTION primary key (key))
;

create table TBL_CATEGORY (
  name                      varchar(255) not null,
  constraint pk_TBL_CATEGORY primary key (name))
;

create table TBL_COMMENT (
  key                       bigint not null,
  content                   varchar(2048),
  created_on                timestamp,
  updated_on                timestamp,
  approved_on               timestamp,
  creator_ip                varchar(255),
  modifier_ip               varchar(255),
  postKey                   bigint,
  created_by                varchar(255),
  updated_by                varchar(255),
  approved_by               varchar(255),
  status                    varchar(1),
  revision                  integer not null,
  constraint ck_TBL_COMMENT_status check (status in ('N','E','U','R','A')),
  constraint pk_TBL_COMMENT primary key (key))
;

create table TBL_CONTENT_REPORT (
  key                       bigint not null,
  created_on                timestamp,
  updated_on                timestamp,
  created_by                varchar(255),
  updated_by                varchar(255),
  content_key               bigint,
  comment                   varchar(512),
  status                    varchar(1),
  content_type              varchar(1),
  reason                    varchar(1),
  revision                  integer not null,
  constraint ck_TBL_CONTENT_REPORT_status check (status in ('N','I','P')),
  constraint ck_TBL_CONTENT_REPORT_content_type check (content_type in ('P','C')),
  constraint ck_TBL_CONTENT_REPORT_reason check (reason in ('E','O','I','C')),
  constraint pk_TBL_CONTENT_REPORT primary key (key))
;

create table TBL_POST (
  key                       bigint not null,
  title                     varchar(512),
  content                   varchar(2048),
  rating                    integer,
  created_on                timestamp,
  updated_on                timestamp,
  approved_on               timestamp,
  creator_ip                varchar(255),
  modifier_ip               varchar(255),
  image_id                  varchar(40),
  created_by                varchar(255),
  updated_by                varchar(255),
  approved_by               varchar(255),
  category                  varchar(255),
  status                    varchar(1),
  version                   integer not null,
  constraint ck_TBL_POST_status check (status in ('N','E','U','R','A')),
  constraint pk_TBL_POST primary key (key))
;

create table TBL_POST_RATING (
  user_key                  varchar(255),
  post_key                  bigint,
  value                     integer,
  created_on                timestamp,
  updated_on                timestamp,
  revision                  integer not null,
  constraint pk_TBL_POST_RATING primary key (user_key, post_key))
;

create table TBL_REPUTATION (
  key                       bigint not null,
  created_on                timestamp,
  updated_on                timestamp,
  reputation_value_key      varchar(255) not null,
  owner_key                 varchar(255) not null,
  revision                  integer not null,
  constraint pk_TBL_REPUTATION primary key (key))
;

create table TBL_REPUTATION_VALUE (
  name                      varchar(255) not null,
  value                     integer,
  constraint pk_TBL_REPUTATION_VALUE primary key (name))
;

create table TBL_S3FILE (
  id                        varchar(40) not null,
  bucket                    varchar(255),
  parent                    varchar(255),
  name                      varchar(255),
  created_on                timestamp,
  updated_on                timestamp,
  revision                  integer not null,
  constraint pk_TBL_S3FILE primary key (id))
;

create table TBL_SECURITY_ROLE (
  key                       bigint not null,
  name                      varchar(50),
  constraint uq_TBL_SECURITY_ROLE_name unique (name),
  constraint pk_TBL_SECURITY_ROLE primary key (key))
;

create table TBL_SOURCE_CONFIG (
  key                       bigint not null,
  source_key                varchar(255),
  name_selector             varchar(255),
  name_value                varchar(255),
  name_required             boolean,
  description_selector      varchar(255),
  description_value         varchar(255),
  description_required      boolean,
  price_selector            varchar(255),
  price_value               varchar(255),
  price_required            boolean,
  discount_price_selector   varchar(255),
  discount_price_value      varchar(255),
  discount_price_required   boolean,
  image_link_selector       varchar(255),
  image_link_value          varchar(255),
  image_link_required       boolean,
  created_on                timestamp,
  updated_on                timestamp,
  revision                  integer not null,
  constraint pk_TBL_SOURCE_CONFIG primary key (key))
;

create table TBL_USER (
  key                       varchar(255) not null,
  original_key              varchar(255),
  created_on                timestamp,
  updated_on                timestamp,
  login_count               integer,
  last_login                timestamp,
  first_name                varchar(255),
  last_name                 varchar(255),
  birthday                  varchar(255),
  email                     varchar(255),
  country                   varchar(255),
  gender                    varchar(255),
  location                  varchar(255),
  profile_image_url         varchar(255),
  provider                  varchar(255),
  access_token              varchar(255),
  access_expires            timestamp,
  status                    varchar(1),
  revision                  integer not null,
  constraint ck_TBL_USER_status check (status in ('N','S','A')),
  constraint pk_TBL_USER primary key (key))
;

create table TBL_USER_FOLLOW (
  source_key                varchar(255),
  target_key                varchar(255),
  created_on                timestamp,
  updated_on                timestamp,
  revision                  integer not null,
  constraint pk_TBL_USER_FOLLOW primary key (source_key, target_key))
;

create table TBL_USERREPUTATION (
  key                       bigint not null,
  value                     integer,
  target_user_key           varchar(255),
  revision                  integer not null,
  constraint pk_TBL_USERREPUTATION primary key (key))
;


create table TBL_USER_SECURITY_ROLE (
  user_key                       varchar(255) not null,
  security_role_key              bigint not null,
  constraint pk_TBL_USER_SECURITY_ROLE primary key (user_key, security_role_key))
;
create sequence TBL_ACTION_seq;

create sequence TBL_CATEGORY_seq;

create sequence TBL_COMMENT_seq;

create sequence TBL_CONTENT_REPORT_seq;

create sequence TBL_POST_seq;

create sequence TBL_POST_RATING_seq;

create sequence TBL_REPUTATION_seq;

create sequence TBL_REPUTATION_VALUE_seq;

create sequence TBL_SECURITY_ROLE_seq;

create sequence TBL_SOURCE_CONFIG_seq;

create sequence TBL_USER_seq;

create sequence TBL_USER_FOLLOW_seq;

create sequence TBL_USERREPUTATION_seq;

alter table TBL_ACTION add constraint fk_TBL_ACTION_targetPost_1 foreign key (target_post_key) references TBL_POST (key) on delete restrict on update restrict;
create index ix_TBL_ACTION_targetPost_1 on TBL_ACTION (target_post_key);
alter table TBL_ACTION add constraint fk_TBL_ACTION_targetComment_2 foreign key (target_comment_key) references TBL_COMMENT (key) on delete restrict on update restrict;
create index ix_TBL_ACTION_targetComment_2 on TBL_ACTION (target_comment_key);
alter table TBL_ACTION add constraint fk_TBL_ACTION_targetUser_3 foreign key (target_user_key) references TBL_USER (key) on delete restrict on update restrict;
create index ix_TBL_ACTION_targetUser_3 on TBL_ACTION (target_user_key);
alter table TBL_ACTION add constraint fk_TBL_ACTION_createdBy_4 foreign key (created_by) references TBL_USER (key) on delete restrict on update restrict;
create index ix_TBL_ACTION_createdBy_4 on TBL_ACTION (created_by);
alter table TBL_COMMENT add constraint fk_TBL_COMMENT_post_5 foreign key (postKey) references TBL_POST (key) on delete restrict on update restrict;
create index ix_TBL_COMMENT_post_5 on TBL_COMMENT (postKey);
alter table TBL_COMMENT add constraint fk_TBL_COMMENT_createdBy_6 foreign key (created_by) references TBL_USER (key) on delete restrict on update restrict;
create index ix_TBL_COMMENT_createdBy_6 on TBL_COMMENT (created_by);
alter table TBL_COMMENT add constraint fk_TBL_COMMENT_updatedBy_7 foreign key (updated_by) references TBL_USER (key) on delete restrict on update restrict;
create index ix_TBL_COMMENT_updatedBy_7 on TBL_COMMENT (updated_by);
alter table TBL_COMMENT add constraint fk_TBL_COMMENT_approvedBy_8 foreign key (approved_by) references TBL_USER (key) on delete restrict on update restrict;
create index ix_TBL_COMMENT_approvedBy_8 on TBL_COMMENT (approved_by);
alter table TBL_CONTENT_REPORT add constraint fk_TBL_CONTENT_REPORT_createdB_9 foreign key (created_by) references TBL_USER (key) on delete restrict on update restrict;
create index ix_TBL_CONTENT_REPORT_createdB_9 on TBL_CONTENT_REPORT (created_by);
alter table TBL_CONTENT_REPORT add constraint fk_TBL_CONTENT_REPORT_updated_10 foreign key (updated_by) references TBL_USER (key) on delete restrict on update restrict;
create index ix_TBL_CONTENT_REPORT_updated_10 on TBL_CONTENT_REPORT (updated_by);
alter table TBL_POST add constraint fk_TBL_POST_image_11 foreign key (image_id) references TBL_S3FILE (id) on delete restrict on update restrict;
create index ix_TBL_POST_image_11 on TBL_POST (image_id);
alter table TBL_POST add constraint fk_TBL_POST_createdBy_12 foreign key (created_by) references TBL_USER (key) on delete restrict on update restrict;
create index ix_TBL_POST_createdBy_12 on TBL_POST (created_by);
alter table TBL_POST add constraint fk_TBL_POST_updatedBy_13 foreign key (updated_by) references TBL_USER (key) on delete restrict on update restrict;
create index ix_TBL_POST_updatedBy_13 on TBL_POST (updated_by);
alter table TBL_POST add constraint fk_TBL_POST_approvedBy_14 foreign key (approved_by) references TBL_USER (key) on delete restrict on update restrict;
create index ix_TBL_POST_approvedBy_14 on TBL_POST (approved_by);
alter table TBL_POST add constraint fk_TBL_POST_category_15 foreign key (category) references TBL_CATEGORY (name) on delete restrict on update restrict;
create index ix_TBL_POST_category_15 on TBL_POST (category);
alter table TBL_USERREPUTATION add constraint fk_TBL_USERREPUTATION_targetU_16 foreign key (target_user_key) references TBL_USER (key) on delete restrict on update restrict;
create index ix_TBL_USERREPUTATION_targetU_16 on TBL_USERREPUTATION (target_user_key);



alter table TBL_USER_SECURITY_ROLE add constraint fk_TBL_USER_SECURITY_ROLE_TBL_01 foreign key (user_key) references TBL_USER (key) on delete restrict on update restrict;

alter table TBL_USER_SECURITY_ROLE add constraint fk_TBL_USER_SECURITY_ROLE_TBL_02 foreign key (security_role_key) references TBL_SECURITY_ROLE (key) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists TBL_ACTION;

drop table if exists TBL_CATEGORY;

drop table if exists TBL_COMMENT;

drop table if exists TBL_CONTENT_REPORT;

drop table if exists TBL_POST;

drop table if exists TBL_POST_RATING;

drop table if exists TBL_REPUTATION;

drop table if exists TBL_REPUTATION_VALUE;

drop table if exists TBL_S3FILE;

drop table if exists TBL_SECURITY_ROLE;

drop table if exists TBL_SOURCE_CONFIG;

drop table if exists TBL_USER;

drop table if exists TBL_USER_SECURITY_ROLE;

drop table if exists TBL_USER_FOLLOW;

drop table if exists TBL_USERREPUTATION;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists TBL_ACTION_seq;

drop sequence if exists TBL_CATEGORY_seq;

drop sequence if exists TBL_COMMENT_seq;

drop sequence if exists TBL_CONTENT_REPORT_seq;

drop sequence if exists TBL_POST_seq;

drop sequence if exists TBL_POST_RATING_seq;

drop sequence if exists TBL_REPUTATION_seq;

drop sequence if exists TBL_REPUTATION_VALUE_seq;

drop sequence if exists TBL_SECURITY_ROLE_seq;

drop sequence if exists TBL_SOURCE_CONFIG_seq;

drop sequence if exists TBL_USER_seq;

drop sequence if exists TBL_USER_FOLLOW_seq;

drop sequence if exists TBL_USERREPUTATION_seq;

