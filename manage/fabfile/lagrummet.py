from fabric.api import *

@task
def build_war():
    """Build war-file locally"""
    with lcd(env.projectroot):
        local("grails clean")
        local("grails war")
        local("ls -l target")
