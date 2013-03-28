"""
Lagrummet.se Management Fabric
"""
from datetime import datetime
from os import path as p
from os import sep
from fabric.api import *


##
# Global settings

env.project = 'lagrummet'

env.use_ssh_config = True

env.manageroot = p.normpath(p.join(p.dirname(__file__), '..'))

# NOTE: must be an absolute path:
env.projectroot = p.normpath(p.join(env.manageroot, '..'))

env.toolsdir = sep.join((env.projectroot, 'tools'))

env.builddir = sep.join((env.projectroot, '_build'))

env.docbuild = sep.join((env.builddir, 'documentation'))

env.timestamp = datetime.utcnow().strftime('%Y_%m_%d_%H-%M-%S')
env.datestamp = datetime.utcnow().strftime('%Y-%m-%d')

# env.roledefs defines available roles but the actual host lists for a certain
# role is environment dependent and set up by the targets defined in
# target.py (see i.e. tg_dev_unix)
env.roledefs = {
    'lagrummet': []
}

##
# Import tasks

import target
import server
import sysconf
import lagrummet

