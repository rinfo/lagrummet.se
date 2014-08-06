#!/bin/bash
# Prepare local install machine for running Fabric scripts
# Will create subdirectory rinfo and checkout develop branch or selected version/feature

WORK_DIR=$(pwd)

# SETUP PATHS
if [ -z "$INSTALL_PATH_RDL" ]; then
	INSTALL_PATH_RDL=/tmp/regressiontest/rinfo/rdl
fi

# Prepare temporary install dir
rm -rf $INSTALL_PATH_RDL
mkdir -p $INSTALL_PATH_RDL

cd $INSTALL_PATH_RDL/..
git clone https://github.com/rinfo/rdl
cd rdl
if [ -z "$1" ]; then
	git checkout develop
else
	git checkout $1
fi

if [ -z "$PW_RINFO" ]; then
        echo "Enter sudo password: "
        read PW_RINFO
fi

if [ -z "$ADMIN_USERNAME" ]; then
        echo "Enter admin username: "
        read ADMIN_USERNAME
fi

if [ -z "$ADMIN_PASSWORD" ]; then
        echo "Enter admin password: "
        read ADMIN_PASSWORD
fi

# RDL (prepare)
cd $INSTALL_PATH_RDL/manage/
fab -p $PW_RINFO target.regression -R service app.service.all:test="0"
fab -p $PW_RINFO target.regression -R main app.main.all:test="0"
fab -p $PW_RINFO target.regression -R service server.restart_apache
fab -p $PW_RINFO target.regression -R service server.restart_tomcat
#sleep 20
#fab -p $PW_RINFO target.regression -R service app.service.ping_start_collect
#fab -p $PW_RINFO target.regression -R main app.main.ping_start_collect_admin
#sleep 20
#fab -p $PW_RINFO target.regression -R main app.main.ping_start_collect_feed
#sleep 60

# lagrummet (setup and test)
cd $WORK_DIR
fab -p $PW_RINFO target.regression sysconf.install_server
fab -p $PW_RINFO target.regression sysconf.config_server
fab -p $PW_RINFO target.regression lagrummet.test_all
EXIT_STATUS=$?
if [ $EXIT_STATUS -ne 0 ];then
   echo "Main module returned $EXIT_STATUS! Exiting!"
   exit $EXIT_STATUS
fi

# RDL (clean up)
cd $INSTALL_PATH_RDL/manage/
fab -p $PW_RINFO target.regression -R service app.service.clean
fab -p $PW_RINFO target.regression -R service app.main.clean
rm -rf $INSTALL_PATH_RDL
