#!/bin/bash 

echo '------------- Install Lagrummet-www multi node'

debconf-set-selections <<< "mysql-server mysql-server/root_password password passwordplaceholderforsed"
debconf-set-selections <<< "mysql-server mysql-server/root_password_again password passwordplaceholderforsed"
apt-get install mysql-server -y

mysql -uroot -ppasswordplaceholderforsed < mysql-config.sql
mysql -uusernameplaceholderforsed -ppasswordplaceholderforsed lagrummet < lagrummet_dump.sql

cp lagrummet /etc/apache2/sites-available/lagrummet
chown root:root /etc/apache2/sites-available/lagrummet
chmod 644 /etc/apache2/sites-available/lagrummet

a2ensite lagrummet

cp lagrummet.se.war /var/lib/tomcat7/webapps/ROOT.war

mkdir -p /etc/lagrummet/
cp lagrummet.se-config.groovy /etc/lagrummet/

cp server.xml /var/lib/tomcat7/conf/
