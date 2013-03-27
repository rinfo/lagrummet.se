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

