# --- create default data

# --- !Ups
insert into TBL_CATEGORY (name) values ('category1');
insert into TBL_CATEGORY (name) values ('category2');
insert into TBL_CATEGORY (name) values ('category3');
insert into TBL_CATEGORY (name) values ('category4');
insert into TBL_CATEGORY (name) values ('category5');

insert into TBL_REPUTATION_VALUE (name, value) values ('RATE_UP', 20);
insert into TBL_REPUTATION_VALUE (name, value) values ('RATE_DOWN', -5);
insert into TBL_REPUTATION_VALUE (name, value) values ('CREATE_POST', 1);

insert into TBL_SECURITY_ROLE (key, name) values (-1, 'admin');
insert into TBL_SECURITY_ROLE (key, name) values (-2, 'role1');
insert into TBL_SECURITY_ROLE (key, name) values (-3, 'role2');
insert into TBL_SECURITY_ROLE (key, name) values (-4, 'role3');
insert into TBL_SECURITY_ROLE (key, name) values (-5, 'role4');

insert into TBL_USER (key, original_key, created_on, updated_on, login_count, last_login, first_name, last_name, email, profile_image_url, provider, revision, status) values ('new_user',           'newKey',    '2012-01-01', '2012-01-01', 1, '2012-01-01', 'New',   'User',  'newbar@test.com',      'http://static.ak.fbcdn.net/rsrc.php/v2/yL/r/HsTZSDw4avx.gif', 'somewhere', 1, 'N');
insert into TBL_USER (key, original_key, created_on, updated_on, login_count, last_login, first_name, last_name, email, profile_image_url, provider, revision, status) values ('testuser',           'testKey',   '2012-01-01', '2012-01-01', 1, '2012-01-01', 'Foo',   'Bar',   'foobar@test.com',      'http://static.ak.fbcdn.net/rsrc.php/v2/yL/r/HsTZSDw4avx.gif', 'somewhere', 1, 'S');
insert into TBL_USER (key, original_key, created_on, updated_on, login_count, last_login, first_name, last_name, email, profile_image_url, provider, revision, status) values ('facebook::807220003','807220003', '2012-10-11', '2012-10-11', 1, '2012-10-11', 'Hakan', 'Dilek', 'hakan@mailinator.com', 'http://graph.facebook.com/807220003/picture',                 'facebook',  1, 'A');
insert into TBL_USER (key, original_key, created_on, updated_on, login_count, last_login, first_name, last_name, email, profile_image_url, provider, revision, status) values ('facebook::758958842','758958842', '2012-10-11', '2012-10-11', 1, '2012-11-11', 'Necip', 'Karakas', 'necipk@gmail.com',   'http://graph.facebook.com/758958842/picture',                 'facebook',  1, 'A');

insert into TBL_USER_SECURITY_ROLE (USER_key, security_role_key) values ('facebook::807220003', -1);
insert into TBL_USER_SECURITY_ROLE (USER_key, security_role_key) values ('facebook::758958842', -1);

insert into TBL_POST (key, category, rating, created_on, updated_on, created_by, updated_by, status, title, content, version) values (-11, 'category1', 345, '2012-01-05 00:01:00', '2012-05-01 00:01:00', 'testuser', 'testuser', 'N', 'some post', 'and  content of it', 1);
insert into TBL_POST (key, category, rating, created_on, updated_on, created_by, updated_by, status, title, content, version) values (-12, 'category1', 123, '2012-01-04 00:02:00', '2012-04-01 00:02:00', 'testuser', 'testuser', 'N', 'another post', 'and another content', 1);
insert into TBL_POST (key, category, rating, created_on, updated_on, created_by, status, title, content, version)             values (-13, 'category1',  23, '2012-01-03 00:03:00', '2012-03-01 00:03:00', 'testuser', 'N', 'yet another post', 'and yet another comment', 1);
insert into TBL_POST (key, category, rating, created_on, updated_on, created_by, status, title, content, version)             values (-14, 'category2',   0, '2012-01-02 00:04:00', '2012-02-01 00:04:00', 'testuser', 'N', 'dummy post', 'some comment on dummy stuff', 1);
insert into TBL_POST (key, category, rating, created_on, updated_on, created_by, status, title, content, version)             values (-15, 'category2', -42, '2012-01-01 00:05:00', '2012-01-01 00:05:00', 'testuser', 'N', 'wow it''s happening', 'yeah, sure!', 1);
insert into TBL_POST (key, category, rating, created_on, updated_on, created_by, status, title, content, version)             values (-16, 'category2', 142, '2012-12-10 00:05:00', '2012-12-10 00:05:00', 'testuser', 'N', 'this is supposed to be a really long post in order to test the line sizes in posts', 'and that should be a really long content of the same post to see how it fits on the screen!', 1);
insert into TBL_POST (key, category, rating, created_on, updated_on, created_by, status, title, content, version)             values (-17, 'category3', 420, '2012-12-10 00:05:00', '2012-12-10 00:05:00', 'testuser', 'E', 'this is supposed to be an another long post in order to test the line sizes in posts', 'and that should be another really long content of the same post to see how it fits on the screen!', 1);
insert into TBL_POST (key, category, rating, created_on, updated_on, created_by, status, title, content, version)             values (-18, 'category2',   0, '2012-01-02 00:04:00', '2012-02-01 00:04:00', 'testuser', 'A', 'another dummy post', 'some comment on dummy stuff', 1);
insert into TBL_POST (key, category, rating, created_on, updated_on, created_by, status, title, content, version)             values (-19, 'category4',   0, '2012-01-02 00:04:00', '2012-02-01 00:04:00', 'testuser', 'A', 'yet dummy post', 'some comment on dummy stuff', 1);
insert into TBL_POST (key, category, rating, created_on, updated_on, created_by, status, title, content, version)             values (-20, 'category2',   0, '2012-01-02 00:04:00', '2012-02-01 00:04:00', 'testuser', 'A', 'this is a dummy post', 'some comment on dummy stuff', 1);
insert into TBL_POST (key, category, rating, created_on, updated_on, created_by, status, title, content, version)             values (-21, 'category1',   0, '2012-01-02 00:04:00', '2012-02-01 00:04:00', 'testuser', 'R', 'this is another dummy post', 'some comment on dummy stuff', 1);

insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-111, -11, '2012-01-01 00:01:00', '2012-01-01 00:01:00', 'testuser', 'N', 'some comment', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-112, -11, '2012-01-02 00:01:00', '2012-01-02 00:01:00', 'testuser', 'N', 'another comment', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-113, -11, '2012-01-03 00:01:00', '2012-01-03 00:01:00', 'testuser', 'N', 'yet another comment', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-114, -11, '2012-01-04 00:01:00', '2012-01-04 00:01:00', 'testuser', 'N', '...and a comment', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-121, -12, '2012-01-05 00:01:00', '2012-01-05 00:01:00', 'testuser', 'N', 'comment 1', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-122, -12, '2012-01-01 00:01:00', '2012-01-01 00:01:00', 'testuser', 'N', 'comment 2', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-123, -12, '2012-01-02 00:01:00', '2012-01-02 00:01:00', 'testuser', 'N', 'comment 3', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-124, -12, '2012-01-03 00:01:00', '2012-01-03 00:01:00', 'testuser', 'A', 'comment 4', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-125, -12, '2012-01-04 00:01:00', '2012-01-04 00:01:00', 'testuser', 'N', 'comment 5', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-126, -12, '2012-01-05 00:01:00', '2012-01-05 00:01:00', 'testuser', 'A', 'comment 6', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-127, -12, '2012-01-01 00:01:00', '2012-01-01 00:01:00', 'testuser', 'A', 'comment 7', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-128, -12, '2012-01-02 00:01:00', '2012-01-02 00:01:00', 'testuser', 'N', 'comment 8', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-131, -13, '2012-01-03 00:01:00', '2012-01-03 00:01:00', 'testuser', 'N', 'comment 1', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-132, -13, '2012-01-04 00:01:00', '2012-01-04 00:01:00', 'testuser', 'N', 'comment 2', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-133, -13, '2012-01-05 00:01:00', '2012-01-05 00:01:00', 'testuser', 'N', 'comment 3', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-141, -14, '2012-01-01 00:01:00', '2012-01-01 00:01:00', 'testuser', 'N', 'comment 1', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-142, -14, '2012-01-02 00:01:00', '2012-01-02 00:01:00', 'testuser', 'N', 'comment 2', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-143, -14, '2012-01-03 00:01:00', '2012-01-03 00:01:00', 'testuser', 'N', 'comment 3', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-151, -15, '2012-01-04 00:01:00', '2012-01-04 00:01:00', 'testuser', 'N', 'comment 1', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-152, -15, '2012-01-05 00:01:00', '2012-01-05 00:01:00', 'testuser', 'N', 'comment 2', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-153, -15, '2012-01-01 00:01:00', '2012-01-01 00:01:00', 'testuser', 'A', 'comment 3', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-161, -16, '2012-11-01 00:01:00', '2012-11-01 00:01:00', 'testuser', 'R', 'comment 1', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-162, -16, '2012-11-02 00:01:00', '2012-11-02 00:01:00', 'testuser', 'N', 'comment 2', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-163, -16, '2012-11-03 00:01:00', '2012-11-03 00:01:00', 'testuser', 'E', 'comment 3', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-171, -17, '2012-11-04 00:01:00', '2012-11-04 00:01:00', 'testuser', 'A', 'comment 1', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-172, -17, '2012-11-05 00:01:00', '2012-11-05 00:01:00', 'testuser', 'N', 'comment 2', 1);
insert into TBL_COMMENT (key, postKey, created_on, updated_on, created_by, status, content, revision) values (-173, -17, '2012-11-01 00:01:00', '2012-11-01 00:01:00', 'testuser', 'E', 'comment 3', 1);

