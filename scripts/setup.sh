#!/bin/bash

# A convenient library to build the tool.
#
# Required: Gradle.

# Figure out script absolute path
pushd `dirname $0` > /dev/null
SCRIPT_DIR=`pwd`
popd > /dev/null

ROOT=`dirname $SCRIPT_DIR`

echo $ROOT
cd ${ROOT}

echo "Running Gradle to build the library"
gradle jar
cp -f build/libs/janala2-gradle-0.2.jar lib/catg-dev.jar

