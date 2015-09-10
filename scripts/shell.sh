#!/bin/bash

SCRIPT_DIR=`dirname $0`
PWD=`pwd`
ROOT=`dirname $PWD`
source "$SCRIPT_DIR/env.sh"

groovysh -cp $CLASSPATH:.
