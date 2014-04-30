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




