from fabric.api import *

@task
def prod():
    """set environment: prod (beta)"""
    env.target='prod'
    env.demodata=False
    env.tomcat_stop = '/etc/init.d/tomcat stop'
    env.tomcat_start = '/etc/init.d/tomcat start'
    env.user = 'rinfo'
    env.deploydir='/opt/tomcat/webapps/'
    env.warname='lagrummet.war'
    env.localwar=env.projectroot+"/target/lagrummet.se-0.8.7.war"
    env.hosts = ['beta.lagrummet.se']
    env.grails_build_env="production"
    env.roledefs = {
        'rinfo': ['beta.lagrummet.se'],
        'apache': ['beta.lagrummet.se'],
    }

@task
def demo():
    """set environment: demo"""
    env.target='demo'
    env.demodata=True
    env.tomcat_stop = '/etc/init.d/tomcat stop'
    env.tomcat_start = '/etc/init.d/tomcat start'
    env.user = 'rinfo'
    env.deploydir='/opt/tomcat/webapps/'
    env.warname='lagrummet.war'
    env.localwar=env.projectroot+"/target/lagrummet.se-0.8.7.war"
    env.hosts = ['demo.lagrummet.se']
    env.grails_build_env="demo"
    env.roledefs = {
        'rinfo': ['demo.lagrummet.se'],
        'apache': ['demo.lagrummet.se'],
    }

@task
def beta():
    """set environment: beta"""
    env.target='beta'
    env.demodata=False
    env.tomcat_stop = '/etc/init.d/tomcat stop'
    env.tomcat_start = '/etc/init.d/tomcat start'
    env.user = 'rinfo'
    env.deploydir='/opt/tomcat/webapps/'
    env.warname='lagrummet.war'
    env.localwar=env.projectroot+"/target/lagrummet.se-0.8.7.war"
    env.hosts = ['beta.lagrummet.se']
    env.grails_build_env="beta"
    env.roledefs = {
        'rinfo': ['beta.lagrummet.se'],
        'apache': ['beta.lagrummet.se'],
    }

@task
def test():
    """set environment: demo"""
    env.target='test'
    env.demodata=True
    env.tomcat_stop = '/etc/init.d/tomcat stop'
    env.tomcat_start = '/etc/init.d/tomcat start'
    env.user = 'rinfo'
    env.deploydir='/opt/tomcat/webapps/'
    env.warname='lagrummet.war'
    env.localwar=env.projectroot+"/target/lagrummet.se-0.8.7.war"
    env.hosts = ['test.lagrummet.se']
    env.grails_build_env="demo"
    env.roledefs = {
        'rinfo': ['test.lagrummet.se'],
        'apache': ['test.lagrummet.se'],
    }

@task
def skrapat():
    """set environment: demo"""
    env.target='skrapat'
    env.demodata=True
    env.tomcat_stop = '/etc/init.d/tomcat stop'
    env.tomcat_start = '/etc/init.d/tomcat start'
    env.user = 'rinfo'
    env.deploydir='/opt/tomcat/webapps/'
    env.warname='lagrummet.war'
    env.localwar=env.projectroot+"/target/lagrummet.se-0.8.7.war"
    env.hosts = ['skrapat.lagrummet.se']
    env.grails_build_env="demo"
    env.roledefs = {
        'rinfo': ['skrapat.lagrummet.se'],
    }
