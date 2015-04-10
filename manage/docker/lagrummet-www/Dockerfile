FROM debian:wheezy
MAINTAINER Domstolsverket RINFO <rinfo@lagrummet.se>

RUN apt-get update

ENV TOMCATVER 7.0.59

RUN apt-get install wget -y
RUN apt-get install openjdk-7-jdk -y

RUN (wget -O /tmp/tomcat7.tar.gz http://www.us.apache.org/dist/tomcat/tomcat-7/v${TOMCATVER}/bin/apache-tomcat-${TOMCATVER}.tar.gz &&\
        cd /opt &&\
        tar zxf /tmp/tomcat7.tar.gz &&\
        rm /tmp/tomcat7.tar.gz &&\
        mv /opt/apache-tomcat* /opt/tomcat)

RUN rm -rf /opt/tomcat/webapps/*
ADD lagrummet.war /opt/tomcat/webapps/ROOT.war
ADD tomcat-server.xml /opt/tomcat/conf/server.xml
ADD lagrummet.se-config.groovy /etc/lagrummet.se/lagrummet.se-config.groovy

EXPOSE 8080
CMD ["/opt/tomcat/bin/catalina.sh","run"]

