import ConfigParser
from fabric.api import *
from fabric.contrib.files import append
from os.path import expanduser



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
    logrotate_tomcat()


def logrotate_tomcat(test=1):
    put("%(projectroot)s/src/conf/tomcat" % env, "/home/rinfo/tomcat")
    sudo("mv /home/rinfo/tomcat /etc/logrotate.d/tomcat ; chown root:root /etc/logrotate.d/tomcat")
    

@task
@roles('rinfo')
def config_apache():
    """Add lagrummet site-config to existing apache"""
    with lcd(env.projectroot):
        with cd("/etc/apache2"):
            put("manage/sysconf/%(target)s/etc/apache2/sites-available/lagrummet" % env, "sites-available",
                use_sudo=True)
            try:
                sudo("ln -s ../sites-available/lagrummet sites-enabled/lagrummet")
            except:
                print "Ignored failed to create symbolic link!"

@task
@roles('rinfo')
def config_war(alternate=False):
    """Install config file for lagrummet grails-app"""
    with lcd(env.projectroot):
        sudo("mkdir -p /etc/lagrummet.se")
        if alternate:
            put("manage/sysconf/%(target)s/alternate/lagrummet.se-config.groovy" % env, "/etc/lagrummet.se",
                use_sudo=True)
        else:
            put("manage/sysconf/%(target)s/etc/lagrummet.se/lagrummet.se-config.groovy" % env, "/etc/lagrummet.se",
                use_sudo=True)


@task
@roles('rinfo')
def install_mysql():
    """ Install mysql """
    require('password', provided_by=env)
    sudo("apt-get update")
    with hide('output', 'running', 'warnings'), settings(warn_only=True):
        sudo('debconf-set-selections <<< "mysql-server mysql-server/root_password password %(password)s"' % env)
        sudo('debconf-set-selections <<< "mysql-server mysql-server/root_password_again password %(password)s"' % env)
    sudo("apt-get install mysql-server -y")
    create_mysql_config_file()


@task
@roles('rinfo')
def setup_mysql():
    """Setup mysql database and user for lagrummet"""
    with lcd(env.projectroot):
        put("manage/sysconf/%(target)s/mysql/setup-mysql.sql" % env, "/tmp")
        #sudo("mysql -u root -p < /tmp/setup-mysql.sql")
        sudo("mysql -u root < /tmp/setup-mysql.sql")


@task
@roles('rinfo')
def create_mysql_config_file():
    #fabric.contrib.files.append(filename, text, use_sudo=False, partial=False, escape=True, shell=False)
    config_text = "[mysqladmin]\nuser=root\npassword=%(password)s\n" % env
    config_text = config_text + "[mysql]\nuser=root\npassword=%(password)s\n" % env
    config_text = config_text + "[mysqldump]\nuser=root\npassword=%(password)s\n" % env
    with hide('output', 'running', 'warnings'), settings(warn_only=True):
        append("/root/.my.cnf", config_text, True)
    sudo("chmod 600 /root/.my.cnf")


@task
@roles('rinfo')
def setup_demodata():
    """Setup mysql demo data"""
    with lcd(env.projectroot):
        put("manage/sysconf/%(target)s/mysql/dump.sql" % env, "/tmp")
        #sudo("mysql -u root -p lagrummet < /tmp/dump.sql")
        sudo("mysql -u root lagrummet < /tmp/dump.sql")


@task
@roles('rinfo')
def install_mysql_backup():
    require('target', 'roledefs', 'user', provided_by=env)
    if env.target is not 'beta':
        print('Right now, we only backup beta-mysql')
        return
    with lcd(env.projectroot):
        sudo("mkdir -p /root/mysql_backup")
        put("manage/cron/backup_mysql_lagrummet.sh", "/root/mysql_backup/backup_mysql_lagrummet.sh", use_sudo=True)
        sudo("chmod +x /root/mysql_backup/backup_mysql_lagrummet.sh")
        put("manage/cron/crontab", "/root/mysql_backup/crontab", use_sudo=True)
        install_crontab()
        create_public_key()
        install_public_key()


def create_public_key():
    sudo('ssh-keygen -t rsa -N "" -f /root/.ssh/id_rsa')


def install_crontab():
    sudo('crontab /root/mysql_backup/crontab')


def install_public_key():
    sudo('cp /root/.ssh/id_rsa.pub /home/%(user)s/id_rsa_tmp.pub' % env)
    get('~/id_rsa_tmp.pub', '/home/%(user)s/id_rsa_tmp.pub' % env)
    local('cat /home/%(user)s/id_rsa_tmp.pub >> /home/%(user)s/.ssh/authorized_keys' % env)
    local('rm /home/%(user)s/id_rsa_tmp.pub' % env)

PASSWORD_FILE_NAME = 'password.cfg'
PASSWORD_FILE_STANDARD_PASSWORD_PARAM_NAME = 'standard.password'
PASSWORD_FILE_FTP_USERNAME_PARAM_NAME = 'ftp.username'
PASSWORD_FILE_FTP_PASSWORD_PARAM_NAME = 'ftp.password'
PASSWORD_FILE_DB_USERNAME_PARAM_NAME = 'db.username'
PASSWORD_FILE_DB_PASSWORD_PARAM_NAME = 'db.password'
PASSWORD_FILE_ADMIN_USERNAME_PARAM_NAME = 'admin.username'
PASSWORD_FILE_ADMIN_PASSWORD_PARAM_NAME = 'admin.password'
PASSWORD_FILE_PARAMS = (PASSWORD_FILE_STANDARD_PASSWORD_PARAM_NAME,
                        PASSWORD_FILE_FTP_USERNAME_PARAM_NAME,
                        PASSWORD_FILE_FTP_PASSWORD_PARAM_NAME,
                        PASSWORD_FILE_ADMIN_USERNAME_PARAM_NAME,
                        PASSWORD_FILE_ADMIN_PASSWORD_PARAM_NAME,
                        PASSWORD_FILE_DB_USERNAME_PARAM_NAME,
                        PASSWORD_FILE_DB_PASSWORD_PARAM_NAME)

def get_password_file_name_and_path():
    return "%s/%s" % (expanduser("~"), PASSWORD_FILE_NAME)

def get_password_config():
    password_file_name_ = get_password_file_name_and_path()
    try:
        config = ConfigParser.RawConfigParser()
        config.read(password_file_name_)
        #print "Opened %s " % password_file_name_
        return config
    except:
        config = ConfigParser.RawConfigParser()
        print "Config file not found %s " % password_file_name_
        return config


@task
def get_value_from_password_store(name, default_value=''):
    if not name in PASSWORD_FILE_PARAMS:
        print "Param name '%s' not accepted here"
        return default_value
    try:
        value = get_password_config().get(env.target, name)
        if not value:
            return default_value
        return value
    except:
        return default_value
