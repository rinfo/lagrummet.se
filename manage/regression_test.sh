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

# RDL (prepare)
cd $INSTALL_PATH_RDL/manage/
fab target.regression -R service app.service.all:test="0"
fab target.regression -R main app.main.all:test="0"
fab target.regression -R service server.restart_apache
fab target.regression -R service server.restart_tomcat
curl http://rinfo.regression.lagrummet.se/feed/current

# lagrummet (setup and test)
cd $WORK_DIR
fab target.regression sysconf.install_server
fab target.regression sysconf.config_server
sleep 10
curl http://regression.lagrummet.se/rinfo/publ/sfs/1999:175/konsolidering/2011-$
#fab target.regression lagrummet.test_all
#EXIT_STATUS=$?
#if [ $EXIT_STATUS -ne 0 ];then
#   echo "Main module returned $EXIT_STATUS! Exiting!"
#   exit $EXIT_STATUS
#fi

fab target.regression sysconf.config_war:alternate=True
fab target.regression lagrummet.test_all:alternate=True
EXIT_STATUS=$?
if [ $EXIT_STATUS -ne 0 ];then
   echo "Main module returned $EXIT_STATUS! Exiting!"
   exit $EXIT_STATUS
fi

# RDL (clean up)
cd $INSTALL_PATH_RDL/manage/
fab target.regression -R service app.service.clean
fab target.regression -R service app.main.clean
rm -rf $INSTALL_PATH_RDL
