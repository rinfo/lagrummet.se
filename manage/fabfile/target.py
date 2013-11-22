from fabric.api import *

@task
def prod():
    """set environment: prod (beta)"""
    env.use_ssh_config = False
    env.tomcat_stop = '/etc/init.d/tomcat stop'
    env.tomcat_start = '/etc/init.d/tomcat start'
    env.user = 'rinfo'
    env.deploydir='/opt/tomcat/webapps/'
    env.warname='lagrummet.war'
    env.localwar=env.projectroot+"/target/lagrummet.se-0.8.7.war"
    env.virtual_host="prod/etc/apache2/sites-available/lagrummet"
    env.groovy_config="prod/etc/lagrummet.se/lagrummet.se-config.groovy"
    env.setup_mysql="prod/mysql/setup-mysql.sql"
    env.hosts = ['beta.lagrummet.se']
    env.roledefs = {
        'rinfo': ['beta.lagrummet.se'],
    }

@task
def demo():
    """set environment: demo"""
    env.use_ssh_config = False
    env.tomcat_stop = '/etc/init.d/tomcat stop'
    env.tomcat_start = '/etc/init.d/tomcat start'
    env.user = 'rinfo'
    env.deploydir='/opt/tomcat/webapps/'
    env.warname='lagrummet.war'
    env.localwar=env.projectroot+"/target/lagrummet.se-0.8.7.war"
    env.virtual_host="demo/etc/apache2/sites-available/lagrummet"
    env.groovy_config="demo/etc/lagrummet.se/lagrummet.se-config.groovy"
    env.setup_mysql="prod/mysql/setup-mysql.sql"
    env.hosts = ['demo.lagrummet.se']
    env.roledefs = {
        'rinfo': ['demo.lagrummet.se'],
    }
