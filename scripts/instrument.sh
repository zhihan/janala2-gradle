#!/bin/bash

#
# Simple script to run catg to instrument classes, writes
# the instrumented classes and then run the file and print
# the instructions executed to the screen
#
# Example
#  ./instrument.sh MyClass

SCRIPT_DIR=`dirname $0`
PWD=`pwd`
ROOT=$PWD
source "$SCRIPT_DIR/env.sh"

java -cp $CLASSPATH:. -javaagent:${ROOT}/lib/catg-dev.jar $@

