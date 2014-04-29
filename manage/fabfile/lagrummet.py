import sys
import time
from fabric.api import *
from server import _managed_tomcat_restart
from server import restart_apache
from server import restart_tomcat
from server import stop_tomcat
from server import start_tomcat
from sysconf import setup_mysql
from sysconf import setup_demodata


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
    put(env.localwar, env.deploydir + "ROOT.war")

@task
@roles('rinfo')
def test():
    """Test functions of lagrummet.se regressionstyle"""
    with lcd(env.projectroot+"/test/regression"):
        local("casperjs test . --xunit=casperjs.log")
    #www_url = "http://%s/" % env.roledefs['rinfo'][0]
    #http_response = verify_url_content(www_url)
    #if not http_response in "Gitticah":
    #    raise

@task
@roles('rinfo')
def clean():
    stop_tomcat()
    sudo("rm -rf %(deploydir)s/ROOT" % env)
    sudo("rm -rf %(deploydir)s/ROOT.war" % env)
    start_tomcat()
    sudo("mysqladmin -u root --force drop lagrummet") ## needs priviliges to be pre-configured
    with lcd(env.projectroot):
        put("manage/sysconf/%(target)s/mysql/drop_user.sql" % env,"/tmp")
        sudo("mysql -u root < /tmp/drop_user.sql")


@task
@roles('rinfo')
def test_all():
    try:
        print "****************************** all() *********************************"
        all()
        print "****************************** setup_mysql() *********************************"
        setup_mysql()
        print "****************************** setup_demodata() *********************************"
        setup_demodata()
        print "****************************** restart_apache() *********************************"
        restart_apache()
        print "****************************** restart_tomcat() *********************************"
        restart_tomcat()
        print "****************************** sleep() *********************************"
        msg_sleep(10, "Wait for install to settle")
        print "****************************** test() *********************************"
        test()
    except:
        print "****************************** except: *********************************"
        e = sys.exc_info()[0]
        print e
        sys.exit(1)
    finally:
        print "****************************** finally: *********************************"
        clean()
    print "****************************** end! *********************************"

def msg_sleep(sleep_time, msg=""):
    print "Pause in {0} second(s) for {1}!".format(sleep_time,msg)
    time.sleep(sleep_time)

def verify_url_content(url, string_exists_in_content, sleep_time=15, max_retry=3):
    retry_count = 1
    while retry_count < max_retry:
        respHttp = local("curl %(url)s"%vars(), capture=True)
        if not string_exists_in_content in respHttp:
            print "Could not find %(string_exists_in_content)s in response! Failed! Retry %(retry_count)s of %(max_retry)s."%vars()
            retry_count = retry_count + 1
            time.sleep(sleep_time)
            continue
        return True
    print "#########################################################################################"
    print respHttp
    print "#########################################################################################"
    return False
