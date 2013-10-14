#!/bin/bash          
af logout
af login hakandilek@gmail.com
git update-index --assume-unchanged conf/application.conf 
patch -N conf/application.conf conf/application.conf.diff
play clean dist
af update
