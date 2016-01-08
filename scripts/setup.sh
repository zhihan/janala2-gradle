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

function download_jars() {
    mkdir -p lib
    cd lib
    wget http://central.maven.org/maven2/junit/junit/4.12/junit-4.12.jar
    wget http://central.maven.org/maven2/dk/brics/automaton/automaton/1.11-8/automaton-1.11-8.jar
    wget http://central.maven.org/maven2/org/ow2/asm/asm-all/5.0.4/asm-all-5.0.4.jar
}

if [[ ! -d "lib" ]]; then
    (download_jars)
fi

echo "Running Gradle to build the library"
gradle jar
cp -f build/libs/janala2-gradle-0.2.jar lib/catg-dev.jar

