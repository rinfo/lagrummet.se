from fabric.api import *
import contextlib
import time


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


@task
def backup_db(name, username , password):
    """Back up database and move file to ftp store"""
    # todo this solution uses poor security. Need improvement
    if not name:
        name = "lagrummet_backup_%s" % env.timestamp
    filename = "lagrummet.sql"
    tar_target_path = "/tmp/" % name

    sudo("mysqldump -a lagrummet > %s/%s" % (tar_target_path, filename))
    tar_and_ftp_push(name, "lagrummet", username, password, tar_target_path, "/tmp")


@task
def restore_db(name, username , password):
    """Restore database backup from ftp store"""
    # todo this solution uses poor security. Need improvement
    download_from_ftp()
    untar()
    stop_tomcat()
    sudo("mysql lagrummet < %s" % name)
    stop_start()


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


def tar(filename, target_path, command='czvf'):
    with cd(target_path):
            run('tar %s %s *' % (command, filename))


def untar(filename, target_path, command='xzvf', use_sudo=False):
    tar_cmd = 'tar %s %s' % (command, filename)
    with cd(target_path):
        elif use_sudo:
            sudo(tar_cmd)
        else:
            run(tar_cmd)


def ftp_push(filename, ftp_address, username, password):
    run('curl -T %s %s --user %s:%s --ftp-create-dirs' % (filename, ftp_address, username, password))


def ftp_fetch(filename, ftp_address, target_path, username, password):
    with cd(target_path):
        run('curl %s/%s --user %s:%s --ftp-create-dirs -o %s' % (ftp_address, filename, username, password,
                                                                     filename))

def tar_and_ftp_push(snapshot_name, name, username, password, source_tar_path, target_path):
    file_to_upload = '%s/%s.tar.gz' % (target_path, name)
    tar(file_to_upload, source_tar_path)
    ftp_push(file_to_upload, '%s/%s/%s.tar.gz' % (env.ftp_server_url, snapshot_name, name), username, password,)


def ftp_fetch_and_untar(snapshot_name, name, tmp_path, target_tar_unpack_path, username, password):
    file_to_download = '%s.tar.gz' % name
    ftp_fetch(file_to_download, "%s/%s" % (env.ftp_server_url, snapshot_name), tmp_path, username, password)
    clean_path(target_tar_unpack_path, use_sudo=True)
    untar('%s/%s.tar.gz' % (tmp_path, name), target_tar_unpack_path, use_sudo=True)


def clean_path(tar_target_path, use_sudo=False):
    if use_sudo:
        sudo("rm -rf %s*" % tar_target_path)
    else:
        run("rm -rf %s*" % tar_target_path)


def create_path(tar_target_path):
    run("mkdir -p %s" % tar_target_path)




