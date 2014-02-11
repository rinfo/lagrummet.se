from fabric.api import *
from server import _managed_tomcat_restart


@task()
def all():
    build_war()
    deploy_war()

@task
def build_war():
    """Build lagrummet grails-war locally"""
    with lcd(env.projectroot):
        local("grails clean")
        local("grails test-app")
        local("grails -Dgrails.env=%(grails_build_env)s war" % env)
        local("ls -l target")

@task
@roles('rinfo')
def deploy_war(headless="0"):
    """Deploy locally built war-file to tomcat and restart"""
    #with _managed_tomcat_restart(5, headless):
    put(env.localwar, env.deploydir+"ROOT.war")

