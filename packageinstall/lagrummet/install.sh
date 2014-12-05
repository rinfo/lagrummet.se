#!/bin/bash

echo '------------- Install Lagrummet-www multi node'

apt-get update
source ./bootstrap.sh
source ./install_apache.sh
source ./install_tomcat.sh

source ./install_lagrummet_www.sh

source start_apache.sh
source start_tomcat.sh
