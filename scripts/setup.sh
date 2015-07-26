#!/bin/bash

# A convenient library to setup the tool.
#
# Required: SBT.

# Figure out script absolute path
pushd `dirname $0` > /dev/null
SCRIPT_DIR=`pwd`
popd > /dev/null

ROOT=`dirname $SCRIPT_DIR`

echo $ROOT
cd ${ROOT}

echo "Running SBT to build the library"
sbt package
cp -f target/scala-2.11/catg_2.11-0.0.jar lib/catg-dev.jar

