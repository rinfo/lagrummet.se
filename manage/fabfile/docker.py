from fabric.api import *
import os
import time
import sys
from fabfile.lagrummet import build_war
from fabfile.server import clean_path, create_path
from fabfile.sysconf import get_value_from_password_store, PASSWORD_FILE_DB_USERNAME_PARAM_NAME, \
    PASSWORD_FILE_DB_PASSWORD_PARAM_NAME


@task()
def install():
    # Not working properly
    local_or_remote_cmd('apt-get update')
    local_or_remote_cmd('apt-get install docker.io -y')


@task()
def build_all(name='lagrummet-www', tag=None, rebuild_war=True, push_to_target=True):
    build_db(tag=tag, push_to_target=push_to_target)
    build_config(tag=tag, push_to_target=push_to_target)
    build_lagrummet(tag=tag, rebuild_war=rebuild_war, push_to_target=push_to_target)


@task()
def run_all():
    run_db()
    run_config()
    run_lagrummet()


@task()
def build_lagrummet(name='lagrummet-www', tag=None, rebuild_war=True, push_to_target=True):
    if rebuild_war==True:
        build_war()

    work_dir = '/tmp/docker_work/'
    dockerfile_path = 'manage/docker/lagrummet-www/*'
    create_path(work_dir, use_local=True, clean=True)

    with lcd(env.projectroot):
        local('cp %s %s.' % (env.localwar, work_dir))
        local('cp %s %s.' % (dockerfile_path, work_dir))
        local('cp manage/sysconf/%s/etc/lagrummet.se/lagrummet.se-config.groovy %s.' % (env.target, work_dir))

    with lcd(work_dir):
        docker_build(name, tag, is_local=True)
        if push_to_target:
            tmp_file = '%s.tar' % name
            docker_save(name, tmp_file, is_local=True)
            put(tmp_file)
            docker_load(tmp_file)

    clean_path(work_dir, use_local=True)


@task()
def build_db(name='lagrummet-db', tag=None, push_to_target=True):

    work_dir = '/tmp/docker_work/'
    dockerfile_path = 'manage/docker/lagrummet-db/*'
    create_path(work_dir, use_local=True, clean=True)

    with lcd(env.projectroot):
        local('cp %s %s.' % (dockerfile_path, work_dir))

    with lcd(work_dir):
        docker_build(name, tag, is_local=True)
        if push_to_target:
            tmp_file = '%s.tar' % name
            docker_save(name, tmp_file, is_local=True)
            put(tmp_file)
            docker_load(tmp_file)

    clean_path(work_dir, use_local=True)


@task()
def build_config(name='lagrummet-config', tag=None, push_to_target=True):

    work_dir = '/tmp/docker_work/'
    dockerfile_path = 'manage/docker/lagrummet-config/*'
    create_path(work_dir, use_local=True, clean=True)

    with lcd(env.projectroot):
        local('cp %s %s.' % (dockerfile_path, work_dir))

    with lcd(work_dir):
        docker_build(name, tag, is_local=True)
        if push_to_target:
            tmp_file = '%s.tar' % name
            docker_save(name, tmp_file, is_local=True)
            put(tmp_file)
            docker_load(tmp_file)

    clean_path(work_dir, use_local=True)


@task()
def run_lagrummet(name='lagrummet-www'):
    docker_run(name, name=name, port=8080, link="lagrummet-db:lagrummet-db", daemon=True)


@task()
def run_db(name='lagrummet-db'):
    docker_run(name, name=name, port=3306, params='MYSQL_ROOT_PASSWORD=changeme', daemon=True)


@task()
def run_config(name='lagrummet-config', volume=None):
    #docker_run(name, name=name, params='MYSQL_ROOT_PASSWORD=changeme', link="lagrummet-db:lagrummet-db", volume='/etc/lagrummet.se/:/etc/lagrummet.se/')
    docker_run(name, name=name, params='MYSQL_ROOT_PASSWORD=changeme', link="lagrummet-db:lagrummet-db", volume=volume)


@task()
def run_lagrummet_old(name='lagrummet-www'):
    tomcat_pwd = 'changeme'

    try:
        docker_inspect(name)
        if not is_running(name):
            docker_start(name)
    except DockerException as e:
        print "Tomcat7 docker not found. Will preform docker run."
        docker_pull('mbentley/tomcat7-oracle')
        docker_run('mbentley/tomcat7-oracle', name, port=8080, params='ADMIN_PASS=%s' % tomcat_pwd, daemon=True)

    wait_for_container_to_start(name)

    build_war()
    with lcd(env.projectroot):
        if env.target == 'local':
            file_and_path = env.localwar
        else:
            file_and_path = '/tmp/ROOT.war'
            put(env.localwar, file_and_path)

        local_or_remote_cmd('curl --upload-file %s --user admin:%s http://@%s:8080/manager/text/deploy?path=/&update=true' % (tomcat_pwd, file_and_path, get_docker_ip_address()))
        #curl --upload-file target\debug.war "http://tomcat:tomcat@localhost:8088/manager/deploy?path=/debug&update=true"


