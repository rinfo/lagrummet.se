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
    if env.mysql_backup:
        create_mysql_config_file()
        install_mysql_backup()

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

@task
@roles('rinfo')
def install_mysql_backup():
    if env.target is not 'beta':
        print('Right now, we only backup beta-mysql')
        return
    with lcd(env.projectroot):
        sudo("mkdir -p /root/mysql_backup")
        put("manage/cron/backup_mysql_lagrummet.sh", "/root/mysql_backup/",use_sudo=True)
        sudo('chmod +x /root/mysql_backup/backup_mysql_lagrummet.sh')
        put("manage/cron/crontab", "/root/mysql_backup/",use_sudo=True)
        install_crontab()
        create_public_key()
        install_public_key()


def create_public_key():
    sudo('ssh-keygen -t rsa -N "" -f /root/.ssh/id_rsa')

def install_crontab():
    sudo('crontab -l > /root/mysql_backup/crontab_backup')
    sudo('crontab /root/mysql_backup/crontab')

def install_public_key():
    get('/root/.ssh/id_rsa.pub', '~/id_rsa_tmp.pub', use_sudo=True)
    local('cat id_rsa_tmp.pub >> .ssh/authorized_keys')
    local('rm ~/id_rsa_tmp.pub')
