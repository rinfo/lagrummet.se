#!/bin/sh

mysql -u root --password=$MYSQL_ROOT_PASSWORD -h lagrummet-db < /sql/setup-mysql.sql
mysql -u root --password=$MYSQL_ROOT_PASSWORD -h lagrummet-db lagrummet < /sql/dump.sql