@task()
def run_mysql_with_test_database(name='lagrummet-db', local_database_dump_file_name_and_path=None):
    db_root_pwd = 'changeme'
    db_username = get_value_from_password_store(PASSWORD_FILE_DB_USERNAME_PARAM_NAME, default_value='root')
    db_password = get_value_from_password_store(PASSWORD_FILE_DB_PASSWORD_PARAM_NAME, default_value='changeme')

    setup_mysql = False
    try:
        docker_inspect(name)
        if not is_running(name):
            docker_start(name)
    except DockerException as e:
        print "Mysql docker not found. Will preform docker run."
        docker_run('mysql:5.5', name, port=3306, params='MYSQL_ROOT_PASSWORD=%s' % db_root_pwd, daemon=True)
        setup_mysql = True

    wait_for_container_to_start(name)

    with lcd(env.projectroot):
        if local_database_dump_file_name_and_path:
            filename = os.path.basename(local_database_dump_file_name_and_path)
            print "filename '%s'" % filename
            local_path = os.path.dirname(local_database_dump_file_name_and_path)+'/'
            print "localpath '%s'" % local_path
        else:
            filename = 'dump.sql'
            local_path = 'manage/sysconf/%s/mysql/' % env.target
        time.sleep(5)
        if setup_mysql:
            put_and_local_or_remote_cmd('mysql -u root --password=%s -h %s' % (db_root_pwd, get_docker_ip_address()) + ' < %s', 'setup-mysql.sql', local_path)
        put_and_local_or_remote_cmd('mysql -u root --password=%s -h %s' % (db_root_pwd, get_docker_ip_address()) + ' lagrummet < %s', filename, local_path)


@task()
def is_running(name):
    try:
        reply = docker_inspect(name, '.State.Running')
        if reply=='true':
            return True
        if reply=='false':
            return False
        return None
    except DockerException as e:
        print "Received DockerException %s! Ignoring!" % e.message
        return None
    except:
        print "Received unknown error %s! Ignoring!" % sys.exc_info()[0]
        return None

@task()
def delete_all_images(force=False):
    force_cmd = ' -f' if force else ''
    local_or_remote_cmd('docker rmi %s $(docker images -q) ' % force_cmd)


@task()
def delete_all_stopped_containers(force=False):
    force_cmd = ' -f' if force else ''
    local_or_remote_cmd('docker rm %s  $(docker ps -a -q) ' % force_cmd)


@task()
def delete_all_untagged_containers(force=False):
    force_cmd = ' -f' if force else ''
    local_or_remote_cmd('docker rmi %s $(docker images | grep "^<none>" | awk "{print $3}") ' % force_cmd)


def docker_inspect(name, json_path=None):
    json_path_cmd = '-f \'{{ json %s }}\'' % json_path if json_path else ''
    return local_or_remote_cmd('docker inspect %s %s' % (json_path_cmd, name))


def docker_start(name):
    return local_or_remote_cmd('docker start %s' % name )


def docker_save(name, filename, is_local=None):
    return local_or_remote_cmd('docker save %s > %s' % (name, filename), is_local=is_local)


def docker_load(filename):
    return local_or_remote_cmd('docker load < %s' % filename )


def docker_build(name, tag=None, is_local=None):
    tag_param = ':%s' % tag if tag else ''
    local_or_remote_cmd('docker build --rm=true -t %s%s .' % (name,tag_param), capture=False, is_local=is_local)


def docker_run(target, name=None, port=None, params=None, volume=None, link=None, daemon=False):
    name_cmd = ' --name=%s' % name if name else ''
    port_cmd = ' -p %s:%s' % (str(port), str(port)) if port else ''
    daemon_cmd = ' -d' if daemon else ''
    volume_cmd = ' -v %s' % volume if volume else ''
    link_cmd = ' --link %s' % link if link else ''
    params_cmd = ' -e %s' % params if params else ''
    if params:
        try:
            for k,v in params.iteritems():
                params_cmd += ' -e %s=%s' % (k,v)
        except:
            params_cmd = ' -e %s' % params

    cmd = 'docker run %s%s%s%s%s%s %s' % (name_cmd, port_cmd, volume_cmd, params_cmd, link_cmd, daemon_cmd, target)
    return local_or_remote_cmd(cmd, capture=False)


def docker_pull(name):
    local_or_remote_cmd('docker pull %s' % name)


def wait_for_container_to_start(name):
    count = 20
    while not is_running(name):
        time.sleep(1)
        count -= 1
        if count==0:
            raise DockerTimeoutException('Failed to start %s' % name)


def get_docker_ip_address():
    return local_or_remote_cmd("ifconfig docker0 | grep 'inet addr:' | cut -d: -f2 | awk '{print $1}'")


def put_and_local_or_remote_cmd(cmd, filename, local_path):
    print cmd
    try:
        if env.target == 'local':
            reply = local(cmd % (local_path+filename), capture=True)
        else:
            print ""
            put("%s%s" % (local_path,filename), "/tmp")
            reply = sudo(cmd % ('/tmp/%s' % filename))
        return reply
    except SystemExit as e:
        raise DockerException(e.message)
    except:
        print sys.exc_info()[0]
        raise DockerException(sys.exc_info()[0])


def local_or_remote_cmd(cmd, capture=True, is_local=None):
    print cmd
    if is_local is None:
        is_local = env.target == 'local'
    if capture:
        try:
            if is_local:
                reply = local(cmd, capture=True)
            else:
                reply = sudo(cmd)
            return reply
        except SystemExit as e:
            raise DockerException(e.message)
        except:
            print sys.exc_info()[0]
            raise DockerException(sys.exc_info()[0])
    else:
        if is_local:
            local(cmd)
        else:
            sudo(cmd)


class DockerException(Exception):
    pass


class DockerNoSuchImageOrContainerException(DockerException):
    pass


class DockerTimeoutException(DockerException):
    pass