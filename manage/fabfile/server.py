from fabric.api import *
import contextlib
import time


@roles('rinfo')
def deploy(headless="0"):
    """Deploy locally built war-file to tomcat and restart"""
    with _managed_tomcat_restart(5, headless):
        put(localwar, env.deploydir+"lagrummet.war")



@contextlib.contextmanager
def _managed_tomcat_restart(wait=5, headless=False):
    #_needs_targetenv()
    result = sudo("%(tomcat_stop)s" % env, shell=not headless)
    if result.failed:
        raise OSError(result)
    yield
    print "... restarting in",
    for i in range(wait, 0, -1):
        print "%d..." % i,
        time.sleep(1)
    print
    sudo("%(tomcat_start)s" % env, shell=not headless)


@roles('rinfo')
def restart_tomcat():
    """Restart tomcat"""
    with _managed_tomcat_restart(): pass







