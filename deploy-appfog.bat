@echo off

call af logout
call af login hakandilek@gmail.com
git update-index --assume-unchanged conf/application.conf 
patch -N conf/application.conf conf/application.conf.diff
call play clean dist
call af update
