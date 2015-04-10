create database if not exists lagrummet;
use lagrummet;
CREATE USER 'lagrummet2'@'%' IDENTIFIED BY 'changeme';
GRANT ALL PRIVILEGES ON *.* TO 'lagrummet2'@'%';

