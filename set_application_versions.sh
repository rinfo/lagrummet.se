#!/bin/bash
#This script nedds to be invoked ". ./set_application_versions.sh" to work properly
export GRAILS_VERSION=`sed '/^\#/d' application.properties | grep 'app.grails.version'  | tail -n 1 | cut -d "=" -f2- | sed 's/^[[:space:]]*//;s/[[:space:]]*$//'`
export GROOVY_VERSION=`sed '/^\#/d' application.properties | grep 'app.groovy.version'  | tail -n 1 | cut -d "=" -f2- | sed 's/^[[:space:]]*//;s/[[:space:]]*$//'`

export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64
export GROOVY_HOME=~/.gvm/groovy/$GROOVY_VERSION
export GRAILS_HOME=~/.gvm/grails/$GRAILS_VERSION
export PATH=$JAVA_HOME/bin:$GROOVY_HOME/bin:$GRAILS_HOME/bin:$PATH

echo "*************** SETUP ******************"
echo "Values extracted from applications.properties"
echo "Grails version: $GRAILS_VERSION"
echo "Groovy version: $GROOVY_VERSION"
echo "Java home: $JAVA_HOME"
echo "GROOVY HOME: $GROOVY_HOME"
echo "GRAILS HOME: $GRAILS_HOME"
echo "PATH: $PATH"
echo "****************************************"

