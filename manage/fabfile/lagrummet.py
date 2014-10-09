import sys
import time
from fabric.api import *
from server import restart_apache, restore_db, backup_db
from server import restart_tomcat
from server import stop_tomcat
from server import start_tomcat
from sysconf import setup_mysql, get_value_from_password_store, PASSWORD_FILE_ADMIN_USERNAME_PARAM_NAME, \
    PASSWORD_FILE_ADMIN_PASSWORD_PARAM_NAME
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
        if test == 1:
            local("grails test-app")
        local("grails -Dgrails.env=%(grails_build_env)s war --non-interactive" % env)
        local("ls -l target")


@task
@roles('rinfo')
def deploy_war(headless="0"):
    """Deploy locally built war-file to tomcat and restart"""
    #with _managed_tomcat_restart(5, headless):
    put(env.localwar, env.deploydir + "ROOT.war")


def restore_database_for_descructive_tests():
    if env.target in ["regression", "test", "demo"]:
        setup_demodata()


@task
@roles('rinfo')
def test(username='testadmin', password='testadmin', wildcard='*.js', use_password_file=True):
    """Test functions of lagrummet.se regressionstyle"""
    if use_password_file:
        username = get_value_from_password_store(PASSWORD_FILE_ADMIN_USERNAME_PARAM_NAME, username)
        password = get_value_from_password_store(PASSWORD_FILE_ADMIN_PASSWORD_PARAM_NAME, password)

    url = "http://"+env.roledefs['rinfo'][0]
    output = "%s/target/test-reports/" % env.projectroot
    with lcd(env.projectroot + "/test/regression"):
        local("casperjs test %s --xunit=../casperjs.log --includes=../GAblocker.js --includes=../CommonCapserJS.js"
              " --url=%s --target=%s --output=%s  --username=%s --password=%s"
              % (wildcard, url, env.target, output, username, password))


@task
@roles('rinfo')
def db_test(username='testadmin', password='testadmin', wildcard='Add_source*.js Edit_source.js Add_synonym.js Remove_*.js', use_password_file=True, preserve_database=False):
    """Test admin functionality using database

     Run tests reading and writing the database and restore database afterwards.
     Default restore database with dump from GitHub.
     Optionally set preserve_database=True to use backup and restore current database.
    """
    if use_password_file:
        username = get_value_from_password_store(PASSWORD_FILE_ADMIN_USERNAME_PARAM_NAME, username)
        password = get_value_from_password_store(PASSWORD_FILE_ADMIN_PASSWORD_PARAM_NAME, password)

    url = "http://"+env.roledefs['rinfo'][0]
    output = "%s/target/test-reports/" % env.projectroot
    backup_name = 'Backup_for_test_%s_%s' % (env.target, env.timestamp)

    if preserve_database:
        backup_db(name=backup_name)

    try:
        with lcd(env.projectroot + "/test/regression/db"):
            local("casperjs test %s --xunit=../casperjs.log --includes=../../GAblocker.js "
                  "--includes=../../CommonCapserJS.js --url=%s --target=%s --output=%s --username=%s --password=%s"
                  % (wildcard, url, env.target, output, username, password))
    finally:
        if preserve_database:
            restore_db(name=backup_name)
        else:
            restore_database_for_descructive_tests()


@task
@roles('rinfo')
def clean():
    stop_tomcat()
    sudo("rm -rf %(deploydir)s/ROOT" % env)
    sudo("rm -rf %(deploydir)s/ROOT.war" % env)
    start_tomcat()
    try:
        sudo("mysqladmin -u root --force drop lagrummet")  # needs priviliges to be pre-configured
    except:
        e = sys.exc_info()[0]
        print "Ignored! Failed to drop database because %s" % e
    with lcd(env.projectroot):
        put("manage/sysconf/%(target)s/mysql/drop_user.sql" % env, "/tmp")
        sudo("mysql -u root < /tmp/drop_user.sql")


@task
@roles('rinfo')
def test_all(username='testadmin', password='testadmin'):
    try:
        all()
        setup_mysql()
        setup_demodata()
        restart_apache()
        restart_tomcat()
        msg_sleep(10, "Wait for install to settle")
        test()
        if env.target in ["regression", "local", "test"]:
            db_test(username, password)
    except:
        e = sys.exc_info()[0]
        print e
        sys.exit(1)
    finally:
        clean()


def msg_sleep(sleep_time, msg=""):
    print "Pause in {0} second(s) for {1}!".format(sleep_time, msg)
    time.sleep(sleep_time)


def verify_url_content(url, string_exists_in_content, sleep_time=15, max_retry=3):
    retry_count = 1
    resp_http = ""
    while retry_count < max_retry:
        resp_http = local("curl %(url)s" % vars(), capture=True)
        if not string_exists_in_content in resp_http:
            print "Could not find %(string_exists_in_content)s in response! Failed! Retry %(retry_count)s " \
                  "of %(max_retry)s." % vars()
            retry_count += 1
            time.sleep(sleep_time)
            continue
        return True
    print "#########################################################################################"
    print resp_http
    print "#########################################################################################"
    return False

@task
def verify_backup(name, username='', password='', use_password_file=True):
    restore_db(name, username=username, password=password, use_password_file=use_password_file)
    time.sleep(20)
    test(use_password_file=use_password_file)

