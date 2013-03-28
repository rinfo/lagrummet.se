from fabric.api import *
import contextlib
import time


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



@task
@roles('apache')
def restart_apache():
    """Restart apache"""
    sudo("/etc/init.d/apache2 restart")


@task
@roles('rinfo')
def restart_tomcat():
    """Restart tomcat"""
    with _managed_tomcat_restart(): pass



@contextlib.contextmanager
def _managed_tomcat_restart(wait=5, headless=False):
    #_needs_targetenv()
    result = sudo("%(tomcat_stop)s" % env, shell=not headless)
    if result.failed:
        raise OSError(result)
    yield
    print "... restarting in",
    for i in range(wait, 0, -1):
        print "%d..." % i,
        time.sleep(1)
    print
    sudo("%(tomcat_start)s" % env, shell=not headless)





