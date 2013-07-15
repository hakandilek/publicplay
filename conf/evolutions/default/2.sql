# --- create default data

# --- !Ups
insert into TBL_CATEGORY (name) values ('category');

insert into TBL_REPUTATION_VALUE (name, value) values ('RATE_UP', 20);
insert into TBL_REPUTATION_VALUE (name, value) values ('RATE_DOWN', -5);
insert into TBL_REPUTATION_VALUE (name, value) values ('CREATE_POST', 1);

insert into TBL_SECURITY_ROLE (key, name) values (-1, 'admin');

insert into TBL_USER (key, original_key, created_on, updated_on, login_count, last_login, first_name, last_name, email, profile_image_url, provider, revision, status) values ('facebook::807220003','807220003', '2012-10-11', '2012-10-11', 1, '2012-10-11', 'Hakan', 'Dilek', 'hakan@mailinator.com', 'http://graph.facebook.com/807220003/picture',                 'facebook',  1, 'A');
insert into TBL_USER (key, original_key, created_on, updated_on, login_count, last_login, first_name, last_name, email, profile_image_url, provider, revision, status) values ('facebook::758958842','758958842', '2012-10-11', '2012-10-11', 1, '2012-11-11', 'Necip', 'Karakas', 'necipk@gmail.com',   'http://graph.facebook.com/758958842/picture',                 'facebook',  1, 'A');

insert into TBL_USER_SECURITY_ROLE (USER_key, security_role_key) values ('facebook::807220003', -1);
insert into TBL_USER_SECURITY_ROLE (USER_key, security_role_key) values ('facebook::758958842', -1);

# --- !Downs

delete from TBL_USER_TBL_security_role where USER_key = 'facebook::807220003';
delete from TBL_USER_TBL_security_role where USER_key = 'facebook::758958842';

delete from TBL_USER where key = 'facebook::807220003';
delete from TBL_USER where key = 'facebook::758958842';

delete from TBL_SECURITY_ROLE where name = 'admin';

delete from category where name = 'category';

delete from TBL_REPUTATION_VALUE where key = 'RATE_UP';
delete from TBL_REPUTATION_VALUE where key = 'RATE_DOWN'; 
delete from TBL_REPUTATION_VALUE where key = 'CREATE_POST';
