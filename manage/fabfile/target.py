from fabric.api import *
from fabfile.sysconf import get_value_from_password_store, PASSWORD_FILE_STANDARD_PASSWORD_PARAM_NAME



@task
def prod():
    """set environment: prod (beta)"""
    env.target = 'prod'
    env.demodata = False
    env.mysql_backup = False  # should be fixed
    env.tomcat_stop = '/etc/init.d/tomcat stop'
    env.tomcat_start = '/etc/init.d/tomcat start'
    env.user = 'rinfo'
    env.deploydir = '/opt/tomcat/webapps/'
    env.warname = 'lagrummet.war'
    env.localwar = env.projectroot+"/target/lagrummet.se-0.8.7.war"
    env.hosts = ['beta.lagrummet.se']
    env.grails_build_env = "production"
    env.roledefs = {
        'rinfo': ['beta.lagrummet.se'],
        'apache': ['beta.lagrummet.se'],
    }
    _initialize_password()


@task
def demo():
    """set environment: demo"""
    env.target = 'demo'
    env.demodata = True
    env.mysql_backup = False
    env.tomcat_stop = '/etc/init.d/tomcat stop'
    env.tomcat_start = '/etc/init.d/tomcat start'
    env.user = 'rinfo'
    env.deploydir = '/opt/tomcat/webapps/'
    env.warname = 'lagrummet.war'
    env.localwar = env.projectroot+"/target/lagrummet.se-0.8.7.war"
    env.hosts = ['demo.lagrummet.se']
    env.grails_build_env = "demo"
    env.roledefs = {
        'rinfo': ['demo.lagrummet.se'],
        'apache': ['demo.lagrummet.se'],
    }
    _initialize_password()


@task
def beta():
    """set environment: beta"""
    env.target = 'beta'
    env.demodata = False
    env.mysql_backup = False
    env.tomcat_stop = '/etc/init.d/tomcat stop'
    env.tomcat_start = '/etc/init.d/tomcat start'
    env.user = 'rinfo'
    env.deploydir = '/opt/tomcat/webapps/'
    env.warname = 'lagrummet.war'
    env.localwar = env.projectroot+"/target/lagrummet.se-0.8.7.war"
    env.hosts = ['beta.lagrummet.se']
    env.grails_build_env = "beta"
    env.roledefs = {
        'rinfo': ['beta.lagrummet.se'],
        'apache': ['beta.lagrummet.se'],
    }
    _initialize_password()


@task
def test():
    """set environment: demo"""
    env.target = 'test'
    env.demodata = True
    env.mysql_backup = False
    env.tomcat_stop = '/etc/init.d/tomcat stop'
    env.tomcat_start = '/etc/init.d/tomcat start'
    env.user = 'rinfo'
    env.deploydir = '/opt/tomcat/webapps/'
    env.warname = 'lagrummet.war'
    env.localwar = env.projectroot + "/target/lagrummet.se-0.8.7.war"
    env.hosts = ['test.lagrummet.se']
    env.grails_build_env = "demo"
    env.roledefs = {
        'rinfo': ['test.lagrummet.se'],
        'apache': ['test.lagrummet.se'],
    }
    _initialize_password()

@task
def dom():
    """set environment: demo"""
    env.target = 'dom'
    env.demodata = True
    env.mysql_backup = False
    env.tomcat_stop = '/etc/init.d/tomcat stop'
    env.tomcat_start = '/etc/init.d/tomcat start'
    env.user = 'rinfo'
    env.deploydir = '/opt/tomcat/webapps/'
    env.warname = 'lagrummet.war'
    env.localwar = env.projectroot + "/target/lagrummet.se-0.8.7.war"
    env.hosts = ['t1.lagr.dev.dom.se']
    env.grails_build_env = "demo"
    env.roledefs = {
        'rinfo': ['t1.lagr.dev.dom.se'],
        'apache': ['t1.lagr.dev.dom.se'],
        }
    _initialize_password()

@task
def ville():
    """set environment: demo"""
    env.target = 'ville'
    env.demodata = True
    env.mysql_backup = False
    env.tomcat_stop = '/etc/init.d/tomcat stop'
    env.tomcat_start = '/etc/init.d/tomcat start'
    env.user = 'rinfo'
    env.deploydir = '/opt/tomcat/webapps/'
    env.warname = 'lagrummet.war'
    env.localwar = env.projectroot + "/target/lagrummet.se-0.8.7.war"
    env.hosts = ['ville.lagrummet.se']
    env.grails_build_env = "demo"
    env.roledefs = {
        'rinfo': ['ville.lagrummet.se'],
        'apache': ['ville.lagrummet.se'],
        }
    _initialize_password()

@task
def valle():
    """set environment: demo"""
    env.target = 'valle'
    env.demodata = True
    env.mysql_backup = False
    env.tomcat_stop = '/etc/init.d/tomcat stop'
    env.tomcat_start = '/etc/init.d/tomcat start'
    env.user = 'rinfo'
    env.deploydir = '/opt/tomcat/webapps/'
    env.warname = 'lagrummet.war'
    env.localwar = env.projectroot + "/target/lagrummet.se-0.8.7.war"
    env.hosts = ['valle.lagrummet.se']
    env.grails_build_env = "demo"
    env.roledefs = {
        'rinfo': ['valle.lagrummet.se'],
        'apache': ['valle.lagrummet.se'],
        }

@task
def regression():
    """set environment: regression"""
    env.target = 'regression'
    env.demodata = True
    env.mysql_backup = False
    env.tomcat_stop = '/etc/init.d/tomcat stop'
    env.tomcat_start = '/etc/init.d/tomcat start'
    env.user = 'rinfo'
    env.deploydir = '/opt/tomcat/webapps/'
    env.warname = 'lagrummet.war'
    env.localwar = env.projectroot+"/target/lagrummet.se-0.8.7.war"
    env.hosts = ['regression.lagrummet.se']
    env.grails_build_env = "prod"
    env.roledefs = {
        'rinfo': ['regression.lagrummet.se'],
    }
    _initialize_password()


@task
def skrapat():
    """set environment: demo"""
    env.target = 'skrapat'
    env.demodata = True
    env.mysql_backup = False
    env.tomcat_stop = '/etc/init.d/tomcat stop'
    env.tomcat_start = '/etc/init.d/tomcat start'
    env.user = 'rinfo'
    env.deploydir = '/opt/tomcat/webapps/'
    env.warname = 'lagrummet.war'
    env.localwar = env.projectroot+"/target/lagrummet.se-0.8.7.war"
    env.hosts = ['skrapat.lagrummet.se']
    env.grails_build_env = "demo"
    env.roledefs = {
        'rinfo': ['skrapat.lagrummet.se'],
        'apache': ['test.lagrummet.se'],
    }
    _initialize_password()


@task
def local():
    """set environment: local"""
    env.target = 'local'
    env.demodata = False
    env.mysql_backup = False
    env.tomcat_stop = '/etc/init.d/tomcat stop'
    env.tomcat_start = '/etc/init.d/tomcat start'
    env.user = 'rinfo'
    env.deploydir = '/opt/tomcat/webapps/'
    env.warname = 'lagrummet.war'
    env.localwar = env.projectroot+"/target/lagrummet.se-0.8.7.war"
    env.hosts = ['localhost:8080/lagrummet.se']
    env.grails_build_env = "test"
    env.roledefs = {
        'rinfo': ['localhost:8080/lagrummet.se'],
    }
    _initialize_password()


def _initialize_password():
    env.password = get_value_from_password_store(PASSWORD_FILE_STANDARD_PASSWORD_PARAM_NAME,env.password)
