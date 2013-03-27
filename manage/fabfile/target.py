from fabric.api import *


def beta():
    """set environment: beta (prod)"""
    env.tomcat_stop = '/etc/init.d/tomcat stop'
    env.tomcat_start = '/etc/init.d/tomcat start'
    env.user = 'rinfo'
    env.deploydir='/opt/tomcat/webapps/'
    env.warname='lagrummet.war'
    env.localwar="../../target/lagrummet.se-0.8.7.war"
    env.key_filename = ["~/.ssh/id_rsa_niklasjohan.pub"]
    env.roledefs = {
        'rinfo': ['beta.lagrummet.se']
    }

