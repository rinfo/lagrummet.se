FROM debian:wheezy
MAINTAINER Domstolsverket RINFO <rinfo@lagrummet.se>

RUN apt-get update
RUN apt-get install mysql-client -y

ADD *.sql /sql/
ADD *.sh /scripts/

CMD ["/scripts/initialize_config.sh"]
