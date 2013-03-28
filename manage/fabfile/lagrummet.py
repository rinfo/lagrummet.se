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



@task
def build_war():
    """Build lagrummet grails-war locally"""
    with lcd(env.projectroot):
        local("grails clean")
        local("grails war")
        local("ls -l target")



@task
@roles('rinfo')
def deploy_war(headless="0"):
    """Deploy locally built war-file to tomcat and restart"""
    with _managed_tomcat_restart(5, headless):
        put(env.localwar, env.deploydir+"lagrummet.war")





