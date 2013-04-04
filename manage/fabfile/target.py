from fabric.api import *

@task
def prod():
    """set environment: prod (beta)"""
    env.tomcat_stop = '/etc/init.d/tomcat stop'
    env.tomcat_start = '/etc/init.d/tomcat start'
    env.user = 'rinfo'
    env.deploydir='/opt/tomcat/webapps/'
    env.warname='lagrummet.war'
    env.localwar=env.projectroot+"/target/lagrummet.se-0.8.7.war"
    env.roledefs = {
        'rinfo': ['beta.lagrummet.se'],
        'apache': ['beta.lagrummet.se']
    }