insert into TBL_CONTENT_REPORT (key, content_key, created_on, updated_on, created_by, comment, status, content_type, reason, revision) values  (-10, -11, '2012-11-01 00:01:00', '2012-11-01 00:01:00', 'testuser', 'comment 1', 'N', 'P', 'E', 1); 
insert into TBL_CONTENT_REPORT (key, content_key, created_on, updated_on, created_by, comment, status, content_type, reason, revision) values  (-11, -11, '2012-11-01 00:01:00', '2012-11-01 00:01:00', 'testuser', 'comment 2', 'N', 'P', 'E', 1); 
insert into TBL_CONTENT_REPORT (key, content_key, created_on, updated_on, created_by, comment, status, content_type, reason, revision) values  (-12, -12, '2012-11-01 00:01:00', '2012-11-01 00:01:00', 'testuser', 'comment 3', 'N', 'P', 'E', 1); 
insert into TBL_CONTENT_REPORT (key, content_key, created_on, updated_on, created_by, comment, status, content_type, reason, revision) values  (-13, -111, '2012-11-01 00:01:00', '2012-11-01 00:01:00', 'testuser', 'comment 1', 'N', 'C', 'E', 1); 
insert into TBL_CONTENT_REPORT (key, content_key, created_on, updated_on, created_by, comment, status, content_type, reason, revision) values  (-14, -111, '2012-11-01 00:01:00', '2012-11-01 00:01:00', 'testuser', 'comment 2', 'N', 'C', 'E', 1); 
insert into TBL_CONTENT_REPORT (key, content_key, created_on, updated_on, created_by, comment, status, content_type, reason, revision) values  (-15, -112, '2012-11-01 00:01:00', '2012-11-01 00:01:00', 'testuser', 'comment 3', 'N', 'C', 'E', 1); 

# --- !Downs

delete from TBL_POST where key = -11;
delete from TBL_POST where key = -12;
delete from TBL_POST where key = -13;
delete from TBL_POST where key = -14;
delete from TBL_POST where key = -15;
delete from TBL_COMMENT where key = -111;
delete from TBL_COMMENT where key = -112;
delete from TBL_COMMENT where key = -113;
delete from TBL_COMMENT where key = -114;
delete from TBL_COMMENT where key = -121;
delete from TBL_COMMENT where key = -122;
delete from TBL_COMMENT where key = -123;
delete from TBL_COMMENT where key = -124;
delete from TBL_COMMENT where key = -125;
delete from TBL_COMMENT where key = -126;
delete from TBL_COMMENT where key = -127;
delete from TBL_COMMENT where key = -128;
delete from TBL_COMMENT where key = -131;
delete from TBL_COMMENT where key = -132;
delete from TBL_COMMENT where key = -133;
delete from TBL_COMMENT where key = -141;
delete from TBL_COMMENT where key = -142;
delete from TBL_COMMENT where key = -143;
delete from TBL_COMMENT where key = -151;
delete from TBL_COMMENT where key = -152;
delete from TBL_COMMENT where key = -153;
delete from TBL_USER_TBL_security_role where USER_key = 'new_user';
delete from TBL_USER_TBL_security_role where USER_key = 'testuser';
delete from TBL_USER_TBL_security_role where USER_key = 'facebook::807220003';
delete from TBL_USER_TBL_security_role where USER_key = 'facebook::758958842';
delete from TBL_USER where key = 'new_user';
delete from TBL_USER where key = 'testuser';
delete from TBL_USER where key = 'facebook::807220003';
delete from TBL_USER where key = 'facebook::758958842';
delete from TBL_SECURITY_ROLE where name = 'admin';
delete from TBL_SECURITY_ROLE where name = 'role1';
delete from TBL_SECURITY_ROLE where name = 'role2';
delete from TBL_SECURITY_ROLE where name = 'role3';
delete from TBL_SECURITY_ROLE where name = 'role4';
delete from category where name = 'category1';
delete from category where name = 'category2';
delete from category where name = 'category3';
delete from category where name = 'category4';
delete from category where name = 'category5';
delete from TBL_REPUTATION_VALUE where key = 'rating';
delete from TBL_REPUTATION_VALUE where key = 'createPost'; 
delete from TBL_CONTENT_REPORT where key = -10; 
delete from TBL_CONTENT_REPORT where key = -11; 
delete from TBL_CONTENT_REPORT where key = -12; 
delete from TBL_CONTENT_REPORT where key = -13; 
delete from TBL_CONTENT_REPORT where key = -14; 
delete from TBL_CONTENT_REPORT where key = -15; 
