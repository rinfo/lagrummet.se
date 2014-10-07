from fabric.api import *
import contextlib
import time
from fabfile.sysconf import get_value_from_password_store, PASSWORD_FILE_FTP_USERNAME_PARAM_NAME, \
    PASSWORD_FILE_FTP_PASSWORD_PARAM_NAME, PASSWORD_FILE_DB_USERNAME_PARAM_NAME, PASSWORD_FILE_DB_PASSWORD_PARAM_NAME


@task
@roles('apache')
def restart_apache():
    """Restart apache"""
    sudo("/etc/init.d/apache2 restart")


@task
@roles('rinfo')
def restart_tomcat():
    """Restart tomcat"""
    with _managed_tomcat_restart():
        pass



@task
def backup_db(name='', username='', password='', db_username='', db_password='', use_password_file=True):
    """Back up database and move file to ftp store"""
    if not name:
        name = "lagrummet_backup_%s_%s" % (env.target, env.timestamp)
    filename = "lagrummet.sql"
    tmp_path = "/tmp/%s" % name
    create_path(tmp_path)

    if use_password_file:
        username = get_value_from_password_store(PASSWORD_FILE_FTP_USERNAME_PARAM_NAME, default_value=username)
        password = get_value_from_password_store(PASSWORD_FILE_FTP_PASSWORD_PARAM_NAME, default_value=password)

    if use_password_file:
        db_username = get_value_from_password_store(PASSWORD_FILE_DB_USERNAME_PARAM_NAME, default_value=db_username)
        db_password = get_value_from_password_store(PASSWORD_FILE_DB_PASSWORD_PARAM_NAME, default_value=db_password)

    run("mysqldump -a -u %s --password=%s lagrummet > %s/%s" % (db_username, db_password, tmp_path, filename))
    pack_and_ftp_push(name, filename, username, password, tmp_path)


@task
def restore_db(name, username='', password='', db_username='', db_password='', use_password_file=True):
    """Restore database backup from ftp store"""
    if not name:
        print "Missing parameter 'name'!"
        return

    tmp_path = "/tmp/%s" % name
    create_path(tmp_path)

    if use_password_file:
        username = get_value_from_password_store(PASSWORD_FILE_FTP_USERNAME_PARAM_NAME, default_value=username)
        password = get_value_from_password_store(PASSWORD_FILE_FTP_PASSWORD_PARAM_NAME, default_value=password)

    if use_password_file:
        db_username = get_value_from_password_store(PASSWORD_FILE_DB_USERNAME_PARAM_NAME, default_value=db_username)
        db_password = get_value_from_password_store(PASSWORD_FILE_DB_PASSWORD_PARAM_NAME, default_value=db_password)

    download_from_ftp_and_unpack(name, "lagrummet.sql", tmp_path, username, password)
    stop_tomcat()
    run("mysql  -u %s --password=%s lagrummet < %s/lagrummet.sql" % (db_username, db_password, tmp_path) )
    start_tomcat(wait=0)


def stop_tomcat(headless=False):
    result = sudo("%(tomcat_stop)s" % env, shell="not headless")
    if result.failed:
        raise OSError(result)


def start_tomcat(wait=5, headless=False):
    print "... restarting in",
    for i in range(wait, 0, -1):
        print "%d..." % i,
        time.sleep(1)
    print
    sudo("%(tomcat_start)s" % env, shell="not headless")


@contextlib.contextmanager
def _managed_tomcat_restart(wait=5, headless=False):
    #_needs_targetenv()
    stop_tomcat(headless)
    yield
    start_tomcat(wait, headless)


def ftp_push(filename, ftp_address, username, password):
    run('curl -T %s %s --user %s:%s --ftp-create-dirs' % (filename, ftp_address, username, password))


def ftp_fetch(filename, ftp_address, target_path, username, password):
    with cd(target_path):
        run('curl %s/%s --user %s:%s --ftp-create-dirs -o %s' % (ftp_address, filename, username, password,
                                                                     filename))

def pack(filename, path):
    source_file_and_path = "%s/%s" % (path,filename)
    target_file_and_path = "%s/%s.gz" % (path,filename)
    run("gzip -c %s > %s" % (source_file_and_path,target_file_and_path))


def unpack(filename, path):
    source_file_and_path = "%s/%s.gz" % (path,filename)
    target_file_and_path = "%s/%s" % (path,filename)
    run("gzip -d -c %s > %s" % (source_file_and_path,target_file_and_path))


def pack_and_ftp_push(snapshot_name, name, username, password, path):
    pack(name, path)
    ftp_push('%s/%s.gz' % (path, name), '%s/%s/%s.gz' % (env.ftp_server_url, snapshot_name, name), username, password,)


def download_from_ftp_and_unpack(snapshot_name, name, path, username, password):
    ftp_fetch('%s.gz' % name, "%s/%s" % (env.ftp_server_url, snapshot_name), path, username, password)
    unpack(name, path)


def clean_path(tar_target_path, use_sudo=False):
    if use_sudo:
        sudo("rm -rf %s*" % tar_target_path)
    else:
        run("rm -rf %s*" % tar_target_path)


def create_path(tar_target_path, use_sudo=False):
    if use_sudo:
        sudo("mkdir -p %s" % tar_target_path)
    else:
        run("mkdir -p %s" % tar_target_path)




