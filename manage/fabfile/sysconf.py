from fabric.api import *

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

@task
@roles('rinfo')
def config_apache():
    """Add lagrummet site-config to existing apache"""
    with lcd(env.projectroot):
        with cd("/etc/apache2"):
            put("manage/sysconf/%(virtual_host)s" % env, "sites-available",use_sudo=True)
            sudo("ln -s sites-available/lagrummet sites-enabled/lagrummet")


@task
@roles('rinfo')
def config_war():
    """Install config file for lagrummet grails-app"""
    with lcd(env.projectroot):
        sudo("mkdir -p /etc/lagrummet.se")
        put("manage/sysconf/%(groovy_config)s" % env,"/etc/lagrummet.se",use_sudo=True)


@task
@roles('rinfo')
def install_mysql():
    """Install mysql"""
    sudo("apt-get update")
    sudo("apt-get install mysql-server")


@task
@roles('rinfo')
def setup_mysql():
    """Setup mysql database and user for lagrummet"""
    with lcd(env.projectroot):
        put("manage/sysconf/%(setup_mysql)s" % env,"/tmp")
        sudo("mysql -u root -p < /tmp/setup-mysql.sql")





