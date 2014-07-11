create database lagrummet;
use lagrummet;
CREATE USER 'lagrummet2'@'localhost' IDENTIFIED BY 'changeme';
GRANT ALL PRIVILEGES ON *.* TO 'lagrummet2'@'localhost';

