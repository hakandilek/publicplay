# --- create default data

# --- !Ups

insert into post (key, created_on, updated_on, revision, title, content) values (-11, '2012-01-01 00:01:00', '2012-01-01 00:01:00', 1, 'some post', 'and  content of it');
insert into post (key, created_on, updated_on, revision, title, content) values (-12, '2012-01-02 00:02:00', '2012-02-01 00:02:00', 1, 'another post', 'and another content');
insert into post (key, created_on, updated_on, revision, title, content) values (-13, '2012-01-03 00:03:00', '2012-03-01 00:03:00', 1, 'yet another post', 'and yet another comment');
insert into post (key, created_on, updated_on, revision, title, content) values (-14, '2012-01-04 00:04:00', '2012-04-01 00:04:00', 1, 'dummy post', 'some comment on dummy stuff');
insert into post (key, created_on, updated_on, revision, title, content) values (-15, '2012-01-05 00:05:00', '2012-05-01 00:05:00', 1, 'wow it''s happening', 'yeah, sure!');

insert into comment (key, postKey, created_on, updated_on, revision, content) values (-111, -11, '2012-01-01 00:01:00', '2012-01-01 00:01:00', 1, 'some comment');
insert into comment (key, postKey, created_on, updated_on, revision, content) values (-112, -11, '2012-01-02 00:01:00', '2012-01-02 00:01:00', 1, 'another comment');
insert into comment (key, postKey, created_on, updated_on, revision, content) values (-113, -11, '2012-01-03 00:01:00', '2012-01-03 00:01:00', 1, 'yet another comment');
insert into comment (key, postKey, created_on, updated_on, revision, content) values (-114, -11, '2012-01-04 00:01:00', '2012-01-04 00:01:00', 1, '...and a comment');
insert into comment (key, postKey, created_on, updated_on, revision, content) values (-121, -12, '2012-01-05 00:01:00', '2012-01-05 00:01:00', 1, 'comment 1');
insert into comment (key, postKey, created_on, updated_on, revision, content) values (-122, -12, '2012-01-01 00:01:00', '2012-01-01 00:01:00', 1, 'comment 2');
insert into comment (key, postKey, created_on, updated_on, revision, content) values (-123, -12, '2012-01-02 00:01:00', '2012-01-02 00:01:00', 1, 'comment 3');
insert into comment (key, postKey, created_on, updated_on, revision, content) values (-124, -12, '2012-01-03 00:01:00', '2012-01-03 00:01:00', 1, 'comment 4');
insert into comment (key, postKey, created_on, updated_on, revision, content) values (-125, -12, '2012-01-04 00:01:00', '2012-01-04 00:01:00', 1, 'comment 5');
insert into comment (key, postKey, created_on, updated_on, revision, content) values (-126, -12, '2012-01-05 00:01:00', '2012-01-05 00:01:00', 1, 'comment 6');
insert into comment (key, postKey, created_on, updated_on, revision, content) values (-127, -12, '2012-01-01 00:01:00', '2012-01-01 00:01:00', 1, 'comment 7');
insert into comment (key, postKey, created_on, updated_on, revision, content) values (-128, -12, '2012-01-02 00:01:00', '2012-01-02 00:01:00', 1, 'comment 8');
insert into comment (key, postKey, created_on, updated_on, revision, content) values (-131, -13, '2012-01-03 00:01:00', '2012-01-03 00:01:00', 1, 'comment 1');
insert into comment (key, postKey, created_on, updated_on, revision, content) values (-132, -13, '2012-01-04 00:01:00', '2012-01-04 00:01:00', 1, 'comment 2');
insert into comment (key, postKey, created_on, updated_on, revision, content) values (-133, -13, '2012-01-05 00:01:00', '2012-01-05 00:01:00', 1, 'comment 3');
insert into comment (key, postKey, created_on, updated_on, revision, content) values (-141, -14, '2012-01-01 00:01:00', '2012-01-01 00:01:00', 1, 'comment 1');
insert into comment (key, postKey, created_on, updated_on, revision, content) values (-142, -14, '2012-01-02 00:01:00', '2012-01-02 00:01:00', 1, 'comment 2');
insert into comment (key, postKey, created_on, updated_on, revision, content) values (-143, -14, '2012-01-03 00:01:00', '2012-01-03 00:01:00', 1, 'comment 3');
insert into comment (key, postKey, created_on, updated_on, revision, content) values (-151, -15, '2012-01-04 00:01:00', '2012-01-04 00:01:00', 1, 'comment 1');
insert into comment (key, postKey, created_on, updated_on, revision, content) values (-152, -15, '2012-01-05 00:01:00', '2012-01-05 00:01:00', 1, 'comment 2');
insert into comment (key, postKey, created_on, updated_on, revision, content) values (-153, -15, '2012-01-01 00:01:00', '2012-01-01 00:01:00', 1, 'comment 3');

# --- !Downs

delete from post where key = -11;
delete from post where key = -12;
delete from post where key = -13;
delete from post where key = -14;
delete from post where key = -15;
delete from comment where key = -111;
delete from comment where key = -112;
delete from comment where key = -113;
delete from comment where key = -114;
delete from comment where key = -121;
delete from comment where key = -122;
delete from comment where key = -123;
delete from comment where key = -124;
delete from comment where key = -125;
delete from comment where key = -126;
delete from comment where key = -127;
delete from comment where key = -128;
delete from comment where key = -131;
delete from comment where key = -132;
delete from comment where key = -133;
delete from comment where key = -141;
delete from comment where key = -142;
delete from comment where key = -143;
delete from comment where key = -151;
delete from comment where key = -152;
delete from comment where key = -153;
