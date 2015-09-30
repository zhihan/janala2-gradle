#!/bin/bash

#
# Run catg to invoke test on a class. The class must already
# be instrumented.
#
# Example 
#   ./run_concolic.sh examples.IntExample1 test1

pushd `dirname $0` > /dev/null
SCRIPT_DIR=`pwd`
popd > /dev/null

ROOT=`dirname $SCRIPT_DIR`
source "$SCRIPT_DIR/env.sh"

if [ "$#" -eq 1 ]; then
  java -cp "$CLASSPATH:${ROOT}/build/classes/integration:${ROOT}/script/instrumented" \
    janala.utils.ClassRunner $@
elif [ "$#" -eq 2 ]; then
  java -cp "$CLASSPATH:${ROOT}/build/classes/integration:${ROOT}/script/instrumented" \
    janala.utils.Runner $@
else
  echo "Wrong number of inputs"
  exit 1
fi
