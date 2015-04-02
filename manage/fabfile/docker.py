from fabric.api import *
import os
import time
import sys
from fabfile.lagrummet import build_war
from fabfile.server import clean_path, create_path


@task()
def install():
    local_or_remote_cmd('sudo apt-get install docker.io -y')


@task()
def build_lagrummet(name='lagrummet-www', tag=None):
    build_war()

    work_dir = '/tmp/docker_work/'
    dockerfile_path = 'manage/docker/lagrummet-www/*'
    clean_path(work_dir, use_local=True)
    create_path(work_dir, use_local=True)

    with lcd(env.projectroot):
        local('cp %s %s.' % (env.localwar, work_dir))
        local('cp %s %s.' % (dockerfile_path, work_dir))
        local('cp manage/sysconf/local/etc/lagrummet.se/lagrummet.se-config.groovy %s.' % (work_dir))

    with lcd(work_dir):
        docker_build(name, tag)


@task()
def run_lagrummet(name='lagrummet-www', volume='/etc/lagrummet.se/:/etc/lagrummet.se/'):
    docker_run(name, port=8080, volume=volume , daemon=True)


@task()
def run_lagrummet_old(name='lagrummet-www'):
    tomcat_pwd = 'changeme'

    try:
        docker_inspect(name)
        if not isRunning(name):
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
def run_mysql_with_test_database(name='lagrummet-mysql', local_database_dump_file_name_and_path=None):
    db_pwd = 'changeme'

    setup_mysql = False
    try:
        docker_inspect(name)
        if not isRunning(name):
            docker_start(name)
    except DockerException as e:
        print "Mysql docker not found. Will preform docker run."
        docker_run('mysql:5.5', name, port=3306, params='MYSQL_ROOT_PASSWORD=%s' % db_pwd, daemon=True)
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
            put_and_local_or_remote_cmd('mysql -u root --password=%s -h %s' % (db_pwd, get_docker_ip_address()) + ' < %s', 'setup-mysql.sql', local_path)
        put_and_local_or_remote_cmd('mysql -u root --password=%s -h %s' % (db_pwd, get_docker_ip_address()) + ' lagrummet < %s', filename, local_path)


@task()
def isRunning(name):
    reply = docker_inspect(name, '.State.Running')
    if reply=='true':
        return True
    if reply=='false':
        return False
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
    return local_or_remote_cmd('docker inspect %s %s' % (json_path_cmd, name) )


def docker_start(name):
    return local_or_remote_cmd('docker start %s' % name )


def docker_build(name, tag=None):
    tag_param = ':%s' % tag if tag else ''
    local_or_remote_cmd('docker build --rm=true -t %s%s .' % (name,tag_param) )


def docker_run(target, name=None, port=None, params=None, volume=None, daemon=False):
    name_cmd = ' --name=%s' % name if name else ''
    port_cmd = ' -p %s:%s' % (str(port), str(port)) if port else ''
    daemon_cmd = ' -d' if daemon else ''
    volume_cmd = ' -v %s' % volume if volume else ''
    params_cmd = ' -e %s' % params if params else ''
    params_cmd = ''
    if params:
        try:
            for k,v in params.iteritems():
                params_cmd += ' -e %s=%s' % (k,v)
        except:
            params_cmd = ' -e %s' % params

    cmd = 'docker run %s%s%s%s %s' % (name_cmd, port_cmd, params_cmd, daemon_cmd, target)
    return local_or_remote_cmd(cmd)


def docker_pull(name):
    local_or_remote_cmd('docker pull %s' % name)


def wait_for_container_to_start(name):
    count = 20
    while not isRunning(name):
        time.sleep(1)
        count -= 1
        if count==0:
            raise DockerTimeoutException('Failed to start %s' % name)


def get_docker_ip_address():
    return local_or_remote_cmd("ifconfig docker0 | grep 'inet addr:' | cut -d: -f2 | awk '{print $1}'")


def put_and_local_or_remote_cmd(cmd, filename, local_path):
    try:
        if env.target == 'local':
            reply = local(cmd % (local_path+filename), capture=True)
        else:
            put("%s%s" % (local_path,filename), "/tmp")
            reply = sudo(cmd % filename, capture=True)
        return reply
    except SystemExit as e:
        raise DockerException(e.message)
    except:
        raise DockerException(sys.exc_info()[0])


def local_or_remote_cmd(cmd):
    try:
        if env.target == 'local':
            reply = local(cmd, capture=True)
        else:
            reply = sudo(cmd, capture=True)
        return reply
    except SystemExit as e:
        raise DockerException(e.message)
    except:
        raise DockerException(sys.exc_info()[0])


class DockerException(Exception):
    pass


class DockerNoSuchImageOrContainerException(DockerException):
    pass


class DockerTimeoutException(DockerException):
    pass