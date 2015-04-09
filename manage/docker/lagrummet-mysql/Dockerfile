FROM mysql
MAINTAINER Domstolsverket RINFO <rinfo@dom.se>

ADD *.sql /sql/

CMD mysql -u root --password=$MYSQL_ROOT_PASSWORD < /sql/setup-mysql.sql
CMD mysql -u root --password=$MYSQL_ROOT_PASSWORD lagrummet < /sql/dump.sql

EXPOSE 3306

CMD ["mysqld"]


