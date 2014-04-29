from fabric.api import *
from fabric.contrib.files import append

@task
@roles('rinfo')
def install_server():
    #install_apache()
    install_mysql()

@task
@roles('rinfo')
def config_server():
    config_apache()
    config_war()
    setup_mysql()
    if env.demodata:
        setup_demodata()

@task
@roles('rinfo')
def config_apache():
    """Add lagrummet site-config to existing apache"""
    with lcd(env.projectroot):
        with cd("/etc/apache2"):
            put("manage/sysconf/%(target)s/etc/apache2/sites-available/lagrummet" % env, "sites-available",use_sudo=True)
            sudo("ln -s ../sites-available/lagrummet sites-enabled/lagrummet")

@task
@roles('rinfo')
def config_war():
    """Install config file for lagrummet grails-app"""
    with lcd(env.projectroot):
        sudo("mkdir -p /etc/lagrummet.se")
        put("manage/sysconf/%(target)s/etc/lagrummet.se/lagrummet.se-config.groovy" % env,"/etc/lagrummet.se",use_sudo=True)

@task
@roles('rinfo')
def install_mysql():
    """Install mysql"""
    sudo("apt-get update")
    sudo("apt-get install mysql-server -y")
    create_mysql_config_file()


@task
@roles('rinfo')
def setup_mysql():
    """Setup mysql database and user for lagrummet"""
    with lcd(env.projectroot):
        put("manage/sysconf/%(target)s/mysql/setup-mysql.sql" % env,"/tmp")
        #sudo("mysql -u root -p < /tmp/setup-mysql.sql")
        sudo("mysql -u root < /tmp/setup-mysql.sql")

@task
@roles('rinfo')
def create_mysql_config_file():
    #fabric.contrib.files.append(filename, text, use_sudo=False, partial=False, escape=True, shell=False)
    config_text = "[mysqladmin]\nuser=root\npassword=%(password)s\n" % env
    config_text = config_text + "[mysql]\nuser=root\npassword=%(password)s\n" % env
    config_text = config_text + "[mysqldump]\nuser=root\npassword=%(password)s\n" % env
    with hide('output','running','warnings'), settings(warn_only=True):
        append("/root/.my.cnf",config_text, True)
    sudo("chmod 600 /root/.my.cnf")

@task
@roles('rinfo')
def setup_demodata():
    """Setup mysql demo data"""
    with lcd(env.projectroot):
        put("manage/sysconf/%(target)s/mysql/dump.sql" % env,"/tmp")
        #sudo("mysql -u root -p lagrummet < /tmp/dump.sql")
        sudo("mysql -u root lagrummet < /tmp/dump.sql")



