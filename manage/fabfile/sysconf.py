from fabric.api import *

@task
@roles('apache')
def config_apache():
    """Add lagrummet site-config to existing apache"""
    with lcd(env.projectroot):
        with cd("/etc/apache2"):
            put("manage/sysconf/prod/etc/apache2/sites-available/lagrummet", "sites-available")
            sudo("ln -s sites-available/lagrummet sites-enables/lagrummet")


@task
@roles('rinfo')
def config_war():
    """Install config file for lagrummet grails-app"""
    with lcd(env.projectroot):
        sudo("mkdir -p /etc/lagrummet.se")
        put("manage/sysconf/prod/etc/lagrummet.se/lagrummet.se-config.groovy","/etc/lagrummet.se")


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
        put("manage/sysconf/prod/mysql/setup-mysql.sql","/tmp")
        sudo("mysql -u root -p < /tmp/setup-mysql.sql")





