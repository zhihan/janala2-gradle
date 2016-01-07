#!/usr/bin/env bash

#
# Run catg to invoke a virtual method on a class. 
#
# Example 
#   ./run_concolic.sh examples.IntExample1 test1

pushd `dirname $0` > /dev/null
SCRIPT_DIR=`pwd`
popd > /dev/null

ROOT=`dirname $SCRIPT_DIR`
source "$SCRIPT_DIR/env.sh"

if (( "$#" == 1 )); then
  java -cp "$CLASSPATH:${ROOT}/build/classes/integration" \
    -javaagent:${ROOT}/lib/catg-dev.jar janala.utils.ClassRunner $@
elif (( "$#" == 2 )); then
  java -cp "$CLASSPATH:${ROOT}/build/classes/integration" \
    -javaagent:${ROOT}/lib/catg-dev.jar janala.utils.Runner $@
else
  echo "Wrong number of inputs"
  exit 1
fi
