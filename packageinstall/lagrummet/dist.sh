
rm -rf tmp
mkdir -p tmp

sed 's/dnsplaceholderforsed/'$1'/g' ../../manage/sysconf/template/etc/apache2/sites-available/lagrummet > tmp/lagrummet
sed 's/dnsplaceholderforsed/lagrummet\.se/g' ../../manage/sysconf/template/etc/apache2/sites-available/lagrummet >> tmp/lagrummet

sed 's/usernameplaceholderforsed/'$2'/;s/passwordplaceholderforsed/'$3'/' ../../manage/sysconf/template/mysql/setup-mysql.sql > tmp/mysql-config.sql

cp ~/.ssh/id_rsa.pub tmp/

cp $4/manage/sysconf/template/www/robots.txt tmp/

cp $4/packageinstall/reuse/bootstrap.sh tmp/
cp $4/packageinstall/reuse/install_apache.sh tmp/
cp $4/packageinstall/reuse/install_tomcat.sh tmp/
cp $4/packageinstall/reuse/start_apache.sh tmp/
cp $4/packageinstall/reuse/start_tomcat.sh tmp/

sed 's/dnsplaceholderforsed/'$1'/;s/usernameplaceholderforsed/'$2'/;s/passwordplaceholderforsed/'$3'/' ../../manage/sysconf/template/etc/lagrummet.se/lagrummet.se-config.groovy > tmp/lagrummet.se-config.groovy

#( cd ../../ && grails prod war )

cp ../../target/lagrummet.se-0.8.7.war tmp/lagrummet.se.war

sed 's/usernameplaceholderforsed/'$2'/;s/passwordplaceholderforsed/'$3'/' ./install_lagrummet_www.sh > tmp/install_lagrummet_www.sh
cp ./install.sh tmp/
cp ./server.xml tmp/

cp ~/releaser/2014DEC17/lagrummet_prod_2014DEC17.sql tmp/lagrummet_dump.sql§

git log -1 | grep 'commit' > tmp/git_status.txt

( cd tmp && tar -zcf ../lagrummet-www.tar.gz * )
