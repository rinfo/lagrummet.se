#!/bin/bash
export GRAILS_VERSION=`sed '/^\#/d' application.properties | grep 'app.grails.version'  | tail -n 1 | cut -d "=" -f2- | sed 's/^[[:space:]]*//;s/[[:space:]]*$//'`
export GROOVY_VERSION=`sed '/^\#/d' application.properties | grep 'app.groovy.version'  | tail -n 1 | cut -d "=" -f2- | sed 's/^[[:space:]]*//;s/[[:space:]]*$//'`

export GROOVY_HOME=~/.gvm/groovy/$GROOVY_VERSION
export GRAILS_HOME=~/.gvm/grails/$GRAILS_VERSION

#grails -Djetty.host=0.0.0.0 run-app
$GRAILS_HOME/bin/grails run-app
