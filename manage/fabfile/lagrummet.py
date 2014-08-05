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
def all(test=1):
    build_war(test)
    deploy_war()

@task
def build_war(test=1):
    """Build lagrummet grails-war locally"""
    with lcd(env.projectroot):
        local("grails clean")
        if test==1:
            local("grails test-app")
        local("grails -Dgrails.env=%(grails_build_env)s war --non-interactive" % env)
        local("ls -l target")

@task
@roles('rinfo')
def deploy_war(headless="0"):
    """Deploy locally built war-file to tomcat and restart"""
    #with _managed_tomcat_restart(5, headless):
    put(env.localwar, env.deploydir + "ROOT.war")

@task
@roles('rinfo')
def test(test_selector='*.js', testpath='%s recorded/%s', username='testadmin', password='testadmin'):
    """Test functions of lagrummet.se regressionstyle"""
    url="http:\\"+env.roledefs['rinfo'][0]
    output = "%s/target/test-reports/" % env.projectroot
    if env.target in ["local","regression"]:
        with lcd(env.projectroot+"/test/regression"):
            local("casperjs test recorded/admin/*.js --xunit=../casperjs.log --url=%s --target=%s --output=%s --username=%s --password=%s" % (url,env.target,output,username,password))
    if env.target != "local":
        with lcd(env.projectroot+"/test/regression"):
            testpath=testpath % (test_selector,test_selector)
            local("casperjs test %s --xunit=../casperjs.log --url=%s --target=%s --output=%s  --username=%s --password=%s" % (testpath,url,env.target,output,username,password))
        with lcd(env.projectroot+"/test/ui"):
            local("casperjs test %s --xunit=../casperjs.log --url=%s --target=%s --output=%s --username=%s --password=%s" % (test_selector,url,env.target,output,username,password) )

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
        all()
        setup_mysql()
        setup_demodata()
        restart_apache()
        restart_tomcat()
        msg_sleep(10, "Wait for install to settle")
        test()
    except:
        e = sys.exc_info()[0]
        print e
        sys.exit(1)
    finally:
        clean()

def msg_sleep(sleep_time, msg=""):
    print "Pause in {0} second(s) for {1}!".format(sleep_time,msg)
    time.sleep(sleep_time)

def verify_url_content(url, string_exists_in_content, sleep_time=15, max_retry=3):
    retry_count = 1
    respHttp = ""
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
