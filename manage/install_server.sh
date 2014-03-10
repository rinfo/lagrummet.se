# Prepare local install machine for running Fabric scripts
# Copy the contents of this script into a shell script file and remember to make it executable. Then run it.
# Will create subdirectory rinfo and checkout develop branch or selected version/feature
# This script is dependent of running rinfo/rdl "fab target.$2 sysconf.install_server sysconf.config_server" first

rm -rf /tmp/install
mkdir /tmp/install /tmp/install
cd /tmp/install
git clone https://github.com/rinfo/lagrummet.se
cd lagrummet.se
if [ -z "$1" ]; then
	git checkout develop
else
	git checkout $1
fi

cd manage/fabfile/

echo "Enter sudo password: "
read pwd

fab -p $pwd target.$2 sysconf.install_server
fab -p $pwd target.$2 sysconf.config_server
fab -p $pwd target.$2 lagrummet.all

read -p "[press any key; to restart tomcat and apache on target server(s)]" -s -n1
fab -p $pwd target.$2 server.restart_tomcat
fab -p $pwd target.$2 server.restart_apache

