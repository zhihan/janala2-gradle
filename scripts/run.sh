#!/bin/bash

#
# Simple script to run catg to instrument classes, writes
# the instrumented classes and then run the file and print
# the instructions executed to the screen
#

SCRIPT_DIR=`dirname $0`
PWD=`pwd`
ROOT=`dirname $PWD`
source "$SCRIPT_DIR/env.sh"

java -cp $CLASSPATH:.:"${ROOT}/build/classes/integration" -javaagent:${ROOT}/lib/catg-dev.jar $@

