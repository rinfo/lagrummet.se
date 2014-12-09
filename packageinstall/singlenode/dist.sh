#!/bin/bash

rm -rf tmp
mkdir tmp

( cd $4/packageinstall/singlenode && source dist.sh $1 )

cp -r $4/packageinstall/singlenode/tmp/* tmp/
mv tmp/git_status.txt tmp/git_status_rdl.txt
rm tmp/install.sh

( cd ../lagrummet && source dist.sh $1 $2 $3 $4 )

cp ../lagrummet/tmp/install_lagrummet_www.sh tmp/
cp ../lagrummet/tmp/lagrummet tmp/
cp ../lagrummet/tmp/lagrummet_dump.sql tmp/
cp ../lagrummet/tmp/lagrummet.se-config.groovy tmp/
cp ../lagrummet/tmp/lagrummet.se.war tmp/
cp ../lagrummet/tmp/mysql-config.sql tmp/
cp ../lagrummet/tmp/server.xml tmp/

cp ./install.sh tmp/

rm rinfo-lagrummet-singlenode.tar.gz

git log -1 | grep 'commit' > tmp/git_status_lagrummet.txt

( cd tmp && tar -zcf ../rinfo-lagrummet-singlenode.tar.gz * )

